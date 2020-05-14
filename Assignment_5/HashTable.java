//HashTable.java


import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.lang.Math;

/*
 * Header:
 * Mitchell Newell (UCID:30006529)
 * CPSC 319
 * Assignment 5
 * Instructor: Dr.Leonard Manzara
 * Program receives a text file, puts each word into a hash function which then will create an address for a word and the
 * place said word into the hash table.
 * The program will then keep track of the the average number of reads per record, the load factor, the hashing efficiency,
 *  and the size of the longest chain when searching
 * These results of the program are then written into the user specified text file.
 * Since: April 9th, 2017
 * 
 * Note: The following code was partially adapted from lecture notes/pseudo code given in lecture L01.
 */

public class HashTable {
	
	/**
	 * This is the array that contains all keys (this array is essentially the Hash table)
	 */
	private String[] data;
	/**
	 * The data members that represent the capacity of the hash table, the number of insertions done, and the number of collisions encountered.
	 */
	private int size, insertions, collisions;
	
	/**
	 * This method is the constructor for the hash table, which initializes the table.
	 */
	public HashTable () {
		data = new String[61];
		size = 61;
		insertions = 0;
	}
	
	/**
	 * This method receives the input arguments (input and output file names),
	 * checks to make sure the correct number of arguments was received
	 * initializes the file and print writers
	 * goes line by line from the input file and sends each line to the insert method,
	 * and writes the programs statistics to the output file
	 * @param args is the string of command line arguments
	 */
	public static void main (String[] args) {
		if (args.length != 2) {
			System.out.println("\nInvalid command line arguments:\n"
			+ "   Incorrect number of arguments: <input> <output>");
			System.exit(-1);
		}
		
		String input = args[0];
		String output = args[1];
		
		if (input.equalsIgnoreCase(output)) {
			System.out.println("\nInvalid command line arguments:\n"
			+ "    Please provide different filenames for arguments: <input> <output>");
			System.exit(-1);
		}
	
		
		Scanner inFile = null;
		PrintWriter outFile = null;
		
		try {
			inFile = new Scanner(new FileReader(input));
		}
		catch (IOException e) {
			System.out.println("\nInvalid command line argument:\n"
			+ "    " + input +" could not be found.");
			System.exit(-1);
		}
		
		try {
			outFile = new PrintWriter(output);
		}
		catch (IOException e) {
			System.out.println("\nInvalid command line argument:\n"
			+ "    " + output +" could not be written to.");
			System.exit(-1);
		}
		
				
		HashTable h = new HashTable();
		while (inFile.hasNextLine()) {
			h.insert(inFile.nextLine());
		}
		
		try {
			inFile = new Scanner(new FileReader(input));
		}
		catch (IOException e) {
			System.out.println("\nInvalid command line argument:\n"
			+ "    " + input +" could not be found.");
			System.exit(-1);
		}
		
		int longestRead = 0;
		int reads = 0;
		int records;
		for (records = 0; inFile.hasNextLine(); records++) {
			int[] results = h.search(inFile.nextLine());
			reads += results[1];
			if (results[1] > longestRead) {
				longestRead = results[1];
			}
		}
		
		DecimalFormat df = new DecimalFormat("#0.00");
		double averageReads = ((double) reads / records);
		outFile.println("Average reads per record: " + df.format(averageReads));
		double loadFactor = h.loadFactor();
		outFile.println("Load factor: " + df.format(loadFactor));
		double hashingEff = h.hashingEff();
		outFile.println("Hashing efficiency: " + df.format(hashingEff));
		outFile.println("Longest searching chain: " + longestRead);
		
		outFile.close();
		inFile.close();
	}
	
	/**
	 * Receives the word from the input file and calls the hash method,
	 * checks if the hash table is over 81% full (and calls expandRehash if it is),
	 * keeps track of the number of collisions and insertions,
	 * and ultimately inserts the word from the input file into the hash table.
	 * @param newElement is the String/word from the input file that is to be added to the hash table.
	 */
	public void insert (String newElement) {
		if (loadFactor() > 0.81) {
			expandandRehash();
		}
		insertions++;
		int i;
		long hashedValue = hash(newElement);
		for (i = 1; data[(int) (hashedValue % size)] != null; i++) {
			hashedValue++;
		}
		data[(int) (hashedValue % size)] = newElement;
		collisions += i;
	}
	
	/**
	 * Takes each string/word and generates a very large value 
	 * which will be altered (in the insert method) to be used as a index to the hash table.
	 * @param el is the String element/word from the input file.
	 * @return a very large value of type long (which is altered (in the insert method) to be used as a index to the hash table).
	 * 
	 * Code adapted from:
	 * http://www.java2s.com/Code/Java/Development-Class/FNVHash.htm
	 * http://will.thimbleby.net/algorithms/doku.php?id=fowler_noll_vo_hash_function
	 */
	public long hash (String el){     
	
		long fnvPrime = 1714078363097L;
		long h = 55762453L;
    
		for (int i=0;  i<el.length(); i++) {
			
			long charCode = el.charAt(i);
       
			long firstOctet = (charCode & 0xFF);
			h = h^ firstOctet;
			
			h= (h* fnvPrime) | 0;
        
			long secondOctet = (charCode >> 107);
			h = h^ secondOctet;
			h = (h * fnvPrime) | 0;
		}
		
	h = (long) Math.abs(h);
    return h;
}
	
	/**
	 * This method expands the hash table so that it is 70% full,
	 * and then calls the hash method (to re-hash all words), 
	 * and re-inserts all words into the newly sized hash table.
	 */
	public void expandandRehash () {
		double temp = ((double) insertions + 1) / .7;
		int newSize = ((int) temp);
		int newCollisions = 0;
		String[] newData = new String[newSize];
		
		for (int i = 0; i < size; i++) {
			if (data[i] != null) {
				int j;
				long hashedValue = hash(data[i]);
				for (j = 1; newData[(int) (hashedValue % newSize)] != null; j++) {
					hashedValue++;
				}
				newData[(int) (hashedValue % newSize)] = data[i];
				newCollisions += j;
			}
		}
		collisions = newCollisions;
		size = newSize;
		data = newData;
	}
	
	/**
	 * This method searches the hash table for a word,
	 * by hashing the incoming word (therefore calculating which index the word should be held at),
	 * checking the index of where the word should be at, and incrementing through the hash table until the word is found.
	 * @param key is the word that is being searched for within the hash table.
	 * @return an array containing where the word should have been stored, and the number of indices between where the word is stored 
	 * and where it should have been stored (according to the hash function).
	 */
		public int[] search (String key) {
		int[] indexRead = new int[2];
		int i;
		long hashedValue = hash(key);
		for (i = 1; data[(int) (hashedValue % size)] != null && !data[(int) (hashedValue % size)].equals(key); i++) {
			hashedValue++;
		}
		if (data[(int) (hashedValue % size)] == null) {
			indexRead[0] = -1;
		} else {
			indexRead[0] = (int) (hashedValue % size);
		}
		indexRead[1] = i;
		return indexRead;
	}

		/**
		 * calculates and returns the load factor of the program.
		 * @return the load factor of the program which was calculated.
		 */
	public double loadFactor () {
		return ((double) insertions / (double) size);
	}
	
	/**
	 * calculates and returns the hashing efficiency of the program.
	 * @return the hashing efficiency of the program which was calculated.
	 */
	public double hashingEff () {
		return ((double) insertions / (double) collisions);
	}
}
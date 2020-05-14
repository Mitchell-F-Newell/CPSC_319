/*
 * Header:
 * Mitchell Newell (UCID:30006529)
 * CPSC 319
 * Assignment 2
 * Instructor: Dr.Leonard Manzara
 * Program recieves a text file, transfers all words in to an array of strings and the determines which strings/words are anagrams of one another. 
 * The program then transfers all anagrams and words into an array of linked lists and alphabetically organizes the arrays and linked lists.
 * THe results of the program are then written into a specified text file.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Anagrams {

	static LinkedList [] newLinkedList;
	static String [] string;
	static boolean [] teller;
	
	public static void main(String[] args) {
		
		long start = System.nanoTime();
		
		if(args.length != 2)
		{
			System.out.println("The number of inputs is not correct... EXITING");
			System.exit(0);
		}
		
		newLinkedList = new LinkedList[0];
		string = new String[0];

		Anagrams newAnagrams = new Anagrams();
		
		System.out.println("Starting to to Read the assigned text file..");
		newAnagrams.OpenInput(args[0]);
		long current=System.nanoTime();
		System.out.println("The input text file has been read.");
		System.out.println("Current Elapsed Time: " + (double)(current - start) / 1E9);
		System.out.println();
		
		teller = new boolean[string.length];
		for(int i = 0; i < teller.length; i++)
			teller[i] = true;
		
		System.out.println("Placing the array of strings into an array of Linked Lists...");
		newAnagrams.fillLinkedLists();
		current = System.nanoTime();
		System.out.println("The array has successfully been placed into an array of Linked Lists.");
		System.out.println("Current Elapsed Time: " + (double)(current - start) / 1E9);
		System.out.println();
		
		System.out.println("Commencing sorting using Insertion Sort...");
		newAnagrams.insertSort();
		current=System.nanoTime();
		System.out.println("Insertion sort completed.");
		System.out.println("Current Elapsed Time: " + (double)(current - start) / 1E9);
		System.out.println();
		
		System.out.println("Commencing sorting using Quick Sort...");
		newAnagrams.quickSort(0, newLinkedList.length-1);
		current = System.nanoTime();
		System.out.println("Quick Sort completed."); 
		System.out.println("Current Elapsed Time: " + (double)(current - start) / 1E9);
		System.out.println();
		
		long end = System.nanoTime();
		
		System.out.println("Writing results to the assigned text file...");
		newAnagrams.openWrite(args[1]+".txt", start, end);
		current = System.nanoTime();
		System.out.println("Program has succesfully finished running.");
		System.out.println("Total elapsed time was (Including writing into text file): " + (double)(current - start) / 1E9);
	}
	/**
	 * The following code was attainted from the tutorial sessions (T01) 
	 * TA: Aniruddha Chattoraj
	 */
	private void OpenInput(String fileName)	 //The following code reads the assigned text file and then calls passes each line of the input file to addToArray.
	{
		try {
			
			FileReader fileReader = new FileReader(fileName);
			String strLine;
			BufferedReader br = new BufferedReader(fileReader);  
			while((strLine = br.readLine()) != null) {
				addToArray(strLine);
			}   
			br.close();         
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");                
		}
		
		catch(IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");                  
		}
	}
	
	private void addToArray(LinkedList addative)	//This function adds the individual linkedlists to an array of linkedlists.
	{
		int newSize = newLinkedList.length + 1;
		LinkedList [] temp = newLinkedList.clone();
		newLinkedList = new LinkedList[newSize];
		for(int i = 0; i < temp.length ; i++)
		{
			newLinkedList[i] = temp[i];
		}
		newLinkedList[newSize-1] = addative;
	}
	
	private void addToArray(String addative)	//This function recieves each line of the input text file and places this data into an array of strings.
	{
		int newSize = string.length+1;
		String [] temp = string.clone();
		string = new String[newSize];
		for(int i = 0; i < temp.length; i++)
		{
			string[i] = temp[i];
		}
		string[newSize-1] = addative;
	
	}
	
	private void fillLinkedLists()				//This funciton places the data from the array of Stings and places the data into a LinkeList.
	{
		for(int j = 0; j < string.length; j++)
		{
			if(teller[j] == true)
			{	
				LinkedList store = new LinkedList();
				addToArray(store);
				store.add(string[j]);
				teller[j] = false;
				
				for(int i = 0; i < string.length; i++)
				{
					if(teller[i] == true && anagram(string[j], string[i]))
					{
						store.add(string[i]);
						teller[i] = false;
					}
				}
			}
		}
	}
	
	/**
	 * The following code was based off of the code on 
	 * http://codereview.stackexchange.com/questions/116918/determining-if-two-strings-are-anagrams-of-each-other
	 */
	private boolean anagram(String first, String second)	//The following funciton looks at the array of Strings and checks if any elements 
															//Of the array are anagrams of one another. It returns true if anagram and false otherwise.
	{
		if(first.length() != second.length())
			return false;
		
		char [] src1 = new char[first.length()+1];
		char [] src2 = new char[first.length()+1];
		String store1 = "";
		String store2 = "";
		for(int i = 0; i < first.length(); i++)
		{
			src1[i] = first.charAt(i);
			src2[i] = second.charAt(i);
		}
		
		insertSort(src1);
		insertSort(src2);

		for(char l:src1)
			store1 += l;
		for(char l:src2)
			store2 += l;
	
		if(store1.equals(store2))
			return true;

		return false;
	}
	
	/**
	 * The following code was heavily based of off the code in the course textbook.
	 * From, Drozdeck p. 502
	 */
	private void insertSort(char[] data)		//Algorithm for insertion sorting	
	{
		for(int i = 1, j; i < data.length; i++)
		{
			char tmp = data[i];
			for(j = i; j > 0 && tmp < data[j-1]; j--)
				data[j] = data[j-1];
			data[j] = tmp;
		}
	}
	
	/**
	 * The following code was heavily based of off the code in the course textbook.
	 * From, Drozdeck p. 502
	 */
	private void insertSort()							//This function sorts each linked list in alphabetical order usings insertion sort algorithm.
	{	
		for(LinkedList l: newLinkedList)
			for(int i = 1, j; i < l.size()+1; i++)
			{
				String tmp = l.get(i);
				int counter = 0;
				
				for(j = i; j > 0 && tmp.compareToIgnoreCase(l.get(j-1)) < 0; j--)
				{
					counter++;
				}
				
				l.remove(i);
				l.add(tmp, j);
			}
	}
	
	/**
	 * The following code was heavily based of off the code in the course textbook.
	 * From, Drozdeck p. 521
	 */
	private void quickSort(int low, int high) {			//This function sorts the array of linked lists into alphabetical order using the quicksort algorithm.
        int i = low, j = high;
        String pivot = newLinkedList[low + (high-low)/2].get(0);
        while (i <= j) {
            while (newLinkedList[i].get(0).compareToIgnoreCase(pivot) < 0) {
                    i++;
            }
            while (newLinkedList[j].get(0).compareToIgnoreCase(pivot) > 0) {
                    j--;
            }
            if (i <= j) {
                    exchange(i, j);
                    i++;
                    j--;
            }
        }
        if (low < j)
                quickSort(low, j);
        if (i < high)
                quickSort(i, high);
	}
	
	private void exchange(int i, int j) {			//This function swaps the values of indecies i and j.
        LinkedList temp = newLinkedList[i];
        newLinkedList[i] = newLinkedList[j];
        newLinkedList[j] = temp;
	}
	
	/**
	 * The following code was based off of code from the tutorial sessions (T01) 
	 * TA: Aniruddha Chattoraj
	 */
private void openWrite(String s,long start, long end) {		//This function writes the results into the assigned text file.
		
		PrintWriter outputFile = null;
		try {
			outputFile = new PrintWriter(s);
		} 
		catch (FileNotFoundException e) {
			System.out.println("File could not be created...EXITING");
			System.exit(0);
		}
		
		outputFile.println("It took, " + (double) ((end - start) / 1E9) + " seconds to find and sort the anagrams.");		
		outputFile.println();
		outputFile.println("Here is the sorted array: ");
		
		for(LinkedList l:newLinkedList)
		outputFile.println(l.toString());
		
	    outputFile.close();	
	}
	
/**
 * The following code was heavily based off of code attained from the tutorial sessions (T01) 
 * As well as heavily based off of code and information given in class.
 * Professor: Dr.Leonard Manzara
 * TA: Aniruddha Chattoraj
 */
	public class LinkedList {	//LinkedList Class which will initialize and do almost all LinkedList operations
		
		private Node head;
		private int listCount;
		
		public LinkedList()		//Creates a linked list with no nodes.
		{
			head = new Node(null);
			listCount = -1;
		}
		public class Node					//Node class which initializes and sets nodes of the linked list.
		{
			Node next;
			String data;
			
			public Node(String Data)	//Sets the tail Node.
			{
				next = null;
				data = Data;
			}
			
			public Node(String Data, Node Next)	//Initializes/Sets Nodes.
			{
				next = Next;
				data = Data;
			}
			
			public String getData()					//Returns data of the node.
			{
				return data;
			}
			
			public void setData(String Data)		//Sets the data in the node.
			{
				data = Data;
			}
			
			public Node getNext()					//Returns the next node.
			{
				return next;
			}
			
			public void setNext(Node Next)			//Sets the next node.
			{
				next = Next;
			}
		}
		
		public void add(String data)				//Recieves data and will place it within the node.
		{
			Node temp = new Node(data);
			Node current = head;
			while(current.getNext() != null)
			{
				current = current.getNext();
			}
			current.setNext(temp);
			listCount++;
		}
		
		public void add(String data, int index)		//Recieves data and an index and will create a node with the 
													//corresponding data at the corresponding index location within the Linked List.
		{
			Node temp = new Node(data);
			Node current = head;
			for(int i = 0; i < index && current.getNext() != null; i++)
			{
				current = current.getNext();
			}
			temp.setNext(current.getNext());
			current.setNext(temp);
			listCount++;
		}
		
		public String get(int index)		//Will recieve an index return the data at the corresponding index.
		{
			if(index < 0)
				return null;
			
			Node current = head.getNext();
			for(int i = 0; i < index; i++)
			{
				if(current.getNext() == null)
					return null;
				
				current = current.getNext();
			}
			return current.getData();
		}
		
		public boolean remove(int index)	//Will recieve an index and remove the node at the corresponding index.
		{
			if(index < 0 || index > size())
				return false;
			
			Node current = head;
			for(int i = 0; i < index; i++)
			{
				if(current.getNext() == null)
					return false;
				
				current = current.getNext();
			}
			current.setNext(current.getNext().getNext());
			listCount--; 
			return true;
		}
		
		public int size()			//Returns the number of nodes/elements within the linked list.
		{
			return listCount;
		}
		@Override
		public String toString()	//Adds [] around each string within the linked list for a cleaner easier look.
		{
			Node current = head.getNext();
			String output = "";
			while(current != null)
			{
				output += "[" + current.getData() + "]";
				current = current.getNext();
			}
			return output;
		}
		
	}
}



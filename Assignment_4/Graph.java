import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/*
 * Header:
 * Mitchell Newell (UCID:30006529)
 * CPSC 319
 * Assignment 4
 * Instructor: Dr.Leonard Manzara
 * Program recieves a text file, and transfers all the data in the file into a graph. 
 * The program then recieves an input query file, and finds the path between two vertices on the graph using various traversals.
 * THe results of the program are then written into three user specified text files.
 * Since: March 30, 2017
 */

//This class contains the graph object.
public class Graph {
	
	//the adjacent list array for the graph
	private Node[] adjList;
	
	//The number of rows within the graph.
	private int length;
	
	// GraphB constructor which initializes the length and creates the adjacent list array of size length.
	public Graph (int length) {
		adjList = new Node[length];
		this.length = length;
	}
	
	//This method does the error checking for the command line arguments as well as the files.
	//Returns true if no error is found and false ohterwise.
	public static boolean fileErr(String[] args, String input, String query, String output1, String output2, String output3)
	{	
	
	//Checks if the commandline recieved the 5 required inputs.
	//Closes the program if this requirement is not met.
		if (args.length != 5) {
			System.out.println("\nInvalid command prompt inputs:\n"
			+ "    Program must be run with 4 arguments: <.txt> <queryinput.txt> <output1.txt> <output2.txt> <output3.txt>");
			System.exit(-1);
		}
		
		//Is a string that contains the input files name and splits it into two for error checking.
		String [] split0 = input.split(Pattern.quote("."));
	
		//Checks to make sure that the input file is input correctly.
		// returns false if the input is not of the correct format.
		if (split0.length != 2 || split0[0].equals("") || !split0[1].equals("txt"))
		{
			System.out.println(" \n The input file was not input into the command line correctly!");
			return false;
		}

		
		//Is a string that contains the queryinput files name and splits it into two for error checking.
		String [] split1 = query.split(Pattern.quote("."));
	
		//Checks to make sure that the queryinput file is input correctly.
		// returns false if the input is not of the correct format.
		if (split1.length != 2 || split1[0].equals("") || !split1[1].equals("txt"))
		{
			System.out.println(" \n The input query file was not input into the command line correctly!");
			return false;
		}
	
		//Is a string that contains the outFile1 files name and splits it into two for error checking.
		String [] split2 = output1.split(Pattern.quote("."));
	
		//Checks to make sure that the queryinput file is input correctly.
		// returns false if the outFile1 is not of the correct format.
		if (split2.length != 2 || split2[0].equals("") || !split2[1].equals("txt"))
		{
			System.out.println(" \n The first output file was not input into the command line correctly!");
			return false;
		}
		
		//Is a string that contains the outFile2 files name and splits it into two for error checking.
		String [] split3 = output2.split(Pattern.quote("."));
	
		//Checks to make sure that the outFile2 file is input correctly.
		// returns false if the outFile2 is not of the correct format.
		if (split3.length != 2 || split3[0].equals("") || !split3[1].equals("txt"))
		{
			System.out.println(" \n The second output file was not input into the command line correctly!");
			return false;
		}
	
		//Is a string that contains the outFile23 files name and splits it into two for error checking.
		String [] split4 = output3.split(Pattern.quote("."));
	
		//Checks to make sure that the outFile3 file is input correctly.
		// returns false if the outFile3 is not of the correct format.
		if (split4.length != 2 || split4[0].equals("") || !split4[1].equals("txt"))
		{
			System.out.println(" \n The third output file was not input into the command line correctly!");
			return false;
		}

		//The following statements check to make sure that the files do not contain the same name.
		//Program will exit if it finds that two inputs for the files have the same name.
		if (input.equalsIgnoreCase(query) || input.equalsIgnoreCase(output1) || input.equalsIgnoreCase(output2)) {
			System.out.println("\nInvalid command line arguments:\n"
			+ "    Please provide different filenames for arguments: <input> <query> <output1> <output2> <output3>");
			return false;
		}
		if (query.equalsIgnoreCase(output1) || query.equalsIgnoreCase(output2) || output1.equalsIgnoreCase(output2)) {
			System.out.println("\nInvalid command line arguments:\n"
			+ "    Please provide different filenames for arguments: <input> <query> <output1> <output2> <output3>");
			return false;
		}
		
		if (input.equalsIgnoreCase(output3) || query.equalsIgnoreCase(output3) || output1.equalsIgnoreCase(output3) || output2.equalsIgnoreCase(output3)) {
			System.out.println("\nInvalid command line arguments:\n"
			+ "    Please provide different filenames for arguments: <input> <query> <output1> <output2> <output3>");
			return false;
			
		} else {
				
			return true;
			
			}
		}
	
	// adjacency list nodes are added based on the data contained within parameter data and to the row specified by parameter row .
	public void addList (String data, int row) {
		String[] split = data.split("\\s+");
		int i;
		for (i = 0; i < length; i++){
			if(!split[i].equals("0")){
				adjList[row] = new Node(null, i, Integer.parseInt(split[i]));
				break;
			}
		}
		i++;
		Node current = adjList[row];
		while (i < length){
			if (!split[i].equals("0")){
				current.setNext(new Node(null, i, Integer.parseInt(split[i])));
				current = current.getNext();
			}
				i++;
		}
	}
	//The method that starts a depth-first traversal (which was implmented recursively) and outputs the results to the outFile1.
	// The code was adapted from material covered in lectures and tutorials (T01).
	//The code was also adapted from http://www.geeksforgeeks.org/depth-first-traversal-for-a-graph/
	// TA: Aniruddha Chattoraj
	public void depthFirst (int start, int end, PrintWriter outFile1) {
		boolean[] visited = new boolean[length];
		String result = depthFirstUtil(start, end, visited);
		if (result == null) {
			outFile1.println(start + ", -1, " + end);
		} else {
			outFile1.println(result);
		}
	}
	
	//The recursive method that actually does the depth-first traversal and returns the path to the depthFirst method).
	// The code was adapted from material covered in lectures and tutorials (T01).
	//The code was also adapted from http://www.geeksforgeeks.org/depth-first-traversal-for-a-graph/
	// TA: Aniruddha Chattoraj
	public String depthFirstUtil (int v, int end, boolean[] visited) {
		if (v == end) {
			return String.valueOf(v);
		}
		visited[v] = true;
		Node current = adjList[v];
		while(current != null) {
			if (!visited[current.getData()]) {
					String temp = depthFirstUtil (current.getData(), end, visited);
					if (temp != null) {
						String temp2 = String.valueOf(v);
						temp2 += (", " + temp);
						return temp2;
					}
				}
			current = current.getNext();
		}
		return null;
	}
	
	//The method that does the breadth-first traversal and outputs the result to outFile2.
	// The code was adapted from material covered in lectures and tutorials (T01).
	//The code was also adapted from http://www.geeksforgeeks.org/breadth-first-traversal-for-a-graph/
	// TA: Aniruddha Chattoraj
	public void breadthFirst (int start, int end, PrintWriter outFile2) {
		boolean[] visited = new boolean[length];
		int[] previous = new int[length];
		for (int i = 0; i < length; i++) {
			previous[i] = -1;
		}
		Queue queue = new Queue();
		visited[start] = true;
		queue.enqueue(start);
		while (!queue.isEmpty()) {
			int location = queue.dequeue();
			if (location == end) {
				break;
			}
			Node current = adjList[location];
			while(current != null) {
				if (!visited[current.getData()]) {
						visited[current.getData()] = true;
						previous[current.getData()] = location;
						queue.enqueue(current.getData());
					}
				current = current.getNext();
			}
		}
		if (previous[end] != -1) {
			int temp = end;
			String st = String.valueOf(end);
			while (previous[temp] != -1) {
				st = String.valueOf(previous[temp]) + ", " + st;
				temp = previous[temp];
			}
			outFile2.println(st);
		} else {
			outFile2.println(String.valueOf(start) + ", -1, " + String.valueOf(end));
		}
	}
	
	//The method that is a bonus part of the assignment that uses Dijkstra's formula and outputs the result to outFile3.
	// The code was adapted from material covered in lectures and tutorials (T01).
	//The code was also adapted from http://www.geeksforgeeks.org/greedy-algorithms-set-6-dijkstras-shortest-path-algorithm/
	// TA: Aniruddha Chattoraj
	public void dijkstra (int start, int end, PrintWriter outFile3) {
		boolean[] visited = new boolean[length];
		int[] previous = new int[length];
		for (int i = 0; i < length; i++) {
			previous[i] = -1;
		}
		int[] distance = new int[length];
		for (int i = 0; i < length; i++) {
			distance[i] = 2147483647;
		}
		distance[start] = 0;
		while (true) {
			boolean allVisited = true;
			for (int i = 0; i < length; i++) {
				if (!visited[i]) {
					allVisited = false;
					break;
				}
			}
			if (allVisited == true) {
				break;
			}
			int v = -1;
			for (int i = 0; i < length; i++) {
				if (!visited[i]) {
					if (v == -1 || distance[i] < distance[v]) {
						v = i;
					}
				}
			}
			visited[v] = true;
			Node current = adjList[v];
			while(current != null) {
				if (!visited[current.getData()]) {
						int edgeWeight = current.getData();
						if (distance[current.getData()] > distance[v] + edgeWeight) {
							distance[current.getData()] = distance[v] + edgeWeight;
							previous[current.getData()] = v;
						}
					}
				current = current.getNext();
			}
		}
		
		if (previous[end] != -1) {
			int temp = end;
			String st = String.valueOf(end);
			while (previous[temp] != -1) {
				st = String.valueOf(previous[temp]) + ", " + st;
				temp = previous[temp];
			}
			outFile3.println(st);
		} else {
			outFile3.println(String.valueOf(start) + ", -1, " + String.valueOf(end));
		}
	}
		
	// This method calls the various methods to produce a graph from an input file and complete the various traversals.
	// The traversals are done according the queryinput file and the results are put into the various outputfiles.
	public static void main (String[] args) {
	
		// Sets the varoius command line arguments to Strings with more appropriate names.
		String input = args[0];
		String query = args[1];
		String output1 = args[2];
		String output2 = args[3];
		String output3 = args[4];
		
		// Initializes the input and output files;
		Scanner inFile1 = null;
		Scanner inFile2 = null;
		PrintWriter outFile1 = null;		
		PrintWriter outFile2 = null;
		PrintWriter outFile3 = null;
		
		
		if(fileErr(args, input, query, output1, output2, output3))
		{
			
			// Sets up the input and output files.
			try {
				inFile1 = new Scanner(new FileReader(input));
			} catch (IOException e) {
				System.out.println("\nInvalid command line argument:\n"
				+ "    " + input +" does not exist.");
					System.exit(-1);
			}
			try {
				inFile2 = new Scanner(new FileReader(query));
			} catch (IOException e) {
				System.out.println("\nInvalid command line argument:\n"
				+ "    " + query +" does not exist.");
					System.exit(-1);
			}
			try {
				outFile1 = new PrintWriter(output1);
			} catch (IOException e) {
				System.out.println("\nInvalid command line argument:\n"
				+ "    " + output1 +" cannot be written to.");
				System.exit(-1);
			}
			try {
				outFile2 = new PrintWriter(output2);
			} catch (IOException e) {
				System.out.println("\nInvalid command line argument:\n"
				+ "    " + output2 +" cannot be written to.");
					System.exit(-1);
			}
			try {
				outFile3 = new PrintWriter(output3);
			} catch (IOException e) {
				System.out.println("\nInvalid command line argument:\n"
				+ "    " + output3 +" cannot be written to.");
					System.exit(-1);
				}
				
		// Initialize a new Graph object.
		Graph g = null;		
		
		// Set the Graph size and adds the data to the graph.
		String temp = inFile1.nextLine();
		String[] sizeTemp = temp.split("\\s+");
		g = new Graph(sizeTemp.length);
		g.addList(temp, 0);
		for (int i = 1; inFile1.hasNextLine(); i++) {
			g.addList(inFile1.nextLine(), i);
		}
		
		// Uses the query file (inFile2) to complete depth first, breadth first, and dijkstra's traversal.
		while (inFile2.hasNextLine()) {
			temp = inFile2.nextLine();
			sizeTemp = temp.split("\\s+");
			g.depthFirst(Integer.parseInt(sizeTemp[0]), Integer.parseInt(sizeTemp[1]), outFile1);
			g.breadthFirst(Integer.parseInt(sizeTemp[0]), Integer.parseInt(sizeTemp[1]), outFile2);
			g.dijkstra(Integer.parseInt(sizeTemp[0]), Integer.parseInt(sizeTemp[1]), outFile3);
		}
		
		//Closes all open files.
		outFile1.close();
		outFile2.close();
		outFile3.close();
		inFile1.close();
		inFile2.close();
	}else{
	
	System.out.println(" The program is closing!");
	System.exit(-1);
	
		}
	
	}
	
	// This class contains the Node object.
	// The class code was adapted from material covered in lectures and tutorials (T01).
	// TA: Aniruddha Chattoraj
	public class Node {
		
		// The next node in the linked list.
		private Node next;
		
		// The row of the graph that the node points to.
		private int data;
		
		// The distance from the row the node is in to the row this node points to.
		private int dist;
		
		// Node constructor which sets and the next node, and the nodes data and dist.
		public Node (Node n, int d, int di) {
			next = n;
			data = d;
			dist = di;
		}
		
		// Sets the next node to the parameter n.
		public void setNext (Node n) {
			next = n;
		}
		
		// Sets the value of data to parameter d.
		public void setData (int d) {
			data = d;
		}
		
		// Sets the value of dist to parameter d.
		public void setDist (int d) {
			dist = d;
		}
		
		// Returns the next node.
		public Node getNext () {
			return next;
		}
		
		// Returns the data value.
		public int getData () {
			return data;
		}
		
		// Returns the dist value.
		public int getDist () {
			return dist;
		}
	}
	
	// This class contains the queue object.
	// The code for this class was adapted from material covered in lectures (T01).
	// TA: Aniruddha Chattoraj
	public class Queue {
		
		// The array to contain the data in the queue.
		private int[] data;
		
		// Pointers to the start and end of the queue.
		private int head, tail;
		
		//The constructor for the Queue which sets the queue equal to 1000 whilst also initializing the tail and head pointers.
		public Queue () {
			data = new int[1000];
			head = 0;
			tail = 0;
		}
		
		//Checks if the queue is full (returns true if queue if full and false otherwise).
		public boolean isFull () {
			if (tail + 1 % 1000 == head) {
				return true;
			}
			return false;
		}
		
		//Checks if the queue is empty (returns true if the queue is empty, and false otherwise).
		public boolean isEmpty () {
			if (tail == head) {
				return true;
			}
			return false;
		}
		
		//adds a new element n to the queue if it is not full.
		public void enqueue (int n) {
			if (isFull()) {
				System.out.println("Error in queue: The queue system for breadth first output can only contain 1000 elements.");
				System.exit(1);
			} else {
				data[tail] = n;
				tail = tail + 1 % 1000;
			}
		}
		
		//returns the object at the beggining of the queue and then removes it whilst going through the queue.
		public int dequeue () {
			int temp = 0;
			if (isEmpty()) {
				System.out.println("Error in queue: The queue system for breadth first output is already empty.");
				System.exit(1);
			} else {
				temp = data[head];
				head = head + 1 % 1000;
			}
			return temp;
		}
	}	
}

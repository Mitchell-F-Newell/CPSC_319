/*
 * Header:
 * Mitchell Newell (UCID:30006529)
 * CPSC 319
 * Assignment 3
 * Instructor: Dr.Leonard Manzara
 * Program receives a text file, transfers the data from the text file into an instruction the will either specify an insert of the specified data into a tree or a deletion of the specified data from the tree.
 * (the type of tree is user defined in the command line).
 * The program then prints out the Node data of the tree to two created text files, one text file for each tree traversal that is used.The two traversals are,
 *  1) using a depth-first in order traversal, and 2) using a breadth first traversal.
 * The two output files can then be accessed from the appropriate folder.
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *Class will create a new binary tree as well as complete various alterations to the tree.
 *(i.e. insert/remove a node from the tree or perform a traversal) 
 *
 */
public class BinarySearchTree {
	public Node root;
	int size;
	
	public BinarySearchTree(int studentNum, String lastName, String homeDep, 
			String program,int year){
		
		root = new Node();
		root.studentNum = studentNum;
		root.lastName = lastName;
		root.homeDep = homeDep;
		root.program = program;
		root.year = year;
		
	}
	/**
	 * The following method performs the reading of the text file and performs the task specified in the file to a Binary Tree.
	 * @param in is the String name of the input text file.
	 * @return returns a binary tree that has executed the instructions in the text file.
	 */
	 public static BinarySearchTree treeInput(String in){
		  
		 	String input = null;
			BinarySearchTree tree = null;
			int studentNum = 0;
			int year = 0;
			String homeDep = "";
			String lastName = "";
			String program = "";
			String operation = "";
			
			try{
				FileReader r = new FileReader(in);
				BufferedReader b = new BufferedReader(r);
				while ((input=b.readLine()) != null){
					operation = input.substring(0, 1);
					studentNum = Integer.parseInt(input.substring(1,8));
					lastName = input.substring(8, 33);
					program = input.substring(33, 37);
					homeDep = input.substring(37, 41);
					year = Integer.parseInt(input.substring(41));
					
					if(operation.compareToIgnoreCase("I") == 0){
						if(tree == null)
							tree = new BinarySearchTree(studentNum, lastName, homeDep, program, year);
						else
							tree.addNode(studentNum, lastName, homeDep, program, year);
						}
					if(operation.compareToIgnoreCase("D") == 0){
						if(tree != null)
							tree.removeNode(lastName);
							}
					}
						b.close();
					}catch (FileNotFoundException e){
						System.out.println("File could not be found: " +e.getMessage());
					}catch (IOException e){
						System.out.println("File reading error: " + e.getMessage());
					}
				return tree;
				}
	 
	 /**
	  * The following method performs the reading of the text file and performs the task specified in the file to an AVL Tree.
	  * @param in in the String name of the input text file.
	  * @return returns an AVL tree that has executed the instructions in the text file.
	  */
	 public static AVLTree avltreeInput(String in){
		  
		 	String input = null;
			AVLTree tree = new AVLTree();
			int studentNum = 0;
			int year = 0;
			String homeDep = "";
			String lastName = "";
			String program = "";
			String operation = "";
			
			try{
				FileReader r = new FileReader(in);
				BufferedReader b = new BufferedReader(r);
				while ((input=b.readLine()) != null){
					operation = input.substring(0, 1);
					studentNum = Integer.parseInt(input.substring(1,8));
					lastName = input.substring(8, 33);
					program = input.substring(33, 37);
					homeDep = input.substring(37, 41);
					year = Integer.parseInt(input.substring(41));
					
					if(operation.compareToIgnoreCase("I") == 0){
						tree.add(studentNum, lastName, homeDep, program, year);
					}
				}
						b.close();
					}catch (FileNotFoundException e){
						System.out.println("File could not be found: " +e.getMessage());
					}catch (IOException e){
						System.out.println("File reading error: " + e.getMessage());
					}
				return tree;
				}
	 /**
	  * The method recieves the node data and begins the insertion tasks. (will call an appropriate insertion method).
	  * -This code was adapted from information given in the Tutorial and Lecture. 
	  * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	  * 
	  * @param studentNum is the students I.D number.
	  * @param lastName is the students last name.
	  * @param homeDep is a four digit value that corresponds the the students home department.
	  * @param program is the program that the student is enlisted in.
	  * @param year is the year of study that the student is currently in.
	  */
	  public void addNode(int studentNum, String lastName, String homeDep,
	 
			String program, int year){
		
		if(lastName.compareToIgnoreCase(root.lastName) < 0){
			if(root.leftChild == null)
				addNode(studentNum, lastName, homeDep, program, year, root, false);
			else
				addNode(studentNum, lastName, homeDep, program, year, root.leftChild);
		}
		
		else if(lastName.compareToIgnoreCase(root.lastName) > 0){
			if(root.rightChild == null)
				addNode(studentNum, lastName, homeDep, program, year, root, true);
			else
				addNode(studentNum, lastName, homeDep, program, year, root.rightChild);
		}
		
	}
	  /**
	   * The method recieves the node data and inserts the new node with the new student data.
	   * -This code was adapted from information given in the Tutorial and Lecture. 
	   * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	   * @param studentNum is the students I.D number
	   * @param lastName is the students last name.
	   * @param homeDep is a four digit value that corresponds the the students home department.
	   * @param program is the program that the student is enlisted in.
	   * @param year year is the year of study that the student is currently in.
	   * @param n	Specifies a node where the data should be inserted.
	   * @param loca returns a true or false value (False is roots left child is null and true otherwise)
	   */
	
	public void addNode(int studentNum, String lastName, String homeDep,
				String program, int year, Node n, boolean loca){
		
		Node temp = new Node();
		temp.studentNum = studentNum;
		temp.lastName = lastName;
		temp.homeDep = homeDep;
		temp.program = program;
		temp.year = year;
		temp.parent = n;
		if(loca == false){
			n.leftChild = temp;
			temp.loc=false;
		}
		else{
			n.rightChild = temp;
			temp.loc = true;
		}
	}
	/**
	 *  * The method recieves the node data and begins the insertion tasks. (will call an appropriate insertion method).
	  * -This code was adapted from information given in the Tutorial and Lecture. 
	  * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	  * 
	  * @param studentNum is the students I.D number.
	  * @param lastName is the students last name.
	  * @param homeDep is a four digit value that corresponds the the students home department.
	  * @param program is the program that the student is enlisted in.
	  * @param year is the year of study that the student is currently in.
	  * @param n Specifies a node where the data should be inserted (or checked if it should be inserted there).
	 */
	public void addNode(int studentNum, String lastName, String homeDep,
			String program, int year, Node n){
		
		if(lastName.compareToIgnoreCase(n.lastName) < 0){
			if(n.leftChild == null)
				addNode(studentNum, lastName, homeDep, program, year, n, false);
			else
				addNode(studentNum, lastName, homeDep, program, year, n.leftChild);		
		}
		if(lastName.compareToIgnoreCase(n.lastName) > 0){
			if(n.rightChild == null)
				addNode(studentNum, lastName, homeDep, program, year, n, true);
			else
				addNode(studentNum, lastName, homeDep, program, year, n.rightChild);		
		}
	
	}
	/**
	 * This method begins the task of deleting a node from the tree.
	 * -This code was adapted from information given in the Tutorial and Lecture. 
	 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	 * @param lastName is the last name of the student.
	 */
	
	public void removeNode(String lastName){
		
	if (lastName.compareToIgnoreCase(root.lastName)==0)
		deleteNode(root);
	if (lastName.compareToIgnoreCase(root.lastName) < 0 && root.leftChild != null)
		removeNode(root.leftChild, lastName);
	if (lastName.compareToIgnoreCase(root.lastName) > 0 && root.rightChild != null)
		removeNode(root.rightChild,lastName);
	
	}
	
	/**
	 * This method will remove a node from the tree.
	 * -This code was adapted from information given in the Tutorial and Lecture. 
	 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	 * @param n
	 * @param lastName
	 */
	public void removeNode(Node n, String lastName){
		
		if (lastName.compareToIgnoreCase(n.lastName) == 0)
			deleteNode(n);
		if (lastName.compareToIgnoreCase(n.lastName) < 0 && n.leftChild != null)
			removeNode(n.leftChild,lastName);
		if (lastName.compareToIgnoreCase(n.lastName) > 0 && n.rightChild != null)
			removeNode(n.rightChild,lastName);
	}
	
	/**
	 * This method will perform the correct cases/changes to the tree depending on the node that is deleted.
	 * @param n is the node that is being removed.
	 */
	public void deleteNode(Node n){
		
		if(n.leftChild == null && n.rightChild == null){
			if(n.loc == false)
				n.parent.leftChild = null;
			else
				n.parent.rightChild = null;
			n = null;
		}
		else if(n.leftChild != null && n.rightChild != null){
			Node temp = new Node();
			copyNode(n.rightChild, temp);
			
			while(temp.leftChild != null)
				copyNode(temp.leftChild, temp);
				replaceNode(n, temp);
		}
		else if(n.leftChild == null){
			if (n.loc == true){
				n.parent.rightChild = n.rightChild;
				n.rightChild.parent = n.parent;
			}
		else if (n.loc == false){
			n.parent.leftChild = n.rightChild;
			n.rightChild.parent = n.parent;
		}
		n=null;
		}
		else if(n.rightChild == null){
			if (n.loc == true){
				n.parent.rightChild = n.leftChild;
				n.leftChild.parent = n.parent;
			}
		else if (n.loc == false){
			n.parent.rightChild = n.leftChild;
			n.leftChild.parent = n.parent;
		}
		n=null;
			}
		}
	/**
	 * This method copies one to node to another.
	 * @param n is the node that you want copied.
	 * @param t is the node that you want to set to the other node.
	 */
	public void copyNode(Node n, Node t){
		
		t.studentNum = n.studentNum;
		t.lastName = n.lastName;
		t.homeDep = n.homeDep;
		t.program = n.program;
		t.year = n.year;
		t.parent = n.parent;
		t.leftChild = n.leftChild;
		t.rightChild = n.rightChild;
		t.loc = n.loc;
		
	}
	
	/**
	 * This method further corrects the tree depending on what case of a deletion has occured.
	 * -This code was adapted from information given in the Tutorial and Lecture. 
	 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	 * @param t is the temp node
	 * @param n is the node that is being replaced.
	 */
	public void replaceNode(Node t, Node n){
		if(n.leftChild != null && n.rightChild != null){
			n.rightChild.parent = n.leftChild;
			n.leftChild.rightChild = n.rightChild;
		}
		if(n.leftChild != null){
			n.parent.leftChild = n.leftChild;
			n.leftChild.parent = n.parent;
		}
		if(n.rightChild != null && n.leftChild == null){
			n.leftChild = n.rightChild;
		}
		if(n.loc == false){
			n.parent.leftChild = n.leftChild;
			n.leftChild.parent = n.parent;
		}
		t.studentNum = n.studentNum;
		t.lastName = n.lastName;
		t.homeDep = n.homeDep;
		t.program = n.program;
		t.year = n.year;
		n = null;
	}
	
	/**
	 * Begins the depth-first traversal through the tree while simultaneously printing to output file.
	 * -This code was adapted from information given in the Tutorial and Lecture. 
	 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	 * @param n node that should be printed out.
	 * @param file is the printWriter to the output file.
	 */
	public void depthFirst(Node n, PrintWriter file){
		if(n != null){
			depthFirst(n.leftChild, file);
			file.println("Student ID Number: " + n.studentNum);
			file.println("Last Name: " + n.lastName);
			file.println("Department: " + n.homeDep);
			file.println("Program: " + n.program);
			file.println("Year: " + n.year);
			file.println("\n");
			depthFirst(n.rightChild, file);
		}	
	}
	
	/**
	 * Begins the breadth-first traversal through the tree while simultaneously printing to output file.
	 * -This code was adapted from information given in the Tutorial and Lecture. 
	 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
	 * @param n node that should be printed out.
	 * @param outFile is printWriter to the outputfile.
	 */
	public void breadthFirst(Node n, String outFile){
		PrintWriter file = null;
		try {
			file = new PrintWriter(outFile);
		} catch (FileNotFoundException e) {
			System.out.println("File could not be created...EXITING");
			System.exit(0);
		}
		
			Queue queue = new Queue();
		    queue.clear();
		    queue.add(n);
		    while(!queue.isEmpty()){
		       Node node = queue.remove();
		   		file.println("Student ID Number: " + node.studentNum);
		   		file.println("Last Name: " + node.lastName);
		   		file.println("Department: " + node.homeDep);
		   		file.println("Program: " + node.program);
		   		file.println("Year: " + node.year);
		   		file.println("\n");
		        if(node.leftChild != null) 
		        	queue.add(node.leftChild);
		        if(node.rightChild != null) 
		        	queue.add(node.rightChild);
		    }
		    file.close();
		}
	
		public static void main(String[] args) {
			
			if(args[3].compareToIgnoreCase("AVLTree") == 0){
				AVLTree avltree = avltreeInput(args[0]);
				System.out.println("\n");
				System.out.println("Beginning depth-first Traversal for the AVL tree... ");
				PrintWriter file = null;
				try {
					file = new PrintWriter(args[1]);
				} 
				catch (FileNotFoundException e) {
					System.out.println("File could not be created...EXITING");
					System.exit(0);
				}
				avltree.depthFirst(avltree.root, file);
				System.out.println("The depth-first traversal output has successfully been placed into the correspponding textfile.");
				System.out.println("\n");
				file.close();
				System.out.println("Beginning breadth-first  for the AVL tree... ");
				avltree.breadthFirst(avltree.root, args[2]);
				System.out.println("The breadth-first traversal output has successfully been placed into the correspponding textfile.");
				System.out.println("\n");
				System.out.println("The program has finished running...");
			}
			else if(args[3].compareToIgnoreCase("BinarySearchTree") == 0){
			BinarySearchTree tree = treeInput(args[0]);
			System.out.println("\n");
			System.out.println("Beginning depth-first Traversal for the Binary Search Tree... ");
			PrintWriter file = null;
			try {
				file = new PrintWriter(args[1]);
			} 
			catch (FileNotFoundException e) {
				System.out.println("File could not be created...EXITING");
				System.exit(0);
			}
				tree.depthFirst(tree.root, file);
				System.out.println("The depth-first traversal output has successfully been placed into the correspponding textfile.");
				System.out.println("\n");
				file.close();
				System.out.println("Beginning breadth-first Traversal for the Binary Search Tree... ");
				tree.breadthFirst(tree.root, args[2]);
				System.out.println("The breadth-first traversal output has successfully been placed into the correspponding textfile.");
				System.out.println("\n");
				System.out.println("The program has finished running...");
		}
			if(args[3].compareToIgnoreCase("AVLTree") != 0 && args[3].compareToIgnoreCase("BinarySearchTree") != 0 && args[3] != null){
				System.out.println("\n");
				System.out.println("Please choose the program to run with a BinarySearchTree or a AVLTree!");
				System.out.println("The program is quitting...");
				System.exit(1);
			}
	}

	/**
	 * This class creates the node obejcts that store the student data.
	 *
	 */
	public class Node{
		int studentNum;
		String lastName;
		String homeDep;
		String program;
		int year;
		Node leftChild;
		Node rightChild;
		Node parent;
		boolean loc;
		
		public Node(){
			this.studentNum=0;
			this.lastName="";
			this.homeDep="";
			this.program="";
			this.year=0;
			this.leftChild=null;
			this.rightChild=null;
			this.parent=null;
		}
	}
	/**
	 * This class creates the queue objects that are used in the breadth-first traversal	
	 *
	 */
	private class Queue 
	{
		
		private Nde fst;
		private Nde lst;
		private int lng;
		
		private class Nde
		{
			private Node node;
			private Nde next;
		}
		
		public Queue()
		{
			fst = null;
			lst = null;
			lng = 0;
		}
		
		public boolean isEmpty()
		{
			if (lng == 0){
				return true;
			} else {
				return false;
			}
		}
		
		public void clear() 
		{
			fst = null;
			lst  = null;
			lng = 0;
		}
			
		public void add(Node n) 
		{
			Nde prev = lst;
			lst = new Nde();
			lst.node = n;
			lst.next = null;
			if (isEmpty()){
				fst = lst;
			}else {
				prev.next = lst;
			}
			lng++;
		}
		
		public Node remove() 
		{
			if (isEmpty())
			{
				System.out.println("No values available for removal");
				return null;
			}
			Node node = fst.node;
			fst = fst.next;
			lng--;
			if (isEmpty())
			{				
				lst = null; 
			}
			return node;
		}
	}
}

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class creates the AVL tree that stores the nodes of the students data.
 *
 */
public class AVLTree {
	public Node root;
	int size;
	
	public AVLTree(){
		root = null;
	}
	
/**
 * This method adds the student data to the AVL tree.
 * -This code was adapted from information given in the Tutorial and Lecture. 
 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
 * @param studentNum is the students I.D number.
 * @param lastName is the students last name.
 * @param homeDep is a four digit value that corresponds the the students home department.
 * @param program is the program that the student is enlisted in.
 * @param year is the year of study that the student is currently in.
 */
	  public void add(int studentNum, String lastName, String homeDep,
			String program, int year){
		  
		  Node current = root;
		  Node parent = null;
		  while(current != null){
			  parent = current;
			  if(lastName.compareToIgnoreCase(current.lastName) > 0){
				  current = current.rightChild;
			  }
			  else{
				  current = current.leftChild;
			  }
		  }
		  Node newNode = new Node();
		  newNode.studentNum = studentNum;
		  newNode.lastName = lastName;
		  newNode.homeDep = homeDep;
		  newNode.program = program;
		  newNode.year = year;
		  newNode.parent = parent;
		  newNode.balance = 0;
		  
		  if(root == null){
			  root = newNode;
		  }
		  else if(lastName.compareToIgnoreCase(parent.lastName) > 0){
			  parent.rightChild = newNode;
		  }
		  else{
			  parent.leftChild = newNode;
		  }
		  Node pivot = findPivot(newNode);
		  if(pivot == null){
			  case1(newNode);
		  }
		  else if(pivot.balance < 0 && lastName.compareToIgnoreCase(pivot.lastName) > 0){
			 
			  case2(newNode, pivot);
		  }
		  else if(pivot.balance > 0 && lastName.compareToIgnoreCase(pivot.lastName) < 0){
		
			  case2(newNode, pivot);
		  }
		  else{
			 
			  case3(newNode, pivot);
		  }
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
/**
 * Finds the pivot node for the rotation
 * @param current is the node/student data that is being inserted
 * @return it returns the found pivot node.
 */
	public Node findPivot(Node current){
		Node pivot = current.parent;
		while( pivot != null){
			if(pivot.balance != 0){
				return pivot;
				}
			pivot = pivot.parent;
			}
		return pivot;
		}
		
/**
 * This method will give the instruction of a case1 insertion, and update to to the tree.
 * -This code was adapted from information given in the Tutorial and Lecture. 
 * (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
 * @param current is the node that is currently being added to the tree.
 */
	public void case1(Node current){
		Node temp = current.parent;
		while(temp != null){
			if(current.lastName.compareToIgnoreCase(temp.lastName) > 0){
				
				temp.balance++;
			}
			else{
				
				temp.balance--;
			}
			temp = temp.parent;
		}
	}
	
/**	
* This method will give the instruction of a case2 insertion, and update to to the tree.
* -This code was adapted from information given in the Tutorial and Lecture. 
* (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
* @param current is the node that is currently being added to the tree.
* @param pivot is the pivot node for the rotation.
*/
	public void case2(Node current, Node pivot){
		Node temp = current.parent;
		while ( temp != pivot){
		if(current.lastName.compareToIgnoreCase(temp.lastName) > 0){
			
			temp.balance++;
		}
		else{
			
			temp.balance--;
		}
		temp = temp.parent;
		}
		if(current.lastName.compareToIgnoreCase(temp.lastName) > 0){
			
			temp.balance++;
		}
		else{
			
			temp.balance--;
			}
		}
	
/**
* This method will give the instruction of a case3 insertion, and update to to the tree.
* -This code was adapted from information given in the Tutorial and Lecture. 
* (T.A:Aniruddha Chattoraj, Instructor: Dr.Leonard Manzara)
* @param current is the node that is currently being added to the tree.
* @param pivot is the pivot node for the rotation
*/
	public void case3 (Node current, Node pivot){
		Node son = null;
		boolean outside = false;
	if(current.lastName.compareToIgnoreCase(pivot.lastName) > 0){
		son = pivot.rightChild;
	}
	else{
		son = pivot.leftChild;
	}
	if(pivot.balance > 0 && current.lastName.compareToIgnoreCase(son.lastName) > 0){
		outside = true;
			}
	else if(pivot.balance < 0 && current.lastName.compareToIgnoreCase(son.lastName) < 0){
		outside = true;
	}
	else{
		outside = false;
	}
	if(outside == true){
		rotate(pivot,son);
		pivot.balance = 0;
		Node temp = current.parent;
		while(temp != son){
			if(current.lastName.compareToIgnoreCase(temp.lastName) > 0){
				
				temp.balance++;
			}
			else{
				
				temp.balance--;
			}
			temp = temp.parent;
			
			}
		}
	
	else{
		Node grandson = null;
		if(current.lastName.compareToIgnoreCase(son.lastName) > 0){
			grandson = son.rightChild;
		}
		else{
			grandson = son.leftChild;
		}
		rotate (son, grandson);
		rotate (pivot, grandson);
		if(grandson != current){
			if(pivot.balance > 0){
				if(current.lastName.compareToIgnoreCase(grandson.lastName) > 0){
					pivot.balance= -1;
				}
				else{
					pivot.balance = 0;
					son.balance = 1;
				}
			}
			else{
				if(current.lastName.compareToIgnoreCase(grandson.lastName) < 0){
					pivot.balance = 1;
				}
				else{
					pivot.balance = 0;
					son.balance = -1;
				}
			}
			Node temp = current.parent;
			while(temp != son && temp != pivot){
				if(current.lastName.compareToIgnoreCase(temp.lastName) > 0){
					temp.balance++;
				}
				else{
					temp.balance--;
				}
				temp = temp.parent;
			}
		}
		else{
			pivot.balance = 0;
			son.balance = 0;
			}
		}
	}
	
/**
* This method will give perform a left of right rotation on the pivot node
* @param son is a son node of the pivot node.
*/
	public void rotate(Node pivot, Node son){
		if(pivot.parent == null){
			son.parent = null; 
			root = son;
		}
		else if(pivot.lastName.compareToIgnoreCase(pivot.parent.lastName) > 0){
			son.parent = pivot.parent;
			pivot.parent.rightChild = son;
		}
		else{
			son.parent = pivot.parent;
			pivot.parent.leftChild = son;
		}
		if(son.leftChild == null && son.rightChild == null){
			if(son.lastName.compareToIgnoreCase(pivot.lastName) > 0){
				son.leftChild = pivot;
				pivot.rightChild = null;
				pivot.parent = son;
			}
			else{
				son.rightChild = pivot;
				pivot.leftChild = null;
				pivot.parent = son;
			}
		}
		else if(pivot.balance > 0 || (pivot.balance ==0 && son.lastName.compareToIgnoreCase(pivot.lastName) > 0)){
			pivot.rightChild = son.leftChild;
			if(son.leftChild != null){
				pivot.rightChild.parent = pivot;
			}
			son.leftChild = pivot;
			pivot.parent = son;
			}
		else{
			pivot.leftChild = son.rightChild;
			if(son.rightChild != null){
				pivot.leftChild.parent = pivot;
			}
			son.rightChild = pivot;
			pivot.parent = son;
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
			int balance;
		
		public Node(){
			this.studentNum=0;
			this.lastName="";
			this.homeDep="";
			this.program="";
			this.year=0;
			this.leftChild=null;
			this.rightChild=null;
			this.parent=null;
			this.balance = 0;
		}
	}
		
/**
* This class creates the queue obejects that are used in the breadth-first traversal	
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

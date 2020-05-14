import java.io.IOException;
import java.io.*;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

public class Algorithms {
	private void bubblesort(int [] arr)	//The following code  for the bubblesort algorithm was taken from the in class notes (from lecture 7, January 23rd,2017) 
	{
		for(int i=0; i<arr.length-1; i++){
			for(int j=arr.length-1; j>i;j--){
				if(arr[j]<arr[j-1]){
					int temp=arr[j-1];
					arr[j-1]=arr[j];
					arr[j]=temp;
				}
			}
		}
	}

	private void insertionsort(int [] data)	//The following code for the insertionsort algorithm was taken from the in class notes (From January 25th,2017)
	{
		for(int i=1,j; i<data.length; i++){
			int tmp=data[i];
			for(j=i ; j>0 && tmp<data[j-1]; j--){
				data[j]=data[j-1];
			}
				data[j]=tmp;
		}
	}

	private void mergesort(int [] arr, int first, int last)	//The following code for the mergesort algorithm was taken from the in class notes (From January 25th,2017)
	{
		if(first<last){	//integer division
			int mid=(first+last)/2;
			mergesort(arr,first,mid);	//applying recursion to left sub-array
			mergesort(arr,mid+1,last);	//aplying recursion to the right sub-array
			merge(arr,first,mid,mid+1,last);	//Calls merge to merge the two sub-arrays together
			}	
	}

	private void merge(int [] arr,int first,int mid,int mid1, int last)	
	{
		int [] temp = new int[arr.length];
		for (int i = first; i <= last; i++)
		{
			temp[i] = arr[i];
		}
		
		int m = first;
		int n = mid1;
		int o = first;
		while (m <= mid && n <= last)
		{
			if (temp[m] <= temp[n])
			{
				arr[o] = temp[m];
				m++;
			}
			else
			{
				arr[o] = temp[n];
				n++;
			}
			o++;
		}
		while (m <= mid)
		{
			arr[o] = temp[m];
			o++;
			m++;
		}
	}
	
	private void quicksort(int [] data)	//The following code for the quicksort algorithm was taken from pg.520 of Data Sructures and Algorithms in Java 4th edition, Drozedek)
	{
		if(data.length<2){
			return;
		}
		int max=0;
		for(int i=1; i<data.length; i++){
			if(data[max]<(data[i])){
				max=i;
			}
		}
		swap(data,data.length-1,max);
		quicksort(data,0,data.length-2);
	}

	private void quicksort(int [] data, int first, int last)	//The following code for the quicksort algorithm was taken from pg.520 of Data Sructures and Algorithms in Java 4th edition, Drozedek)
	{	
		int lower = first + 1, upper = last;
		swap(data, first, (first + last) / 2);
		int bound = data[first];
		while (lower <= upper){
			while (bound > data[lower]){
				lower++;
			}
			while (bound < data[upper]){
				upper--;
			}
			if (lower < upper){
				swap(data, lower++, upper--);
			}
			else{
				lower++;
			}
		}
		swap(data, upper, first);
		if (first < upper - 1){
			quicksort(data, first, upper - 1);
		}
		if (upper + 1 < last){
			quicksort(data, upper + 1, last);
			}
		}
	private void swap(int [] data, int in, int in1 )
	{
		int temp=data[in];
		data[in]=data[in1];
		data[in1]=temp;
	}

	private boolean errors(String[] arr)		
	{
		boolean error;
		if (arr[2].equals("bubble") || arr[2].equals("insertion") || arr[2].equals("merge") || arr[2].equals("quick")){
			error = false;
		}
		else{
			System.out.println("A valid Algorithm input was not detected.");
			System.out.println("Valid Algorithm types are: bubble, insertion, merge and quick.");
			System.out.println("The program is now quitting.");
			return true;
		}
		if (arr[0].equals("ascending") || arr[0].equals("descending") || arr[0].equals("random")){
			error = false;				
		}
		else{
			System.out.println("A valid input for order type was not detected.");
			System.out.println("Valid order types are: ascending, descending and random.");
			System.out.println("The program is now quitting.");
			return true;
		}
		int x = Integer.parseInt(arr[1]);
		if (x < 1){
			System.out.println("Inputted array size is too small.");
			System.out.println("A valid array size input is an integer greater than or equal to one.");
			System.out.println("The program is now quitting.");
			return true;
		}
		try
		{
			x = Integer.parseInt(arr[1]);
		}
		catch(NumberFormatException nFE)
		{
					System.out.println("Array size inputted was not an integer.");
					System.out.println("A valid array size input is an integer greater than or equal to one.");
					System.out.println("The program is now quitting.");
					return true;
				}
		return error;
	}
	
	public void arrayset(int [] arr,String type)
	{
		if(type.equals("random")){
		return;
		}
		if(type.equals("ascending")){
			Arrays.sort(arr);
			return;
		}
		if(type.equals("descending")){
			for(int i=0; i<arr.length;i++){
				arr[i]=arr[i]*-1;
			}
			Arrays.sort(arr);
			for(int i=0; i<arr.length; i++){
				arr[i]=arr[i]*-1;
			}
			return;
		}
	}
	public void writeArray(int[] arr, String[]  args, int arraysize, long start, long end)		// Copies the array to a text file after having been sorted by one of the algorithms.
	{
		try{
			PrintWriter txtdata = new PrintWriter(args[3]);
			txtdata.println("For an array with " + arraysize + " items set in " + args[0] +" order, it took "  + (double) ((end - start) / 1E9) + " seconds" + " to sort the array using the " + args[2] + " sort algorithm.");		//The following code was attained from http://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-a-file-in-java
																																																					//as well as from tutorial T01 (T.A:Aniruddha Chattoraj)
			txtdata.println();
			txtdata.println("Below is the array after having been sorted by the algorithm: ");
			for (int i = 0; i < arr.length; i++)
			{
				txtdata.println(arr[i]);
			}
			txtdata.close();
			
		}
		catch(IOException e){
			System.err.println("AN IOException was Caught: "+e.getMessage());
		}
		
		catch(ArrayIndexOutOfBoundsException e){
			if(arr.length < 1)
			System.out.println("Provide a file name as a command line argument.");
		}
	}
	public static void main(String[] args)
	{
		Algorithms sort=new Algorithms();
		
		if(sort.errors(args)){
			return;
		}
		
		else{
			
			int[] arr;										//Code attained from tutorial section T01 (T.A:Aniruddha Chattoraj)
			int length=Integer.parseInt(args[1]);
			arr=new int[length];
			Random randomGenerator = new Random();
			for(int i=0; i<length; i++){
				arr[i]=randomGenerator.nextInt(75000000);
				}
				long start = 0, end = 0;		// initialization of the variables that will be used to measure the time.
				
				if (args[2].equals("bubble")) // Will sort the array using the bubblesort algorithm.
				{
					System.out.println("Sorting for a " + args[0] + " order array using the bubble sort algorithm commencing. ");
					int []  bubblearr =arr;
					sort.arrayset(bubblearr, args[0]);
					
					start = System.nanoTime();		//Timing initializes and the sort begins.
					sort.bubblesort(bubblearr);
					end = System.nanoTime();
					double time=(end-start)/1E9;
					
					System.out.println("Sorting has concluded. Data is now being inserted into a text file.");
					sort.writeArray(bubblearr, args, length, start, end);
					System.out.println("For an array with " + length + " items set in " + args[0] +" order, it took "  + time + " seconds" + " to sort the array using the bubble sort algorithm.");		//The time it too for the algorithm to sort the array is printed.
					
				}
				
				if (args[2].equals("insertion")) // Will sort the array using the insertionsort algorithm.
				{
					System.out.println("Sorting for a " + args[0] + " order array using the insertion sort algorithm commencing. ");
					int []  insertionarr = arr;
					sort.arrayset(insertionarr, args[0]);
					
					start = System.nanoTime();	//Timing initializes and the sort begins.
					sort.insertionsort(insertionarr);
					end = System.nanoTime();
					double time=(end-start)/1E9;
					
					System.out.println("Sorting has concluded. Data is now being inserted into a text file.");
					sort.writeArray(insertionarr, args, length, start, end);
					System.out.println("For an array with " + length + " items set in " + args[0] +" order, it took "  + time + " seconds" + " to sort the array using the insertion sort algorithm.");	//The time it too for the algorithm to sort the array is printed.
				}
				
				if (args[2].equals("merge")) // Will sort the array using the mergesort algorithm.
				{
					System.out.println("Sorting for a " + args[0] + " order array using the merge sort algorithm commencing. ");
					int []  mergearr = arr;
					sort.arrayset(mergearr, args[0]);
					
					start = System.nanoTime();	//Timing initializes and the sort begins.
					sort.mergesort(mergearr, 0, length - 1);
					end = System.nanoTime();
					double time=(end-start)/1E9;
					
					System.out.println("Sorting has concluded. Data is now being inserted into a text file.");
					sort.writeArray(mergearr, args, length, start, end);
					System.out.println("For an array with " + length + " items set in " + args[0] +" order, it took "  + time + " seconds" + " to sort the array using the merge sort algorithm.");		//The time it too for the algorithm to sort the array is printed.
				}
				
				if (args[2].equals("quick")) // Will sort the array using the quicksort algorithm.
				{
					System.out.println("Sorting for a " + args[0] + " order array using the quick sort algorithm commencing.");
					int []  quickarr = arr;
					sort.arrayset(quickarr, args[0]);
					
					start = System.nanoTime();	//Timing initializes and the sort begins.
					sort.quicksort(quickarr);
					end = System.nanoTime();	//Timing ends
					double time=(end-start)/1E9;
					
					System.out.println("Sorting has concluded. Data is now being inserted into a text file.");
					sort.writeArray(quickarr, args, length, start, end);
					System.out.println("For an array with " + length + " items set in " + args[0] +" order, it took "  + time + " seconds" + " to sort the array using the quick sort algorithm.");		// Prints the number of seconds taken as a double
				}
		}
	}
}

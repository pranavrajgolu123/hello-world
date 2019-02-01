package CollectionProgram;

import java.util.Comparator;
import java.util.*;

class Ascend implements Comparator<Integer>{

	@Override
	public int compare(Integer arg0, Integer arg1) {
		
		return arg0.compareTo(arg1);
	}
	
}

class Descend implements Comparator<Integer>{

	@Override
	public int compare(Integer arg0, Integer arg1) {
		
		return arg1.compareTo(arg0);
	}
	
}
public class ArrayCompareClassDemo {

	public static void main(String arg[]) {
		
		Scanner input=new Scanner(System.in);
		
		Integer arr[]=new Integer[5];
		for(Integer i=0;i<5;i++) {
			System.out.println("Enter an Integer");
			arr[i]=Integer.parseInt(input.nextLine());
		}
		
		System.out.println("Content Of Array");
	    display(arr);
	    Arrays.sort(arr, new Ascend());
		System.out.println("Ascending Sorted Arrays");
		display(arr);
		Arrays.sort(arr, new Descend());
		System.out.println("Descending Sorted Arrays");
		display(arr);
		
		
		System.out.println("BinarySearchTree");
		int element=Integer.parseInt(input.nextLine());
		int index=Arrays.binarySearch(arr, element);
		if(index<0)System.out.println("Array is empty");
		else System.out.println("Position"+ (index+1));
		
	}

	static void display(Integer arr[]) {
		for (Integer i = 0; i<5; i++) {
			System.out.println(arr[i]);
		}
	}
	

}

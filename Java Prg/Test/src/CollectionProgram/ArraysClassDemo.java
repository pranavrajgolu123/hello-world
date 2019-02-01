package CollectionProgram;

import java.util.Arrays;
import java.util.Scanner;

public class ArraysClassDemo {

	public static void main(String arg[]) {
		
		Scanner input=new Scanner(System.in);
		
		int arr[]=new int[5];
		for(int i=0;i<5;i++) {
			System.out.println("Enter an Integer");
			arr[i]=Integer.parseInt(input.nextLine());
		}
		
		System.out.println("Content Of Array");
			    display(arr);
	    Arrays.sort(arr,0,3);
		System.out.println("After index 8Sorted Arrays");
		display(arr);
		Arrays.sort(arr);
		System.out.println("After Sorted Arrays");
		display(arr);
		
		
		System.out.println("BinarySearchTree");
		int element=Integer.parseInt(input.nextLine());
		int index=Arrays.binarySearch(arr, element);
		if(index<0)System.out.println("Array is empty");
		else System.out.println("Position"+ (index+1));
		
	}

	static void display(int arr[]) {
		for (int i = 0; i<5; i++) {
			System.out.println(arr[i]);
		}
	}
}

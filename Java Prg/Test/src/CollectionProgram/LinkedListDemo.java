package CollectionProgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class LinkedListDemo {
	/**
	 * @param arg
	 */
	public static void main(String arg[])
	{
	LinkedList<Integer> linklist=new LinkedList<Integer>();
	int choice=0;
	int element,position;
	Scanner br=new Scanner(System.in);
	System.out.println("LinkedList operation");
	System.out.println("1. Add Element");
	System.out.println("2. Remove Element");
	System.out.println("3. Change Element");
	
	while(choice <= 4)
	{
	System.out.println("Enter your Choice");
	choice=Integer.parseInt(br.nextLine());
	
	switch (choice) {
	case 1:
		System.out.println("Enter the Elemnet");
		element=Integer.parseInt(br.nextLine());
		System.out.println("At What Position");
		position=Integer.parseInt(br.nextLine());
		linklist.add(position-1, element);
		break;
		
	case 2:
		System.out.println("Remove the Elemnet");
		position=Integer.parseInt(br.nextLine());
		linklist.remove(position-1);
		break;
	
	case 3:
		System.out.println("At What Position");
		position=Integer.parseInt(br.nextLine());
		System.out.println("Enter the Elemnet");
		element=Integer.parseInt(br.nextLine());
		linklist.set(position-1, element);
		break;
		
	case 4:
		
		 Iterator<Integer> itr=linklist.iterator();  
		  while(itr.hasNext()){  
		   System.out.println(itr.next());  
		  }  
		 
		break;
		
	 default:
		System.out.println("OOps Choose Correct Selection");
		break;
	}
	
	}
	Object[] t=linklist.toArray();
    for(int i=0;i<t.length;i++) {
    	System.out.println("LinkedList Element:- "+t[i]);
    }
	
	
	}
}

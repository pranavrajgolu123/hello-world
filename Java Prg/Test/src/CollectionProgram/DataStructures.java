package CollectionProgram;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class DataStructures {
	
	public void Stackdemo()
	{
		Stack<Integer> st=new Stack<Integer>();
		int choice=0;
		int element,position;
		Scanner br=new Scanner(System.in);
		System.out.println("Stack operation");
		System.out.println("1. Push Element");
		System.out.println("2. Pop Element");
		System.out.println("3. Search Element");

		
		while(choice <=4)
		{
		System.out.println("Enter your Choice");
		choice=Integer.parseInt(br.nextLine());
			
			switch(choice)
			{
				case 1:
				System.out.println("Enter the Element");
				element=Integer.parseInt(br.nextLine());
				st.push(element);
				break;
				
				case 2:
				Integer obj=st.pop();
				System.out.println("The POP Element"+ obj);				
				break;
				
				case 3:
				System.out.println("Enter the Search Element");
				element=Integer.parseInt(br.nextLine());
				position=st.search(element);
				if(position == -1)
				{
					System.out.println("Element not Found");
					
				}
				else
				{
					System.out.println("Position of the Element"+position);
				}				
				break;
				
				case 4:
					
					 Iterator<Integer> itr=st.iterator();  
					  while(itr.hasNext()){  
					   System.out.println(itr.next());  
					  }  
					 
					break;
					
				default:
				
				System.out.println("OOps Choose Correct Selection");
				break;
				
			}
			
			
			
		}
		System.out.println("Stack Element"+st);
	}
	
	public void LinkedListDemo() {
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
		
		System.out.println("LinkedList Element:- "+linklist);
	}

	public static void main(String arg[]) {
		DataStructures ds=new DataStructures();
		System.out.println("1. Stack");
		System.out.println("2. LinkedList");
		int choice;
		Scanner key=new Scanner(System.in);
		System.out.println("Enter Your choice");
		choice=Integer.parseInt(key.nextLine());
		switch (choice) {
		case 1:
			ds.Stackdemo();
			break;
			
		case 2:
			ds.LinkedListDemo();
			break;

		default:
			System.out.println("OOps Choose Correct Selection");
			break;
		}
		
}

}

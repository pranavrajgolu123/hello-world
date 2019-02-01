package CollectionProgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class StackDemo {
	public static void main(String arg[])
	{
		Stack<Integer> st=new Stack<Integer>();
		int choice=0;
		int element,position;
		Scanner br=new Scanner(System.in);
		System.out.println("Stack operation");
		System.out.println("1. Push Element");
		System.out.println("2. Pop Element");
		System.out.println("3. Search Element");

		
		while(choice < 4)
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
				
				default:
				
				System.out.println("OOps Choose Correct Selection");
				break;
				
			}
			
			
			
		}
		System.out.println("Stack Element"+st);
		
	}
	
}
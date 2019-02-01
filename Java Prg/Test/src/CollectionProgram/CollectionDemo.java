package CollectionProgram;

import java.util.Arrays;
import java.util.Scanner;

public class CollectionDemo {

	int id;
	String name;
	
	public CollectionDemo(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void display() {
		System.out.println("Id:- "+id+"\t"+"name:- "+name);
	}
}
class group{
	public static void main(String arg[]) {
		Scanner st=new Scanner(System.in);
		CollectionDemo arr[]=new CollectionDemo[5];
		for(int i=0;i<5;i++) {
			System.out.print("Enter Id:- ");
			int id=Integer.parseInt(st.nextLine());
			System.out.print("Enter name:- ");
			String name=st.nextLine();
			arr[i]=new CollectionDemo(id, name);
		}
		
		System.out.println("\n The Employe Data");
		
		for(int i=0; i<arr.length;i++) {
			
			arr[i].display();
			
		}
			
	}
}

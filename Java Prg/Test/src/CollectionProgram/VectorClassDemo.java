package CollectionProgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;


public class VectorClassDemo {
	public static void main(String arg[]) {
		Vector<String> vector=new Vector<String>();
	    String data[]= {"a","b","c","d"};

		for(int i=0;i<data.length;i++) {
			vector.add(data[i]);
		}
		System.out.println("Vector Element");	
		for(int i=0;i<vector.size();i++) {
			System.out.println(vector.get(i));	
		}
				
		System.out.println("VectorList using Iterator");
		
		ListIterator<String> it=vector.listIterator();
		System.out.println("\n In Forward Direction");	
		while(it.hasNext())
		System.out.println(it.next());
		
		System.out.println("\n In Backward Direction");		
		while(it.hasPrevious())
		System.out.println(it.previous());
		
	}
}



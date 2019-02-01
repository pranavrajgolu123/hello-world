package CollectionProgram;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {
 
	public static void main(String arg[]) {
		ArrayList<String> al=new ArrayList<String>();
		al.add("apple");
		al.add("mango");
		al.add("orange");
		al.add("banana");
		al.add("mango");
		
		
		System.out.println("ArrayList:-"+al);
		
		al.add(2, "raj");
		System.out.println("ArrayList:-"+al);
		
		al.remove(3);
		al.remove("apple");
		
		System.out.println("ArrayList afterremove:-"+al);
		System.out.println("ArrayList Size:-"+al.size());
		
		System.out.println("ArrayList using Iterator");
		
		Iterator<String> it=al.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		
	}
}

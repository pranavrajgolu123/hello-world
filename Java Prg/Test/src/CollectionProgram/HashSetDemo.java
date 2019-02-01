package CollectionProgram;

import java.util.HashSet;
import java.util.Iterator;


public class HashSetDemo {
  
	public static void main(String arg[]) {
		HashSet<String> hs=new HashSet<String>();
		hs.add("a");
		hs.add("b");
		hs.add("c");
		hs.add("d");
		hs.add("e");
		hs.add("f");
		hs.add("f");
		
		System.out.println("Hashset:-"+hs);
		System.out.println("Hashset:-"+hs.size());
		System.out.println("Hashset:-"+hs.contains("f"));
		System.out.println("Hashset:-"+hs.isEmpty());
		
		Iterator<String> It=hs.iterator();
		System.out.println("Element Using Iterator");
		while(It.hasNext()) {
			String s=(String)It.next();
			System.out.println(s);
		}
		
	}
}

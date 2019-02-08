package CollectionProgram;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class HashTableDemo {
	public static void main(String arg[]) {
		
		Hashtable<String, Long> hT=new Hashtable<String, Long>();
		
		String name,str;
		Long pNo;
		Scanner input=new Scanner(System.in);
		
		while(true) {
			System.out.println("HashMap operation");
			System.out.println("1.Enter Phone Enteries");
			System.out.println("2. Lookup in the phone book");
			System.out.println("3. Display name in the book");
			System.out.println("4. Display Phone no in the book");
			
			System.out.println("Enter Your Choice");
            int n=Integer.parseInt(input.nextLine());
            
            switch(n) {
            
            case 1: System.out.println("Enter your name");
                    name=input.nextLine();
                    System.out.println("Enter Your Phone no");
                    str=input.nextLine();
                    pNo=new Long(str);
                    hT.put(name, pNo);
                    break;
                    
            case 2: System.out.println("Enter your name");
            name=input.nextLine();
            name=name.trim();
            pNo=hT.get(name);
            System.out.println("your no"+pNo);
            break;
            
            case 3: Set<String> set=new HashSet<String>();
                   set=hT.keySet();
                   System.out.println(set);
                   break;
                   
            case 4: 
            Collection<Long> value=new HashSet<Long>();
            value=hT.values();
            System.out.println(value);
            break;
                    
             default:
            	 break;
            }            
            }
		}
	}


package CollectionProgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HashMapDemo {
	
	public static void main(String arg[]) {
	
		HashMap<String, Long> hm=new HashMap<String, Long>();
		
		String name,str;
		Long pNo;
		Scanner input=new Scanner(System.in);
		
		while(true) {
			System.out.println("HashMap operation");
			System.out.println("1.Enter Phone Enteries");
			System.out.println("2. Lookup in the phone book");
			System.out.println("3. Display name in the book");
			
			System.out.println("Enter Your Choice");
            int n=Integer.parseInt(input.nextLine());
            
            switch(n) {
            
            case 1: System.out.println("Enter your name");
                    name=input.nextLine();
                    System.out.println("Enter Your Phone no");
                    str=input.nextLine();
                    pNo=new Long(str);
                    hm.put(name, pNo);
                    break;
                    
            case 2: System.out.println("Enter your name");
            name=input.nextLine();
            name=name.trim();
            pNo=hm.get(name);
            System.out.println("your no"+pNo);
            break;
            
            case 3: Set<String> set=new HashSet<String>();
                   set=hm.keySet();
                   System.out.println(set);
                   break;
                    
             default:
            	 break;
            }            
            }
		}
	}


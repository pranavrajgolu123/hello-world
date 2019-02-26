package Converting;

import java.util.Scanner;

public class StringletterCompare {

	public static void main(String arg[]) {

		Scanner st=new Scanner(System.in);
		System.out.println("Enter first String");
		String s1=st.nextLine();
		System.out.println("Enter Second String");
		String s2=st.nextLine();
		int count=0;
		
		for(int i=0;i<s1.length();i++) {
			for(int j=0;j<s2.length();j++)
			{
				if(s1.charAt(i) == s2.charAt(j)) {
					System.out.println(s1.charAt(i)+" == "+s2.charAt(j));
					System.out.println("count1  "+count);
					count++;
					System.out.println("count2  "+count);
				}
				
			}
			if(count == 0) {
				System.out.println("Not Same "+count);	
				count=0;
				break;
			}
			else if(i != s1.length()-1){
			count=0;
			}
	}
		
		if(count != 0) {
			System.out.println("Same");
		}
}
}

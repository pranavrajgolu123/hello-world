package CollectionProgram;

import java.util.Scanner;


public class imu {
	public static void main(String s[]) {

		Scanner input=new Scanner(System.in);
	    String str=input.nextLine();
	    String temp=str;
	    StringBuffer sb=new StringBuffer(str);
	    sb.reverse();
	    str=sb.toString();
	    
		System.out.println(str);
		if(temp.equalsIgnoreCase(str)) {System.out.println(temp+" is Palindrom");}
		else {System.out.println(temp+" is not Palindrom");}
	}
}

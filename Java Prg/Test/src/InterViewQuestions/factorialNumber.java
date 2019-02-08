package InterViewQuestions;

import java.util.Scanner;

public class factorialNumber {


	
	public static void main(String arg[]) {
		int fact=1;
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the no:  ");
		int num = Integer.parseInt(input.nextLine());
	    int factorial = fact(num);
		System.out.println("using recursion:  "+factorial);
		while(num>0) {
			fact=fact * num;
			num--;
		}
		System.out.println("fact no=  "+fact);
		
		
	}
	
	 static int fact(int n)
	   {
	       int output;
	       if(n==1){
	         return 1;
	       }
	       //Recursion: Function calling itself!!
	       output = fact(n-1)* n;
	       return output;
	   }

}

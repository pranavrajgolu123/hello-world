package InterViewQuestions;

import java.util.Scanner;

public class factorialNumber {

	static long usingRescursion(int num) {
		long fact;
		if(num == 1) return 1;
		fact=usingRescursion(num-1)*num;
		//System.out.println("using recursion"+fact);
		return fact;
	}
	
	public static void main(String arg[]) {
		int fact=1;
		
		Scanner input = new Scanner(System.in);
		int num = Integer.parseInt(input.nextLine());
		while(num>0) {
			fact=fact * num;
			num--;
		}
		System.out.println(fact);
		System.out.println(factorialNumber.usingRescursion(num));
		
	}

}

package ThreadProgram.Array;

import java.util.Scanner;

public class armstronum {

	public static void main(String arg[]) {
		Scanner input = new Scanner(System.in);
		int num = Integer.parseInt(input.nextLine());
		int c = 0;
		int temp = num;
		int a;
		while (num > 0) {
			a = num % 10;
			//System.out.println("A value is: "+a);
			num = num / 10;
			c = c + (a * a * a);
			//System.out.println("C value is: "+c);
		}
//		/System.out.println("C value is: "+c);
		if (num == c) {
			System.out.println("Num is Armstrong");
		} else
			System.out.println("Num is not Armstrong");
	}

}

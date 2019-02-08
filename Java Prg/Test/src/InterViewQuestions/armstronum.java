package InterViewQuestions;

import java.util.Scanner;
/*An Armstrong number is a number such that the sum
! of its digits raised to the third power is equal to the number
! itself.  For example, 371 is an Armstrong number, since
! 3**3 + 7**3 + 1**3 = 371.*/
public class armstronum {

	public static void main(String arg[]) {
		Scanner input = new Scanner(System.in);
		int num = Integer.parseInt(input.nextLine());
		int c = 0;
		int temp = num;
		int a;
		while (num > 0) {
			a = num % 10;
			num = num / 10;
			c = c + (a * a * a);

		}
		if (temp == c) {
			System.out.println("Num is Armstrong");
		} else {
			System.out.println("Num is not Armstrong");}
	}

}

package InterViewQuestions;
import java.util.Scanner;
public class PalindromNo {

	public static void main(String arg[]) {
		int num,a;
		int c=0;
		
		Scanner input = new Scanner(System.in);
		num = Integer.parseInt(input.nextLine());
		int temp=num;
		while (num > 0) {
			a = num % 10;
			c = (c * 10)+a;
			num = num / 10;
		}
//		/System.out.println("C value is: "+c);
		if (temp == c) {
			System.out.println("Num is palindrom");
		} else
			System.out.println("Num is not palindrom");
	}
}

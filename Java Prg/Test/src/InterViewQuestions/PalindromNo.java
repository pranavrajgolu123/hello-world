package InterViewQuestions;
import java.util.Scanner;
public class PalindromNo {
	static int a;
	static int c=0;
	static int temp;
	static int palindrom(int num) {
		
		if(num > 0) {
			a = num % 10;
			c = (c * 10) + a;
			System.out.println("k  "+c);
	       //  num = num / 10;
			
			palindrom(num / 10);
			
		}

		return c;
		
	}
	public static void main(String arg[]) {
		int num,a;
		int c=0;
		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the number: ");
		num = Integer.parseInt(input.nextLine());
		int er=palindrom(num);
		int temp=num;
		while (num > 0) {
			a = num % 10;
			c = (c * 10)+a;
			num = num / 10;
			
		}
		
		System.out.println("recursion value is: "+er);
		if (temp == c) {
			System.out.println("Num is palindrom");
		} else
			System.out.println("Num is not palindrom");
	}
}

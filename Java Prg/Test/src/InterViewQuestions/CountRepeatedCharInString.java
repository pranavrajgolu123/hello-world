package InterViewQuestions;

import java.util.Scanner;

public class CountRepeatedCharInString {

	public static void main(String args[]) {
		int count = 0;
		System.out.println("Enter the String:");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		String t = s.toLowerCase();
		for (char i = 'a'; i <= 'z'; i++) {
			for (int j = 0; j < t.length() - 1; j++) {
				if (t.charAt(j) == i) {
					count++;
				}
			}
			if (count != 0) {
				System.out.println(i + "=" + count);
				count = 0;
			}
		}
	}
}
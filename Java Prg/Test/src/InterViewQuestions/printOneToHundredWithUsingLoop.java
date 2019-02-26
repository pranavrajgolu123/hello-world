package InterViewQuestions;

public class PrintOneToHundredWithUsingLoop {
	
	static int i=100;
	static int j=0;
	static void num() {
		if(j<=i) {
			System.out.print(j);
			j++;
			num();
		}
	}

	public static void main(String r[]) {
		PrintOneToHundredWithUsingLoop.num();
	}
}

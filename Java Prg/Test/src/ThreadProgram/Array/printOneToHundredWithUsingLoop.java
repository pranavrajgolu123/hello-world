package ThreadProgram.Array;

public class printOneToHundredWithUsingLoop {
	
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
		printOneToHundredWithUsingLoop.num();
	}
}

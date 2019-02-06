package InterViewQuestions;

public class gerneralizationExample {

	public static void main(String r[]) {
	one obj=new two();
	two obj1=(two)obj;
	one.show1();
//	obj1.show1();

	}
}
class one{
	static public void show1() {
		System.out.println("Supper class Method");
	}
}

class two extends one{
	static public void show1() {
		
		System.out.println("Sub-class class Method");
	}
}
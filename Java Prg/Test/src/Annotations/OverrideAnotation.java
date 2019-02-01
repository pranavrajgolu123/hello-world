package Annotations;

class one{
	public void Display() {
		System.out.println("Display");
	}
}

class Two extends one{
	@Override
	public void Display() {
		System.out.println("Display1");
	}
	//When you enable the function comment the upper display function it gives you error because function name is different
	/*@Override
	public void display() {
		System.out.println("Display1");
	}*/ 
}

public class OverrideAnotation {

	public static void main(String arg[]) {
		Two t=new Two();
		t.Display();
	}
}


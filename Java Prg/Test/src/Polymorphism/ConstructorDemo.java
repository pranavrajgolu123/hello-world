package Polymorphism;


class b{

	public b() {
		System.out.println("class b");
		// TODO Auto-generated constructor stub
	}
	void display() {
		System.out.println("method b");
	}
	
}
public class ConstructorDemo extends b{
	static boolean b1,b2;
	
	
	
	public ConstructorDemo() {
		super();
		System.out.println("class Constructor");
	}
	void display() {
	//	super.display();
		System.out.println("method Constructor");

	}
	
	public static void main(String arg[]) {
		ConstructorDemo ct=new ConstructorDemo();
		ct.display();//print method Constructor
		
		//b B=new b();//print only class b
		//B.display();
		
		/* cannot give refernece child class to parent class
		ConstructorDemo ct1=(ConstructorDemo) new b();
		ct1.display();*/
		/*
		b B=new ConstructorDemo();
		B.display();//print first 
*/	}

	

}

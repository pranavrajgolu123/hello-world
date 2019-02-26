package Threading;

public class SingleThreadDemo implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		task1();
		task2();
		task3();
	}
	
	void task1() {
		System.out.println("Task1 is Done");
	}
	void task2() {
		System.out.println("Task2 is Done");
	}
	void task3() {
		System.out.println("Task3 is Done");
	}
	
	public static void main(String arg[]) {
		
		SingleThreadDemo sT=new SingleThreadDemo();
		Thread t=new Thread(sT);
		t.start();
		
	}
	

}

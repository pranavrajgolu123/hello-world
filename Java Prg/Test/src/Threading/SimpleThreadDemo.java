package Threading;

public class SimpleThreadDemo implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Wellcome to thread Program");
		try {
			Thread.sleep(1000);
			System.out.println(" after Sleep Wellcome to thread Program");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String arg[]) {
		
		SimpleThreadDemo smt=new SimpleThreadDemo();
		Thread t=new Thread(smt);
		t.start();
	}
	

}

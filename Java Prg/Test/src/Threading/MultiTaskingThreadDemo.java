package Threading;

public class MultiTaskingThreadDemo  implements Runnable{
    String str;
	public MultiTaskingThreadDemo(String str) {
		this.str = str;
	}
	@Override
	public void run() {
		for(int i=0; i<10;i++) {
			System.out.println(str+"="+i);
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
	public static void main(String arg[]) {
		MultiTaskingThreadDemo mt=new MultiTaskingThreadDemo("object one");
		MultiTaskingThreadDemo mt2=new MultiTaskingThreadDemo("object two");
		
		Thread t1=new Thread(mt);
		Thread t2=new Thread(mt2);
		t1.start();
		t2.start();
		
	}

}

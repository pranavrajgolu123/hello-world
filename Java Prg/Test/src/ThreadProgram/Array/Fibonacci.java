package ThreadProgram.Array;

public class Fibonacci {
 
	 static int n1=0,n2=1,n3=0;    
	 static void printFibonacci(int count){    
	    if(count>0){    
	         n3 = n1 + n2;    
	         n1 = n2;    
	         n2 = n3;    
	         System.out.print(" "+n3);   
	         printFibonacci(count-1);    
	     }    
	 } 
	 
	 void withoutRecursion(int count)
	 {
		 int t1=0,t2=1,nexterm;
			for(int i=0;i<count;i++) {
				System.out.print(t1+" ");
				nexterm=t1+t2;
				t1=t2;
				t2=nexterm;
			}
	 }
	public static void main(String arg[]) {
		  int count=10;    
		  System.out.print(n1+" "+n2);//printing 0 and 1    
		  printFibonacci(count-2);//n-2 because 2 numbers are already printed  
		  Fibonacci t=new Fibonacci();
		  t.withoutRecursion(count);
	}
}

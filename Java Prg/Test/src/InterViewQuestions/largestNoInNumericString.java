package InterViewQuestions;

public class largestNoInNumericString {

	public static void main(String r[]) {
		
		String s="547801"; 
		int n,r1= 0;
		int Id1=0;
		
		int i=Integer.parseInt(s); 
		n=i;
		System.out.println(i);
		   while (n > 0) {
	            r1 = n % 10;
	            System.out.println("r1: "+r1); 
	            if (Id1 < r1) {
	            	System.out.println("id: "+Id1); 
	                Id1 = r1;
	            }
	            n = n / 10;
	           System.out.println("n: "+n); 
	        }
		   
		   System.out.println(Id1);   
		   
	}
}

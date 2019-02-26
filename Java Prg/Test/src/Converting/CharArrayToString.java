package Converting;

public class CharArrayToString {

	public static void main(String arg[]) {
		  try {
	        	int a=5;
	        	int b=0;
	        	int c=a/b;
	        	System.out.println(c);
		// Convert char array to String
		System.out.println("Convert char array to String");
		char arr[] = { 'p', 'r', 'a', 'n', 'a', 'v' };
		String str = new String(arr);
		System.out.println(str);

		// Convert char array to String array
		System.out.println("Convert char array to String array");
		char arr1[] = { 'p', 'r', 'a', 'n', 'a', 'v' };
		int i = 0;
		String str1 = new String(arr1);
		String[] str_arr = str1.split("");
		while (i < str_arr.length) {
			System.out.print(str_arr[i]+",");
			i++;
		}
		System.out.println();

		// Convert String to char array
		System.out.println("Convert String to char array");
		String s = "pranav";
		int j=0;
		char char_arr[] = s.toCharArray();
		while (j < char_arr.length) {
			System.out.print(char_arr[j]+",");
			j++;
		}
		
        System.out.println();
        //convert string array to integer array
        System.out.println("convert string array to integer array");
        String str_arr1[]= {"123", "345", "437", "894"};
        Integer[] intarray=new Integer[str_arr1.length];
        int k=0;
        for(String str11:str_arr1){
            intarray[k]=Integer.parseInt(str11);//Exception in this line
            k++;
        }
        k=0;
        while(k<intarray.length) {
        System.out.print(intarray[k]+",");
        k++;
        }
        
      
        }catch (ArithmeticException e) {
			// TODO: handle exception
		}
        

	}
}

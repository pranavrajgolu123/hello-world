package InterViewQuestions;

public class findMinandMaxNoInAnArray {
	int arr1[]= {1,4,7,8,9,10,3,2};
    void largest() {
    	int max=arr1[0];
    	for(int i=1;i<arr1.length;i++) {
    		if(arr1[i]>max) {
    			max=arr1[i];
    		}
    	}
    	System.out.println("Highest no in array:  "+max);
    	
    }
    
    void smallest() {
    	int min=arr1[0];
    	for(int i=1;i<arr1.length;i++) {
    		if(arr1[i]<min ) {
    			min=arr1[i];
    		}
    	}
    	System.out.println("Lowest no in array:  "+min);
    	
    }
	public static void main(String arr[]) {
		
	
		findMinandMaxNoInAnArray ft=new findMinandMaxNoInAnArray();
		ft.largest();
		ft.smallest();
		
	}

}

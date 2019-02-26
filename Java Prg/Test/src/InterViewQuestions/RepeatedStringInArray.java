package InterViewQuestions;

import java.util.Arrays;

public class RepeatedStringInArray {

	public static void main(String arg[]) {
		
		String arr[]= {"a", "b","c","d","a","b","c"};
		int c=0;
		int arr1[]={-2, 1, -3, 4, -1, 2, 1, -5, 4};
		for(int i=0;i<arr1.length;i++){
		   c= c + (arr1[i]);
		    }
		    System.out.println("count:   "+c);
		    
		String countarr[]=new String[arr.length];
		int count=1;
		int time[]=new int[arr.length];
/*		for(int y=0; y<arr.length; y++) {
			time[y]=1;
		}*/
		for(int i=0;i<arr.length;i++) {

			for(int j=i+1;j<arr.length;j++) {
				if(arr[i].equals(arr[j])) {
					/*countarr[i]=arr[i];
					time[i]++;*/
					count=count+1;
					arr[j]="0";
					
				}
			}
			if(arr[i] !="0") {
				System.out.println(arr[i]+"= "+count);
				count=1;
				}
		}
		
	/*	for(int t=0;t<countarr.length;t++) {
			System.out.println("\t" + arr[t] + "\t" +  countarr[t] + "\t" + time[t]);
		}*/
		

	}
}

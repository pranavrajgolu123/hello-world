package InterViewQuestions;

import java.util.ArrayList;
import java.util.List;

public class RepeatedIntegerInArray {

	public static void main(String rf[]) {
		int arr[]= {1,2,3,4,5,3,2,1,5,6,7};
		int count=1;
		int countarr[]=new int[arr.length];
		int time[]=new int[arr.length];
/*		ArrayList<Integer> list = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			list.add(Integer.valueOf(i));
		}
		boolean t=list.contains(1);
		System.out.println(t);
		int tt=list.get(4);
		System.out.println(tt);*/
		for(int y=0; y<arr.length; y++) {
			time[y]=1;
		}
		for(int i=0;i<arr.length;i++) {

			for(int j=i;j<arr.length;j++) {
				if(arr[i] == arr[j] && i != j) {
					countarr[i]=arr[i];
					time[i]++;
					
				}
			}
		}
		
		for(int t=0;t<countarr.length;t++) {
			System.out.println("\t" + arr[t] + "\t" +  countarr[t] + "\t" + time[t]);
		}

	}
}

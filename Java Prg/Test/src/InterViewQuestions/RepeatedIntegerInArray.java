package InterViewQuestions;

import java.util.ArrayList;
import java.util.List;

public class RepeatedIntegerInArray {

	public static void main(String rf[]) {
		int arr[]= {1,2,3,4,5,3,2,1};
		int count=1;
		ArrayList<Integer> list = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			list.add(Integer.valueOf(i));
		}
		for(int i=0;i<list.size();i++) {

			for(int j=i+1;j<list.size();j++) {
				if(arr[i] == arr[j]) {
					count=count+1;
					list.remove(j);
				}
			}
			if(count!=1) {
				System.out.println(arr[i]+"= "+count);
				count=1;
				}
			else {
				System.out.println(arr[i]+"= "+count);
				
				}
		}
	}
}

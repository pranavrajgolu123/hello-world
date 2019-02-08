package InterViewQuestions;

public class CountRepeatedWordInString {
/*input="Pranav raj Pranav"

output= Pranav= 2
 	    raj= 1*/
	public static void main(String arf[]) {
		
		String str="Pranav raj Pranav";
		String word[]=str.split(" ");
		int count=1;
		for(int i=0;i<word.length;i++) {
			
			for(int j=i+1;j<word.length;j++) {

				if(word[i].equals(word[j])) {
					
					count=count+1;
					word[j]="0";
				}			
			}
			if(word[i] !="0") {
			System.out.println(word[i]+"= "+count);
			count=1;
			}
			
		}
	}

}

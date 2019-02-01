package ThreadProgram.Array;

import java.util.Locale;

public class StringExample {

	public static void main(String r[]) {

		String str = "pranav";
		// String to Array
		System.out.println("String To Array");
		String strArray[] = str.split("");
		System.out.println(strArray[0]);

		// String to CharArray

		System.out.println("String To CharArray");
		char[] characters = str.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			System.out.print(characters[i] + ":");
		}
		System.out.println("");

		// String to char

		System.out.println("String To Char");
		char c = str.charAt(0);
		System.out.println(c);

		// String to Integer

		System.out.println("String To Integer But String is always in digit/number format");
		String s = "200";
		int i = Integer.parseInt(s);
		System.out.println(i);

		// String to IntegerArray

		System.out.println("String to IntegerArray");
		String test = "12 41 21 19 15 10";
		// The string you want to be an integer array.
		String[] integerStrings = test.split(" ");
		// Splits each spaced integer into a String array.
		int[] integers = new int[integerStrings.length];
		// Creates the integer array.
		for (int i1 = 0; i1 < integers.length; i1++) {
			integers[i1] = Integer.parseInt(integerStrings[i1]);
			// Parses the integer for each string.
			System.out.println(integers[i1]);
		}
		
		Locale locale=Locale.getDefault();  
		//Locale locale=new Locale("fr","fr");//for the specific locale  
		  
		System.out.println(locale.getDisplayCountry());  
		System.out.println(locale.getDisplayLanguage());  
		System.out.println(locale.getDisplayName());  
		System.out.println(locale.getISO3Country());  
		System.out.println(locale.getISO3Language());  
		System.out.println(locale.getLanguage());  
		System.out.println(locale.getCountry());  
		
		 Locale enLocale = new Locale("en", "US");  
	        Locale frLocale = new Locale("fr", "FR");  
	        Locale esLocale = new Locale("es", "ES");  
	        System.out.println("English language name (default): " +   
	                            enLocale.getDisplayLanguage());  
	  
	        System.out.println("English language name in French: " +   
	                            enLocale.getDisplayLanguage(frLocale));  
	        System.out.println("English language name in spanish: " +   
	                enLocale.getDisplayLanguage(esLocale));  


	}
}

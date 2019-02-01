package ThreadProgram.Array;

public class RevserString {
	static String g="fggg";
	void display() {
		System.out.println(g);
	}
	public static void main(String st[]) {
	String str="pranav is a good boy";
	/*StringBuilder t=new StringBuilder();
	t.append(str);
	t.reverse();*/
	char[] characters=str.toCharArray();
	for(int i=characters.length-1;i>=0;i--) {
		System.out.print(characters[i]);
	}
	System.out.println(g);
	RevserString r=new RevserString();
	r.display();

	}
}

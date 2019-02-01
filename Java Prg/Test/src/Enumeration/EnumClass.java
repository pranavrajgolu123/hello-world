package Enumeration;

import static Enumeration.OwnEnumeration.*;

enum st{aaa,bb,ccc};
public class EnumClass {
	
	static OwnEnumeration spicesness;
	
	public EnumClass(OwnEnumeration spicesness) {
		this.spicesness=spicesness;
	}
	
	public String toString()
	{
		return "spicesness:  "+spicesness;
	}

	public static void main(String t[]) {
		
	for(OwnEnumeration s : OwnEnumeration.values()) {
		System.out.println(s+"  ordinal  "+s.ordinal());
		System.out.println(s.compareTo(OwnEnumeration.bihar)+" ");
		System.out.println(s == OwnEnumeration.bihar);
		System.out.println(s.name());
		System.out.println("---------------------------------");
		System.out.println(new EnumClass(raj));
		System.out.println("---------------------------------");
	}
	
	for( String s : "aaa bb ccc".split(" ")) {
		st st1=Enum.valueOf(st.class, s);
		System.out.println(st1);
		
	}
     
	}

}

package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.*;
import java.lang.reflect.*;;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Mysingle{
	int value();
}

class myclass{
	@Mysingle(value = 100)
	public void mymethod() {
		System.out.println("Hello");
	}
}
public class CustomAnnotation {

	public static void main(String arg[]) throws Exception {
		myclass mc=new myclass();
		
		Method m=mc.getClass().getMethod("mymethod");
		
		Mysingle anno=m.getAnnotation(Mysingle.class);
		
		System.out.println("Value=  "+anno.value());
		
		
		
	}
}

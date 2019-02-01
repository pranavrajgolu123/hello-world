package Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.*;
import java.lang.reflect.*;;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyMultiple{
	int value();
	String value2();
	String value3();
}

class myMultipleclass{
	@MyMultiple(value = 100,value2="hii",value3="Pranav")
	public void mymethod() {
		System.out.println("Hello");
	}
}
public class MultiLevelAnnotation  {
	
	public static void main(String ae[]) throws Exception {
		
	

	myMultipleclass multi=new myMultipleclass();
	
	Method m=multi.getClass().getMethod("mymethod");
	
	MyMultiple anno=m.getAnnotation(MyMultiple.class);
	
	System.out.println("Value1= "+anno.value());
	System.out.println("Value2= "+anno.value2());
	System.out.println("Value3= "+anno.value3());
	}
}

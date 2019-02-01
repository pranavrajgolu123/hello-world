package ThreadProgram.Array; 
  
public class wordReverse 
{ 
static String wordReverse1(String str) 
{ 
    int i = str.length() - 1; 
    System.out.println("i value is: "+i);
    int start, end = i + 1; 
    String result = ""; 
      
    while(i >= 0) 
    { 
        if(str.charAt(i) == ' ') 
        { 
            start = i + 1; 
            System.out.println("start value is: "+start);
            while(start != end) 
                result += str.charAt(start++); 
            System.out.println("result value is: "+result);
            result += ' '; 
              
            end = i; 
            System.out.println("end value is: "+end);
        } 
        i--; 
    } 
      
    start = 0; 
    while(start != end) 
    {
        result += str.charAt(start++); 
    }
    return result; 
}
  
// Driver code 
public static void main(String[] args) 
{ 
    String str = "Pranav is a good boy"; 
    System.out.print(wordReverse1(str)); 
} 
} 


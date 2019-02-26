package NetWorkingJava;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2 {
	
	public static void main(String ar[]) throws Exception {
		
		Socket s=new Socket("localhost", 7643);
		
		DataOutputStream dom=new DataOutputStream(s.getOutputStream());
		
		BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		BufferedReader kb=new BufferedReader(new InputStreamReader(System.in));
		String str1,str2;
		while(!(str1 = kb.readLine()).equals("Exit")) {
			dom.writeBytes(str1+"\n");
			str2=br.readLine();
			System.out.println(str2);
		}
		
		dom.close();
		br.close();
		kb.close();
		s.close();
	}

}

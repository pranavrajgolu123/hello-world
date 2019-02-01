package NetWorkingJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClinetClass {
	
	public static void main(String arg[]) throws UnknownHostException, IOException {

	Socket s=new Socket("localhost", 7777);
	
	InputStream in=s.getInputStream();
	
	BufferedReader br=new BufferedReader(new InputStreamReader(in));
	
	String str;
	
	while((str = br.readLine()) != null)	
	System.out.println("From server: "+str);
	
	br.close();
	s.close();
	}
}

package NetWorkingJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class KnowingIPAddress {

	public static void main(String arg[]) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the website name");
		String name=br.readLine();
		try {
			InetAddress ip=InetAddress.getByName(name);
			System.out.println("Your Ip Address=  "+ip);
			System.out.println("---------------------------------------");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		URL obj=new URL("http://dreamtechpress.com/index.html");
		System.out.println("Protocol:  "+obj.getProtocol()); 
		System.out.println("Host:  "+obj.getHost());
		System.out.println("File:  "+obj.getFile());
		System.out.println("Port:  "+obj.getPort());
		System.out.println("Path:  "+obj.getPath());
		System.out.println("External form:  "+obj.toExternalForm());
	}
	
}

package NetWorkingJava;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class UrlConnectionClass {

	public static void main(String arg[]) throws IOException {
		
		URL obj=new URL("http://www.google.com/index.html");
		URLConnection conn=obj.openConnection();
		
		System.out.println("Date:  "+conn.getDate());
		
		System.out.println("Connection-Type:  "+conn.getContentType());
		
		System.out.println("Expiry:  "+conn.getExpiration());
		
		System.out.println("Last modified :   "+new Date(conn.getLastModified()));
		
		int i=conn.getContentLength();
		System.out.println("Length of content: "+i);
		if(i == 0) {
			System.out.println("Content not available");
		}
		else {
			int ch;
			InputStream in=conn.getInputStream();
			while((ch = in.read()) != -1) {
				System.out.println((char) ch);
			}
		}
	}
}

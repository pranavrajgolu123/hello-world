package NetWorkingJava;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerClass {
	
	public static void main(String arg[]) throws IOException {
	
	ServerSocket st=new ServerSocket(7777);
	
	Socket s=st.accept();
	System.out.println("Established Connection");
	
	OutputStream out=s.getOutputStream();
	
	PrintStream pt=new PrintStream(out);
	pt.println("Hii");
	pt.println("Pranav");
	
	out.close();
	st.close();
	s.close();
	
	}
}

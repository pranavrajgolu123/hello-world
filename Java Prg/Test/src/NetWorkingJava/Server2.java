package NetWorkingJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	public static void main(String arg[]) throws Exception{
		
		ServerSocket st=new ServerSocket(8888);
		
		Socket s=st.accept();
		
		PrintStream ps=new PrintStream(s.getOutputStream());
		
		BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		BufferedReader kb=new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			
			String str1,str2;
			while((str1 = br.readLine()) != null) {
				System.out.println(str1);
				str2=kb.readLine();
				ps.println(str2);
			}
			ps.close();
			br.close();
			kb.close();
			st.close();
			s.close();
			System.exit(0);
		}
		
		
	}
}

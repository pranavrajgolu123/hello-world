package JdbcPrg;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StorigFileIntoDatabase {
	
	public static void main(String s[]) throws ClassNotFoundException, SQLException, IOException {
  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/data3","pranav","password"); 
			
			PreparedStatement ps=con.prepareStatement(  
					"insert into myfile values(?,?)");  
					              
					File f=new File("d:\\myfile.txt");  
					FileReader fr=new FileReader(f); 
					
					              
					ps.setInt(2,101);  
					ps.setCharacterStream(1,fr,(int)f.length());  
					int i=ps.executeUpdate();  
					System.out.println(i+" records affected");  
	
					// Fetch the Stored file into the databases//
					Statement smt=con.createStatement();
					ResultSet rs=smt.executeQuery("select * from myfile");  
					rs.next();//now on 1st row  
					              
					Clob c=rs.getClob(1);  
					Reader r=c.getCharacterStream();              
					              
					FileWriter fw=new FileWriter("d:\\retrivefile.txt");  
					              
					int i1;  
					while((i1=r.read())!=-1)  
					fw.write((char)i1);  
					              
					fw.close();  
					con.close();  
					              
					System.out.println("success");   
	}

}

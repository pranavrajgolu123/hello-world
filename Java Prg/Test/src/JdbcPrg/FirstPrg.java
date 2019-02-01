package JdbcPrg;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FirstPrg {
	public static void main(String args[]){  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/data","pranav","password");  
		//here sonoo is database name, root is username and password  
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from raj");  
		while(rs.next()) {
		System.out.println("id: "+rs.getLong(1));  
		System.out.println("name: "+rs.getString(2)); 
		}
		/*int n=stmt.executeUpdate("create table raj(id long,name varchar(20))");*/
		/*int n=stmt.executeUpdate("insert into raj values(87,'p1')");*/
	//	System.out.println("n value: "+n); 
		con.close();  
		}catch(Exception e){ System.out.println(e);}  
		}  
		}  
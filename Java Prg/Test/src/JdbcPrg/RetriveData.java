package JdbcPrg;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RetriveData {
	public static void main(String args[]){  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/data3","pranav","password");  
		//here sonoo is database name, root is username and password  
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from raj2");  
		while(rs.next()) {
		System.out.println("id: "+rs.getLong(1));  
		System.out.println("name: "+rs.getString(2)); 
		}
		
		ResultSetMetaData rsmd=rs.getMetaData();  
		
		System.out.println("Column Name of 1st column: "+rsmd.getTableName(1));  
		System.out.println("Total columns: "+rsmd.getColumnCount());  
		System.out.println("Column Name of 1st column: "+rsmd.getColumnName(1));  
		System.out.println("Column Type Name of 1st column: "+rsmd.getColumnTypeName(1)); 
		
		System.out.println("Column Name of 1st column: "+rsmd.getColumnName(2));  
		System.out.println("Column Type Name of 1st column: "+rsmd.getColumnTypeName(2)); 
		
		DatabaseMetaData dbmd=con.getMetaData();  
		String tables[]= {"TABLE"};
		String table[]={"VIEW"};  
		rs=dbmd.getTables(null, null, null, table);
		  
		System.out.println("Driver Name: "+dbmd.getDriverName());  
		System.out.println("Driver Version: "+dbmd.getDriverVersion());  
		System.out.println("UserName: "+dbmd.getUserName());  
		System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());  
		System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
		System.out.println("Database Result Tables: ");
		while (rs.next()) {
			
			System.out.println(rs.getString(3)); 
			
		}
		  
		/*int n=stmt.executeUpdate("create table raj(id int,name varchar(20))");*/
	//	System.out.println("n value: "+n); 
		con.close();  
		}catch(Exception e){ System.out.println(e);}  
		}  
		}  
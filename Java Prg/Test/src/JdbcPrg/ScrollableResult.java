package JdbcPrg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollableResult {
	
	public static void main(String arg[]) throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/data3","pranav","password");
		Statement smt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet rs=smt.executeQuery("Select *from raj2");
		
		while(rs.next()) {
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));
			System.out.println("=================================");
		}
		
		rs.first();
		System.out.println(rs.getInt(1));
		System.out.println(rs.getString(2));
		System.out.println("=================================");
		
		rs.absolute(3);
		System.out.println("====absolute(3)====");
		System.out.println(rs.getInt(1));
		System.out.println(rs.getString(2));
		System.out.println("=================================");
		
		
		System.in.read();		
		rs.last();
		System.out.println("No of Rows"+rs.getRow());
		
		/*rs=smt.executeQuery("Select id,name from raj2");//Always make primary keys in the table
		rs.absolute(5);
		rs.updateInt(1, 5);
		rs.updateString(2, "pranavraj5");
		rs.updateRow();*/
		
		rs.absolute(1);
		rs.deleteRow();
		
		rs.close();
		con.close();
		
		
		
		
	}

}

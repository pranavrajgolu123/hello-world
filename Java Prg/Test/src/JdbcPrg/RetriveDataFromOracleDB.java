package test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
	public class RetriveDataFromOracleDB {
		 
		public static void main(String args[]){  
			 File f = null;
		      File f1 = null;
		      String v, v1;
		      boolean bool = false;
		      ArrayList<String> tableList=new ArrayList<String>();
		      List<String> folderList = new ArrayList<String>();
			try{  
				Class.forName("oracle.jdbc.driver.OracleDriver");  
				Connection conection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ora12c","roc","roc");  
				 
				Statement stmt=conection.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from IMAGE_TBL");    
 
			while(rs.next()) {
			//System.out.println("Path: "+rs.getString(4)); 
				tableList.add(rs.getString(4));
			}

			conection.close();  
			}
			catch(Exception e)
			{ 
				System.out.println(e);
			}
			
			
			
			File[] files = new File("D:\\apache-tomcat-7.0.93\\webapps\\entrypoint-3.6.1.4\\themes\\common\\images\\default\\icons").listFiles();
			//If this pathname does not denote a directory, then listFiles() returns null. 

			for (File file : files) {

				folderList.add("/icons/"+file.getName());
	
			}
			
			tableList.removeAll(folderList);
			System.out.println(tableList.size());
			for(String str : tableList)
				System.out.println(str);
		}
		
}  


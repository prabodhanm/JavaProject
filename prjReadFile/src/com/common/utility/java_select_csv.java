package com.common.utility;

import java.io.FileWriter;
import java.sql.*;

import au.com.bytecode.opencsv.CSVWriter;



import static com.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static com.opencsv.CSVWriter.NO_QUOTE_CHARACTER;
//import com.ReadFile.Utility.*;

public class java_select_csv {
	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/STUDENTS";

	   //  Database credentials
	   static final String USER = "username";
	   static final String PASS = "password";
	   
	   //private static String JDBC_CONNECTION_URL = 
		//	"jdbc:sqlserver://10.208.4.170:1433;databaseName=RFOperations;user=sappo;password=%R&b5k!^A";
	   private static FileData fdata = new FileData();
	   public void insertToCSV(){
		   {
			    
			   
			   /*String JDBC_CONNECTION_URL = 
						"jdbc:sqlserver://10.208.4.170:1433;databaseName=RFOperations;user=sappo;password=%R&b5k!^A";*/
			   
			    /*String JDBC_CONNECTION_URL = 
						"jdbc:sqlserver://127.0.0.1:1433;instance=SQLEXPRESS;databaseName=Stock;user=sa;password=parvani21;";*/
			   Connection conn = null;
			   Statement stmt = null;
			   CSVWriter writer = null;
			   
			   Utility utility = new Utility();
			   fdata = utility.ReadProperty();
			   
			   
			   
			   try{
			      //STEP 2: Register JDBC driver
			      //Class.forName("oracle.jdbc.driver.OracleDriver");
				   Class.forName(fdata.DRIVER);
			      //STEP 3: Open a connection
			      System.out.println("Connecting to a selected database...");
			      conn = DriverManager.getConnection(fdata.JDBC_CONNECTION_URL + ";user=" + fdata.User + ";password=" + fdata.Password);
			      System.out.println("Connected database successfully...");
			      
			      //STEP 4: Execute a query
			      System.out.println("Creating statement...");
			      stmt = conn.createStatement();

			      String sql = "select top 10 * from " + fdata.Step2Table + ";";
			      ResultSet rs = stmt.executeQuery(sql);
			      //STEP 5: Extract data from result set
//			      while(rs.next()){
//			         //Retrieve by column name
//			         
//			         String first = rs.getString("OrderNumber");
//			          
//			         System.out.print(", First: " + first); 
//			      }
			      writer = new CSVWriter(new FileWriter(fdata.TargetFile),  DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER);


			    //  java.sql.ResultSet myResultSet = .... //your resultset logic here

			      writer.writeAll(rs, true);

			      writer.close();
			      
			      rs.close();
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            conn.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try
			   System.out.println("Goodbye!");
			}
	   }
	   
	   public static void main(String[] args) {
	   }//end main
	   
}
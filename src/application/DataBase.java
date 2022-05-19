package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
	private static DataBase Db=null;
	private static Connection conn=null;
	private DataBase() {}
	
	public static Connection connect() {
		if(conn==null) {
			try {
				Db=new DataBase();
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		        String connectionURL="jdbc:sqlserver://localhost:1433;databaseName=BANK;encrypt=false";		        
		        conn = DriverManager.getConnection(connectionURL,"sa",".");
		        System.out.println("DB Connected.");
			} catch (Exception e) {
				System.out.println("ERROR !!"+e);
			}
		}
		
		return conn;
	}

}

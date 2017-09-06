package datainsert;

import java.sql.*;
public class Dataconn {

	private String username;
	private String url;
	private String password;
	private Connection connection;
	public Dataconn()
	{
		 this.url = "jdbc:oracle:thin:@localhost:1521:xe";
		 this.username = "SYSTEM";
		 this.password = "jurong123";
	}
	public Connection getconn()
	{
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.connection = DriverManager.getConnection(this.url,this.username,this.password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.connection;
		
	}
	public void closeconn()
	{
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

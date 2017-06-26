package rs.ftn.mr.webforum.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DbConnection {
	private static Connection con;
	public void setConnection(Connection conn)
	{
		con = conn;
	}
	
	/**
	 * Gets the current thread's connection
	 * @return Connection
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */

	
	public static Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException
	{
    	if (con == null){
    		Class.forName("org.sqlite.JDBC").newInstance();
    		con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\mihajlo\\JavaWebProjekti\\rs.ftn.mr.webforum\\database\\webforum.db");	
    	}
	    
		return con; 
	}	
}

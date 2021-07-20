package Model;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * classe singleton che gestisce la connessione con il database.
 */
public class ConnessioneDB{
		
	private  static Connection connessione = null;
	
	static{
		String url = "jdbc:mysql://localhost:3306/bikesharing?user=user&password=password";
		try{
			connessione = DriverManager.getConnection(url);
			System.out.println("Connessione con DB avvenuta!");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @return connessione
	 */
	public static Connection getConnessione(){
		return connessione;
	}
}
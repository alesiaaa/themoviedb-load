import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection {
	
	private Connection conn = null;
	
	public DBConnection (String db, String username, String password){
		try {
			this.conn = DriverManager.getConnection(db, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return this.conn;
	}
	
}

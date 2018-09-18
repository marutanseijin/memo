
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class SqlExecutor {

	private String jdbcUrl;
	private String dbUser;
	private String dbPass;
	public Connection conn;
	public PreparedStatement ps;
	public ResultSet rs;

	//set jdbc properties. confirm seleniumtest.prop exists on class path.
	public SqlExecutor() {
		
        Properties prop = new Properties();
        String file = "\\seleniumtest.prop";
		InputStream is = getClass().getClassLoader().getResourceAsStream(file);
		
		try {
			
			prop.load(is);
			
		} catch (IOException e) {
			
			System.out.println("Failed to load prop file. file name: " + file);
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
		this.jdbcUrl = prop.getProperty("jdbcUrl");
		this.dbUser = prop.getProperty("dbUser");
		this.dbPass = prop.getProperty("dbPass");
	}
	
    /**
    * Use this for update, insert, delete
    * @param SQLstatement as a String. 
    * @return Updating result as a int. 
    */
	public int executeUpdate(String statement, String... args){
		
		close();
		
		try {
			
			this.conn = DriverManager.getConnection(this.jdbcUrl, this.dbUser, this.dbPass);
			this.ps = this.conn.prepareStatement(statement);
			
			for(int i = 0; i < args.length; i++){
				ps.setString(i + 1, args[i]);
			}
			
			return this.ps.executeUpdate();
			
		} catch (SQLException e) {
			
			System.out.println("Failed to establish the connection.");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}
	
    /**
    * Use this for select
    * @param SQLstatement as a String. 
    * @return Result of query as ResultSet. 
    */
	public void executeQuery(String statement, String... args){
		
		close();
		
		try {
			
			this.conn = DriverManager.getConnection(this.jdbcUrl, this.dbUser, this.dbPass);
			this.ps = this.conn.prepareStatement(statement);
			
			for(int i = 0; i < args.length; i++){
				ps.setString(i + 1, args[i]);
			}
			
			this.rs = this.ps.executeQuery();
			
		} catch (SQLException e) {
			
			System.out.println("Failed to establish the connection.");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
	}
	
    /**
    * invoke this always after executeQuery / executeUpdate
    */
	public void close(){
		
		if(this.rs != null){
			try {
				this.rs.close();
			} catch (SQLException e) {
				System.out.println("Failed to close ResultSet");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		if(this.ps != null){
			try {
				this.ps.close();
			} catch (SQLException e) {
				System.out.println("Failed to close PreparedStatement");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		if(this.conn != null){
			try {
				this.conn.close();
			} catch (SQLException e) {
				System.out.println("Failed to close Connection");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

}

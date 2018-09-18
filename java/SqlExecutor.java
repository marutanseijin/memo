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
	public int executeUpdate(String statement){
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(this.jdbcUrl, this.dbUser, this.dbPass);
			ps = conn.prepareStatement(statement);
			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to establish the connection.");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if(ps != null){
				try {ps.close();} catch (SQLException e) {}
			}
			if(conn != null){
				try {conn.close();} catch (SQLException e) {}
			}
		}
	}
    
     /**
    * Use this for select
    * @param SQLstatement as a String. 
    * @return Result of query as ResultSet. 
    */
	public ResultSet executeQuery(String statement){
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(this.jdbcUrl, this.dbUser, this.dbPass);
			ps = conn.prepareStatement(statement);
			return ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("Failed to establish the connection.");
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if(ps != null){
				try {ps.close();} catch (SQLException e) {}
			}
			if(conn != null){
				try {conn.close();} catch (SQLException e) {}
			}
		}
	}

}

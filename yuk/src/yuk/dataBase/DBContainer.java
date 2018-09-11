package yuk.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public abstract class DBContainer {
	protected static Map<String, DBController> dbMap = new HashMap<String, DBController>();
	private Connection connection;
	private String dbName = "";
	private String id = "";
	private String pass = "";
	private String driver = "";
	private String connect = "";
	
	public String getDbName() {
		return dbName;
	}
	public String getId() {
		return id;
	}
	public String getPass() {
		return pass;
	}
	public String getDriver() {
		return driver;
	}
	public String getConnect() {
		return connect;
	}
	
	public static boolean isFullConntcted() throws SQLException{
		for(DBController dc : dbMap.values()){
			if(!dc.isConnected())
				return false;
		}
		return true;
	}
	
	protected DBContainer(String name, String id, String pass, String driver, String connect) throws Exception{
			Class.forName(driver);
			connection = DriverManager.getConnection(connect, id, pass);
			connection.setAutoCommit(true);
			this.dbName = name;
			this.id = id;
			this.pass = pass;
			this.driver = driver;
			this.connect = connect;		
	}
	
	protected Connection getConnection() throws Exception{
		if(!isConnected())
			reConnection();
		return connection;
	}
	
	protected void reConnection() throws SQLException{
		synchronized (connection) {
			if(!connection.isClosed())
				connection.close();
			connection = null;
			connection = DriverManager.getConnection(connect, id, pass);
			connection.setAutoCommit(true);
		}
	}	
	
	public boolean isConnected() throws SQLException{
		if(connection.isClosed())
			return false;
		return true;
	}
	
	public void reopenDataBase() throws Exception{
		Statement statement= null;
			try {
				synchronized (connection) {
					Connection connection = getConnection();
					statement = connection.createStatement();
					statement.execute("SHUTDOWN COMPACT");
				}
				reConnection();
			} catch (Exception e) {
				throw e;
			}
			finally{
				if(statement != null)
					statement.close();
				statement = null;
			}
	}
	
	/**
	 * need to database stop Action than override this;
	 * this func is not auto call by yuk lib.
	 */
	public static void stopAll()  throws Exception{
		for(String s : dbMap.keySet()){
			DBContainer dc = dbMap.get(s);
			dc.stop();
		}
	}
	
	public void stop()  throws Exception{
		Statement statement = null;
		try {
			Connection connection = getConnection();
			if(!connection.isClosed()){
				statement = connection.createStatement();
				statement.execute("SHUTDOWN COMPACT");
			}
		} catch (Exception e) {
			throw e;
		}
		finally{
			if(statement != null)
				statement.close();	
			statement = null;
		}
		
	}
	
	/**
	 * need to database start Action than override this;
	 */
	public void init() throws Exception{
		Statement statement= null;
		try {
			Connection connection = getConnection();
			statement = connection.createStatement();
			statement.execute("SET AUTOCOMMIT ON");
			statement.execute("SET CACHE_SIZE 10240");
			statement.execute("SET DB_CLOSE_DELAY 1");
			statement.execute("SET LOG 2");
			statement.execute("SET LOCK_MODE 3");
			statement.execute("SET MAX_LOG_SIZE 10");
			statement.execute("SET MAX_MEMORY_ROWS 40000");
			statement.execute("SET TRACE_LEVEL_FILE 0");
			statement.execute("SET WRITE_DELAY 1");
		} catch (Exception e) {
			throw e;
		}
		finally{
			if(statement != null)
				statement.close();
			statement = null;
		}
	}	
}

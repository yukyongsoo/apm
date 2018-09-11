package yuk.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import yuk.model.AbsFake;

public class DBController extends DBContainer{
	public DBController(String name, String id, String pass, String driver,String connect) throws Exception{
		super(name, id, pass, driver, connect);
		dbMap.put(name, this);
		init();		
	}
	
	public static DBController getDb(String name) throws Exception{
		if(dbMap.get(name) != null)
			return dbMap.get(name);
		else
			throw new Exception(name + "db is not exist");
	}
	
	public <T extends AbsFake> Collection<T> ReadPrepareSql(Class<T> c,String sql, Object...meta) throws Exception{
		Connection connection = getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			for(int i =0;i < meta.length; i++)
				setType(preparedStatement,meta[i],i+1);			
			resultSet = preparedStatement.executeQuery();
			resultSet.setFetchSize(100);
			DBMapper<T> mapper = new DBMapper<T>(resultSet,c);
			return mapper.getData();
		} catch (SQLException e) {
			throw e;
		}
		finally{
			if(resultSet != null)
			resultSet.close();
			if(preparedStatement != null){
				preparedStatement.clearParameters();
				preparedStatement.close();	
			}
			resultSet = null;
			preparedStatement = null;
		}
	}
	
	public <T extends AbsFake> Collection<T> ReadSql(Class<T> c,String sql) throws Exception{
		Connection connection = getConnection();
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.setFetchSize(1000);
			DBMapper<T> mapper = new DBMapper<T>(resultSet,c);
			return mapper.getData();
		} catch (SQLException e) {
			throw e;
		}
		finally{
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();	
			resultSet = null;
			statement = null;
		}		
	}
		
	public void WritePrepareSql(String sql, Object...meta) throws Exception{
		Connection connection = getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {	
				preparedStatement = connection.prepareStatement(sql);
				for (int i = 0; i < meta.length; i++)
					setType(preparedStatement, meta[i], i + 1);
				preparedStatement.execute();
				preparedStatement.clearParameters();
		} catch (SQLException e) {
			throw e;
		}
		finally{
			if(resultSet != null)
			resultSet.close();
			if(preparedStatement != null)
			preparedStatement.close();
			resultSet = null;
			preparedStatement = null;			
		}		
	}
	
	public void WriteSql(String sql) throws Exception{
		Connection connection = getConnection();
		Statement statement = null;
		try {
				statement = connection.createStatement();
				statement.execute(sql);
		} catch (SQLException e) {
			throw e;
		}
		finally{
			if (statement != null)
				statement.close();	
			statement = null;
		}
		
	}
		
	public void WriteBatchPrepareSql(String sql, Collection<String[]> list) throws Exception{
		Connection connection = getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
				preparedStatement = connection.prepareStatement(sql);
				for (String[] meta : list) {
					for (int i = 0; i < meta.length; i++) {
						setType(preparedStatement, meta[i], i + 1);	
					}
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				preparedStatement.clearParameters();
		
		} catch (SQLException e) {
			throw e;
		}
		finally{
			if(resultSet != null)
			resultSet.close();
			if(preparedStatement != null)
			preparedStatement.close();
			resultSet = null;
			preparedStatement = null;
		}		
	}
	
	private void setType(PreparedStatement statement, Object object, int index) throws Exception{
		if(object instanceof String)
			statement.setString(index, (String) object);
		else if(object instanceof Long)
			statement.setLong(index, (Long) object);
		else if(object instanceof Boolean)
			statement.setBoolean(index, (Boolean) object);
		else
			statement.setObject(index, object);
	}
}

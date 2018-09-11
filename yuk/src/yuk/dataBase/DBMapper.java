package yuk.dataBase;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import yuk.model.AbsFake;
import yuk.model.etc.EventData;
import yuk.model.etc.ServerDBData;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;

/**
 * 
 * @author yongsoo
 * do not make instance. it is automatically call by dbcontroller.class
 * @param <T>
 */
public class DBMapper<T extends AbsFake> {
	private ResultSet resultSet;
	private List<String> columnList = new ArrayList<String>();
	private Collection<T> data = new ArrayList<T>();
	private Class<T> type;

	protected DBMapper(ResultSet resultSet, Class<T> c) throws Exception {
		this.resultSet = resultSet;
		this.type = c;
		columnMapping();
		typeMapping();
	}

	public Collection<T> getData() {
		return data;
	}
	
	private void columnMapping() throws SQLException{
		ResultSetMetaData meta  = resultSet.getMetaData();
		for(int i = 0 ; i < meta.getColumnCount(); i++){
			String name =  meta.getColumnName(i+1);		
			columnList.add(name);
		}
	}
		
	private void typeMapping() throws Exception{
		//etc
		if(type ==  EventData.class){
			EtcDbMapper etc = new EtcDbMapper();
			etc.eventMapping(resultSet, (Collection<EventData>) data);
		}
		else if(type ==  ServerDBData.class){
			EtcDbMapper etc = new EtcDbMapper();
			etc.dbMapping(resultSet,columnList,(Collection<ServerDBData>) data);
		}
		//single
		else if(type == LogData.class){
			SingleDbMapper single = new SingleDbMapper();
			single.logMapping(resultSet,(Collection<LogData>)data);
		}
		else if(type == MacData.class){
			SingleDbMapper single = new SingleDbMapper();
			single.macMapping(resultSet,(Collection<MacData>)data);
		}
		else if(type == ResourceData.class){
			SingleDbMapper single = new SingleDbMapper();
			single.resourceMapping(resultSet,(Collection<ResourceData>)data);
		}
		else if(type == StackData.class){
			SingleDbMapper single = new SingleDbMapper();
			single.stackMapping(resultSet,(Collection<StackData>)data);
		}
		else if(type == TransactionData.class){
			SingleDbMapper single = new SingleDbMapper();
			single.transMapping(resultSet,(Collection<TransactionData>)data);
		}
		//multi
		else if(type == LogContainer.class){
			MultiDbMapper multi = new MultiDbMapper();
			multi.logMapping(resultSet,(Collection<LogContainer>)data);
		}
		else if(type == ResourceContainer.class){
			MultiDbMapper multi = new MultiDbMapper();
			multi.resourceMapping(resultSet,(Collection<ResourceContainer>)data);
		}
		else if(type == TransContainer.class){
			MultiDbMapper multi = new MultiDbMapper();
			multi.transMapping(resultSet,(Collection<TransContainer>)data);	
		}
		else
			throw new Exception("not supported Mapping Type");
	}
	


}












































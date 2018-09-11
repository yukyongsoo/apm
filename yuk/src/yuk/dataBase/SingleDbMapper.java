package yuk.dataBase;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;

import yuk.model.etc.SubResourceData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;

public class SingleDbMapper {
	protected SingleDbMapper(){}

	public void logMapping(ResultSet resultSet, Collection<LogData> data) throws Exception {
		while(resultSet.next()){
			LogData log = new LogData(resultSet.getString("NAME"),resultSet.getString("LEVEL"), resultSet.getString("TEXT"));
			log.time = resultSet.getLong("TIME");
			data.add(log);
		}
	}

	public void macMapping(ResultSet resultSet, Collection<MacData> data) throws Exception {
		while(resultSet.next()){
			MacData mac = (MacData)resultSet.getObject("Data");
			mac.time = resultSet.getLong("TIME");
			mac.name = resultSet.getString("NAME");
			mac.command = resultSet.getString("COMMAND");
			data.add(mac);
		}
	}

	public void resourceMapping(ResultSet resultSet, Collection<ResourceData> data) throws Exception, Exception {
		while(resultSet.next()){
			ResourceData resource = new ResourceData(resultSet.getString("NAME"), 
					resultSet.getString("COMMAND"), 
					resultSet.getString("RESOURCEID"));
			resource.time = resultSet.getLong("TIME");
			resource.map = (Map<String, SubResourceData>) resultSet.getObject("META");
			data.add(resource);
		}
	}

	public void stackMapping(ResultSet resultSet, Collection<StackData> data) throws Exception {
		while(resultSet.next()){
			StackData stack = (StackData)resultSet.getObject("Data");
			stack.time = resultSet.getLong("TIME");
			stack.name = resultSet.getString("NAME");
			data.add(stack);
		}
	}

	public void transMapping(ResultSet resultSet, Collection<TransactionData> data) throws Exception {
		while(resultSet.next()){
			TransactionData trans = new TransactionData(resultSet.getString("NAME"), 
					resultSet.getString("GROUPED"),
					resultSet.getLong("SUC"),
					 resultSet.getLong("FAIL"),
					resultSet.getLong("AVGVALUE"));
			trans.time = resultSet.getLong("TIME");
			data.add(trans);
		}
	}
}

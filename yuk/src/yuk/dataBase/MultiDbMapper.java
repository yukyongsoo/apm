package yuk.dataBase;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import yuk.model.etc.SubResourceData;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.LogData;
import yuk.model.single.ResourceData;
import yuk.model.single.TransactionData;

public class MultiDbMapper{
	protected MultiDbMapper(){}

	public void logMapping(ResultSet resultSet, Collection<LogContainer> data) throws Exception {
		Map<String, LogContainer> map = new HashMap<String, LogContainer>();
		LogContainer container;
		while(resultSet.next()){
			LogData log = new LogData(resultSet.getString("NAME"),resultSet.getString("LEVEL"), resultSet.getString("TEXT"));
			log.time = resultSet.getLong("TIME");
			container = map.get(log.name);
			if(container == null){
				container = new LogContainer();
				container.name = log.name;
				map.put(log.name, container);
			}
			container.list.add(log);
		}
		data.addAll(map.values());
	}

	public void resourceMapping(ResultSet resultSet, Collection<ResourceContainer> data) throws Exception {
		Map<String, ResourceContainer> map = new HashMap<String, ResourceContainer>();
		ResourceContainer container;
		while(resultSet.next()){
			ResourceData resource = new ResourceData(resultSet.getString("NAME"), 
					resultSet.getString("COMMAND"), 
					resultSet.getString("RESOURCEID"));
			resource.time = resultSet.getLong("TIME");
			resource.map = (Map<String, SubResourceData>) resultSet.getObject("META");
			
			container = map.get(resource.name);
			if(container == null){
				container = new ResourceContainer();
				container.name = resource.name;
				map.put(container.name, container);
			}
			container.list.add(resource);
		}
		data.addAll(map.values());
	}

	public void transMapping(ResultSet resultSet, Collection<TransContainer> data) throws Exception {
		Map<String, TransContainer> map = new HashMap<String, TransContainer>();
		TransContainer container;
		while(resultSet.next()){
			TransactionData trans = new TransactionData(resultSet.getString("NAME"), 
					resultSet.getString("GROUPED"),
					resultSet.getLong("SUC"),
					 resultSet.getLong("FAIL"),
					resultSet.getLong("AVGVALUE"));
			trans.time = resultSet.getLong("TIME");
			
			container = map.get(trans.name);
			if(container == null){
				container = new TransContainer();
				container.name = trans.name;
				map.put(container.name, container);
			}
			container.list.add(trans);
		}
		data.addAll(map.values());
	}
}

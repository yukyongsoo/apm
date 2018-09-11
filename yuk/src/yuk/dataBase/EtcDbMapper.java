package yuk.dataBase;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;

import yuk.model.etc.EventData;
import yuk.model.etc.ServerDBData;

public class EtcDbMapper{
	protected EtcDbMapper(){}
	
	public void eventMapping(ResultSet resultSet, Collection<EventData> data) throws Exception {
		while(resultSet.next()){
			EventData event = new EventData(resultSet.getString("NAME"), 
					resultSet.getString("EVENTLEVEL"),
					resultSet.getString("TEXT"));
			event.time = resultSet.getLong("TIME");
			data.add(event);
		}
	}

	public void dbMapping(ResultSet resultSet, List<String> columnList, Collection<ServerDBData> data) throws Exception  {
		ServerDBData dbData = new ServerDBData();
		dbData.columnList = columnList;
		int columnSize = columnList.size();
		while(resultSet.next()){
			String[] row = new String[columnSize];
			for(int i =0; i<(columnSize);i++){
				row[i] = resultSet.getString(i+1);
			}
			dbData.dataList.add(row);
		}
		data.add(dbData);
	}
}

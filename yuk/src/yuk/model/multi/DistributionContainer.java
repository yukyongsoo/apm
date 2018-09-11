package yuk.model.multi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import yuk.model.etc.RowTransactionData;
import yuk.model.single.DistributionData;

public class DistributionContainer extends AbsContainer{
	public long time = 0;
	public Map<Integer,DistributionData> list = new ConcurrentHashMap<Integer, DistributionData>();
	
	@Override
	public long getSize() {
		return list.size();
	}
	
	public void addData(RowTransactionData row){
		Integer index = (int)(row.res / 100);
		DistributionData data = list.get(index);
		if(data == null){
			data = new DistributionData();
			data.count = 1;
			data.totalTime = row.res;
			data.avgTime = row.res;
			list.put(index, data);
		}
		else{			
			data.count += 1;
			data.totalTime += row.res;			
		}
	}
	
	public void addData(DistributionData rowDis){
		Integer index = (int) (rowDis.avgTime / 100);
		DistributionData data = list.get(index);
		if(data == null){
			list.put(index, rowDis);
		}
		else{
			data.count += rowDis.count;
			data.totalTime += rowDis.totalTime;
		}		
	}
	
	public void cal(){
		for(DistributionData sub : list.values())
			sub.cal();
	}
}

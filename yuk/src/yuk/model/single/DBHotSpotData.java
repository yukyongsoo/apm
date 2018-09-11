package yuk.model.single;

public class DBHotSpotData  extends AbsData{
	public DBHotSpotData(String name, String query){
		this.name = name;
		this.queryName = query;
	}
	
	public String queryName = "";
	public long count = 0;
	public long avgTime = 0;
	public long totalTime = 0;
	
	public void cal(){
		if(count != 0)
		avgTime = totalTime / count;
	}
	
	public void modelAdd(DBHotSpotData source,boolean cal){
		count += source.count;
		totalTime += source.totalTime;
		if(cal)
			cal();
	}
}

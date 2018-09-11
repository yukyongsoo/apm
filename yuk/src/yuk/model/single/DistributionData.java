package yuk.model.single;

public class DistributionData extends AbsData{
	public long totalTime = 0;
	public long count = 0;
	public long avgTime = 0;
	
	public void cal(){
		if(count != 0)
			avgTime = totalTime / count;
	}
}

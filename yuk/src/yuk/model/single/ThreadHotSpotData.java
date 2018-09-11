package yuk.model.single;

public class ThreadHotSpotData  extends AbsData{
	public String methodName = "";
	public long count = 0;
	public long avgTime = 0;
	public long totalTime = 0;
	public long start = 0;
	
	public ThreadHotSpotData(String myName) {
		this.name = myName;
	}

	public void cal(){
		if(count != 0)
			avgTime = totalTime / count;
	}
	
	public void modelAdd(ThreadHotSpotData source , boolean cal){
		this.count += source.count;
		this.totalTime += source.totalTime;
		if(cal)
			cal();
	}
}

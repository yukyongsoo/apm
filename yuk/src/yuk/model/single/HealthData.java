package yuk.model.single;

public class HealthData  extends AbsData{
	public HealthData(String name){
		this.name = name;
	}
	
	public long time = 0;
	public boolean status = true;
	public String message = "";
}

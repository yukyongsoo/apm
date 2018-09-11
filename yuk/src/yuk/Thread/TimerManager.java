package yuk.Thread;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class TimerManager {
	private long last = System.currentTimeMillis();
	private Map<String, Timer> timerMap = new HashMap<String, Timer>();
	private Map<String, TimerWork> workMap = new HashMap<String, TimerWork>();
			
	public void addWork(String name, TimerWork work, String delayType, long recusive) throws Exception{
		if(timerMap.get(name) != null)
			throw new Exception("current work name exist");
		Timer timer = new Timer(name);
		Date date = getDelay(delayType);
		timer.scheduleAtFixedRate(work, date, recusive);
		timerMap.put(name, timer);
		workMap.put(name, work);
		last = date.getTime();
	}
	
	public boolean isWork(String name){
		if(timerMap.get(name) != null)
			return true;
		return false;
	}
	
	private Date getDelay(String type){
		//delay 300ms
		Date date = new Date(last + 300);
		return date;
	}
	
	public void removeWork(String name) throws Exception{
		if(timerMap.get(name) == null)
			throw new Exception("current work name not exist");
		Timer t = timerMap.get(name);
		t.cancel();
		timerMap.remove(name);
	}
	
	public void stopALL(){
		for(Timer t : timerMap.values()){
			t.cancel();
		}
	}
	
	public <T> T getWork(String name,Class<T> c){
		return (T) workMap.get(name);
	}
}

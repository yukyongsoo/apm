package monServer.event;

import java.util.Collection;

import monServer.config.XmlProperty;
import yuk.model.etc.EventData;

public class EventHandle {
	private static EventHandle handle;
	private AbsPostWork work;
	
	private EventHandle(AbsPostWork work) {
		this.work = work;
	}
	
	public static synchronized void init(AbsPostWork work){
		if(handle == null)
			handle = new EventHandle(work);
	}
	
	public static EventHandle getHandle() {
		return handle;
	}

	public void handleData(Collection<EventData> event) {
		for(EventData data : event){
			if(data.eventLog.contains("not connected") && XmlProperty.health)
				work.realWork(data);
			if(data.eventLog.contains("transaction") && XmlProperty.trans)
				work.realWork(data);
			if(data.eventLog.contains("SYSTEM CPU") && XmlProperty.cpu)
				work.realWork(data);
			if(data.eventLog.contains("App Memory") && XmlProperty.appMem)
				work.realWork(data);
			if(data.eventLog.contains("SYSTEM Memory") && XmlProperty.mem)
				work.realWork(data);
			if(data.eventLog.contains("Disk I/O") && XmlProperty.disk)
				work.realWork(data);
			if(data.eventLog.contains("Network I/O") && XmlProperty.net)
				work.realWork(data);
			if(data.eventLog.contains("Exception") && XmlProperty.excpt)
				work.realWork(data);		
		}
	}
}

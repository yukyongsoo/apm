package monui.ui.component.notify;

import yuk.dic.MsgDic;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import de.steinwedel.messagebox.MessageBox;

public class NotifyManager {
	private static Map<UI, NotifyManager> notifyMap = new HashMap<UI, NotifyManager>();
	public static NotifyManager getManager(UI ui){
		NotifyManager manager = notifyMap.get(ui);
		if(manager == null){
			manager = new NotifyManager(ui);
			notifyMap.put(ui, manager);
		}
		return manager;
	}
	
	public static void removeManager(UI ui){
		notifyMap.remove(ui);
	}
	
	private NotifyManager(UI ui){
		this.ui = ui;
	}
	UI ui;
	boolean messageLock = false;
	
	private synchronized boolean setLock(){
		if(!messageLock){
			messageLock = true;
			return messageLock;
		}
		else
			return false;
	}
	
	private synchronized void releaseLock(){
		if(messageLock)
			messageLock = false;
	}
	
	public void showErrorBox(String caption, String message){
		if(setLock()){
			MessageBox box = MessageBox.createError().withCaption(caption).withMessage(message);
			box.withCloseButton(new Runnable() {
				@Override
				public void run() {
					releaseLock();
				}
			}).open();
			ui.push();
		}
	}	
	
	public void showNotify(EventData data){
		if(setLock()){
			Notification notif = new Notification("Event :" + data.name,data.eventLog,Notification.Type.WARNING_MESSAGE);
			notif.setDelayMsec(10000);
			notif.setPosition(Position.BOTTOM_RIGHT);
			notif.show(ui.getPage());
		}
		releaseLock();
	}
	
	public boolean showErrordata(final CastHeader header){
		if((header.code != MsgDic.ok) && setLock()){
			MessageBox box = MessageBox.createWarning().withCaption("Waning!").withMessage(header.msg);
			box.withCloseButton(new Runnable() {
				@Override
				public void run() {
					releaseLock();
				}
			}).open();		
			ui.push();
			return true;
		}
		return false;
	}
}

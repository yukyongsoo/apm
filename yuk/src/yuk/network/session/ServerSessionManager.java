package yuk.network.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import yuk.model.etc.InitData;
import yuk.network.NetAccepter;

public class ServerSessionManager {
	NetAccepter parent;
	private Map<String, List<SessionWaraper>> sessionMap = new ConcurrentHashMap<String, List<SessionWaraper>>();
	private Map<String, InitData> childMap = new ConcurrentHashMap<String, InitData>();
	private Map<String, List<SessionWaraper>> castMap = new ConcurrentHashMap<String, List<SessionWaraper>>();
		
	public Map<String, InitData> getChildMap() {
		return childMap;
	}

	public void init(NetAccepter netAccepter) {
		this.parent = netAccepter;
	}
	
	public void putCastTarget(String name, SessionWaraper session){
		List<SessionWaraper> list = castMap.get(name);
		if(list == null){
			list = new ArrayList<SessionWaraper>();
			castMap.put(name, list);
		}
		list.add(session);
	}
	
	public List<SessionWaraper> getTarget(){
		List<SessionWaraper> list = new ArrayList<SessionWaraper>();
		for(List<SessionWaraper> child : castMap.values()){
			loop : for(SessionWaraper session : child){
				if(session.isWritable()){
					list.add(session);
					break loop;
				}
			}			
		}
		return list;
	}
	
	public SessionWaraper getSession(String key){
		List<SessionWaraper> list = sessionMap.get(key);
		if(list == null)
			return null;
		for(SessionWaraper session : list){
			if(session.isWritable())
				return session;
		}
		return null;
	}
	
	public void putSession(String key,SessionWaraper session){
		List<SessionWaraper> list;
		if(sessionMap.get(key) == null){
			list = new ArrayList<SessionWaraper>();
			sessionMap.put(key, list);
		}
		else
			list = sessionMap.get(key);
		list.add(session);
	}
	
	public void removeSession(Channel session){
		int remove = -1;
		for(List<SessionWaraper> list : sessionMap.values()){
			for(int i = 0 ; i < list.size(); i++){
				SessionWaraper temp = list.get(i);
				if(temp.checkChannel(session))
					remove = i;
			}
			if(remove > -1)
				list.remove(remove);
		}
	}
	
	public List<InitData> getAllChild() throws Exception {
		List<InitData> list = new ArrayList<InitData>();
		list.addAll(childMap.values());
		return list;
	}
	
	public void addChild(String key,InitData data){
		childMap.put(key, data);
	}		
	
	public String getParent(String name){
		for(String key : childMap.keySet()){
			InitData data = childMap.get(key);
			if(data.parent.equals(name)){
				return data.parent;
			}
		}		
		return null;
	}
}

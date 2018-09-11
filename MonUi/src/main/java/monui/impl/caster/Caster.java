package monui.impl.caster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import monui.impl.net.ServerControll;
import yuk.dic.MsgDic;
import yuk.model.AbsFake;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.multi.DBSpotContainer;
import yuk.model.multi.DistributionContainer;
import yuk.model.multi.ExcptContainer;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.ThreadContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.HealthData;
import yuk.model.single.MacData;
import yuk.model.single.StackData;
import yuk.util.CommonLogger;

public class Caster implements Serializable{
	public static Caster getCaster(String key) {
		return ServerControll.getCaster(key);
	}
	
	private List<InitData> initList = new ArrayList<InitData>();
	private Map<String,List<CasteeInterface>> broadCaster = new HashMap<String, List<CasteeInterface>>();
	private Map<Long, CasteeInterface> TargetCaster = new ConcurrentHashMap<Long, CasteeInterface>();
	
	public Map<Long, CasteeInterface> getTargetCaster() {
		return TargetCaster;
	}
	
	public void cleanUp() {
		 for(String key : broadCaster.keySet()){
			List<CasteeInterface> list = broadCaster.get(key); 
			 for(CasteeInterface ci : list){
				 if(!ci.isLived())
					 list.remove(ci);
			 }		 
		 }
	}


	public synchronized void addTarget(CasteeInterface ci){
		try {		
			if(TargetCaster.containsValue(ci)){
				for(Long key : TargetCaster.keySet()){
					if(ci.equals(TargetCaster.get(key)))
						TargetCaster.remove(key);
				}
			}
			TargetCaster.put(System.currentTimeMillis(), ci);
		} catch (Exception e) {
			CommonLogger.getLogger().warn(getClass(), "target add fail", e);
		}
	}
		
	public List<InitData> getInitList() {
		return initList;
	}

	public void setInitList(List<InitData> initList) {
		this.initList = initList;
	}
	
	public void register(CastInfo info, CasteeInterface ci){
		for(String target : info.target){
			List<CasteeInterface> list = broadCaster.get(target);
			if(list == null){
				list = Collections.synchronizedList(new ArrayList<CasteeInterface>());
				broadCaster.put(target, list);
			}
			list.add(ci);
		}
	}

	public void deRegister(CastInfo info, CasteeInterface ci) {
		for(String target : info.target){
			List<CasteeInterface> list = broadCaster.get(target);
			list.remove(ci);
		}
	}

	public void castEvent(CastHeader header, Collection<EventData> dataSet) {
		if(checkHeader(header, "EVENT",dataSet)){
			for(EventData data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
							try {
								ci.singleEvent(header,data);
							} catch (Exception e) {
								CommonLogger.getLogger().error(getClass(), "event ui update Fail", e);
							}
					}
				}
			}
		}
	}

	public void castStack(CastHeader header, Collection<StackData> dataSet) {
		if(checkHeader(header, "STACK",dataSet)){
			for(StackData data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
							try {
								ci.singleStack(header,data);
							} catch (Exception e) {
								CommonLogger.getLogger().error(getClass(), "stack ui update Fail", e);
							}
					}
				}
			}
		}
	}

	public void castMac(CastHeader header, Collection<MacData> dataSet) {
		if(checkHeader(header, "INFO",dataSet)){
			for(MacData data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleInfo(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "info ui update Fail", e);
						}
					}
				}
			}
		}
	}

	public void castDbHotSpot(CastHeader header, Collection<DBSpotContainer> dataSet) {
		if(checkHeader(header, "DBHOTSPOT",dataSet)){
			for(DBSpotContainer data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleDbHotSpot(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "Database ui update Fail", e);
						}
					}
				}
			}
		}
	}

	public void castExcpt(CastHeader header, Collection<ExcptContainer> dataSet) {
		if(checkHeader(header, "EXCPT",dataSet)){
			for(ExcptContainer data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleExcpt(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "Exception ui update Fail", e);
						}
					}
				}
			}
		}
	}

	public void castLog(CastHeader header, Collection<LogContainer> dataSet) {
		if(checkHeader(header, "LOG",dataSet)){
			for(LogContainer data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleLog(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "Log ui update Fail", e);
						}
					}
				}
			}
		}
	}

	public void castReso(CastHeader header, Collection<ResourceContainer> dataSet) {
		if(checkHeader(header, "RES",dataSet)){
			for(ResourceContainer data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleRes(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "Resource ui update Fail", e);
						}
					}
				}
			}
		}
	}

	public void castHotSpot(CastHeader header, Collection<ThreadContainer> dataSet) {
		if(checkHeader(header, "HOTSPOT",dataSet)){
			for(ThreadContainer data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleHotSpot(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "Thread Hotspot ui update Fail", e);
						}
					}
				}
			}
		}		
	}

	public void castTrans(CastHeader header, Collection<TransContainer> dataSet) {
		if(checkHeader(header, "TRANS",dataSet)){
			for(TransContainer data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleTrans(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "Transaction ui update Fail", e);
						}
					}
				}
			}	
		}
	}
	
	//only use in broadCast
	public void castDis(CastHeader header,Collection<DistributionContainer> mDisSet) {
		if(checkHeader(header, "DIS",mDisSet)){
			for(List<CasteeInterface> list : broadCaster.values()){
				for(CasteeInterface ci : list){
					try {
						ci.singleDis(header,mDisSet);
					} catch (Exception e) {
						CommonLogger.getLogger().error(getClass(), "distribution ui update Fail", e);
					}
				}
			}		
		}
	}
	
	public void castHealth(CastHeader header, Collection<HealthData> dataSet) {
		if(checkHeader(header, "HEALTH",dataSet)){
			for(HealthData data : dataSet){
				List<CasteeInterface> list =  broadCaster.get(data.name);
				if(list != null){
					for(CasteeInterface ci : list){
						try {
							ci.singleHealth(header,data);
						} catch (Exception e) {
							CommonLogger.getLogger().error(getClass(), "health ui update Fail", e);
						}
					}
				}
			}	
		}		
	}

	private boolean checkHeader(CastHeader header,String type,Collection<? extends AbsFake> dataSet){
		if(header == null)
			return true;
		Long target = null;
		for(Long key : TargetCaster.keySet()){
			CasteeInterface temp = TargetCaster.get(key);
			if(temp.headerCheck(header.sessionNames)){
				target = key;
				try {
					if(header.code != MsgDic.ok)
						targerSelecter(temp,header,type,null);					
					else
						targerSelecter(temp,header,type,dataSet);
				} catch (Exception e) {
					CommonLogger.getLogger().error(getClass(), "target ui update Fail", e);
				}			
			}	
		}	
		//for many data get
		/*if(target != null)
			TargetCaster.remove(target);*/
		return false;
	}
	
	private void targerSelecter(CasteeInterface ci,CastHeader header,String type,Collection<? extends AbsFake> dataSet) throws Exception{
		if(type.equals("TRANS"))
			ci.targetTrans(header, (Collection<TransContainer>) dataSet);
		else if(type.equals("HOTSPOT"))
			ci.targetHotSpot(header, (Collection<TransContainer>) dataSet);
		else if(type.equals("RES"))
			ci.targetRes(header, (Collection<ResourceContainer>) dataSet);
		else if(type.equals("LOG"))
			ci.targetLog(header, (Collection<LogContainer>) dataSet);
		else if(type.equals("EXCPT"))
			ci.targetExcpt(header, (Collection<ExcptContainer>) dataSet);
		else if(type.equals("DBHOTSPOT"))
			ci.targetDbHotSpot(header, (Collection<DBSpotContainer>) dataSet);
		else if(type.equals("INFO"))
			ci.targetInfo(header, (Collection<MacData>) dataSet);
		else if(type.equals("STACK"))
			ci.targetStack(header, (Collection<StackData>) dataSet);
		else if(type.equals("EVENT"))
			ci.targetEvent(header, (Collection<EventData>) dataSet);
		else if(type.equals("DIS"))
			ci.targetDis(header, (Collection<DistributionContainer>) dataSet);
		else if(type.equals("HEALTH"))
			ci.targetHealth(header, (Collection<HealthData>) dataSet);
	}

	
	
}

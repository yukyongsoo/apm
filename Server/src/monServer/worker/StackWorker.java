package monServer.worker;

import java.util.Collection;
import java.util.List;

import monServer.aggre.AggreInMem;
import monServer.dbAction.DbActionBase;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.StackData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class StackWorker extends TimerWork{

	public StackWorker() {
		super();
		
	}

	@Override
	public void work() throws Exception {
		Collection<StackData> stack = AggreInMem.getStack();
		for(StackData dbStack : stack){
			dbStack.time = System.currentTimeMillis();
			DbActionBase.insertStack(dbStack);
		}			
		
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<StackData> data = new MonitorData<StackData>(ModelDic.CONSOLE, ModelDic.TYPE_STACK, null);
		data.dataList.addAll(stack);
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
	}

	@Override
	public void preWork() throws Exception {
		
	}
}

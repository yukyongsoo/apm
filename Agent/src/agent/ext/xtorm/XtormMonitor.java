package agent.ext.xtorm;

import com.windfire.base.asysCmnd;
import com.windfire.base.asysTransact;

import yuk.dic.ModelDic;
import agent.agentC.Agent;
import agent.agentC.tracer.MainTracer;
import agent.ext.AbsMonitor;

public class XtormMonitor extends AbsMonitor{
	private Agent agent = Agent.getInstance();
	@Override
	public void init() {
		agent.init();
		agent.event(ModelDic.EVENTLEVEL_INFO, "Server has been inited.");
	}

	@Override
	public void end() {
		agent.event(ModelDic.EVENTLEVEL_ERROR, "Server has been shutdowned.");
		agent.shutdown();
	}

	@Override
	public void transStart(Object targetClass, Object[] args) {
		asysTransact t = (asysTransact)targetClass;
		if (t.m_parm != null) {
			// create
			if (t.m_parm.m_subCmd == asysCmnd.DATA_ADDELEMENT) {
				MainTracer.initTrans(t.transactionId, "CREATE");
			}
			// download
			else if (t.m_parm.m_subCmd == asysCmnd.DATA_GETCONTENT) {
				MainTracer.initTrans(t.transactionId, "DOWN");
			}
			// delete
			else if (t.m_parm.m_subCmd == asysCmnd.DATA_DELELEMENT) {
				MainTracer.initTrans(t.transactionId, "DELETE");
			}
		}
	}

	@Override
	public void transEnd(Object targetClass,Object returnObject,Object[] args) {
		Boolean check = (Boolean)returnObject;
		asysTransact t = (asysTransact)targetClass;
		if (check == null || !check || t.m_rCode != 0)
			MainTracer.endTrans(t.transactionId, false);
		else {
			if (t.m_parm != null && 
					(t.m_parm.m_subCmd == asysCmnd.DATA_ADDELEMENT
					|| t.m_parm.m_subCmd == asysCmnd.DATA_GETCONTENT 
					|| t.m_parm.m_subCmd == asysCmnd.DATA_DELELEMENT)) {
				MainTracer.endTrans(t.transactionId, true);
			}
		}		
	}
	
	//0: agent, 1: level , 2: errorCode , 3: msg , 4: null , 5 : null
	@Override
	public void log(Object[] args) {
		Integer iLevel = (Integer)args[1];
		String level = "Trace";
		switch (iLevel) {
			case 0:
				level = "Error";
				break;
			case 1:
				level = "Warn";
				break;
			case 2:
				level = "Info";
				break;
			case 3:
				level = "Debug";
				break;
			case 4:
				level = "Debug";
				break;		
		}
		String defMsg = (String) args[3];
		agent.log(level, defMsg);
	}
}

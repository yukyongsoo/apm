package agent.ext.xtorm;

import com.windfire.apis.asysConnectBase;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.oper.asysOperActivity;
import com.windfire.base.asysParmList;
import com.windfire.base.asysParmVector;
import com.windfire.base.asysTransact;

import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class XtormDbConn {

	private int[] getDBConnectionCount() throws Exception {
		asysConnectBase con = null;
		try {
			con = new asysConnectData(XmlProperty.XVARMIP, XmlProperty.XVARMPORT, "MONITOR", XmlProperty.XVARMID, XmlProperty.XVARMPASS);
			asysOperActivity oa = new asysOperActivity(con, false);
			int rCode = oa.retrieve("DATA", "constatus", "");

			if (rCode == asysTransact.RCODE_OK) {
				int processing = 0;
				int ready = 0;
				asysParmList l = (asysParmList) oa.m_data;
				for (int i = 0; i < l.count(); i++) {
					asysParmVector r = (asysParmVector) l.get(i);
					String status = r.get(1);
					if ("Processing".equals(status))
						processing++;
					if ("Ready".equals(status))
						ready++;
				}
				return new int[]{processing,ready};
			}
			return new int[]{0,0};
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(),"can't get db connection data",e);
			return new int[]{0,0};
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}
	public void work(MonitorData<ResourceData> data) {
		try {	
			int[] aData = getDBConnectionCount();
		
			ResourceData rData = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_DBCONN, "");

			SubResourceData sub1 = new SubResourceData(aData[0], 1);
			SubResourceData sub2 = new SubResourceData(aData[1], 1);
			
			rData.map.put(ModelDic.PROCESSING, sub1);
			rData.map.put(ModelDic.READY, sub2);
			
			data.dataList.add(rData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send db connection data",e);
		}				
	}	
}

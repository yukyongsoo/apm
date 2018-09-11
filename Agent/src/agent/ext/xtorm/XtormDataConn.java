package agent.ext.xtorm;

import com.windfire.apis.asysConnectBase;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.oper.asysOperActivity;
import com.windfire.base.asysParmList;
import com.windfire.base.asysTransact;

import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class XtormDataConn {
	private int getConnectionCount() throws Exception{
		asysConnectBase con = null;

		try {
			con = new asysConnectData(XmlProperty.XVARMIP, XmlProperty.XVARMPORT, "MONITOR", XmlProperty.XVARMID, XmlProperty.XVARMPASS);
			
			asysOperActivity transCon = new asysOperActivity(con, true);
			int rCode = transCon.retrieve("COMM", "transact", "");
			if (rCode == asysTransact.RCODE_OK) {
				asysParmList tcons = (asysParmList)transCon.m_data;
				return tcons.count();
			}
			return 0;
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(),"can't get connction data", e);
			return 0;
		} finally {
			if (con != null){
				con.close();
			}
		}
	}

	public void work(MonitorData<ResourceData> data) {
		try {
			ResourceData rData = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_DATACONN, "");
			SubResourceData sub = new SubResourceData(getConnectionCount(), 1);
			rData.map.put(ModelDic.USED, sub);
			data.dataList.add(rData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send connction data", e);
		}
	}

}

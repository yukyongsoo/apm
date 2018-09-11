package agent.ext.xtorm;

import java.util.HashMap;
import java.util.Map;

import com.windfire.apis.asysConnectBase;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.asys.asysUsrSql;
import com.windfire.apis.data.asysDataResult;

import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class XtormArchive {

	private Map<String, String[]> getArchiveInfos() {
		asysConnectBase con = null;

		StringBuffer query = new StringBuffer();
		query.append("select A.archiveId, sum(B.maxSpace), sum(B.spaceLeft) from asysArchiveVolume A, asysVolume B \n");
		query.append("where A.volumeId = B.volumeId                                                                \n");
		query.append("group by A.archiveId                                                                         \n");

		Map<String, String[]> result = new HashMap<String, String[]>();

		try {
			con = new asysConnectData(XmlProperty.XVARMIP, XmlProperty.XVARMPORT, "MONITOR", XmlProperty.XVARMID, XmlProperty.XVARMPASS);
			asysUsrSql sql = new asysUsrSql(con);
			int ret = sql.retrieve("SIMPLESQL_MAIN", query.toString(), 1000);

			if (ret != 0) {
				ret = sql.retrieve("TRANSACTSQL_MAIN", query.toString(), 1000);
				if (ret != 0)
					throw new Exception(sql.getLastError());
			}

			asysDataResult res = sql.getResult();

			while (res.nextRow()) {
				String archiveId = res.getColValue(0);
				String maxSpace = res.getColValue(1);
				String spaceLeft = res.getColValue(2);
				long usedSpace = Long.parseLong(maxSpace)- Long.parseLong(spaceLeft);
				usedSpace = Math.max(0, usedSpace);
				result.put(archiveId,
						new String[] { maxSpace, Long.toString(usedSpace) });
			}
			return result;
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(),"can't get archive data", e);
			String archiveId = "NONE";
			String maxSpace = "0";
			long usedSpace = 0;
			result.put(archiveId,
					new String[] { maxSpace, Long.toString(usedSpace) });
			return result;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public void work(MonitorData<ResourceData> mData) {
		try {
			Map<String, String[]> aData = getArchiveInfos();
			for (String key : aData.keySet()) {
				String[] sValues = aData.get(key);
				
				ResourceData rData = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_ARCHIVE, key);
					
				SubResourceData sub1 = new SubResourceData(changeValue(Long.parseLong(sValues[0])),1);
				SubResourceData sub2 = new SubResourceData(changeValue(Long.parseLong(sValues[1])), 1);
				rData.map.put(ModelDic.TOTAL, sub1);
				rData.map.put(ModelDic.USED, sub2);
				
				mData.dataList.add(rData);
			}
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send archive data", e);
		}
	}

	public long changeValue(long value) {
		if (value != 0) {
			if (XmlProperty.PHYSICAL) {
				return value / (1024 * 1024);
			}
			// byte
			else {
				return value / (1024 * 1024 * 1024);
			}
		}
		return value;
	}
}

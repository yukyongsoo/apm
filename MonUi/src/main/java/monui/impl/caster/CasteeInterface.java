package monui.impl.caster;

import java.util.Collection;

import yuk.model.CastHeader;
import yuk.model.etc.EventData;
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

public interface CasteeInterface {
	void register(CastInfo info);
	void deRegister(CastInfo info);
	boolean headerCheck(String id);
	void refresh();
	boolean isLived();
	
	void singleEvent(CastHeader header, EventData data) throws Exception;
	void singleHealth(CastHeader header, HealthData data) throws Exception;
	void singleStack(CastHeader header, StackData data) throws Exception;
	void singleInfo(CastHeader header, MacData data) throws Exception;
	void singleDis(CastHeader header, Collection<DistributionContainer> mDisSet) throws Exception;
	void singleDbHotSpot(CastHeader header, DBSpotContainer data) throws Exception;
	void singleExcpt(CastHeader header, ExcptContainer data) throws Exception;
	void singleLog(CastHeader header, LogContainer data) throws Exception;
	void singleHotSpot(CastHeader header, ThreadContainer data) throws Exception;
	void singleRes(CastHeader header, ResourceContainer data) throws Exception;
	void singleTrans(CastHeader header, TransContainer data) throws Exception;
	
	void targetTrans(CastHeader header, Collection<TransContainer> dataSet) throws Exception;
	void targetHotSpot(CastHeader header, Collection<TransContainer> dataSet) throws Exception;
	void targetRes(CastHeader header, Collection<ResourceContainer> dataSet) throws Exception;
	void targetLog(CastHeader header, Collection<LogContainer> dataSet) throws Exception;
	void targetExcpt(CastHeader header, Collection<ExcptContainer> dataSet) throws Exception;
	void targetDbHotSpot(CastHeader header, Collection<DBSpotContainer> dataSet) throws Exception;
	void targetInfo(CastHeader header, Collection<MacData> dataSet) throws Exception;
	void targetStack(CastHeader header, Collection<StackData> dataSet) throws Exception;
	void targetEvent(CastHeader header, Collection<EventData> dataSet) throws Exception;
	void targetDis(CastHeader header, Collection<DistributionContainer> dataSet) throws Exception;
	void targetHealth(CastHeader header, Collection<HealthData> dataSet) throws Exception;
		
}

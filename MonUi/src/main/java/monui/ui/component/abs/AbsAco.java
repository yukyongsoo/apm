package monui.ui.component.abs;

import java.util.Collection;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.UI;

import monui.impl.caster.CastInfo;
import monui.impl.caster.CasteeInterface;
import monui.impl.caster.Caster;
import monui.impl.util.UiUtil;
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

public class AbsAco  extends Accordion  implements CasteeInterface{
	protected UI ui;
	protected CastInfo castInfo;
	
	public AbsAco(CastInfo info) {
		super();
		ui = UiUtil.getUI();
		castInfo = info;
	}

	@Override
	public void register(CastInfo info) {
		info.session = String.valueOf(getUI().getUIId());
		Caster.getCaster(UiUtil.getKey()).register(info, this);
	}

	@Override
	public void deRegister(CastInfo info) {
		Caster.getCaster(UiUtil.getKey()).deRegister(info, this);
	}
	

	@Override
	public boolean isLived() {
		return 	ui.isAttached();
	}
	
	@Override
	public void attach() {
		super.attach();
		register(castInfo);
	};
	
	@Override
	public void detach() {
		deRegister(castInfo);
		super.detach();
	}
	
	@Override
	public void refresh() {
				
	}

	@Override
	public boolean headerCheck(String id) {
		if(castInfo.session.equals(id))
			return true;
		return false;
	}

	@Override
	public void singleEvent(CastHeader header, EventData data) throws Exception {
		
	}

	@Override
	public void singleHealth(CastHeader header, HealthData data) throws Exception {
		
	}

	@Override
	public void singleStack(CastHeader header, StackData data) throws Exception {
		
	}

	@Override
	public void singleInfo(CastHeader header, MacData data) throws Exception {
		
	}

	@Override
	public void singleDis(CastHeader header, Collection<DistributionContainer> data) throws Exception {
		
	}

	@Override
	public void singleDbHotSpot(CastHeader header, DBSpotContainer data) throws Exception {
		
	}

	@Override
	public void singleExcpt(CastHeader header, ExcptContainer data) throws Exception {
		
	}

	@Override
	public void singleLog(CastHeader header, LogContainer data) throws Exception {
		
	}

	@Override
	public void singleHotSpot(CastHeader header, ThreadContainer data) throws Exception {
			
	}

	@Override
	public void singleRes(CastHeader header, ResourceContainer data) throws Exception {
		
	}

	@Override
	public void singleTrans(CastHeader header, TransContainer data) throws Exception {
		
	}

	@Override
	public void targetTrans(CastHeader header, Collection<TransContainer> dataSet) throws Exception {
		
	}

	@Override
	public void targetHotSpot(CastHeader header, Collection<TransContainer> dataSet) throws Exception {
	
	}

	@Override
	public void targetRes(CastHeader header, Collection<ResourceContainer> dataSet) throws Exception {
		
	}

	@Override
	public void targetLog(CastHeader header, Collection<LogContainer> dataSet) throws Exception {
		
	}

	@Override
	public void targetExcpt(CastHeader header, Collection<ExcptContainer> dataSet) throws Exception {
	
	}

	@Override
	public void targetDbHotSpot(CastHeader header, Collection<DBSpotContainer> dataSet) throws Exception {
		
	}

	@Override
	public void targetInfo(CastHeader header, Collection<MacData> dataSet) throws Exception {
		
	}

	@Override
	public void targetStack(CastHeader header, Collection<StackData> dataSet) throws Exception {
		
	}

	@Override
	public void targetEvent(CastHeader header, Collection<EventData> dataSet) throws Exception {
		
	}

	@Override
	public void targetDis(CastHeader header, Collection<DistributionContainer> dataSet) throws Exception {
		
	}

	@Override
	public void targetHealth(CastHeader header, Collection<HealthData> dataSet) throws Exception {
		
	}
}

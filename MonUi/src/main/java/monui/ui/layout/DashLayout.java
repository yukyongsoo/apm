package monui.ui.layout;

import java.util.Collection;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.notify.NotifyManager;
import monui.ui.component.usage.dash.AppPanel;
import monui.ui.component.usage.dash.EventPanel;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.SubResourceData;
import yuk.model.multi.DistributionContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.DistributionData;
import yuk.model.single.HealthData;
import yuk.model.single.ResourceData;
import yuk.model.single.TransactionData;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.HorizontalLayout;

public class DashLayout extends AbsVer{
	AppPanel metric;
	EventPanel event;
	ChartPanel trans;
	ChartPanel radar;
	ChartPanel resource;
	
	
	public DashLayout(CastInfo info) {
		super(info);
		setSizeFull();
		setSpacing(true);
		
		HorizontalLayout upperLayout = new HorizontalLayout();
		upperLayout.setSizeFull();
		upperLayout.setSpacing(true);
		addComponent(upperLayout);
		
		metric = new AppPanel();
		metric.setIcon(VaadinIcons.POWER_OFF);
		upperLayout.addComponent(metric);	
		
		event = new EventPanel();
		event.setIcon(VaadinIcons.EXCLAMATION_CIRCLE);
		upperLayout.addComponent(event);
		
		
		trans = new ChartPanel("Transaction ( EA )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		trans.setIcon(VaadinIcons.LINE_CHART);
		upperLayout.addComponent(trans);
		
		HorizontalLayout lowerLayout = new HorizontalLayout();
		lowerLayout.setSizeFull();
		lowerLayout.setSpacing(true);
		addComponent(lowerLayout);
		
		resource = new ChartPanel("Server Resource ( % )",ChartType.BAR_CHART,ChartOptionFactory.getDefault());
		resource.setIcon(VaadinIcons.SERVER);
		lowerLayout.addComponent(resource);		
		
		radar = new ChartPanel("R-Bubble ( Ms )",ChartType.RADAR_CHART,ChartOptionFactory.getDefault());
		radar.setIcon(VaadinIcons.SCATTER_CHART);
		radar.addLabel("0","100","200","300","400","500","600","700","800","900","1 Sec Over");
		lowerLayout.addComponent(radar);		
	}
	
	@Override
	public void singleEvent(CastHeader header, EventData data) throws Exception {
		NotifyManager.getManager(ui).showNotify(data);
		if(!isAttached())
			return;
		event.updateData(data,ui);
	}
	
	@Override
	public void singleHealth(CastHeader header, HealthData data) throws Exception {
		if(!isAttached())
			return;
		metric.updateData(data,ui);
	}
	
	@Override
	public void singleDis(CastHeader header, Collection<DistributionContainer> data) throws Exception {
		if(!isAttached())
			return;
		radar.clear();
		radar.addLabel("0","100","200","300","400","500","600","700","800","900","1 Sec Over");
		for(DistributionContainer con : data){
			for(final Integer index : con.list.keySet()){
				final DistributionData dis = con.list.get(index);
				if(index > 9)
					radar.addPoint(con.command, String.valueOf(index * 100), dis.count, 0,  ChartType.RADAR_CHART);
				else
					radar.addPoint(con.command, "1 Sec Over", dis.count, 0,  ChartType.RADAR_CHART);
			}
		}	
		radar.draw();
	}
	
	@Override
	public void singleTrans(CastHeader header, TransContainer data) throws Exception {
		if(!isAttached())
			return;
		TransactionData temp = new TransactionData("", "", 0, 0, 0);
		for(TransactionData trans : data.list){
			temp.name = trans.name;
			temp.time = trans.time;
			temp.modelAdd(trans, false);
		}
		temp.cal();
		trans.addPoint(temp.name, temp.time, temp.count, ChartType.LINE_CHART);
		trans.draw();						
	}
	
	@Override
	public void singleRes(CastHeader header, ResourceContainer data) throws Exception {
		if(!isAttached())
			return;
		for (final ResourceData reso : data.list) {
			if (reso.command.equals(CommandDic.COMMAND_SYSCPU)) {
				SubResourceData sub = reso.map.get(ModelDic.TOTAL);
				resource.addPoint("CPU", reso.name, sub.value, ChartType.BAR_CHART);
			} 
			else if (reso.command.equals(CommandDic.COMMAND_SYSMEM)) {
				SubResourceData total = reso.map.get(ModelDic.TOTAL);
				SubResourceData used = reso.map.get(ModelDic.USED);
				long per = (long) (((double) used.value / (double) total.value) * 100);
				resource.addPoint("Memory", reso.name, per, ChartType.BAR_CHART);
			}
		}
		resource.draw();						
	}
}

package monui.ui.layout.stat;

import java.util.Collection;

import org.joda.time.format.DateTimeFormatter;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsGrid;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.notify.NotifyManager;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.multi.ResourceContainer;
import yuk.model.single.ResourceData;
import yuk.util.NormalUtil;

public class StatColl extends AbsGrid{
	ChartPanel cpu;
	ChartPanel mem;
	DateTimeFormatter dateFomat = NormalUtil.getDateFomat(ModelDic.SEC);
	Boolean messageLock = false;
	
	public StatColl(CastInfo info) {
		super(info);
		setColumns(2);
		setRows(1);
		setSpacing(true);
		setSizeFull();
		
		cpu = new ChartPanel("CPU", ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		cpu.setSizeFull();
		addComponent(cpu);
		
		mem = new ChartPanel("Memory", ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		mem.setSizeFull();
		addComponent(mem);

	}
	
	@Override
	public void targetRes(CastHeader header, Collection<ResourceContainer> dataSet) throws Exception {
		if(!isAttached())
			return;
		if(NotifyManager.getManager(ui).showErrordata(header)){
			return;
		}
		for(ResourceContainer con : dataSet){
			for(ResourceData resource : con.list){
				if(resource.command.equals(CommandDic.COMMAND_SYSCPU)){
					cpu.addPoint(ModelDic.TOTAL, resource.time, resource.map.get(ModelDic.TOTAL).value, ChartType.LINE_CHART);
					cpu.addPoint(ModelDic.USER, resource.time, resource.map.get(ModelDic.USER).value,  ChartType.LINE_CHART);
					cpu.addPoint(ModelDic.SYSTEM, resource.time, resource.map.get(ModelDic.SYSTEM).value,  ChartType.LINE_CHART);
					
				}
				else if(resource.command.equals(CommandDic.COMMAND_SYSMEM)){
					mem.addPoint(ModelDic.TOTAL, resource.time, resource.map.get(ModelDic.TOTAL).value, ChartType.LINE_CHART);
					mem.addPoint(ModelDic.USED, resource.time, resource.map.get(ModelDic.USED).value, ChartType.LINE_CHART);	
					
				}				
			}
		}
		cpu.draw();
		mem.draw();
		ui.push();
	}
}

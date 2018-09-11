package monui.ui.layout.stat;

import java.util.Collection;

import org.joda.time.format.DateTimeFormatter;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.impl.caster.CastInfo;
import monui.sTable.YukSTable;
import monui.ui.component.abs.AbsGrid;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.notify.NotifyManager;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.etc.SubResourceData;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.LogData;
import yuk.model.single.ResourceData;
import yuk.model.single.TransactionData;
import yuk.util.NormalUtil;

public class StatAgent extends AbsGrid{
	ChartPanel trans;
	ChartPanel res;
	ChartPanel fail;	
	YukSTable log;
	ChartPanel cpu;
	ChartPanel mem;	
	DateTimeFormatter dateFomat = NormalUtil.getDateFomat(ModelDic.SEC);
	
	public StatAgent(CastInfo info) {
		super(info);
		setColumns(3);
		setRows(3);
		setSpacing(true);
		setSizeFull();		
		
		trans = new ChartPanel("Transaction", ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		trans.setSizeFull();
		addComponent(trans);
		
		res = new ChartPanel("Response",  ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		res.setSizeFull();
		addComponent(res);
		
		log = new YukSTable("Time","Level","Log");
		log.setSizeFull();		
		addComponent(log, 2, 0, 2, 2);
		
		fail = new ChartPanel("Fail", ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		fail.setSizeFull();
		addComponent(fail);		
		
		cpu = new ChartPanel("CPU",  ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		cpu.setSizeFull();
		addComponent(cpu);
		
		mem = new ChartPanel("Memory",  ChartType.LINE_CHART, ChartOptionFactory.getDefaultLine());
		mem.setSizeFull();
		addComponent(mem);		
	}
	
	@Override
	public void targetTrans(CastHeader header, Collection<TransContainer> dataSet) throws Exception {
		if(!isAttached())
			return;
		trans.clear();
		res.clear();
		fail.clear();
		if(NotifyManager.getManager(ui).showErrordata(header)){
			return;
		}
		for(TransContainer con : dataSet){
			for (TransactionData transData : con.list) {
				trans.addPoint(transData.command, transData.time,transData.count, ChartType.LINE_CHART);
				res.addPoint(transData.command, transData.time, transData.res,ChartType.LINE_CHART);
				fail.addPoint(transData.command, transData.time,transData.fail, ChartType.LINE_CHART);
			}
		}
		trans.draw();
		res.draw();
		fail.draw();
		ui.push();
	}
	
	@Override
	public void targetLog(CastHeader header, Collection<LogContainer> dataSet) throws Exception {
		if(!isAttached())
			return;
		log.removeAllItems();
		if(NotifyManager.getManager(ui).showErrordata(header))
			return;
		if(dataSet != null){
			for (LogContainer con : dataSet) {
				for (LogData data : con.list) {
					String time = dateFomat.print(data.time);
					log.addLogItem(time, data.level, data.log);
				}
			}
			log.draw();
			ui.push();
		}
	}
	
	@Override
	public void targetRes(CastHeader header, Collection<ResourceContainer> dataSet) throws Exception {
	//	cpu.clear();
	//	mem.clear();
		if(!isAttached())
			return;
		if(NotifyManager.getManager(ui).showErrordata(header))
			return;
		for(ResourceContainer con : dataSet){
			for(ResourceData resource : con.list){
				if(resource.command.equals(CommandDic.COMMAND_CPU)){
					for(String key : resource.map.keySet()){
						SubResourceData sub = resource.map.get(key);
						cpu.addPoint(key, resource.time, sub.value,  ChartType.LINE_CHART);
					}	
					
				}
				else if(resource.command.equals(CommandDic.COMMAND_MEM)){
					for(String key : resource.map.keySet()){
						SubResourceData sub = resource.map.get(key);
						mem.addPoint(key, resource.time, sub.value,  ChartType.LINE_CHART);
					}						
				}
			}
		}
		cpu.draw();		
		mem.draw();
		ui.push();
	}	
}

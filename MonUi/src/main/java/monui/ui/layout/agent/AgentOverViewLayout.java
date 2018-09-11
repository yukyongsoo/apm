package monui.ui.layout.agent;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsGrid;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.usage.agent.LogPanel;
import monui.ui.component.usage.agent.QueryPanel;
import yuk.dic.CommandDic;
import yuk.model.CastHeader;
import yuk.model.etc.SubResourceData;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;

public class AgentOverViewLayout extends AbsGrid{
	ChartPanel trans;
	ChartPanel res;
	ChartPanel fail;
	ChartPanel cpu;
	ChartPanel mem;
	ChartPanel thread;
	
	LogPanel logPanel;
	QueryPanel queryPanel;
	
	public AgentOverViewLayout(CastInfo info) {
		super(info);
		setSpacing(true);
		setColumns(3);
		setRows(2);
		setSizeFull();

		trans = new ChartPanel("Transaction ( EA )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(trans);
		
		res = new ChartPanel("Response ( MS )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(res);
		
		fail = new ChartPanel("fail ( EA )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(fail);
	
		cpu = new ChartPanel("CPU ( % )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(cpu);
		
		mem = new ChartPanel("Memory ( MB )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(mem);
			
		thread = new ChartPanel("Thread Count ( EA )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(thread);
	}
	
	@Override
	public void singleRes(CastHeader header, ResourceContainer data) throws Exception {
		if(!isAttached())
			return;
		for(ResourceData resource : data.list){
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
		cpu.draw();		
		mem.draw();	
	}
	
	@Override
	public void singleTrans(CastHeader header, TransContainer data) throws Exception {
		if(!isAttached())
			return;
		for (TransactionData transData : data.list) {
			trans.addPoint(transData.command, transData.time, transData.count,  ChartType.LINE_CHART);
			res.addPoint(transData.command, transData.time, transData.res, ChartType.LINE_CHART);
			fail.addPoint(transData.command, transData.time, transData.fail, ChartType.LINE_CHART);
		}
		trans.draw();
		res.draw();
		fail.draw();
	}
	
	@Override
	public void singleStack(CastHeader header, StackData data) throws Exception {
		if(!isAttached())
			return;
		thread.addPoint("Thread", data.time, data.threadState.size(),  ChartType.LINE_CHART);
		thread.draw();
	}  
}

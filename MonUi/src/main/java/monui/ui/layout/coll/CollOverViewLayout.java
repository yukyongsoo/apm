package monui.ui.layout.coll;

import java.util.Map;

import com.vaadin.ui.Image;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.impl.caster.CastInfo;
import monui.impl.util.IconDic;
import monui.impl.util.UiUtil;
import monui.ui.component.abs.AbsGrid;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.base.YukTable;
import monui.ui.component.usage.coll.CollInfoPanel;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.multi.ResourceContainer;
import yuk.model.single.HealthData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;

public class CollOverViewLayout extends AbsGrid{
	ChartPanel cpu;
	ChartPanel mem;
	ChartPanel net;
	ChartPanel disk;
	CollInfoPanel infoPanel;
	YukTable table;
	
	public CollOverViewLayout(CastInfo info) {
		super(info);
		setSizeFull();
		setSpacing(true);
		setColumns(3);
		setRows(3);
		
		infoPanel = new CollInfoPanel();
		addComponent(infoPanel,0,0,1,0);
		infoPanel.initData(info.name);
		
		cpu = new ChartPanel("CPU ( % )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(cpu,0,1,0,1);
		
		mem = new ChartPanel("Memory ( MB )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(mem,1,1,1,1);
		
		net = new ChartPanel("NetWork ( KB )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(net,0,2,0,2);
		
		disk = new ChartPanel("Disk ( KB )",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(disk,1,2,1,2);
		
		table = new YukTable();
		table.setSizeFull();
		table.addColumn("icon", Image.class);
		table.addColumn("Application", String.class);
		table.setColumnExpandRatio("Application", 1f);
		addComponent(table,2,0,2,2);
	}
	
	@Override
	public void singleInfo(CastHeader header, MacData data) throws Exception {
		if(!isAttached())
			return;
		long read = 0;
		long write = 0;
		for(String key : data.dataList.keySet()){
			if(!key.equals("Gen")){
				Map<String, Object> map = data.dataList.get(key);
				read += (Double)map.get("Read");
				write += (Double)map.get("Write");
			}
		}
		long r = read;
		long w = write;
		if(data.command.equals(CommandDic.COMMAND_SYSNET)){
			net.addPoint("Download", data.time, r,ChartType.LINE_CHART);
			net.addPoint("Upload", data.time, w,  ChartType.LINE_CHART);
			net.draw();					
		}
		else if(data.command.equals(CommandDic.COMMAND_SYSDISK)){
			disk.addPoint("Read", data.time, r,  ChartType.LINE_CHART);
			disk.addPoint("Write", data.time, w,  ChartType.LINE_CHART);
			disk.draw();			
		}
	}
	
	@Override
	public void singleRes(CastHeader header, ResourceContainer data) throws Exception {
		if(!isAttached())
			return;
		for(ResourceData resource : data.list){
			if (resource.command.equals(CommandDic.COMMAND_SYSCPU)) {
				cpu.addPoint(ModelDic.TOTAL, resource.time, resource.map.get(ModelDic.TOTAL).value, ChartType.LINE_CHART);
				cpu.addPoint(ModelDic.USER, resource.time, resource.map.get(ModelDic.USER).value,  ChartType.LINE_CHART);
				cpu.addPoint(ModelDic.SYSTEM, resource.time, resource.map.get(ModelDic.SYSTEM).value,  ChartType.LINE_CHART);						
			} 
			else if (resource.command.equals(CommandDic.COMMAND_SYSMEM)) {
				mem.addPoint(ModelDic.TOTAL, resource.time, resource.map.get(ModelDic.TOTAL).value, ChartType.LINE_CHART);
				mem.addPoint(ModelDic.USED, resource.time, resource.map.get(ModelDic.USED).value, ChartType.LINE_CHART);				
			} 
		}	
		cpu.draw();	
		mem.draw();		
	}
	
	@Override
	public void singleHealth(CastHeader header, final HealthData data) throws Exception {
		if(!isAttached())
			return;
		ui.access(new Runnable() {
			@Override
			public void run() {
				Image image;
				if (data.status)
					image = new Image(null, UiUtil.getResource(IconDic.blueLight));
				else
					image = new Image(null, UiUtil.getResource(IconDic.redLight));
				table.addOrRefreshData(data.name, new Object[] { image, data.name });
			}
		});
		ui.push();		
	}
}

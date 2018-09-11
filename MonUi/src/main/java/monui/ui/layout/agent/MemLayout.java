package monui.ui.layout.agent;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.base.YukTable;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.etc.SubResourceData;
import yuk.model.multi.ResourceContainer;
import yuk.model.single.ResourceData;

public class MemLayout extends AbsVer{
	ChartPanel newer;
	ChartPanel older;
	YukTable table;

	//yong : Copy,PS Scavenge, ParNew, G1 Young Generation
	//old : MarkSweepCompact, PS MarkSweep, ConcurrentMarkSweep, G1 Mixed Generation
	public MemLayout(CastInfo info) {
		super(info);
		setSizeFull();
		setSpacing(true);
		
		newer = new ChartPanel("Minor Gc infomation",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(newer);
		
		older= new ChartPanel("Major Gc infomation",ChartType.LINE_CHART,ChartOptionFactory.getDefaultLine());
		addComponent(older);
		
		table = new YukTable();
		table.setSizeFull();
		table.addColumn("Memory Pool Name", String.class);
		table.addColumn("Max(MB)", Long.class);
		table.addColumn("Used(MB)", Long.class);
		table.addColumn("Peek(MB)", Long.class);
		table.addColumn("%", Double.class);
		addComponent(table);
	}
	
	@Override
	public void singleRes(CastHeader header, ResourceContainer data) throws Exception {
		if(!isAttached())
			return;
		for (final ResourceData resource : data.list) {
			if (resource.command.equals(CommandDic.COMMAND_GC)) {
				final SubResourceData commit = resource.map.get(ModelDic.COMMITED);
				final SubResourceData time = resource.map.get(ModelDic.TIME);
				if (resource.Rid.equals("Copy") || resource.Rid.equals("PS Scavenge") || resource.Rid.equals("ParNew")
						|| resource.Rid.equals("Young Generation")) {
					newer.addPoint(ModelDic.COMMITED, resource.time, commit.value, ChartType.LINE_CHART);
					newer.addPoint(ModelDic.TIME, resource.time, time.value, ChartType.LINE_CHART);
					newer.draw();		
				} 
				else {
					older.addPoint(ModelDic.COMMITED, resource.time, commit.value, ChartType.LINE_CHART);
					older.addPoint(ModelDic.TIME, resource.time, time.value, ChartType.LINE_CHART);
					older.draw();							
				}
			} 
			else if (resource.command.equals(CommandDic.COMMAND_MEMPOOL)) {
				final long total = resource.map.get(ModelDic.TOTAL).value;
				final long used = resource.map.get(ModelDic.USED).value;
				final double per = calper(total, used);
				ui.access(new Runnable() {
					@Override
					public void run() {
						table.addOrRefreshData(resource.Rid,new Object[] { resource.Rid, total, used,
										resource.map.get(ModelDic.PEEK).value, per});
					}
				});

			}
		}
		ui.push();	
	}
	
	private double calper(long total, long used){
		if(total < 1)
			return 100;
		double value = ((double)used / (double)total) * 100;
		value = Math.floor(value);
		return value;
	}
	
}

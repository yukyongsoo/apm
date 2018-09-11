package monui.ui.component.usage.coll;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;

import monui.chartjs.ChartType;
import monui.chartjs.util.ChartOptionFactory;
import monui.ui.component.base.ChartPanel;
import monui.ui.component.base.YukTable;
import monui.ui.component.notify.NotifyManager;

public class DiskContent extends HorizontalLayout{
	Map<String, Map<String, Object>> dataList = new HashMap<String, Map<String,Object>>();
	ChartPanel chart;
	YukTable table;
	
	public DiskContent() {
		setSizeFull();
		setMargin(new MarginInfo(false, false, false, true));
		setSpacing(true);
		
		chart = new ChartPanel("Disk Capacity",ChartType.DOUGHNUT_CHART,ChartOptionFactory.getDefault());
		chart.setSizeFull();
		addComponent(chart);
		setExpandRatio(chart, 3f);
		
		table = new YukTable();
		table.setSizeFull();
		table.addColumn("Write I/O(KB)", Double.class);
		table.addColumn("Read I/O(KB)", Double.class);
		table.addColumn("Disk Usage(%)", Double.class);
		table.addColumn("Disk Queue(%)", Double.class);
		addComponent(table);
		setExpandRatio(table, 7f);
	}

	public void updateData(Map<String, Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
	
	public void changeData(String name){
		Map<String, Object> map = dataList.get(name);
		if(map != null){
			try {
				chart.clear();
				table.removeAllItems();
				double u = (Double)map.get("used");
				double a =(Double)map.get("avail");
				long used = (long)u;
				long avail = (long)a;
				chart.addPoint(name, "used", used, ChartType.DOUGHNUT_CHART);
				chart.addPoint(name, "avail", avail, ChartType.DOUGHNUT_CHART);
				chart.draw();
				table.addOrRefreshData(name, new Object[]{map.get("Write"),map.get("Read"),map.get("usage"),map.get("queue")});
			} catch (Exception e) {
				NotifyManager.getManager(getUI()).showErrorBox("Error", e.getMessage());
			}
		}
	}
}

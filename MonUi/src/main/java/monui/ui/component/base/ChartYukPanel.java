package monui.ui.component.base;


import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;

import monui.chartjs.ChartJs;
import monui.chartjs.options.ChartConfig;

public class ChartYukPanel extends YukPanel{
	ChartJs chart;
		
	public ChartYukPanel(String name,String type,ChartConfig config) {
		super(name);
		chart = new ChartJs(type,config);
		chart.setSizeFull();
		setContent(chart);
		setMargin(new MarginInfo(true, false, false, false));
	}
	
	public void addPoint(String index, String time, double value,double count,String type) throws Exception{
		chart.addPoint(index, time, value,count, type);
	}
	
	public void addPoint(String index, long time, double value,double count, String type) throws Exception{
		chart.addPoint(index, time, value,count, type);
	}
	
	public void addPoint(String index, String time, double value, String type) throws Exception{
		chart.addPoint(index, time, value,  type);
	}
	
	public void addPoint(String index,long time,double value, String type) throws Exception{
		chart.addPoint(index, time, value,  type);
	}

	public void draw(){
		chart.draw();
	}
	
	public void clear(){
		chart.clear();
	}

	@Override
	public void setMenu(HorizontalLayout menuBar) {
		
	}
}

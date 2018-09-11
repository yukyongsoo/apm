package monui.ui.component.base;

import com.vaadin.ui.Panel;

import monui.chartjs.ChartJs;
import monui.chartjs.options.ChartConfig;

public class ChartPanel extends Panel{
	ChartJs chart;
	
	public ChartPanel(String name,String type,ChartConfig config){
		setSizeFull();
		chart = new ChartJs(type,config);
		chart.setSizeFull();
		setContent(chart);
		setCaption(name);
	}
	
	public void addLabel(String...labels){
		chart.addLabel(labels);
	}
	
	public void addPoint(String index, String time, double value,double count, String type) throws Exception{
		chart.addPoint(index, time, value,count,  type);
	}
	
	public void addPoint(String index, long time, double value,double count, String type) throws Exception{
		chart.addPoint(index, time, value,count, type);
	}
	
	public void addPoint(String index, String time, double value,String type) throws Exception{
		chart.addPoint(index, time, value, type);
	}
	
	public void addPoint(String index,long time,double value, String type) throws Exception{
		chart.addPoint(index, time, value, type);
	}

	public void draw() {
		chart.draw();
	}
	
	public void clear(){
		chart.clear();
	}
	
	public void setShift(int max){
		chart.setShift(max);
	}
}

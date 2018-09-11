package monui.chartjs.datas;

import monui.chartjs.ChartJs;
import monui.chartjs.colorSet.ColorSet;

public abstract class AbsDataSet {
	ChartJs chart;
	String type = "";
	String label = "";
	String color = "";
	ColorSet set;
	int idx = -1;
	
	public AbsDataSet(String type, String index, String color, int idx,ChartJs chart){
		this.type = type;
		this.label = index;
		this.color = color;
		this.idx = idx;
		this.chart = chart;
		makeDataSet();
	}
	
	public AbsDataSet(String type, String index, ColorSet color, int idx,ChartJs chart){
		this.type = type;
		this.label = index;
		this.set = color;
		this.idx = idx;
		this.chart = chart;
		makeDataSet();
	}
	
	public abstract void addPoint(String time, double value, double count) throws Exception;
	public abstract void addPoint(long time, double value, double count) throws Exception;
	protected abstract void makeDataSet();
	
	
}
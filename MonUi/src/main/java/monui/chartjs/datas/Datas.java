package monui.chartjs.datas;

import java.util.HashMap;
import java.util.Map;

import monui.chartjs.ChartJs;
import monui.chartjs.ChartType;
import monui.chartjs.colorSet.ColorSet;
import monui.chartjs.colorSet.ColorSetFactory;
import monui.chartjs.util.ChartArray;

public class Datas{
	private ChartJs chart;
	//not use. only for begin value
	private String type = "";
	private ChartArray labels = new ChartArray(); 
	private ColorSet colorSet;
	private int nextIdx = 0;
	private Map<String, AbsDataSet> map = new HashMap<String, AbsDataSet>();
	
	public Datas(String type, ChartJs chartJs) {
		this.type = type;
		this.chart = chartJs;
		colorSet = ColorSetFactory.getRandomSet();
		labels = new ChartArray();
	}
	
	public void addLabel(String...labelData){
		for(String label : labelData){
			boolean has = labels.get(label);
			if (!has) {
				chart.addLabel(label);
				labels.put(label);
			}
		}
	}

	public void addPoint(String index, String time, double value, double count, String type) throws Exception{
		addLabel(time);
		AbsDataSet dataSet = makeSeries(index, type);
		dataSet.addPoint(time, value, count);
	}

	public void addPoint(String index, long time, double value, double count, String type)throws Exception
	{
		AbsDataSet dataSet = makeSeries(index, type);
		dataSet.addPoint(time, value, count);
	}

	public void clear() {
		labels.removeAll();
		colorSet = null;
		map.clear();
		nextIdx = 0;
	}
	
	private AbsDataSet makeSeries(String index, String type) throws Exception{
		AbsDataSet dataSet = map.get(index);
		if(colorSet == null)
			colorSet = ColorSetFactory.getRandomSet();
		if(dataSet == null){
			if (type.equals(ChartType.BAR_CHART))
				dataSet = new BarDataSet(type, index, colorSet.getRandomColor(), nextIdx,chart);
			else if (type.equals(ChartType.BUBBLE_CHART))
				dataSet = new BubbleDataSet(type, index, colorSet.getRandomColor(), nextIdx,chart);
			else if (type.equals(ChartType.DOUGHNUT_CHART))
				dataSet = new DoughnutDataSet(type, index, colorSet, nextIdx,chart);
			else if (type.equals(ChartType.HORIZONTALBAR_CHART))
				dataSet = new HBarDataSet(type, index, colorSet.getRandomColor(), nextIdx,chart);
			else if (type.equals(ChartType.PIE_CHART))
				dataSet = new PieDataSet(type, index, colorSet, nextIdx,chart);
			else if (type.equals(ChartType.POLARAREA_CHART))
				dataSet = new PolarDataSet(type, index, colorSet.getRandomColor(), nextIdx,chart);
			else if (type.equals(ChartType.RADAR_CHART))
				dataSet = new RadarDataSet(type, index, colorSet.getRandomColor(), nextIdx,chart);
			else
				dataSet = new LineDataSet(type, index, colorSet.getRandomColor(), nextIdx,chart);
			map.put(index, dataSet);
			nextIdx++;
		}
		return dataSet;
	}

}

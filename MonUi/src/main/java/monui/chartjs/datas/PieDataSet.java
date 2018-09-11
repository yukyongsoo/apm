package monui.chartjs.datas;

import monui.chartjs.ChartJs;
import monui.chartjs.colorSet.ColorSet;
import monui.chartjs.util.ChartArray;
import monui.chartjs.util.ChartObject;

public class PieDataSet extends AbsDataSet{

	public PieDataSet(String type, String index, ColorSet color, int idx, ChartJs chart) {
		super(type, index, color, idx, chart);
		
	}

	@Override
	public void addPoint(String time, double value, double count) {
		chart.addPoint(idx, value);		
		
	}

	@Override
	public void addPoint(long time, double value, double count) throws Exception {
		throw new Exception("not supported function");
	}

	@Override
	protected void makeDataSet() {
		ChartObject object = new ChartObject();
		object.put("type", type);
		object.put("data", new ChartArray().getReal());
		ChartArray array = new ChartArray();
		for(String color : set.getAllColor())
			array.put(color);
		object.put("backgroundColor", array.getReal());
		chart.makeDataSet(idx,object.getReal());				
	}


}

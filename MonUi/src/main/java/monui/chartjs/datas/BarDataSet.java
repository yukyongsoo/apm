package monui.chartjs.datas;

import monui.chartjs.ChartJs;
import monui.chartjs.util.ChartArray;
import monui.chartjs.util.ChartObject;

public class BarDataSet extends AbsDataSet{

	public BarDataSet(String type, String index, String color, int idx, ChartJs chart) {
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
		object.put("label", label);
		object.put("borderColor", color);
		object.put("backgroundColor", color);		
		object.put("data", new ChartArray().getReal());
		chart.makeDataSet(idx, object.getReal());		
	}

}

package monui.chartjs.datas;

import java.util.ArrayList;
import java.util.List;

import monui.chartjs.ChartJs;
import monui.chartjs.util.ChartArray;
import monui.chartjs.util.ChartObject;

public class LineDataSet extends AbsDataSet{
	List<ChartObject> data = new ArrayList<ChartObject>();
	
	public LineDataSet(String type, String index, String color, int idx, ChartJs chart) {
		super(type, index, color, idx, chart);
	}

	@Override
	public void addPoint(String time, double value, double count) throws Exception {
		throw new Exception("not supported function");
	}

	@Override
	public void addPoint(long time, double value, double count) {
		ChartObject object = new ChartObject();
		object.put("x", time);
		object.put("y", value);
		chart.addPoint(idx, object.getReal().toString());
	}

	@Override
	protected void makeDataSet() {
		ChartObject object = new ChartObject();
		object.put("type", type);
		object.put("label", label);
		object.put("borderColor", color);
		object.put("data", new ChartArray().getReal());
		chart.makeDataSet(idx,object.getReal());
	}
}

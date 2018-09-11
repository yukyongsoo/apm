package monui.chartjs;

import com.google.gson.JsonObject;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import monui.chartjs.datas.Datas;
import monui.chartjs.options.ChartConfig;
import monui.chartjs.util.ChartArray;
import monui.chartjs.util.ChartObject;

@JavaScript({"Chart.bundle.min.js","ChartJsConnector.js"})
public class ChartJs extends AbstractJavaScriptComponent{
	private static int nextId = 0;
	private Datas datas;
	private ChartObject data;
	private ChartArray dataSet;
	
	public Datas getMapper() {
		return datas;
	}

	public ChartJs(String type,ChartConfig config){
		 String id = getDomId();
	     setId(id);
	     data = new ChartObject();
	     dataSet = new ChartArray();
	     data.put("datasets", dataSet.getReal());
	     getState().domId = id;
	     getState().config = config.toJson();
	     getState().type = type;
	     getState().data = data.getReal().toString();
	     datas = new Datas(type,this);
	     markAsDirtyRecursive();
	}
	
	private String getDomId(){
		return "chart" + nextId++;
	}
	
	@Override
	protected ChartJsState getState() {
		return (ChartJsState)super.getState();
	}

	public void addLabel(String...labels){
		datas.addLabel(labels);
	}
	
	public void addPoint(String index, String time, double value, double count, String type) throws Exception {
		datas.addPoint(index, time, value, count, type);
		
	}

	public void addPoint(String index, long time, double value, double count, String type) throws Exception {
		datas.addPoint(index, time, value, count, type);
		
	}

	public void addPoint(String index, String time, double value, String type) throws Exception {
		addPoint(index, time, value, 0, type);	
	}

	public void addPoint(String index, long time, double value, String type) throws Exception {
		addPoint(index, time, value, 0, type);	
	}
	
	public void draw() {
		callFunction("update");		
	}
	
	public void clear() {
		datas.clear();
		data.clear();
		dataSet = new ChartArray();
	    data.put("datasets", dataSet.getReal());
		callFunction("clear");
		getState().data = data.getReal().toString();
	}
	
	public void redraw(){
		callFunction("redraw");
	}
	
	public void setShift(int max){
		if(max > 1)
			getState().shift = max;
		callFunction("setShift", max);
	}
	
	/**
	 * do not use. this is invoked method
	 */
	public void addPoint(int index, String dataPoint){
		callFunction("addPoint", index, dataPoint);
	}
	
	/**
	 * do not use. this is invoked method
	 */
	public void addPoint(int index, double dataPoint){
		callFunction("addPoint", index, dataPoint);
	}
	
	/**
	 * do not use. this is invoked method
	 */
	public void makeDataSet(int index, JsonObject jsonObject){
		callFunction("makeDataSet", index, jsonObject.toString());
		this.dataSet.put(jsonObject);
		getState().data = data.getReal().toString();
	}

	/**
	 * do not use. this is invoked method
	 */
	public void addLabel(String labels) {
		callFunction("makeLabels", labels);
	}
}

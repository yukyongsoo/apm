package monui.chartjs.options;

import com.google.gson.JsonElement;

import monui.chartjs.util.ChartArray;
import monui.chartjs.util.ChartObject;

public class ChartAxisX {
	ChartObject object = new ChartObject();
	ChartObject typeObject = new ChartObject();
	ChartObject displayObject = new ChartObject();
		
	public void setType(String value){
		object.put("type", value);
		object.put(value, typeObject.getReal());
	}
	
	public void setTooltipFormat(String value){
		typeObject.put("tooltipFormat", value);
	}
	
	public void setDisplayFormat(String type,String value){
		displayObject.put(type, value);
		typeObject.put("displayFormats", displayObject.getReal());
	}
	
	public JsonElement getOption() {
		ChartArray array = new ChartArray();
		array.put(object);
		return array.getReal();
	}

	public void setUnit(String value) {
		typeObject.put("unit", value);
	}
}

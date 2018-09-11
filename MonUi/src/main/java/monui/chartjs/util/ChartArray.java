package monui.chartjs.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ChartArray {
	JsonArray array = new JsonArray();
	public JsonArray getReal(){
		return array;
	}
	
	public void put(ChartObject element){
		array.add(element.getReal());
	}
	
	public void put(JsonObject element){
		array.add(element);
	}
	
	public void put(String value){
		JsonPrimitive priv = new JsonPrimitive(value);
		array.add(priv);
	}
	
	public ChartObject get(int index){
		ChartObject chartObject = new ChartObject();
		chartObject.setJsonObject(array.get(index).getAsJsonObject());
		return chartObject;
	}
	
	public boolean get(String index){
		return array.contains(new JsonPrimitive(index));
	}
	
	public void remove(int index){
		array.remove(index);
	}
	
	public void remove(JsonObject object){
		array.remove(object);
	}
	
	
	public int getSize(){
		return array.size();
	}
	
	public void removeAll(){
		array = new JsonArray();
	}
}

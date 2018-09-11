package monui.chartjs.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ChartObject {
	JsonObject jsonObject = new JsonObject();
	
	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JsonObject getReal(){
		return jsonObject;
	}
	
	public void put(String key, JsonElement element){
		jsonObject.add(key, element);
	}
		
	public void put(String key, String element){
		jsonObject.addProperty(key, element);
	}
	
	public void put(String key, Boolean element){
		jsonObject.addProperty(key, element);
	}

	public void put(String key, Number element){
		jsonObject.addProperty(key, element);
	}
	
	public JsonElement get(String key){
		return jsonObject.get(key);
	}
	
	public Number getNum(String key){
		return jsonObject.get(key).getAsNumber();
	}
	
	public void remove(String key){
		jsonObject.remove(key);
	}

	public void clear() {
		jsonObject = new JsonObject();		
	}
}

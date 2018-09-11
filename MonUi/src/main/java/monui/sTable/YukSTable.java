package monui.sTable;

import com.google.gson.JsonArray;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"jquery.slim.min.js","datatables.min.js","YukSTableConnector.js"})
public class YukSTable extends AbstractJavaScriptComponent{
	private static int nextId = 0;
	private int size = 0;
	private int removeCount = 0;
	private JsonArray array = new JsonArray();
	
	
	public YukSTable(String...columns){
		 String id = getDomId();
	     setId(id);
	     JsonArray array = new JsonArray();
	     for(String s : columns)
	    	 array.add(s);
	     getState().domId = id;
	     getState().header = array.toString();
	     markAsDirtyRecursive();
	}
	
	private String getDomId(){
		return "sTable" + nextId++;
	}
		
	@Override
	protected STableState getState() {
		return (STableState)super.getState();
	}

	public void removeAllItems() {
		callFunction("clear");
	}

	public int getSize() {
		return size;
	}

	public void removeItem() {
		size--;
		removeCount++;
	}

	public void addLogItem(String time, String level, String log) {
		size++;
		JsonArray obj = new JsonArray();
		obj.add(time);
		obj.add(level);
		obj.add(log);
		array.add(obj);
	}

	public void draw() {
		callFunction("draw",array.toString(),removeCount);	
		array = new JsonArray();
		removeCount = 0;
	}
	
	
}

package monui.ui.component.base;

import java.util.ArrayList;
import java.util.List;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.FilterGenerator;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Item;

public class YukFilterTable extends FilterTable{
	List<String> list = new ArrayList<String>();
	FilterGenerator gen;
	FilterDecorator dec;
	
	public YukFilterTable() {
		this(null);
	}
	
	public YukFilterTable(String string) {
		setCaption(string);
		setNullSelectionAllowed(false);
		setFilterBarVisible(true);
		setImmediate(false);
	}
	
	public void addColumn(String name, Class c){
		addContainerProperty(name, c, null);
		list.add(name);
		if(gen == null){
			gen = new YukFilterGen(name);
			dec = new YukFilterDec();
		}
		setFilterGenerator(gen);
		setFilterDecorator(dec);
	}
	
	public void addData(String id, Object... objects) {
		addItem(objects, id);		
	}

	public void removeItem() {
		Object next = getContainerDataSource().getItemIds().iterator().next();
		removeItem(next);		
	}	
	
	public void addOrRefreshData(String id, Object...objects){
		Item item;
		if((item = getItem(id)) != null){
			int max = list.size();
			for(int i = 0; i < max; i++){
				String t= list.get(i);
				item.getItemProperty(t).setValue(objects[i]);
			}
		}			
		else
			addItem(objects, id);		
	}
	
	public void addOrUpdateData(String id, Object...objects){
		Item item;
		if((item = getItem(id)) != null){
			for(int i = 0; i < list.size(); i++){
				long value = 0;
				String t= list.get(i);
				Object o = item.getItemProperty(t).getValue();
				if(o instanceof Long || o instanceof Double){
					value = (Long) o;
					value = value +  (Long)objects[i];
					item.getItemProperty(t).setValue(value);
				}
				else{
					item.getItemProperty(t).setValue(o);
				}
			
			}
		}			
		else
			addItem(objects, id);		
	}
}

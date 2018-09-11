package monui.ui.component.base;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class YukTable extends Table{
	List<String> list = new ArrayList<String>();
	
	public YukTable() {
		this(null);
	}
	
	public YukTable(String string) {
		setCaption(string);
		setNullSelectionAllowed(false);
		setImmediate(false);
	}
	
	public void addColumn(String name, Class c){
		addContainerProperty(name, c, null);
		list.add(name);
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
				if(o instanceof Number){
					value = (Long) o;
					value = value +  (Long)objects[i];
					item.getItemProperty(t).setValue(value);
				}
			}
		}			
		else
			addItem(objects, id);		
	}

	public void addData(String id, Object... objects) {
		addItem(objects, id);		
	}

	public void removeItem() {
		Object next = getContainerDataSource().getItemIds().iterator().next();
		removeItem(next);		
	}
}

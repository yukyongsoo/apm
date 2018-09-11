package monui.ui.component.base;

import java.util.ArrayList;
import java.util.List;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.FilterGenerator;
import org.tepi.filtertable.paged.PagedFilterControlConfig;
import org.tepi.filtertable.paged.PagedFilterTable;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.HorizontalLayout;

public class YukPagedTable extends PagedFilterTable{
	List<String> list = new ArrayList<String>();
	FilterGenerator gen;
	FilterDecorator dec;
	boolean filter = false;
	HorizontalLayout controlPanel;
	Container con;
	
	public HorizontalLayout getControlPanel() {
		return controlPanel;
	}

	public YukPagedTable(boolean filter) {
		this(null,filter);
	}
	
	public YukPagedTable(String string,boolean filter) {
		this.filter = filter;
		setCaption(string);
		setNullSelectionAllowed(false);
		setFilterBarVisible(this.filter);
		setImmediate(false);
		con = getContainerDataSource();
		PagedFilterControlConfig config = new PagedFilterControlConfig();
		controlPanel = createControls(config);		
	}
	
	public void addColumn(String name, Class c){
		addContainerProperty(name, c, null);
		list.add(name);
		if(filter){
			if (gen == null) {
				gen = new YukFilterGen(name);
				dec = new YukFilterDec();
			}
			setFilterGenerator(gen);
			setFilterDecorator(dec);
		}
	}
	
	public void massiveAddStart(){
		setContainerDataSource(new IndexedContainer());
	}
	
	public void massiveAddItem(String id, Object... objects){
		Item item = con.addItem(id);
		if(item != null){
			int count = 0;
			for (Object o : objects) {
				item.getItemProperty(list.get(count++)).setValue(o);
			}
		}
	}
	
	public void massiveAddOrUpdateItem(String id, Object... objects) {
		Item item;
		if((item = con.getItem(id)) != null){
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
			massiveAddItem(id,objects);	
	}
	
	public void massiveAddEnd(){
		setContainerDataSource(con);
	}
	
	public void addData(String id, Object... objects) {
		addItem(objects, id);		
	}

	public void removeItem() {
		Object next = con.getItemIds().iterator().next();
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

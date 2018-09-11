package monui.ui.component.base;

import com.vaadin.ui.Tree;

public class YukTree extends Tree{
	public YukTree() {
		setMultiSelect(false);
		setNullSelectionAllowed(false);
	}
	
	public void addChild(String name,String parent){
		addItem(name);
		setParent(name, parent);
	}
	
	public void addRoot(String name){
		addItem(name);
	}
}

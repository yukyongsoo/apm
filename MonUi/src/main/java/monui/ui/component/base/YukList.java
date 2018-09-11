package monui.ui.component.base;

import com.vaadin.ui.ListSelect;

public class YukList extends ListSelect{
	public YukList() {
		this(null);
	}

	public YukList(String string) {
		super(string);
		setNullSelectionAllowed(false);
	}
}

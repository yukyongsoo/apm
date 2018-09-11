package monui.ui.component.base;

import com.vaadin.ui.ComboBox;

public class YukCombo extends ComboBox{
	
	public YukCombo() {
		this(null);
	}
	
	public YukCombo(String caption){
		setCaption(caption);
		setInputPrompt("Please Select");
		setNullSelectionAllowed(false);
		setTextInputAllowed(false);
	}
}

package monui.ui.component.base;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class LinkButton extends Button{
	public LinkButton(String name) {
		 setCaption(name);
		 addStyleName(ValoTheme.BUTTON_LINK);
	}
}

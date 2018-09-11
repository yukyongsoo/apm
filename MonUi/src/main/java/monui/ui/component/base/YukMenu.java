package monui.ui.component.base;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import monui.impl.util.UiUtil;

public class YukMenu extends HorizontalLayout{
	 VerticalLayout contentArea = new VerticalLayout();
	 CssLayout allmenuArea = new CssLayout();
	 CssLayout topMenuArea = new CssLayout();
	 VerticalLayout itemMenuArea = new VerticalLayout();
	 Map<String, CssLayout> areaMap = new HashMap<String, CssLayout>();
	 Map<String, Component> savedMap = new HashMap<String, Component>();
	 
	public YukMenu() {
		setSizeFull();

		allmenuArea.setPrimaryStyleName(ValoTheme.MENU_ROOT);

		contentArea.setPrimaryStyleName("valo-content");
		contentArea.setMargin(true);
		contentArea.addStyleName("v-scrollable");
		contentArea.setSizeFull();

		addComponents(allmenuArea, contentArea);
		setExpandRatio(contentArea, 1);
		
		topMenuArea.addStyleName(ValoTheme.MENU_PART);
		allmenuArea.addComponent(topMenuArea);
				
		buildTop();
	}
	
	public void setScreen(Component c){
		String name = c.getClass().getName();
		Component old = savedMap.get(name);
		if(old == null){
			savedMap.put(name, c);
			old = c;
		}
		contentArea.removeAllComponents();
		contentArea.addComponent(old);
	}
	
	public void addMenuArea(String name){
		CssLayout layout = areaMap.get(name);
		if(layout == null){
			layout = new CssLayout();
			Label label = new Label(name, ContentMode.HTML);
			label.setValue(name + " <span class=\"valo-menu-badge\">" +"</span>");
			label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
			label.addStyleName(ValoTheme.LABEL_H4);
			label.setSizeUndefined();
			layout.addComponent(label);
			areaMap.put(name, layout);
			itemMenuArea.addComponent(layout);
			
		}
	}
	
	public void addItem(String area, String name,Resource icon, ClickListener listener){
		CssLayout layout = areaMap.get(area);
		if(layout == null)
			throw new RuntimeException("area not exist");
		Button item = new Button(name, listener);
		if(icon != null)
			item.setIcon(icon);
		item.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		layout.addComponent(item);
	}

	public void buildUserMenu(String userName, Command setting, Command help,Command signOut) {
		 MenuBar settings = new MenuBar();
	     settings.addStyleName("user-menu");
	     MenuItem settingsItem = settings.addItem(userName,UiUtil.getResource("user.jpg"),null);
	     settingsItem.addItem("Settings", VaadinIcons.COG_O, setting);
	     settingsItem.addItem("Help", VaadinIcons.QUESTION_CIRCLE_O, help);
	     settingsItem.addSeparator();
	     settingsItem.addItem("Sign Out",VaadinIcons.EXIT_O, signOut);
	     topMenuArea.addComponent(settings);

	     itemMenuArea.setPrimaryStyleName("valo-menuitems");
	     topMenuArea.addComponent(itemMenuArea);
	}

	private void buildTop() {
		 HorizontalLayout top = new HorizontalLayout();
	     top.setWidth("100%");
	     top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	     top.addStyleName(ValoTheme.MENU_TITLE);
	     topMenuArea.addComponent(top);
	     	     
	     Button showMenu = new Button("Menu");
	     showMenu.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				 if (topMenuArea.getStyleName().contains("valo-menu-visible")) 
					 topMenuArea.removeStyleName("valo-menu-visible");
	             else
	            	 topMenuArea.addStyleName("valo-menu-visible");
			}
		});
	    showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
	    showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(FontAwesome.LIST);
      
        Label title = new Label("<h3>Yuk Monitoring</h3>",ContentMode.HTML);
        title.addStyleName("label-font");
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
                
        topMenuArea.addComponent(showMenu);
	}
}

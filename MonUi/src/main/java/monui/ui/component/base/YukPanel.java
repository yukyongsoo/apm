package monui.ui.component.base;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

//we need to fast
public abstract class YukPanel extends VerticalLayout{
	Component content;
	LinkButton button;
	Label label ;
	
	public abstract void setMenu(HorizontalLayout menuBar);
	
	public YukPanel(String name) {
		this(name,null,true,null);
	}
	
	public YukPanel(String name,Component content) {
		this(name,content,true,null);
	}
	
	public YukPanel(String name,boolean link){
		this(name,null,link,null);
	}
	
	public YukPanel(String name,boolean link, Object o){
		this(name,null,link,o);
	}
	
	public YukPanel(String name, Component content, boolean link, Object o){
		if(o != null)
			preInit(o);
		addStyleName(ValoTheme.LAYOUT_CARD);
		makeTop(name,link);
		setSizeFull();
		setContent(content);
	}	
	
	public void preInit(Object o) {
		
		
	}

	public void setContent(Component content){
		if(content == null)
			return;
		if(this.content != null)
			this.content.setParent(null);
		this.content = content;
		addComponent(this.content);
		this.content.setHeight(100, Unit.PERCENTAGE);
		setExpandRatio(this.content, 9);
	}
	
	public void setName(String name){
		if(button != null)
			button.setCaption(name);
		else
			label.setValue(name);
	}
	
	private void makeTop(String name, boolean link){
		 HorizontalLayout panelCaption = new HorizontalLayout();
		 panelCaption.addStyleName("v-panel-caption");
		 panelCaption.addStyleName("colorful");
		 panelCaption.setWidth(100, Unit.PERCENTAGE);
		 if(link){
			button = new LinkButton(name);
			button.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					click();
				}
			});
			panelCaption.addComponent(button);
		 }
		 else{
			 label = new Label();
			 label.setValue(name);
			 panelCaption.addComponent(label);
		 }
	     addComponent(panelCaption);
	     setExpandRatio(panelCaption, 1);
	     
	     HorizontalLayout menuBar = new HorizontalLayout();
	     panelCaption.addComponent(menuBar);
	     panelCaption.setComponentAlignment(menuBar, Alignment.MIDDLE_RIGHT);
	     setMenu(menuBar);
	}	
	
	/*
	 * button click override
	 */
	public void click(){
		
	}
}

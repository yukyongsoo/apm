package monui.ui.layout.stat;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.usage.search.StatSearch;

public class StatLayout  extends AbsVer{
	CssLayout contentArea;
	
	public StatLayout(CastInfo info) {
		super(info);
		setSizeFull();
		
		StatSearch menu = new StatSearch("Statistical Search",info,this);
		addComponent(menu);
		menu.setSizeUndefined();
		menu.setWidth(100, Unit.PERCENTAGE);
		
		contentArea = new CssLayout();
		contentArea.setSizeFull();
		addComponent(contentArea);
		setExpandRatio(contentArea, 1f);
	}
	
	public void setContent(Component c){
		contentArea.removeAllComponents();
		contentArea.addComponent(c);
	}
}

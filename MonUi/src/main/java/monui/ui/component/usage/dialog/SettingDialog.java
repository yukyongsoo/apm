package monui.ui.component.usage.dialog;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

import monui.impl.util.UiUtil;
import monui.ui.component.base.YukTree;
import monui.ui.component.usage.SettingContent;
import yuk.model.etc.InitData;

public class SettingDialog extends Window {
	SettingContent content;
	
	public SettingDialog(){
		super("Setting");
		
		setIcon(VaadinIcons.AUTOMATION);
		setWidth(300, Unit.PIXELS);
		setHeight(300,  Unit.PIXELS);
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSizeFull();		
		setContent(layout);
		
		Panel innerPanel = new Panel();
		innerPanel.setSizeFull();
		layout.addComponent(innerPanel);
		
		HorizontalLayout innerLayout = new HorizontalLayout();
		innerLayout.setSpacing(true);
		innerLayout.setSizeFull();		
		innerPanel.setContent(innerLayout);
		
		YukTree tree = new YukTree();
		tree.addItem("Server");
		for(InitData data : UiUtil.getInitServer()){
			tree.addItem(data.name);
			tree.setParent(data.name, "Server");
			tree.setChildrenAllowed(data.name, false);
		}			
		
		tree.addItem("Collector");
		for(InitData data : UiUtil.getInitColl()){
			tree.addItem(data.name);
			tree.setParent(data.name, "Collector");
			tree.setChildrenAllowed(data.name, false);
		}	
		
		tree.addItem("Agent");
		for(InitData data : UiUtil.getInitAgent()){
			tree.addItem(data.name);
			tree.setParent(data.name, "Agent");
			tree.setChildrenAllowed(data.name, false);
		}	
		
		tree.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				String key = (String) event.getItemId();
				InitData data = UiUtil.getInit(key);
				if(data != null)
					content.updateData(data);
			}
		});
		innerLayout.addComponent(tree);
		
		content = new SettingContent();
		content.setSizeFull();
		innerLayout.addComponent(content);
		innerLayout.setExpandRatio(content,1f);
		
		
	}
}

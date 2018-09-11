package monui.ui.layout.coll;

import java.util.Collection;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.YukList;
import monui.ui.component.notify.NotifyManager;
import monui.ui.component.usage.coll.DiskContent;
import monui.ui.component.usage.search.DiskSearch;
import yuk.dic.CommandDic;
import yuk.model.CastHeader;
import yuk.model.single.MacData;

public class DiskLayout extends AbsVer{
	YukList list;
	DiskContent diskContent;
	DiskSearch search;
	
	public DiskLayout(CastInfo info) {
		super(info);
		setSizeFull();
		setSpacing(true);
		
		search = new DiskSearch("Disk Search",info,this);
		search.setSizeUndefined();
		search.setWidth(100, Unit.PERCENTAGE);
		addComponent(search);
		
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		addComponent(content);
		setExpandRatio(content, 1f);
		
		Panel panel = new Panel("Mount Point");
		panel.addStyleName("colorful");
		panel.setSizeFull();
		list = new YukList();
		panel.setContent(list);		
		list.setSizeFull();
		list.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String) event.getProperty().getValue();
				if(value != null)
					diskContent.changeData(value);
			}
		});
		content.addComponent(panel);
		content.setExpandRatio(panel, 15);
		
		diskContent = new DiskContent();
		content.addComponent(diskContent);
		content.setExpandRatio(diskContent, 85);
		
	}
	
	@Override
	public void singleInfo(CastHeader header, MacData data) throws Exception {
		if(!isAttached())
			return;
		if(search.isRefresh() && data.command.equals(CommandDic.COMMAND_SYSDISK))
			updateUI(data);
		ui.push();
	}
	
	@Override
	public void targetInfo(CastHeader header, Collection<MacData> dataSet) throws Exception {
		if(!isAttached())
			return;
		if(NotifyManager.getManager(ui).showErrordata(header))
			return;
		for(MacData data : dataSet){
			if (!search.isRefresh() && data.command.equals(CommandDic.COMMAND_SYSDISK)) {
				updateUI(data);				
			}
		}
		ui.push();
	}

	private void updateUI(final MacData data){
		ui.access(new Runnable() {
			@Override
			public void run() {
				list.removeAllItems();
				list.addItems(data.controllerList);
			}
		});
		diskContent.updateData(data.dataList);		
	}
}

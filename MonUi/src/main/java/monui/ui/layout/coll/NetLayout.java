package monui.ui.layout.coll;

import java.util.Collection;
import java.util.Map;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.YukList;
import monui.ui.component.base.YukTable;
import monui.ui.component.notify.NotifyManager;
import monui.ui.component.usage.coll.EthernetStatue;
import monui.ui.component.usage.search.NetSearch;
import yuk.dic.CommandDic;
import yuk.model.CastHeader;
import yuk.model.single.MacData;

public class NetLayout extends AbsVer{
	YukList tree;
	YukTable table;
	EthernetStatue ethernetStatue;
	NetSearch search;
	
	public NetLayout(CastInfo info) {
		super(info);
		setSizeFull();
		setSpacing(true);

		search = new NetSearch("Network Search",info,this);
		search.setSizeUndefined();
		search.setWidth(100, Unit.PERCENTAGE);
		addComponent(search);
		
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		content.setSpacing(true);
		addComponent(content);
		setExpandRatio(content, 1f);
		
		table = new YukTable("TCP Connection Statue");
		table.setSizeFull();
		table.addColumn("Type", String.class);
		table.addColumn("Number", Double.class);
		content.addComponent(table);
		
		HorizontalLayout subContent = new HorizontalLayout();
		subContent.setSpacing(true);
		subContent.setSizeFull();
		content.addComponent(subContent);
		
		tree = new YukList("Controller List");
		tree.setSizeFull();
		tree.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String)event.getProperty().getValue();
				ethernetStatue.changeData(value);
			}
		});
		subContent.addComponent(tree);
		subContent.setExpandRatio(tree, 20);
		
		ethernetStatue = new EthernetStatue("Ethernet Satue");
		subContent.addComponent(ethernetStatue);
		subContent.setExpandRatio(ethernetStatue, 80);
	}
	
	@Override
	public void singleInfo(CastHeader header, MacData data) throws Exception {
		if(!isAttached())
			return;
		if(search.isRefresh() && data.command.equals(CommandDic.COMMAND_SYSNET))
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
			if(!search.isRefresh() && data.command.equals(CommandDic.COMMAND_SYSNET))
				updateUI(data);
		}
		ui.push();
	}

	private void updateUI(final MacData data){
		ui.access(new Runnable() {
			@Override
			public void run() {
				tree.removeAllItems();
				tree.addItems(data.controllerList);
				Map<String, Object> genMap = data.dataList.get("Gen");
				for(String type : genMap.keySet()){
					double num = (Double)genMap.get(type);
					table.addOrRefreshData(type, new Object[] {type,num});
				}
			}
		});
		ethernetStatue.updateData(data.dataList);
	}	
}

package monui.ui.layout.agent;

import java.util.Collection;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import monui.impl.caster.CastInfo;
import monui.impl.util.IconDic;
import monui.impl.util.UiUtil;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.YukList;
import monui.ui.component.base.YukTable;
import monui.ui.component.notify.NotifyManager;
import monui.ui.component.usage.agent.StackPanel;
import monui.ui.component.usage.search.ThreadSearch;
import yuk.model.CastHeader;
import yuk.model.single.StackData;

public class ThreadLayout extends AbsVer{
	ThreadSearch menu;
	YukTable threadList;
	YukList lockList;
	StackPanel stack;
	
	public ThreadLayout(CastInfo info) {
		super(info);
		setSizeFull();
		
		menu = new ThreadSearch("Thread Search", info,this);
		addComponent(menu);
		menu.setSizeUndefined();
		menu.setWidth(100, Unit.PERCENTAGE);
		
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		addComponent(content);
		setExpandRatio(content, 1f);
		
		lockList = new YukList("Locking Thread");
		lockList.setSizeFull();
		content.addComponent(lockList);
		content.setExpandRatio(lockList, 2);
		
		HorizontalLayout subLayout = new HorizontalLayout();
		subLayout.setSizeFull();
		subLayout.setSpacing(true);
		content.addComponent(subLayout);
		content.setExpandRatio(subLayout, 8);
				
		threadList = new YukTable("Thread");
		threadList.setSizeFull();
		threadList.setSelectable(true);
		threadList.addColumn("icon", Image.class);
		threadList.addColumn("State", String.class);
		threadList.addColumn("Name", String.class);
		threadList.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				String value = (String)event.getItem().getItemProperty("Name").getValue();
				if(value != null){	
					stack.changeList(value);
				}				
			}
		});
		
		subLayout.addComponent(threadList);
		subLayout.setExpandRatio(threadList, 3);
		
		stack = new StackPanel("StackTrace");
		stack.setSizeFull();
		
		subLayout.addComponent(stack);
		subLayout.setExpandRatio(stack, 7);
	}
	
	@Override
	public void singleStack(CastHeader header, StackData data) throws Exception {
		if(!isAttached())
			return;
		if(menu.isRefresh())
			updateUI(data);
		ui.push();
	}
	
	@Override
	public void targetStack(CastHeader header, Collection<StackData> dataSet) throws Exception {
		if(!isAttached())
			return;
		if(NotifyManager.getManager(ui).showErrordata(header))
			return;
		for(StackData data : dataSet){
			if (!menu.isRefresh())
				updateUI(data);
		}
		ui.push();
	}
	
	private void updateUI(final StackData data){
		ui.access(new Runnable() {
			@Override
			public void run() {
				threadList.removeAllItems();
				lockList.removeAllItems();
				for(String name : data.threadState.keySet()){
					String state = data.threadState.get(name);
					Image image = null;
					if(state.equals("RUNNABLE"))
						image = new Image(null, UiUtil.getResource(IconDic.sgreenLight));
					else if(state.equals("TIMED_WAITING"))
						image = new Image(null,UiUtil.getResource(IconDic.sblueLight));
					else if(state.equals("WAITING"))
						image = new Image(null,UiUtil.getResource(IconDic.syelloLight));
					else
						image = new Image(null,UiUtil.getResource(IconDic.sredLight));					
					threadList.addOrRefreshData(name, new Object[]{image,state,name});
					
				}
				lockList.addItems(data.lockThreadList);
			}
		});				
		stack.updateList(data.stackTrace);		
	}
}

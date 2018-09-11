package monui.ui.component.usage.coll;

import java.util.Map;

import org.vaadin.alump.scaleimage.ScaleImage;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import monui.impl.util.IconDic;
import monui.impl.util.UiUtil;
import yuk.model.etc.InitData;

public class CollInfoPanel extends Panel{
	private Label host;
	private Label osName;
	private Label osVer;
	private Label core;
	private Label clock;
	private Label mem;
	private Label process;
	private Label swap;
	
	public CollInfoPanel() {
		setSizeFull();
		setCaption("Infomation");
			
		GridLayout content = new GridLayout();
		content.setColumns(5);
		content.setRows(2);
		content.setSizeFull();
		content.setMargin(true);
		setContent(content);
		
		ScaleImage image = new ScaleImage(UiUtil.getResource(IconDic.serverInfo));
		image.setSizeFull();
		content.addComponent(image,0,0,0,1);
		
		host = new Label();
		host.setCaption("HostName");
		host.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(host);
		
		osName = new Label();
		osName.setCaption("Os Name");
		osName.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(osName);

		osVer = new Label();
		osVer.setCaption("Os Version");
		osVer.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(osVer);
		
		core = new Label();
		core.setCaption("Cpu Core");
		core.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(core);
		
		clock = new Label();
		clock.setCaption("Cpu Clock ( Hz )");
		clock.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(clock);
		
		mem = new Label();
		mem.setCaption("Memory ( MB )");
		mem.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(mem);
		
		process = new Label();
		process.setCaption("Process");
		process.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(process);
		
		swap = new Label();
		swap.setCaption("Total Swap Page Size ( MB )");
		swap.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(swap);
	}

	public void initData(String name) {
		InitData data = UiUtil.getInit(name);
		Map<String, String> infoData = data.infoData;
		host.setValue(infoData.get("Host Name"));
		osName.setValue(infoData.get("OS Name"));
		osVer.setValue(infoData.get("OS Version"));
		core.setValue(infoData.get("CPU Core"));
		clock.setValue(infoData.get("CPU Clock"));
		mem.setValue(infoData.get("Total Memory"));
		process.setValue(infoData.get("Total Process"));
		swap.setValue(infoData.get("Total Swap Page Size"));
	}
}

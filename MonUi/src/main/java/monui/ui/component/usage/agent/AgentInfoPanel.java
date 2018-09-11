package monui.ui.component.usage.agent;

import java.util.Map;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import monui.impl.util.UiUtil;
import yuk.model.etc.InitData;

public class AgentInfoPanel extends Panel{
	private Label pid;
	private Label version;
	private Label arch;
	private Label option;
	private Label classPath;
	private Label userDir;

	public AgentInfoPanel() {
		setSizeFull();
		setCaption("Infomation");
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin(false);
		setContent(content);
			
		pid = new Label("Process ID");
		pid.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(pid);
		
		version = new Label("JDK Version");
		version.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(version);
		
		arch = new Label("arch");
		arch.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(arch);
		
		userDir = new Label("User dir");
		userDir.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(userDir);
		
		classPath = new Label("classPath");
		classPath.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(classPath);
		
		option = new Label("Option");
		option.setWidth(100, Unit.PERCENTAGE);
		content.addComponent(option);
		
		
	}

	public void init(String name) {
		InitData data = UiUtil.getInit(name);
		Map<String, String> map = data.infoData;
		pid.setValue(map.get("pid"));
		version.setValue(map.get("Version"));
		arch.setValue(map.get("arch"));
		userDir.setValue(map.get("userdir"));
		classPath.setValue(map.get("classpath"));
		option.setValue(map.get("jvmopt"));
	}

	
}

package monui.ui.component.usage.dialog;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import monui.impl.util.UiUtil;
import monui.ui.component.base.YukButton;
import yuk.dic.SystemDic;

public class HelpDialog extends Window{
	public HelpDialog() {
		setCaption("Help");
		setIcon(VaadinIcons.QUESTION_CIRCLE_O);
		setWidth(230, Unit.PIXELS);
		setHeight(185, Unit.PIXELS);
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		
		
		YukButton button = new YukButton("Download Help File",true,SystemDic.PCODE_MIN) {
			@Override
			public void clickFunction() {
				// noting
			}
		};
		button.setSizeUndefined();
		FileResource resource = UiUtil.getHelpResource("YukMonitoring.pdf");
		FileDownloader downloader = new FileDownloader(resource);
		downloader.extend(button);
		layout.addComponent(button);
		
		Label label = new Label("for prevent script injection, we use download by button. sorry..");
		layout.addComponent(label);		
		
		setContent(layout);
	}	
	
	
}

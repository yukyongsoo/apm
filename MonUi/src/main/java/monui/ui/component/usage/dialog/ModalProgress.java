package monui.ui.component.usage.dialog;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ModalProgress extends Window{
	
	public ModalProgress() {
		setSizeFull();
		setModal(true);
		setHeight(100,Unit.PIXELS);
		setWidth(200,Unit.PIXELS);
		setClosable(false);
		setResizable(false);
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		setContent(content);
		
        Label label = new Label("Plase Wait init!");
        content.addComponent(label);
		
        ProgressBar bar = new ProgressBar(); 
		bar.setSizeFull();
		bar.setIndeterminate(true);
		content.addComponent(bar);
		content.setComponentAlignment(bar, Alignment.MIDDLE_CENTER);
	}
	
	public void progress(){
		for(int i = 0 ; i < 30; i++){
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				break;
			}
		}
		close();
	}
}

package monui.ui.component.base;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public abstract class YukButton extends Button{
	boolean lock = false;
	String caption = "";
	long lockTime = 0;
	
	public abstract void clickFunction();
	
	public YukButton(String caption) {
		this(caption,false,0);
	}
	
	public YukButton(final String caption,final boolean lock, long lockTime) {
		super(caption);
		addStyleName(ValoTheme.BUTTON_PRIMARY);
		this.caption = caption;
		this.lock = lock;
		this.lockTime = lockTime;
		addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(!lock)
					clickFunction();
				else{
					if(isEnabled()){
						clickFunction();	
						setEnabled(false);
						setCaption("Lock now");
						LockThread lock = new LockThread("lock Button " + caption);
						lock.start();
					}					
				}
			}
		});
	}
	
	class LockThread extends Thread {
		public LockThread(String name) {
			super(name);	
		}
		
		@Override
		public void run() {
			try {
				sleep(lockTime);
			} catch (Exception e) {
				
			}
			setEnabled(true);
			setCaption(caption);
			if(getUI() != null)
				getUI().push();
		}
	}
}

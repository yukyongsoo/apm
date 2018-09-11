package monui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import de.steinwedel.messagebox.MessageBox;
import monui.impl.config.XmlAutoReconfig;
import monui.impl.net.ServerControll;
import monui.impl.util.UiUtil;
import yuk.util.CommonLogger;

@PreserveOnRefresh
@Push(PushMode.MANUAL)
@Theme("MonUI")
public class MonUI extends UI {
	public String ip = "";
	public String port = "";
	
	@Override
	protected void init(VaadinRequest request) {	
		XmlAutoReconfig.init();
		setPollInterval(10000);
		Responsive.makeResponsive(this);
		addStyleName(ValoTheme.UI_WITH_MENU);
		new Navigator(this, this);
		getNavigator().addView("", Login.class);
		getNavigator().addView(Login.name, Login.class);
				
		getNavigator().addViewChangeListener(new ViewChangeListener() {
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof Login;
				if (!isLoggedIn && !isLoginView) {
					getNavigator().navigateTo(Login.name);
					return false;
				} else if (isLoggedIn && isLoginView) {
					return false;
				}
				return true;
			}
			
			@Override
			public void afterViewChange(ViewChangeEvent event) {
				//not to do
			}
		});
		
		setErrorHandler(new ErrorHandler() {
			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				if(event != null){
					MessageBox.createError().withCaption("Error").withMessage("Unhandled Error Occuer. please show log").open();
					CommonLogger.getLogger().error(event.getClass(), event.getThrowable().getMessage(),new Exception(event.getThrowable()));
					getNavigator().navigateTo(Login.name);
				}
			}
		});
		
		addDetachListener(new DetachListener() {
			@Override
			public void detach(DetachEvent event) {
				try {
					ServerControll sc = ServerControll.getServerControll(UiUtil.getKey());
					sc.distroy();
				} catch (Exception e) {
					CommonLogger.getLogger().error(getClass(),"Server Clean Up has Error",e);
				}				
			}
		});
	}
}
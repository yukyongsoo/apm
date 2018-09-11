package monui;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.steinwedel.messagebox.MessageBox;
import monui.impl.config.XmlProperty;
import monui.impl.net.ServerControll;
import monui.impl.util.IconDic;
import monui.impl.util.UiHelper;
import monui.impl.util.UiUtil;
import monui.ui.component.base.YukText;
import monui.ui.component.usage.dialog.ModalProgress;

public class Login extends HorizontalLayout implements View {
	public static final String name = "LoginView";
  
    public Login() {
    	setSizeFull();
                
        Panel panel = new Panel();
        panel.setIcon(UiUtil.getResource(IconDic.login));
        panel.setCaption("Yuk Monitoring!");
        panel.addStyleName("login-panel");
        panel.setWidth(33, Unit.PERCENTAGE);
        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        
        final VerticalLayout layout = new VerticalLayout(); 
        layout.setSpacing(true);
        layout.setMargin(new MarginInfo(true, true, true, true));
        layout.setSizeFull();
        panel.setContent(layout); 
          
        final YukText user = new YukText("User","Your username (eg. yys or yukMon)",12);
        user.setRequired(true);
        user.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        user.setIcon(VaadinIcons.USER);
        user.setWidth(100, Unit.PERCENTAGE);
        layout.addComponent(user);

        // Create the password input field
        final PasswordField password = new PasswordField("Password:");
        password.setRequired(true);
        password.setMaxLength(32);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setIcon(VaadinIcons.PASSWORD);
        password.setWidth(100, Unit.PERCENTAGE);
        layout.addComponent(password);
        
        final YukText ip = new YukText("Server Ip :","MonServer Ip(eg. 127.0.0.1)",16);
        ip.setValue(XmlProperty.IP);
        ip.setRequired(true);
        ip.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        ip.setIcon(UiUtil.getResource(IconDic.server));
        ip.setWidth(100, Unit.PERCENTAGE);
        layout.addComponent(ip);
        
        final YukText port = new YukText("Port Num","MonServer Port(eg. 1055 ~ 65535)",6);
        port.setValue(String.valueOf(XmlProperty.PORT));
        port.setWidth(100, Unit.PERCENTAGE);
        port.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        port.setIcon(UiUtil.getResource(IconDic.port));
        port.setRequired(true);
        layout.addComponent(port);
        
        Button loginButton = new Button("Login");
        loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginButton.setClickShortcut(KeyCode.ENTER, null);
        layout.addComponent(loginButton);
        loginButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (!user.isValid() || !password.isValid()) return;
				String username = user.getValue();
				String pass = password.getValue();
				try {
					if (username.equals("yuk") && pass.equals("yuk")) {
						ServerControll serverControll = ServerControll.getServerControll(ip.getValue(), port.getValue());
						UiUtil.setKey(ip.getValue(), port.getValue());
						UiHelper.getHelper().sendInit(String.valueOf(getUI().getUIId()));
						ModalProgress mpWait = new ModalProgress();
						getUI().addWindow(mpWait);	
						mpWait.center();
						getUI().push();
						mpWait.progress();
						getUI().removeWindow(mpWait);
						getSession().setAttribute("user", username);
						getUI().getNavigator().addView(Main.name, Main.class);
						getUI().getNavigator().navigateTo(Main.name);//			
					} else {
						MessageBox.createError().withCaption("Login Error").withMessage("id or password is wrong").withOkButton().open();
						password.setValue(null);
						password.focus();
					}
				} catch (Exception e) {
					MessageBox.createError().withCaption("Login Error").withMessage("Server is not connected." + e.getMessage()).withOkButton().open();
				} 
			}
		});
        
        //for test
        user.setValue("yuk");
        password.setValue("yuk");
    }

    @Override
    public void enter(ViewChangeEvent event) {
       
    }
}

package monui.impl.util;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * @author yys
 * @version $Revision: 1.0 $
 */
public class LocaleBundle {	
	public static Locale locale;
	static ResourceBundle bundle;

	public static String getMessage(String message)
	{
		try{
			bundle = ResourceBundle.getBundle(UiUtil.getBasePath() + "/WEB-INF/lang", locale);
			String temp = bundle.getString(message);					
			return temp;
		}
		catch(Exception e){				
			return "error";
		}
	}

	public static String reverseGetMessage(String name){
		String temp = "";
		for (String string : bundle.keySet()) {
			if (getMessage(string).equals(name)) {
				temp = string;
				return temp;
			}
		}
		return temp;
	}
}

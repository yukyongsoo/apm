package monui.impl.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.steinwedel.messagebox.MessageBox;
import monui.MonUI;
import monui.impl.caster.Caster;
import monui.ui.component.base.YukMenu;
import yuk.dic.ModelDic;
import yuk.model.etc.InitData;
import yuk.util.NormalUtil;

//util
public abstract class UiUtil {	
	public static void changeTopCotent(AbstractComponent c, Component target, String caption){
		YukMenu findAncestor = c.findAncestor(YukMenu.class);
		if(findAncestor == null)
			MessageBox.createError().withCaption("Internal Error").withMessage("Can't find ancester").open();
		else{
			findAncestor.setScreen(target);
		}		
	}
	
	
	public static String getBasePath(){
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		return basepath;
	}
	
	public static FileResource getResource(String name){
		FileResource icon = new FileResource(new File(getBasePath() + "/WEB-INF/resource/" + name));
	    return icon;   
	}
	
	public static FileResource getHelpResource(String name){
		FileResource icon = new FileResource(new File(getBasePath() + "/WEB-INF/help/" + name));
	    return icon;   
	}
	
	public static UI getUI(){
		return UI.getCurrent();
	}
	
	public static VaadinSession getSession() throws Exception{
		UI ui = getUI();
		if(ui.getSession() == null)
			throw new Exception("Nullable Session value");
		return ui.getSession();
	}
	
	public static List<InitData> getInit(){
		return Caster.getCaster(getKey()).getInitList();
	}
	
	public static InitData getInit(String name){
		List<InitData> initList = getInit();
		for(InitData data : initList){
			if(data.name.equals(name))
				return data;
		}
		return null;
	}
	
	public static List<InitData> getInitServer(){
		List<InitData> initList = new ArrayList<InitData>();
		for(InitData data : getInit()){
			if(data.type.equals(ModelDic.SERVER))
				initList.add(data);
		}
		return initList;
	}
	
	public static List<InitData> getInitColl(){
		List<InitData> initList = new ArrayList<InitData>();
		for(InitData data : getInit()){
			if(data.type.equals(ModelDic.COLLECTOR))
				initList.add(data);
		}
		return initList;
	}
	
	public static List<InitData> getInitAgent(){
		List<InitData> initList = new ArrayList<InitData>();
		for(InitData data : getInit()){
			if(data.type.equals(ModelDic.AGENT))
				initList.add(data);
		}		
		return initList;		
	}
	
	public static boolean isChild(String parent, String agent){
		InitData init = getInit(parent);
		return init.childList.contains(agent);
	}
	
	public static boolean isColl(String name){
		InitData init = getInit(name);
		if(init.type.equals(ModelDic.COLLECTOR))
			return true;
		return false;
	}
	
	public static String getKey(){
		MonUI ui =  (MonUI) getUI();
		String key = NormalUtil.makeKey("::", ui.ip,String.valueOf(ui.port));
		return key;
	}
	
	public static void setKey(String ip, String port){
		MonUI ui =  (MonUI) getUI();
		ui.ip = ip;
		ui.port = port;
	}
	
	public static String checkTime(long start , long end, long limit) { 
		String ret = "";
		if(start >= end)
			ret = "wrong time set.. start time is higher than end time";
		if(end - start > limit)
			ret = "time interval too long.";
		return ret;
	}
}

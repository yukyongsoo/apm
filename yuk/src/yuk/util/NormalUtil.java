package yuk.util;

import java.lang.reflect.Field;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import yuk.dic.ModelDic;

public abstract class NormalUtil {
	private static 	DateTimeFormatter aFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private static 	DateTimeFormatter sFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	private static 	DateTimeFormatter mFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	private static 	DateTimeFormatter hFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH");
	private static 	DateTimeFormatter dFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
	private static 	DateTimeFormatter MFormat = DateTimeFormat.forPattern("yyyy-MM");
	
	public static String makeKey(String sep,String...strings){
		String key = "";
		if(strings != null){
			for(int i =0; i < strings.length; i++){
				if(i == strings.length-1){
					key = key + strings[i];
					break;
				}
				key = key + strings[i] + sep;
			}
		}
		
		return key;
	}
	
	public static DateTimeFormatter getDateFomat(String type){
		if(type.equals(ModelDic.SEC))
			return sFormat;
		else if(type.equals(ModelDic.Min))
			return mFormat;
		else if(type.equals(ModelDic.Hour))
			return hFormat;
		else if(type.equals(ModelDic.Day))
			return dFormat;
		else if(type.equals(ModelDic.Month))
			return MFormat;
		else
			return aFormat;
	}
	
	
	public static String SysmileToDateString(long time,String type) throws Exception{
		DateTimeFormatter dateFormat = getDateFomat(type);		
		
		return dateFormat.print(new DateTime(time));
	}
	
	public static long SysmileToDateLong(long time,String type) throws Exception{
		String sDate = SysmileToDateString(time,type);
		DateTime date = getDateFomat(type).parseDateTime(sDate);
		return date.getMillis();
	}	
	
	public static String getDateStringNow(String type) throws Exception{
		return SysmileToDateString(System.currentTimeMillis(),type);
	}	

	public static boolean nullValueChecker(Object object,boolean spacerCheker){
		try {
			for(Field f : object.getClass().getFields()) {
				if(f.get(object) == null)
					return false;
				if(spacerCheker && f.getType().equals(String.class)){
					String s = (String) f.get(object);
					if(s.equals("") || s.length() < 1 || !s.equals("null"))
						return false;
				}
			}
			return true;
		} catch (Exception e1) {
			return false;
		}
	}
}

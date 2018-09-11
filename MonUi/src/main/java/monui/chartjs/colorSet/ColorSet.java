package monui.chartjs.colorSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ColorSet {
	Map<String, Boolean> colorMap = new HashMap<String, Boolean>();
	
	protected ColorSet(){
		
	}
	
	public String getRandomColor() throws Exception{
		String selected = null;
		for(String color : colorMap.keySet()){
			Boolean used = colorMap.get(color);
			if(!used){
				selected = color;
				break;
			}
		}
		if(selected != null)
			colorMap.put(selected, true);
		else
			throw new Exception("colorMap overFlow");
		return selected;
	}
	
	public Set<String> getAllColor(){
		return colorMap.keySet();
	}
}

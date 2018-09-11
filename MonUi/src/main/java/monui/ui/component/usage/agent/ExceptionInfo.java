package monui.ui.component.usage.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.format.DateTimeFormatter;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;
import org.vaadin.aceeditor.AceTheme;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import monui.ui.component.base.YukList;
import yuk.model.multi.ExcptContainer;
import yuk.model.single.ExceptionData;
import yuk.util.NormalUtil;

public class ExceptionInfo extends HorizontalSplitPanel{
	YukList time;
	YukList trace;
	AceEditor code;
	String curType = "";
	Map<String,List<ExceptionData>> allData = new HashMap<String, List<ExceptionData>>();
	DateTimeFormatter dateFomat = NormalUtil.getDateFomat("");
	
	public ExceptionInfo() {
		setSizeFull();
		addStyleName(ValoTheme.SPLITPANEL_LARGE);
		
		VerticalLayout left = new VerticalLayout();
		left.setSizeFull();
		addComponent(left);
		
		time = new YukList("Time To Maked");
		time.setSizeFull();
		time.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String) event.getProperty().getValue();
				if(value != null){
					trace.removeAllItems();
					long millis = dateFomat.parseDateTime(value).getMillis();
					List<ExceptionData> list = allData.get(curType);
					for(ExceptionData data : list){
						List<String> stack = data.entireStack.get(millis);
						if(stack != null)
							trace.addItems(stack);
					}
					code.setReadOnly(false);
					code.setValue(list.get(0).code);
					code.setReadOnly(true);
				}
			}
		});
		left.addComponent(time);

		trace = new YukList("Stack Trace");
		trace.setSizeFull();
		left.addComponent(trace);
				
		VerticalLayout right = new VerticalLayout();
		right.setSizeFull();
		addComponent(right);
		
		Label label = new Label("Code Viewer");
		right.addComponent(label);
		
		code = new AceEditor();
		code.setMode(AceMode.java);
		code.setTheme(AceTheme.eclipse);
		code.setSizeFull();
		code.setReadOnly(true);
		right.addComponent(code);
		right.setExpandRatio(code, 1f);
	}

	public void updateData(ExcptContainer data) {
		for(ExceptionData excpt : data.list){
			List<ExceptionData> list = allData.get(excpt.type);
			if(list == null){
				list = new ArrayList<ExceptionData>();
				allData.put(excpt.type, list);
			}
			list.add(excpt);
		}
	}
	
	public void changeData(String type){
		time.removeAllItems();
		trace.removeAllItems();
		curType = type;
		List<ExceptionData> list = allData.get(curType);
		for(ExceptionData data : list){
			for(long temp : data.entireStack.keySet())
					time.addItems(dateFomat.print(temp));	
		}
	}
}

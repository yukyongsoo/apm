package monui.ui.component.base;

import com.vaadin.ui.TextField;

public class YukText extends TextField{
	public YukText(String caption, String prompt, int max) {
		if(caption != null && caption.length() > 0)
			setCaption(caption);
		if(prompt != null && prompt.length() > 0)
			setInputPrompt(prompt);
		if(max > 0)
			setMaxLength(max);
		else
			setMaxLength(255);
	}
	
	public YukText(String caption){
		this(caption,"",-1);
	}
	
	public YukText(int max){
		this(null,null,max);
	}
	
	public YukText(){
		this(null,null,-1);
	}
}

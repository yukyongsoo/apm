package monui.ui.component.base;

import java.util.Date;

import com.vaadin.data.Validator;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.PopupDateField;

public class YukDate extends PopupDateField{
	public YukDate(String caption, Resolution res) {
		if(caption != null && caption.length() > 0)
			setCaption(caption);
		if(res != null)
			setResolution(res);
		else
			setResolution(Resolution.SECOND);
		setInvalidAllowed(false);		
		setRangeEnd(new Date());
		setValue(new Date());
		addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if(value == null)
					setComponentError(new UserError("Date Must Be Set"));
				else
					setComponentError(null);
			}
		});
	}
}

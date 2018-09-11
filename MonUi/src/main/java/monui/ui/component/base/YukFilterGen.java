package monui.ui.component.base;

import org.tepi.filtertable.FilterGenerator;

import com.vaadin.data.Container.Filter;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;

public class YukFilterGen implements FilterGenerator{
	String filterId = "";

	public YukFilterGen(String name) {
		this.filterId = name;
	}

	@Override
	public Filter generateFilter(Object propertyId, Object value) {
        return null;
	}

	@Override
	public Filter generateFilter(Object propertyId, Field<?> originatingField) {
		return null;
	}

	@Override
	public AbstractField<?> getCustomFilterComponent(Object propertyId) {
		return null;
	}

	@Override
	public void filterRemoved(Object propertyId) {
		
	}

	@Override
	public void filterAdded(Object propertyId,
			Class<? extends Filter> filterType, Object value) {
		
	}

	@Override
	public Filter filterGeneratorFailed(Exception reason, Object propertyId,Object value) {
		return null;
	}

}

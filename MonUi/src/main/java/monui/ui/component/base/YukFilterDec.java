package monui.ui.component.base;

import java.util.Locale;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.Resolution;

public class YukFilterDec implements FilterDecorator{

	@Override
	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		return null;
	}

	@Override
	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		return null;
	}

	@Override
	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
		return null;
	}

	@Override
	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		return null;
	}

	@Override
	public boolean isTextFilterImmediate(Object propertyId) {
		return false;
	}

	@Override
	public int getTextChangeTimeout(Object propertyId) {
		return 5000;
	}

	@Override
	public String getFromCaption() {
		return null;
	}

	@Override
	public String getToCaption() {
		return null;
	}

	@Override
	public String getSetCaption() {
		return null;
	}

	@Override
	public String getClearCaption() {
		return null;
	}

	@Override
	public Resolution getDateFieldResolution(Object propertyId) {
		return null;
	}

	@Override
	public String getDateFormatPattern(Object propertyId) {
		return null;
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public String getAllItemsVisibleString() {
		return "Show all";
	}

	@Override
	public NumberFilterPopupConfig getNumberFilterPopupConfig() {
		return null;
	}

	@Override
	public boolean usePopupForNumericProperty(Object propertyId) {
		return true;
	}

}

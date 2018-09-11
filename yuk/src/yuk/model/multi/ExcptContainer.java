package yuk.model.multi;

import java.util.ArrayList;
import java.util.List;

import yuk.model.single.ExceptionData;

public class ExcptContainer extends AbsContainer{
	public List<ExceptionData> list = new ArrayList<ExceptionData>();

	@Override
	public long getSize() {
		return list.size();
	}
}

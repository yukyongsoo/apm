package yuk.model.multi;

import java.util.ArrayList;
import java.util.List;

import yuk.model.single.LogData;

public class LogContainer extends AbsContainer{
	public List<LogData> list = new ArrayList<LogData>();

	@Override
	public long getSize() {
		return list.size();
	}
}

package yuk.model.multi;

import java.util.ArrayList;
import java.util.List;

import yuk.model.single.ThreadHotSpotData;

public class ThreadContainer extends AbsContainer{
	public List<ThreadHotSpotData> list = new ArrayList<ThreadHotSpotData>();

	@Override
	public long getSize() {
		return list.size();
	}
}

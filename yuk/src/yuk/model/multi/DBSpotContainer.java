package yuk.model.multi;

import java.util.ArrayList;
import java.util.List;

import yuk.model.single.DBHotSpotData;

public class DBSpotContainer extends AbsContainer{
	public List<DBHotSpotData> list = new ArrayList<DBHotSpotData>();

	@Override
	public long getSize() {
		return list.size();
	}
}

package yuk.model.multi;

import java.util.ArrayList;
import java.util.List;

import yuk.model.single.ResourceData;

public class ResourceContainer extends AbsContainer{	
	public List<ResourceData> list = new ArrayList<ResourceData>();

	@Override
	public long getSize() {
		return list.size();
	}
}

package yuk.model.multi;

import java.util.ArrayList;
import java.util.List;

import yuk.model.single.TransactionData;

public class TransContainer extends AbsContainer{
	public List<TransactionData> list = new ArrayList<TransactionData>();

	@Override
	public long getSize() {
		return list.size();
	}
}

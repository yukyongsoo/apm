package yuk.model.etc;

import yuk.model.AbsFake;

public class SubResourceData extends AbsFake{
	public SubResourceData(long value, long count){
		this.value = value;
		this.count = count;
		this.total = value * count;
	}
	
	public long value = 0;
	public long count = 0;
	public long total = 0;
	
	public void cal() {
		if(count != 0)
			value = total / count;
	}
	
	public void modelAdd(SubResourceData subResourceData,boolean cal){
		total += subResourceData.total;
		count += subResourceData.count;
		if(cal)
			cal();
	}
}

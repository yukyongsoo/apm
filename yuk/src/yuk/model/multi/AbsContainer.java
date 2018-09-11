package yuk.model.multi;

import yuk.model.AbsFake;

public abstract class AbsContainer extends AbsFake{
	public String name = "";
	public String command = "";
	
	public abstract long getSize();
}

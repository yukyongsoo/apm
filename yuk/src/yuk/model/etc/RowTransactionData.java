package yuk.model.etc;

import yuk.model.AbsFake;

public class RowTransactionData extends AbsFake{
	public long time = 0;
	public long res = 0;
	public String tid = "";
	public String command = "";
	public boolean result = true;
	public boolean end = false;
}

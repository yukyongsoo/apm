package yuk.model.single;

import yuk.model.etc.RowTransactionData;

public class TransactionData  extends AbsData{
	public TransactionData(String name, String command,long suc, long fail, long res) throws Exception{
		this.name = name;
		this.command = command;
		this.suc = suc;
		this.fail = fail;
		this.res = res;
		this.count = this.suc + this.fail;
		this.totalRes = this.count * this.res;
	}
	
	public long time= 0;
	public String command = "";
	public long count = 0;
	public long res = 0;		
	public long totalRes = 0;
	public long suc = 0;
	public long fail = 0;
	
	public void cal(){
		if(count != 0)
		res = totalRes / count;
	}
	
	public void modelAdd(TransactionData source,boolean cal){
		count += source.count;
		totalRes += source.totalRes;		
		suc += source.suc;
		fail += source.fail;
		if(cal)
			cal();
	}
	
	public void rowModelAdd(RowTransactionData data,boolean cal){
		count += 1;
		totalRes += data.res;
		if(data.result)
			suc += 1;
		else
			fail += 1;
		if(cal)
			cal();
	}
}

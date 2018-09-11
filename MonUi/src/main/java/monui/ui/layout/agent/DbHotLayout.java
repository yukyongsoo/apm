package monui.ui.layout.agent;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.YukFilterTable;
import yuk.model.CastHeader;
import yuk.model.multi.DBSpotContainer;
import yuk.model.single.DBHotSpotData;

public class DbHotLayout  extends AbsVer{
	YukFilterTable dataBase;
	
	public DbHotLayout(CastInfo info) {
		super(info);
		setSizeFull();
		
		dataBase = new YukFilterTable();
		dataBase.addColumn("query", String.class);
		dataBase.addColumn("excution", Long.class);
		dataBase.addColumn("totalTime", Long.class);
		dataBase.addColumn("average", Long.class);	
		dataBase.setSizeFull();
		addComponent(dataBase);
		setExpandRatio(dataBase, 1f);
	}
	
	@Override
	public void singleDbHotSpot(CastHeader header, final DBSpotContainer data) throws Exception {
		if(!isAttached())
			return;
		ui.access(new Runnable() {
			@Override
			public void run() {
				for(DBHotSpotData spot : data.list){
					dataBase.addOrUpdateData(spot.queryName, 
						new Object[]{spot.queryName,spot.count,spot.totalTime,spot.avgTime});
				}
			}
		});
		ui.push();
	}
}

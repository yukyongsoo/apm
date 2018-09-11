package monui.chartjs.options;

import monui.chartjs.util.ChartObject;

public class ChartConfig {
	ChartObject main = new ChartObject();
	ChartObject scales;
		
	public String toJson() {
		main.put("maintainAspectRatio", false);
		
		return main.getReal().toString();
	}
	
	public void setAxisX(ChartAxisX x){
		if(scales == null){
			scales = new ChartObject();
			main.put("scales", scales.getReal());
		}		
		scales.put("xAxes", x.getOption());
	}

	public void setAxisY(ChartAxisY y){
		if(scales == null){
			scales = new ChartObject();
			main.put("scales", scales.getReal());
		}		
		scales.put("yAxes", y.getOption());
	}
}

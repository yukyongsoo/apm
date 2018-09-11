package monui.chartjs.util;

import monui.chartjs.options.ChartAxisX;
import monui.chartjs.options.ChartConfig;

public abstract class ChartOptionFactory {
	public static ChartConfig getDefaultLine(){
		ChartConfig config = new ChartConfig();
		ChartAxisX x = new ChartAxisX();
		x.setType("time");
		x.setTooltipFormat("HH:mm:ss");
		x.setDisplayFormat("second", "HH:mm:ss");
		config.setAxisX(x);
		return config;
	}
	
	public static ChartConfig getDefaultBubble(){
		ChartConfig config = new ChartConfig();
		ChartAxisX x = new ChartAxisX();
		x.setType("time");
		x.setTooltipFormat("ss");
		x.setUnit("second");
		x.setDisplayFormat("second", "ss");
		config.setAxisX(x);
		return config;
	}
	
	public static ChartConfig getDefault(){
		return new ChartConfig();
	}
}

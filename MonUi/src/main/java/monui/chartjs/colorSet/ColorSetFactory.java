package monui.chartjs.colorSet;

import java.util.Map;
import java.util.Random;

public abstract class ColorSetFactory {
	public static ColorSet getRandomSet() {
		ColorSet set = new ColorSet();
		makeColorSet(set.colorMap);
		return set;
	}
	
	private static void makeColorSet(Map<String, Boolean> colorMap){
		Random random = new Random();
		switch (random.nextInt(5)) {
		default:
			case3(colorMap);
			break;
		}
	}

	private static void case5(Map<String, Boolean> colorMap) {
		colorMap.put("#293241", false);
		colorMap.put("#ee6c4d", false);
		colorMap.put("#e0fbfc", false);
		colorMap.put("#98c1d9", false);
		colorMap.put("#3d5a80", false);
	}

	private static void case4(Map<String, Boolean> colorMap) {
		colorMap.put("#d8dbe2", false);
		colorMap.put("#a9bcd0", false);
		colorMap.put("#58a4b0", false);
		colorMap.put("#373f51", false);
		colorMap.put("#1b1b1e", false);
	}

	private static void case3(Map<String, Boolean> colorMap) {
		colorMap.put("#00a8e8", false);
		colorMap.put("#007ea7", false);
		colorMap.put("#003459", false);
		colorMap.put("#00171f", false);
	}

	private static void case2(Map<String, Boolean> colorMap) {
		colorMap.put("#1d3557", false);
		colorMap.put("#457b9d", false);
		colorMap.put("#a8dadc", false);
		colorMap.put("#f1faee", false);
		colorMap.put("#e63946", false);
	}

	private static void case1(Map<String, Boolean> colorMap) {
		colorMap.put("#05668d", false);
		colorMap.put("#028090", false);
		colorMap.put("#00a896", false);
		colorMap.put("#02c39a", false);
		colorMap.put("#f0f3bd", false);
	}

	private static void case0(Map<String, Boolean> colorMap) {
		colorMap.put("#247ba0", false);
		colorMap.put("#70c1b3", false);
		colorMap.put("#b2dbbf", false);
		colorMap.put("#f3ffbd", false);
		colorMap.put("#ff1654", false);
	}
	
}

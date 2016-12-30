package suite;

import java.util.Comparator;

public class HouseDist implements Comparator<HouseDist>{
	
	House toHouse;
	Double distance;
	
	public HouseDist(){}
	
	public HouseDist(House h, Double dist){
		toHouse = h;
		distance = dist;
	}

	@Override
	public int compare(HouseDist one, HouseDist two) {
		if(one.distance > two.distance)
			return 1;
		if(one.distance == two.distance)
			return 0;
		if(one.distance < two.distance)
			return -1;
		return 0;
	}

	
}

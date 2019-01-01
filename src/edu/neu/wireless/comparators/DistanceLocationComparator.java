package edu.neu.wireless.comparators;

import java.util.Comparator;

import edu.neu.wireless.bean.DistanceLocation;

public class DistanceLocationComparator implements Comparator<DistanceLocation>{

	@Override
	public int compare(DistanceLocation o1, DistanceLocation o2) {
		// TODO Auto-generated method stub
		return o1.getDistance().compareTo(o2.getDistance());
	}

}

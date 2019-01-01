package edu.neu.wireless.comparators;

import java.util.Comparator;
import java.util.Map.Entry;

public class KNNMapComparator implements Comparator<Entry<String,Double>>{

	@Override
	public int compare(Entry<String,Double> loc1, Entry<String,Double> loc2) {
		// TODO Auto-generated method stub
		
		return (int) ((loc1.getValue()*1000)-(loc2.getValue()*100));
	}

}

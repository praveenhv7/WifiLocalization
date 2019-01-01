package edu.neu.wireless.comparators;

import java.util.Comparator;

import edu.neu.wireless.dto.LocationInfo;

public class SortLocationOnCount implements Comparator<LocationInfo> {

	@Override
	public int compare(LocationInfo locInfoOne, LocationInfo locInfoTwo) {
		// TODO Auto-generated method stub
		return locInfoTwo.getNumberOfReportedRecords()-locInfoOne.getNumberOfReportedRecords();
	}
	
	

}

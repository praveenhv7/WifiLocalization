package edu.neu.wireless.dto;

public class LocationInfo {
	
	private String location;
	private Integer numberOfReportedRecords;
	
	

	public Integer getNumberOfReportedRecords() {
		return numberOfReportedRecords;
	}

	public void setNumberOfReportedRecords(Integer numberOfReportedRecords) {
		this.numberOfReportedRecords = numberOfReportedRecords;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}

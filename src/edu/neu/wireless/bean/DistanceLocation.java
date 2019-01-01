package edu.neu.wireless.bean;

import java.util.List;

public class DistanceLocation {
	
	Double distance;
	String location;
	
	
	
	public DistanceLocation(Double distance, String location) {
		super();
		this.distance = distance;
		this.location = location;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}

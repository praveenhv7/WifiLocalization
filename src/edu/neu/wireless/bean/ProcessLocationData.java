package edu.neu.wireless.bean;

import java.util.List;

import edu.neu.wireless.mapper.LocationMapper;

public class ProcessLocationData {
	
	public List<LocationMapper> locationRSS;
	public List<LocationMapper> locationBssid;
	
	public List<LocationMapper> getLocationRSS() {
		return locationRSS;
	}
	public void setLocationRSS(List<LocationMapper> locationRSS) {
		this.locationRSS = locationRSS;
	}
	public List<LocationMapper> getLocationBssid() {
		return locationBssid;
	}
	public void setLocationBssid(List<LocationMapper> locationBssid) {
		this.locationBssid = locationBssid;
	}
	
}

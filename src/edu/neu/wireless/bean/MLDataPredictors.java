package edu.neu.wireless.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MLDataPredictors {
	
	private int BSSID;
	private double signalStrength;
	private double avgSignalStrength;
	private double rssSD;
	private double rssMinAllowed;
	private double rssMaxAllowed;
	private double rssMax;
	private double rssMin;
	private String location;
	private List<DistanceLocation> distanceLocation;
	
	public MLDataPredictors() {
		distanceLocation=new ArrayList<>();
	}
	
	public MLDataPredictors(int bSSID, double signalStrength, double avgSignalStrength, double rssSD,
			double rssMinAllowed, double rssMaxAllowed, double rssMax, double rssMin, String location) {
		super();
		BSSID = bSSID;
		this.signalStrength = signalStrength;
		this.avgSignalStrength = avgSignalStrength;
		this.rssSD = rssSD;
		this.rssMinAllowed = rssMinAllowed;
		this.rssMaxAllowed = rssMaxAllowed;
		this.rssMax = rssMax;
		this.rssMin = rssMin;
		if(location!=null)
			this.location = location;
		else
			this.location=null;
		distanceLocation=new ArrayList<>();
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<DistanceLocation> getDistanceLocation() {
		if(this.distanceLocation == null)
			return new ArrayList<>();
		else
			return this.distanceLocation;
	}
	public void setDistanceLocation(List<DistanceLocation> distanceLocation) {
		this.distanceLocation = distanceLocation;
	}
	public int getBSSID() {
		return BSSID;
	}
	public void setBSSID(int bSSID) {
		BSSID = bSSID;
	}
	public double getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(double signalStrength) {
		this.signalStrength = signalStrength;
	}
	public double getAvgSignalStrength() {
		return avgSignalStrength;
	}
	public void setAvgSignalStrength(double avgSignalStrength) {
		this.avgSignalStrength = avgSignalStrength;
	}
	public double getRssSD() {
		return rssSD;
	}
	public void setRssSD(double rssSD) {
		this.rssSD = rssSD;
	}
	public double getRssMinAllowed() {
		return rssMinAllowed;
	}
	public void setRssMinAllowed(double rssMinAllowed) {
		this.rssMinAllowed = rssMinAllowed;
	}
	public double getRssMaxAllowed() {
		return rssMaxAllowed;
	}
	public void setRssMaxAllowed(double rssMaxAllowed) {
		this.rssMaxAllowed = rssMaxAllowed;
	}
	public double getRssMax() {
		return rssMax;
	}
	public void setRssMax(double rssMax) {
		this.rssMax = rssMax;
	}
	public double getRssMin() {
		return rssMin;
	}
	public void setRssMin(double rssMin) {
		this.rssMin = rssMin;
	}
	
	public String toString() {
		return "Bssid:"+getBSSID()+"\n"+
		"signalStrength:"+this.signalStrength+"\n"+
		"avgSignalStrength:"+this.avgSignalStrength+"\n"+
		"rssSD:"+this.rssSD+"\n"+
		"rssMinAllowed:"+this.rssMinAllowed+"\n"+
		"rssMaxAllowed:"+this.rssMaxAllowed+"\n"+
		"rssMax:"+this.rssMax+"\n"+
		"rssMin:"+this.rssMin+"\n"+
		"location"+this.location;
		}
	

}

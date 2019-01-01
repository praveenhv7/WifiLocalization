package edu.neu.wireless.xmlParser;

public class WLANWifi {

	private String ssid;
	private String bssid;
	private String sourceAddress;
	private float signalStrength;
	
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(String bssid) {
		this.bssid = bssid;
	}
	public String getSourceAddress() {
		return sourceAddress;
	}
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	public float getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(float signalStrength) {
		this.signalStrength = signalStrength;
	}
	
	
}

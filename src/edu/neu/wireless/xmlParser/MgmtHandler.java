package edu.neu.wireless.xmlParser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.neu.wireless.bean.ProcessLocationData;
import edu.neu.wireless.servicedao.InsertRecords;

/*
<structure> 					0
<section>No.</section>			1
<section>Time</section>			2
<section>Source</section>		3
<section>Destination</section>	4
<section>Protocol</section>		5
<section>Length</section>		6
<section>BSS Id</section>		7
<section>Signal strength (dBm)</section>	8
<section>SSID</section>			9
<section>Info</section>			10
</structure>

<packet>
<section>6</section>					0
<section>0.010269410</section>			1
<section>ArubaNet_12:71:e1</section>	2
<section>Broadcast</section>			3
<section>802.11</section>				4
<section>219</section>					5
<section>94:b4:0f:12:71:e1</section>	6
<section>-64</section>
<section>NUwave-guest</section>
<section>Beacon frame, SN=2254, FN=0, Flags=........C, BI=100, SSID=NUwave-guest</section>
</packet>

 */

public class MgmtHandler extends DefaultHandler{
	
	public boolean bssId=false;
	public boolean ssid=false;
	public boolean signalStrength=false;
	public boolean sourceAddress=false;
	public boolean packet=false;
	public boolean sectionTag=false;
	
	private boolean trainingData;
	private String location;
	
	private int count = 0;
	private WLANWifi wlanWifi;
	private List<WLANWifi> wlanWifiList=new ArrayList<WLANWifi>();
	private List<String> locationList;
	private ProcessLocationData locationData;
	
	public MgmtHandler(boolean trainingData,String location) {
		this.trainingData=trainingData;
		this.location=location;
	}
	
	@Override
	   public void startElement(String uri, 
	   String localName, String qName, Attributes attributes) throws SAXException {
		
		//System.out.println("Tag-Name :"+qName);
		//System.out.println("Count :"+count);
	      if (qName.equalsIgnoreCase("packet")) {
	    	  wlanWifi=new WLANWifi();
				packet=true;
    
	      }
	      
	      else  if (qName.equalsIgnoreCase("section") && packet == true) {
	    	  sectionTag=true;
	    	   count++;
	      } 
	   }
	
	
	 public ProcessLocationData getLocationData() {
		return locationData;
	}


	public void setLocationData(ProcessLocationData locationData) {
		this.locationData = locationData;
	}


	@Override
	   public void endElement(String uri, 
	   String localName, String qName) throws SAXException {
	      if (qName.equalsIgnoreCase("packet")) {
	         count=0;
	         wlanWifiList.add(wlanWifi);
	         packet=false;
	      }
	   }
	 
	 @Override
	   public void characters(char ch[], int start, int length) throws SAXException {
	     
		 if(sectionTag){
			 
			 
			 //System.out.println("count value inside parser : "+count );
	       if (count==1) {
	    	  //System.out.println("tag value "+ new String(ch, start, length));
	         wlanWifi.setBssid(new String(ch,start,length));
	         wlanWifi.setSourceAddress(new String(ch,start,length));
	         bssId = false;
	      } else if (count==3) {
	    	 // System.out.println("tag value "+ new String(ch, start, length));
	         wlanWifi.setSsid(new String(ch,start,length));
	         ssid = false;
	      } else if (count==2) {
	    	  //System.out.println("tag value "+ new String(ch, start, length));
	         wlanWifi.setSignalStrength(Float.parseFloat(new String(ch,start,length)));
	         signalStrength = false;
	      }
	      sectionTag=false;
		 }
	   }
	 
	 @Override	    	  	
		public void startDocument (){
			//System.out.println("Starting document");
			
		}
		
		@Override
		public void endDocument() throws SAXException {
			
			
			
			System.out.println("end of document " + wlanWifiList.size());
			InsertRecords records=new InsertRecords();
			records.insertWifiMgmtRecords(wlanWifiList,this.trainingData,this.location);
			//System.out.println("BSSID "+wlanWifiList.get(0).getBssid());
			//System.out.println("SS "+wlanWifiList.get(0).getSignalStrength());
			//System.out.println("SSID "+wlanWifiList.get(0).getSsid());
		}
		
		
		public List<String> getLocationList(){
			return locationList;
		}
		

}

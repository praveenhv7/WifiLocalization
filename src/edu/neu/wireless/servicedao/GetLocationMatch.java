package edu.neu.wireless.servicedao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.neu.wireless.bean.ProcessLocationData;
import edu.neu.wireless.mapper.ListLocationMapper;
import edu.neu.wireless.mapper.LocationMapper;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class GetLocationMatch implements ConnectionDataConst{
	
	

/*	public static void main(String[] args){
		
		
		GetLocationMatch geoMatch=new GetLocationMatch();
		List<LocationMapper> locMapper = geoMatch.getLocationMatch().getLocationRSS();
		System.out.println(locMapper.size());
		
		for(LocationMapper location: locMapper){
			System.out.println("Obtained location "+location);
		}
		
	}*/
	
	
	public List<LocationMapper> getLocationMatch(){
		
		Connection con = null;
		
		try {
			con=DriverManager.getConnection(URL, userName,password);
			Map myMap=con.getTypeMap();
			myMap.put("MATCHED_LOCATIONS", Class.forName("edu.neu.wireless.mapper.LocationMapper"));
			//myMap.put("MATCHED_LOCATIONS_TAB", Class.forName("edu.neu.wireless.mapper.ListLocationMapper"));
			con.setTypeMap(myMap);    
			List<LocationMapper> locationListBssid=new ArrayList<LocationMapper>();
			
			OracleCallableStatement ocs = 
					  (OracleCallableStatement)con.prepareCall("{ call GET_LOCATION_DYNAMIC_BSSID(?) }");
			
			ocs.registerOutParameter(1, OracleTypes.ARRAY, "MATCHED_LOCATIONS_TAB");
			ocs.execute();
			
			
			Object[] data = (Object[]) ((Array) ocs.getObject(1)).getArray();
			for(Object tmp : data) {
				locationListBssid.add((LocationMapper)tmp);
				
	        }
			
			
			ocs.close();
			con.close();
			
			
			
			return locationListBssid;
		}
		 catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	}

	
	public List<LocationMapper> getLocationRSS(List<LocationMapper> locationListBssid,int delta) {
		
		  Connection con=null;
		  List<LocationMapper> locationListRss=new ArrayList<LocationMapper>();
		
		try {
			con=DriverManager.getConnection(URL, userName,password);
			Map myMap=con.getTypeMap();
			myMap.put("MATCHED_LOCATIONS", Class.forName("edu.neu.wireless.mapper.LocationMapper"));
			myMap.put("MATCHED_LOCATIONS_TAB", Class.forName("edu.neu.wireless.mapper.ListLocationMapper"));
			con.setTypeMap(myMap);  
			
			
		ListLocationMapper listLocMap=new ListLocationMapper();
		listLocMap.setListLocMap(locationListBssid);
					
		
		ArrayDescriptor descriptor=ArrayDescriptor.createDescriptor("PRAVEEN.MATCHED_LOCATIONS_TAB",con);
		ARRAY locationArray=new ARRAY(descriptor,con,locationListBssid.toArray());
		listLocMap.setSqlArray(locationArray);
		
		
		OracleCallableStatement ocsRss = 
				  (OracleCallableStatement)con.prepareCall("{ call GET_LOCATION_DYNAMIC_RSS(?,?,?)}");
		
		ocsRss.setArray(1, locationArray);
		ocsRss.registerOutParameter(2, OracleTypes.ARRAY, "MATCHED_LOCATIONS_TAB");
		ocsRss.setInt(3, delta);
		ocsRss.execute();
		
		
		Object[] dataRSS = (Object[]) ((Array) ocsRss.getObject(2)).getArray();
		for(Object tmp : dataRSS) {
			locationListRss.add((LocationMapper)tmp);
			
        }
		ocsRss.close();
		con.close();
		return locationListRss;
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return locationListRss;
	}
	
	public Map<String, Integer> getUniqueBSSIDForLocation(List<String> location) {

		Map<String, Integer> locBSSIDCount = new LinkedHashMap<String, Integer>();

		Connection con = null;
		try {
			String query="select distinct BSSID from wifi_mgmt_raw_data_req" + 
					" INTERSECT" + 
					" (select distinct BSSID from WIFI_MGMT_RAW_DATA where location = ?" + 
					" MINUS" + 
					" select distinct BSSID from WIFI_MGMT_RAW_DATA where location = ?)";
			
			System.out.println(query);
			con = DriverManager.getConnection(URL, userName, password);
			PreparedStatement stmtLocOne=con.prepareStatement(query);
			stmtLocOne.setString(1, location.get(0));
			stmtLocOne.setString(2, location.get(1));
			ResultSet resultLocOne =stmtLocOne.executeQuery();
			int locOneCount=0;
			while(resultLocOne.next()) {
				locOneCount++;
			}
			locBSSIDCount.put(location.get(0), locOneCount);
			stmtLocOne.close();
			resultLocOne.close();
			
			PreparedStatement stmtLocTwo=con.prepareStatement(query);
			stmtLocTwo.setString(1, location.get(1));
			stmtLocTwo.setString(2, location.get(0));
			
			ResultSet resultLocTwo=stmtLocTwo.executeQuery();
			int locTwoCount=0;
			while(resultLocTwo.next()) {
				locTwoCount++;
			}
			
			System.out.println("Location One: "+location.get(0) +"| Count: "+locOneCount );
			System.out.println("Location Two: "+location.get(1) +"| Count: "+locTwoCount );
			locBSSIDCount.put(location.get(1), locTwoCount);
			stmtLocTwo.close();
			resultLocTwo.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return locBSSIDCount;

	}
	
}

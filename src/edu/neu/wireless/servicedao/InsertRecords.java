package edu.neu.wireless.servicedao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.neu.wireless.bean.ProcessLocationData;
import edu.neu.wireless.mapper.LocationMapper;
import edu.neu.wireless.xmlParser.WLANWifi;

public class InsertRecords implements ConnectionDataConst{



	public void insertWifiMgmtRecords(List<WLANWifi> records, boolean trainingData, String location) {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		int count = 0;
		Connection con = null;
		System.out.println("Is this training data "+trainingData);
		String query = "";
		try {
			if (trainingData) {
				query = "insert into wifi_mgmt_raw_data (ssid,bssid,signal_strength,source_address,location,creation_date) values (?,?,?,?,?,?)";
			} else {
				
				query = "insert into wifi_mgmt_raw_data_req (ssid,bssid,signal_strength,source_address,location,creation_date) values (?,?,?,?,?,?)";
			}
			con = DriverManager.getConnection(URL, userName, password);
			for (WLANWifi wifi : records) {
				// System.out.println("Count :"+count);

				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, wifi.getSsid());
				stmt.setString(2, wifi.getBssid());
				stmt.setFloat(3, wifi.getSignalStrength());
				stmt.setString(4, wifi.getSourceAddress());
				stmt.setString(5, location);
				stmt.setTimestamp(6, currentTimestamp);
				int num = stmt.executeUpdate();
				count = count + 1;
				stmt.close();
			}

			System.out.println("Total records inserted: " + count);

		

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}

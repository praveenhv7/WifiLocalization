package edu.neu.wireless.servicedao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralQueries implements ConnectionDataConst {

	public List<String> getAllLocations() {
		
		List<String> allLocations=new ArrayList<String>();
		Connection con = null;
		System.out.println("Extract all locations");
		String query = "";
		try {
			con = DriverManager.getConnection(URL, userName, password);
			query = "select distinct location  from wifi_mgmt_raw_data";
			PreparedStatement statement=con.prepareStatement(query);
			ResultSet result=statement.executeQuery();
			while(result.next())
			{
				allLocations.add(result.getString(1));
			}
			
			return allLocations;

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
		return allLocations;

	}

}

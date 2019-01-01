package edu.neu.wireless.servicedao;

import java.io.LineNumberInputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.neu.wireless.dto.KNNData;
import edu.neu.wireless.dto.LocationInfo;
import edu.neu.wireless.mapper.ListLocationMapper;
import edu.neu.wireless.mapper.LocationMapper;
import edu.neu.wireless.mapper.TestDataMapper;
import edu.neu.wireless.mapper.TrainingDataMapper;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class ExtractData implements ConnectionDataConst{
	

	
	public KNNData getTrainingAndTestData(Set<String> locSet) {
		
		List<LocationMapper> locMapper = new ArrayList<>();
		List<LocationInfo> listLoc=new ArrayList<>();
		for(String loc:locSet) {
			LocationMapper info=new LocationMapper();
			info.setLocation(loc);
			info.setNumberOfReportedRecords(0);
			locMapper.add(info);
		}
		
		
		int count = 0;
		Connection con = null;
		List<TrainingDataMapper> trainingSet=new LinkedList<TrainingDataMapper>();
		List<TestDataMapper> testSet=new LinkedList<TestDataMapper>();
		
		ListLocationMapper listLocMap=new ListLocationMapper();
		listLocMap.setListLocMap(locMapper);
		System.out.println(locMapper.toArray());
		try {
			con=DriverManager.getConnection(URL, userName,password);
			ArrayDescriptor descriptor=ArrayDescriptor.createDescriptor("PRAVEEN.MATCHED_LOCATIONS_TAB",con);
			ARRAY locationArray=new ARRAY(descriptor,con,locMapper.toArray());
			listLocMap.setSqlArray(locationArray);
			
			
			Map myMap=con.getTypeMap();
			myMap.put("TRAINING_DATA", Class.forName("edu.neu.wireless.mapper.TrainingDataMapper"));
			myMap.put("TEST_DATA", Class.forName("edu.neu.wireless.mapper.TestDataMapper"));
			myMap.put("MATCHED_LOCATIONS_TAB", Class.forName("edu.neu.wireless.mapper.ListLocationMapper"));
			con.setTypeMap(myMap);    
			

			System.out.println("Calling The procedure");
			OracleCallableStatement ocs = 
					  (OracleCallableStatement)con.prepareCall("{ call GET_TRAINING_AND_TEST_DATA(?,?,?) }");
			
			ocs.setArray(1, locationArray);
			ocs.registerOutParameter(2, OracleTypes.ARRAY, "TRAINING_DATA_TAB");
			ocs.registerOutParameter(3, OracleTypes.ARRAY, "TEST_DATA_TAB");
			ocs.execute();
			
			Object[] data = (Object[]) ((Array) ocs.getObject(2)).getArray();
			for(Object tmp : data) {
				trainingSet.add((TrainingDataMapper)tmp);
				
	        }
			
			Object[] dataBssid = (Object[]) ((Array) ocs.getObject(3)).getArray();
			for(Object tmp : dataBssid) {
				testSet.add((TestDataMapper)tmp);
			
			}
			KNNData knnData=new KNNData();
			knnData.setTrainingSet(trainingSet);
			knnData.setTestSet(testSet);
			
			
			System.out.println("Total Training Records"+trainingSet.size() );
			System.out.println(trainingSet.get(0).toString());
			System.out.println("Total Test Records"+testSet.size() );
			ocs.close();
			con.close();
			
			return knnData;
			
			
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
		return null;
		
	}

}

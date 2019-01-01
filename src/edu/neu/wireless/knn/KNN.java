package edu.neu.wireless.knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.neu.wireless.bean.DistanceLocation;
import edu.neu.wireless.bean.MLDataPredictors;
import edu.neu.wireless.comparators.DistanceLocationComparator;
import edu.neu.wireless.comparators.KNNMapComparator;
import edu.neu.wireless.dto.KNNData;
import edu.neu.wireless.mapper.TestDataMapper;
import edu.neu.wireless.mapper.TrainingDataMapper;

public class KNN {
	
	/**
	 * Take one object from test data and compare it with all training data.
	 * @param trainingData
	 * @param MLDataPredictors
	 * @param length
	 */
	private void euclideanDistance( List<TrainingDataMapper> trainingData,MLDataPredictors testData) {	
		
		
		for(int i=0;i<trainingData.size();i++) {
			double distance=0;
			distance = Math.sqrt(differeceData(trainingData.get(i) , testData));
			//System.out.println("Creating Distance Location "+distance +" For location" +trainingData.get(i).getLocation() );
			DistanceLocation distLoc=new DistanceLocation(distance,trainingData.get(i).getLocation());
			
				
				testData.getDistanceLocation().add(distLoc);
				
		}
		
	}
	
	/**
	 * Calculating distance (x1-x2)^2 .... (Z1-Z2)^2 for all n dimensions
	 * @param trainingData
	 * @param testData
	 * @return
	 */
	private double differeceData(MLDataPredictors trainingData, MLDataPredictors testData) {
		double distanceSummation=0;
		int k=1;
		/**
		 * to improve performance select oly 4 bssids around a given bssid
		 */
		if( trainingData.getBSSID() -k <= testData.getBSSID() && trainingData.getBSSID()+k >=testData.getBSSID()) {
		//if(trainingData.getBSSID() == testData.getBSSID()) {
		distanceSummation = Math.pow(trainingData.getBSSID()-testData.getBSSID(),2 );
		distanceSummation+=Math.pow(trainingData.getAvgSignalStrength()-testData.getAvgSignalStrength(),2);
		distanceSummation+=Math.pow(trainingData.getSignalStrength()-testData.getSignalStrength(),2);
		//distanceSummation+=Math.pow(trainingData.getRssMax()-testData.getRssMax(),2);
		//distanceSummation+=Math.pow(trainingData.getRssMin()- testData.getRssMin(),2);
		distanceSummation+=Math.pow(trainingData.getRssMaxAllowed()- testData.getRssMaxAllowed(),2);
		distanceSummation+=Math.pow(trainingData.getRssMinAllowed()- testData.getRssMinAllowed(),2);
		//distanceSummation+=Math.pow(trainingData.getRssSD()-testData.getRssSD(), 2);
		return distanceSummation;
		}
		
		return Double.MAX_VALUE;
		
	}

	/**
	 * Getting 3 neighbour locations and then calculating the overall location using max locations reported
	 * @param trainingData
	 * @param testData
	 * @return
	 */
	private List<String> getNeighbours(List<TrainingDataMapper> trainingData,List<TestDataMapper> testData) {
		int k=3;
		Map<String,Double> locationCount=new LinkedHashMap<>();
		
		for(int i=0;i<testData.size();i++) {
			euclideanDistance(trainingData, testData.get(i));
			Collections.sort(testData.get(i).getDistanceLocation(), new DistanceLocationComparator());
			
			//System.out.println("Test Data Sort Result size is "+testData.size());
			//System.out.println("Size of Distance location "+testData.get(i).getDistanceLocation().size());
			/**
			 * Taking k smallest distance with its location for each test data
			 * and adding it to map location with its count
			 */
			
		
			//double minDistance=Double.MAX_VALUE;
			for(int j=0;j<testData.get(i).getDistanceLocation().size();j++) {
				DistanceLocation distLoc=testData.get(i).getDistanceLocation().get(j);
				
				if(locationCount.containsKey(distLoc.getLocation())) {
					double count=locationCount.get(distLoc.getLocation());
					locationCount.put(distLoc.getLocation(), count+distLoc.getDistance());
					//minDistance=distLoc.getDistance();
				}
				else
					locationCount.put(distLoc.getLocation(),distLoc.getDistance());
			}
			
			for (Map.Entry<String, Double> entry : locationCount.entrySet()) {
			    String key = entry.getKey();
			    Double value = entry.getValue();
			System.out.println("Location :" + key + ". Count :"+value );    
			}
		}
		
		System.out.println("Total Records in Map "+locationCount.size());
		
		Set<Entry<String, Double>> entries = locationCount.entrySet();
		List<Entry<String, Double>> listOfEntries = new ArrayList<Entry<String, Double>>(entries);

		Collections.sort(listOfEntries, new KNNMapComparator());
		
		
		/**
		 * calculating the max count for a location observed.
		 * and returning the one which was observed the most.
		 */
		String[] returnLoc=new String[2];
		List<String> tempStrHolder=new ArrayList<>();
		for(Map.Entry<String,Double> entry : locationCount.entrySet()) {
			
			tempStrHolder.add(entry.getKey());
			if(tempStrHolder.size()==2) {
				break;
			}
		}
	
		
		
		return tempStrHolder;
		
	}
	/**
	 * Connect to database and pull all requests and start KNN. Fingers crossed.
	 */
	public List<String> getDataAndProcess(KNNData knnData) {
		//TO DO
		/**
		 * Call Database procedure and load the mapper for both test data and training data.
		 */
		return getNeighbours(knnData.getTrainingSet(),knnData.getTestSet());
		
	}
	
}

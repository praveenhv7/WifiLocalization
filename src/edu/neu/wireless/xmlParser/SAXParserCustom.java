package edu.neu.wireless.xmlParser;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.neu.wireless.bean.ProcessLocationData;
import edu.neu.wireless.comparators.SortLocationOnCount;
import edu.neu.wireless.display.DisplayFloorMap;
import edu.neu.wireless.dto.KNNData;
import edu.neu.wireless.dto.LocationDisplayConfig;
import edu.neu.wireless.dto.LocationInfo;
import edu.neu.wireless.knn.KNN;
import edu.neu.wireless.mapper.LocationMapper;
import edu.neu.wireless.servicedao.CleanUpData;
import edu.neu.wireless.servicedao.ExtractData;
import edu.neu.wireless.servicedao.GetImageLocations;
import edu.neu.wireless.servicedao.GetLocationMatch;

public class SAXParserCustom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GetImageLocations locationLocConfig = new GetImageLocations();
		Map<String, LocationDisplayConfig> mapDisplayConfig = locationLocConfig.getImageLocations();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.updateComponentTreeUI(frame);

		boolean trainingData = false;
		String setLocation = "";
		String folderPath = "E:\\Wireless\\Wireshark\\Test Data Good";
				

		folderPath = folderPath.replaceAll("\\\\", "//");

		FolderExtract files = new FolderExtract();
		List<String> filesInFolder = files.getAllFiles(folderPath);
		DisplayFloorMap floorMap = new DisplayFloorMap();
		CleanUpData cleanUp = new CleanUpData();
		ExtractData allData = new ExtractData();
		try {

			for (String file : filesInFolder) {

				Map<String, Integer> bssidUniqCount = null;
				List<String> finalLocation = null;
				floorMap.displayImage(frame, null, mapDisplayConfig, 0, file);

				if (!trainingData) {
					String[] fileSplit = file.split("\\\\");
					int totalSize = fileSplit.length;
					setLocation = fileSplit[totalSize - 1];
				}

				Set<String> locationSetBssid = new LinkedHashSet<>();
				Set<String> locationSetRss = new LinkedHashSet<>();
				System.out.println("Starting file " + file);
				File inputFile = new File(file);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				MgmtHandler mgmtHandler = new MgmtHandler(trainingData, setLocation);
				saxParser.parse(inputFile, mgmtHandler);
				System.out.println("Completed file " + file);

				if (!trainingData) {

					GetLocationMatch locEstimate = new GetLocationMatch();
					List<LocationMapper> locBssid = locEstimate.getLocationMatch();

					System.out.println("**********************************************************");
					System.out.println("BASED ON BSSID");

					Collections.sort(locBssid, new SortLocationOnCount());

					int limitRec = 6;

					if (locBssid.size() == 0) {
						cleanUp.cleanUpAllData();
						continue;
					}

					for (LocationMapper location : locBssid) {
						
						System.out.println("* Location Obtained " + location.getLocation());
						System.out.println("* Count of Records  " + location.getNumberOfReportedRecords());
						if (limitRec > 0) {
							locationSetBssid.add(location.getLocation());
							limitRec--;
						}
					}

					System.out.println("-------------------");
					System.out.println("BASED ON RSS");
					int delta = 1;
					List<LocationMapper> locRSS = locEstimate.getLocationRSS(locBssid, delta);

					if (locRSS.size() < 4) {
						delta = 2;
						locRSS = locEstimate.getLocationRSS(locBssid, delta);
					}

					Collections.sort(locRSS, new SortLocationOnCount());
					limitRec = 3;
					for (LocationMapper location : locRSS) {
						System.out.println("* Location Obtained " + location.getLocation());
						System.out.println("* Count of Records  " + location.getNumberOfReportedRecords());
						if (limitRec > 0) {
							locationSetRss.add(location.getLocation());
							limitRec--;
						}
					}
					System.out.println("**********************************************************");

					Set<String> locationSet = null;

					if (locationSetRss.size() == 1 && locationSetBssid.size() > 0) {
						locationSet = locationSetBssid;
					} else if (locationSetRss.size() > 1) {
						locationSet = locationSetRss;
					}

					System.out.println("Selected for KNN");
					for (String loc : locationSet) {
						System.out.println(loc);
					}
					System.out.println("****************************************");

					int accuracyBssid = 0;
					if (locationSetBssid.size() > 0) {
						accuracyBssid = 25;
					}
					floorMap.displayImage(frame, locationSetBssid, mapDisplayConfig, accuracyBssid, file);

					Thread.sleep(2000);

					int accuracyRSS = 0;
					if (locationSetRss.size() > 0) {
						accuracyRSS = 25 + accuracyBssid;
					} else {
						accuracyRSS = accuracyRSS + accuracyBssid;
					}
					floorMap.displayImage(frame, locationSetRss, mapDisplayConfig, accuracyRSS, file);

					Thread.sleep(2000);

					if (locationSet.size() > 1) {
						System.out.println("Calling extract data");

						KNNData knnData = allData.getTrainingAndTestData(locationSet);

						KNN myKnn = new KNN();
						finalLocation = myKnn.getDataAndProcess(knnData);
					}
					int accuracyKNN = 0;
					if (finalLocation != null) {
						if (finalLocation.size() > 0) {
							accuracyKNN = 50 + accuracyRSS;
						} else {
							accuracyKNN += accuracyRSS;
						}
					
					locationSet.clear();
					for (String loc : finalLocation) {
						System.out.println("KNN RESULT " + loc);
						locationSet.add(loc);
					}

					floorMap.displayImage(frame, locationSet, mapDisplayConfig, accuracyKNN, file);

					Thread.sleep(2000);

					if (finalLocation.size() == 2) {
						bssidUniqCount = locEstimate.getUniqueBSSIDForLocation(finalLocation);
					}

					int count = 0;
					String lastLocation = null;
					for (Map.Entry<String, Integer> entry : bssidUniqCount.entrySet()) {
						if (count < entry.getValue()) {
							lastLocation = entry.getKey();
							count = entry.getValue();
						}
					}
					Set<String> lastLocationSet = new LinkedHashSet<>();

					if (lastLocation != null) {
						lastLocationSet.add(lastLocation);
					} else {
						lastLocationSet.add(finalLocation.get(0));
					}
					floorMap.displayImage(frame, lastLocationSet, mapDisplayConfig, accuracyKNN, file);
					}
					Thread.sleep(2000);
					
					cleanUp.cleanUpAllData();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	 

}

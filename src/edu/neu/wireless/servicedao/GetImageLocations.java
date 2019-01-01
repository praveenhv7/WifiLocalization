package edu.neu.wireless.servicedao;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.neu.wireless.dto.LocationDisplayConfig;

public class GetImageLocations {

	
	public Map<String,LocationDisplayConfig> getImageLocations() {
		
		GeneralQueries queryLoc=new GeneralQueries();
		List<String> allLoc=queryLoc.getAllLocations();
	
		Properties prop = new Properties();
		String propFileName = "config.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Map<String,LocationDisplayConfig> mapDisplayConfig=new LinkedHashMap<>();  
		for(String location : allLoc) {
			String coOrdinates = prop.getProperty(location);
			if(coOrdinates !=null) {
				LocationDisplayConfig locConfig=new LocationDisplayConfig();
				String[] coOrdinatesSplit=coOrdinates.split(",");
				locConfig.setX(Integer.parseInt(coOrdinatesSplit[0].split(":")[1]));
				locConfig.setY(Integer.parseInt(coOrdinatesSplit[1].split(":")[1]));
				locConfig.setSCALE_WIDTH(Integer.parseInt(coOrdinatesSplit[2].split(":")[1]));
				locConfig.setSCALE_HEIGHT(Integer.parseInt(coOrdinatesSplit[3].split(":")[1]));
				mapDisplayConfig.put(location, locConfig);
			}
		}
		
		return mapDisplayConfig;
		
		
	}

}

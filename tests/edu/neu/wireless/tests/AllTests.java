package edu.neu.wireless.tests;

import org.junit.jupiter.api.Test;

public class AllTests {

	@Test
	public void fileSplit() {
		
		String location="E:\\Wireless\\Wireshark\\TestData_snell\\Packets10-1520723981.21_CO-LAB_K.xml";
		System.out.println(location);
		
		String[] locationArray=location.split("\\\\");
		System.out.println(locationArray[4]);
		
		
	}
}

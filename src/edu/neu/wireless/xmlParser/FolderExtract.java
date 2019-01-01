package edu.neu.wireless.xmlParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderExtract {
	
	public static void main(String[] args){
		
		String folderPath="E:\\Wireless\\Wireshark\\TestData_snell";
		
		folderPath=folderPath.replaceAll("\\\\", "//");
		
		
		FolderExtract ex=new FolderExtract();
		ex.getAllFiles(folderPath);
		
	}
	//E:\Wireless\Wireshark\TestData_snell

	public List<String>  getAllFiles(String folderPath){
		// TODO Auto-generated method stub
		List<String> filesInFolder=new ArrayList<String>();
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		
		System.out.println("Files " + listOfFiles.length);
		int numberOfFiles = listOfFiles.length;
		String path = folder.getAbsolutePath();
		System.out.println("folder_name " + folder.getName());
		for(int i= 0 ;i < numberOfFiles; i++ ){
			
			System.out.println(listOfFiles[i].getName());
			filesInFolder.add(path+"\\"+listOfFiles[i].getName());
		}
		
		return filesInFolder;
	}

}

package edu.neu.wireless.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import edu.neu.wireless.dto.LocationDisplayConfig;

public class DisplayFloorMap {

	public void displayImage(JFrame frame, Set<String> locations, Map<String, LocationDisplayConfig> mapDisplayConfig,int accuracy,String file) {
		
		File inputOneFloor1 = new File("E:\\Wireless\\Programs\\workspace_java\\XMLParserMgmtFrame\\Resources\\Capture.JPG");
		File inputOneFloor2 = new File("E:\\Wireless\\Programs\\workspace_java\\XMLParserMgmtFrame\\Resources\\floor2.jpg");
		File inputTwo = new File("E:\\Wireless\\Programs\\workspace_java\\XMLParserMgmtFrame\\Resources\\location.png");
		List<String> secondFloorLoc=new ArrayList<String>();
		/*
		 * 	LIB2ROOM296
			LIB2DMCINFO
			LIB2CIRCLE1
			LIB2STAIR2
		 */
		
		secondFloorLoc.add("LIB2ROOM296");
		secondFloorLoc.add("LIB2DMCINFO");
		secondFloorLoc.add("LIB2CIRCLE1");
		secondFloorLoc.add("LIB2STAIR2");
		boolean flagSecondFloor=false;
		
		if (locations != null) {
			for (String location : locations) {
				if(secondFloorLoc.contains(location)) {
					flagSecondFloor=true;
				}
			}
		}
		try {
			BufferedImage imOriginal=null;
			if(flagSecondFloor) {
				 imOriginal = ImageIO.read(inputOneFloor2);
			}
			else
			{
				imOriginal = ImageIO.read(inputOneFloor1);
			}
			display(imOriginal, frame,accuracy,file);

			if (locations != null) {
				frame.getContentPane().removeAll();

				for (String location : locations) {
					imOriginal = overLayImage(imOriginal, mapDisplayConfig.get(location));
				}

			}
			display(imOriginal, frame,accuracy,file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage overLayImage(BufferedImage image, LocationDisplayConfig imgConfig) {
		try {
			File inputTwo = new File(
					"E:\\Wireless\\Programs\\workspace_java\\XMLParserMgmtFrame\\Resources\\location.png");
			BufferedImage imageOverlay = ImageIO.read(inputTwo);

			Graphics2D g = image.createGraphics();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			// x:735,y:550,SCALE_WIDTH:128,SCALE_HEIGHT:128
			g.drawImage(imageOverlay,imgConfig.getX(),imgConfig.getY(),imgConfig.getSCALE_WIDTH(),imgConfig.getSCALE_HEIGHT(), null);
			g.dispose();

			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;

	}

	public void display(BufferedImage image, JFrame f,int accuracy,String file) {

		JLabel text = new JLabel("TEST FILE->"+file);
		text.setForeground(Color.red);
		text.setSize(new Dimension(1000, 20));
		text.setLocation(new Point(500, 10));
		f.add(text);
		
		Integer accuracyStr=new Integer(accuracy);
		JLabel textAccuracy = new JLabel("ACCURACY->"+accuracyStr.toString()+"%");
		textAccuracy.setForeground(Color.red);
		textAccuracy.setSize(new Dimension(1000, 30));
		textAccuracy.setLocation(new Point(500, 30));
		f.add(textAccuracy);
		

		f.getContentPane().add(new JLabel(new ImageIcon(image)));
		f.pack();
		f.setVisible(true);
		f.invalidate();
		f.validate();
		f.repaint();
	}

}

package filetools.tiff;

import java.util.ArrayList;

public class TiffTagZbw {

	ArrayList<String> properties = new ArrayList<String>();		
	
	int decTiffTag; // e. g. 256 for imageWidth
	String hexValue; //e. g. 0x100 for imageWidth
	String tiffTagName; 
	String tiffTagContent;	
	String tiffTagDescription;	
	String tiffTagKind;			
}

package preservingfiles;

import java.util.ArrayList;

public class ZbwTiffTag {
	
	ArrayList<String> properties = new ArrayList<String>(); // necessary?

	int decTiffTag; // e. g. 256 for imageWidth
	String hexValue; // e. g. 0x100 for imageWidth
	String tiffTagName;
	String tiffTagContent;
	String tiffTagDescription;
	String tiffTagKind;
}

package filetools.tiff;

import java.util.ArrayList;

public class TiffTagZbw {

	ArrayList<String> properties = new ArrayList<String>();		
	
	int decTiffTag; // e. g. 256 for imageWidth
	boolean tiffTagexists; // true wenn vorhanden, false wenn nicht vorhanden

	boolean mandatoryTiffTag; // true fuer 12 mandatory TiffTags, false für alle
								// anderen

	// ggf koennte man noch hinzufuegen, ob ein TiffTag optional moeglich ist
	// oder eher nicht vorgesehen. Da wir noch nicht entschieden haben wie hier
	// unsere Policy ist, machen wir das aber erst einmal nicht.
	
	
	

}

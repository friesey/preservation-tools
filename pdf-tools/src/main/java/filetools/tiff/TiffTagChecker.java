package filetools.tiff;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

public class TiffTagChecker {
	
    //12 mandatory
    public static String ImageWidth = "256";
    public static boolean booleanImageWidth = false;
    public static String ImageLength = "257";
    public static boolean booleanImageLength = false;
    public static String Compression = "259";
    public static boolean booleanCompression = false;
    public static String Photometric = "262";
    public static boolean booleanPhotometric = false;
    public static String StripOffSets = "273";
    public static boolean booleanStripOffSets = false;
    public static String SamplesPerPixel = "277";
    public static boolean booleanSamplesPerPixel = false;
    public static String RowsPerStrip = "278";
    public static boolean booleanRowsPerStrip = false;
    public static String StripByteCounts = "279";
    public static boolean booleanStripByteCounts = false;
    public static String XResolution = "282";
    public static boolean booleanXResolution = false;
    public static String YResolution = "283";
    public static boolean booleanYResolution = false;
    public static String ResolutionUnit = "296";
    public static boolean booleanResolutionUnit = false;
    public static String BitPerSample = "258";
    public static boolean booleanBitPerSample = false;
    //optional & possible
    public static String SubFileType = "255";
    public static boolean booleanSubFileType = false;
    public static String NewSubFileType = "254";
    public static boolean booleanNewSubFileType = false;
    public static String TreshHolding = "263";
    public static boolean booleanTreshHolding = false;
    public static String CellWidth = "264";
    public static boolean booleanCellWidth = false;
    public static String CellLength = "265";
    public static boolean booleanCellLength = false;
    public static String FillOrder = "266";
    public static boolean booleanFillOrder = false;
    public static String ImageDescription = "270";
    public static boolean booleanImageDescription = false;
    public static String Make = "271";
    public static boolean booleanMake = false;
    public static String Model = "272";
    public static boolean booleanModel = false;
    public static String Orientation = "274";
    public static boolean booleanOrientation = false;
    public static String MinSampleValue = "280";
    public static boolean booleanMinSampleValue = false;
    public static String MaxSampleValue = "281";
    public static boolean booleanMaxSampleValue = false;
    public static String PlanarConfig = "284";
    public static boolean booleanPlanarConfig = false;
    public static String FreeOffSets = "288";
    public static boolean booleanFreeOffSets = false;
    public static String FreebyteCounts = "289";
    public static boolean booleanFreebyteCounts = false;
    public static String GrayResponseUnit = "290";
    public static boolean booleanGrayResponseUnit = false;
    public static String GrayResponseCurve = "291";
    public static boolean booleanGrayResponseCurve = false;
    public static String Software = "305";
    public static boolean booleanSoftware = false;
    public static String Datetime = "306";
    public static boolean booleanDatetime = false;
    public static String Artist = "315";
    public static boolean booleanArtist = false;
    public static String HostComputer = "316";
    public static boolean booleanHostComputer = false;
    public static String ColorMap = "320";
    public static boolean booleanColorMap = false;
    public static String ExtraSamples = "338";
    public static boolean booleanExtraSamples = false;
    public static String Copyright = "33432";
    public static boolean booleanCopyright = false;
    // the rest of the tags should not be in a Baseline Tiff
    public static String T4Option = "292";
    public static boolean booleanT4Option = false;
    public static String T6Option = "293";
    public static boolean booleanT6Option = false;
    public static String PageNumber = "297";
    public static boolean booleanPageNumber = false;

	public static void analyseTiffTags(File file) {
		// TODO Auto-generated method stub
		
		try {
			
		}
		
		 catch (Exception e) {
				JOptionPane.showMessageDialog(null, "An exception occured " + e);
			}
		
	}

}

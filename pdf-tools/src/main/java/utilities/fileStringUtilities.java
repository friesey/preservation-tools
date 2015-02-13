package utilities;

import java.io.File;
import java.util.regex.Pattern;

public class fileStringUtilities {	
	
	public static String getExtension(File file) {
		String[] segs = file.toString().split(Pattern.quote("."));
		return segs[segs.length - 1];
	}
	
	public static String getExtension(String filename) {
		String[] segs = filename.split(Pattern.quote("."));
		return segs[segs.length - 1];
	}

	public static String getFileName(File file) {		
		String filename;						
		String[] parts = file.toString().split(Pattern.quote("\\"));	
		filename = parts[parts.length-1]; //filename including extension
		
		String[] segs = filename.split(Pattern.quote("."));
		filename = segs[segs.length - 2];				
		
		return filename;
	}
	
	public static String getFileName(String filename) {								
		String[] parts = filename.toString().split(Pattern.quote("\\"));	
		filename = parts[parts.length-1]; //filename including extension		
		String[] segs = filename.split(Pattern.quote("."));
		filename = segs[segs.length - 2];						
		return filename;
	}
	
	public static String reduceXmlEscapors(String string) {
		string = string.replace("\"", "&quot;");
		string = string.replace("\'", "&apos;");
		string = string.replace("<", "&lt;");
		string = string.replace(">", "&gt;");
		string = string.replace("&", " &amp;");
		return string;
	}
	
	public static String reduceNULvalues (String string){
		string = string.replace("\0", "");
		return string;		
	}
	
	public static String reduceUnitSeparator (String string){
		string = string.replace("^_", ""); //there might be more: Wikipedia, C0 and C1 control codes
		//this has yet to be tested
		return string;		
	}
}

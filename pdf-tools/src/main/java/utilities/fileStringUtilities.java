package utilities;

import java.util.regex.Pattern;

public class fileStringUtilities {	
	
	public static String getExtension (String file) {
		String extension;
		String[] parts = file.split(Pattern.quote("."));		
		extension = parts[parts.length-1];			
		return extension;				
	}
}

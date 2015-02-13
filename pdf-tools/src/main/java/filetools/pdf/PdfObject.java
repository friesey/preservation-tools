package filetools.pdf;

import java.util.regex.Pattern;

public class PdfObject {	
	
	String path;
	String name;
	
	public void setPath (String newPath){
		path = newPath;
	}

	public String getPath() {
		return path;
	}
	
	public String setName(String path) {								
		String[] parts = path.toString().split(Pattern.quote("\\"));	
		name = parts[parts.length-1]; //filename including extension		
		String[] segs = name.split(Pattern.quote("."));
		name = segs[segs.length - 2];						
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	//encryption
	//size
	//number of lines
	//pdf ok
	//pdf valid (but do not use jhove here)
	//pdf version?
	//pdf a?
	
	// get xmp metadata
	
	//what is available under pdfAnalysis
	
	
	
}

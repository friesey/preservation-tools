package filetools.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.pdf.PdfReader;

public class PdfObject {
	// org.slf4j.Logger logger = LoggerFactory.getLogger(PdfObject.class);
	String path;
	String name;
	File pdfFile;
	String checksumMD5;
	boolean isEncrypted;
	boolean isPdfA;
	String xmpMetadata;
	PdfReader reader;
	
	//constructor for the PdfObject
	public int id;		
	public static int count = 0;
	
	public PdfObject (String path){
		id = count;
		count++; 
	}
	

	public void setPath(String newPath) {
		path = newPath;
	}

	public String getPath() {
		return path;
	}

	public String getName(String path) {
		String[] parts = path.toString().split(Pattern.quote("\\"));
		name = parts[parts.length - 1]; // filename including extension
		String[] segs = name.split(Pattern.quote("."));
		name = segs[segs.length - 2];
		return name;
	}
	
	public void showNameXml (String name){
		System.out.println("<FileName>" + name+ "</FileName>");
	}

	public File toFile(String newPath) {
		File pdfFile = new File(newPath);
		return pdfFile;
	}

	public String getMD5Checksum(File file) {
		try {
			checksumMD5 = DigestUtils.md5Hex(new FileInputStream(file));
			return checksumMD5;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isEncrypted(File file) throws IOException {
		// TODO: might test type of encryption via iText

		PDDocument pdf = PDDocument.load(file);
		if (pdf.isEncrypted()) {
			isEncrypted = true;
		} else {
			isEncrypted = false;
		}
		pdf.close();
		// logger.error (e.toString());
		return isEncrypted;
	}

	public boolean isPdfA(String path) throws IOException {
		try {
		reader = new PdfReader(path);
		if (reader.getMetadata() != null) {
			xmpMetadata = new String(reader.getMetadata());
			if (xmpMetadata.contains("pdfaid:conformance")) {
				isPdfA = true;
			} else {
				isPdfA = false;
			}
		} else {
			isPdfA = false;
		}
		return isPdfA;
		}
		catch (Exception e){
			isPdfA = false;
			return isPdfA;
		}
	}

	// encryption
	// size
	// number of lines
	// pdf ok
	// pdf valid (but do not use jhove here)
	// pdf version?

	// get xmp metadata

	// what is available under pdfAnalysis

}

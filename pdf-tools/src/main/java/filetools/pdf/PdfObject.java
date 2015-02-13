package filetools.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

public class PdfObject {

	String path;
	String name;
	File pdfFile;
	String checksumMD5;

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

	public File toFile(String newPath) {
		File pdfFile = new File(newPath);
		return pdfFile;
	}

	public String getMD5Checksum(File file) {
		System.out.println(file.toString());

		try {
			checksumMD5 = DigestUtils.md5Hex(new FileInputStream(file));
			return checksumMD5;
		} catch (Exception e) {
			return null;
		}
	}

	// encryption
	// size
	// number of lines
	// pdf ok
	// pdf valid (but do not use jhove here)
	// pdf version?
	// pdf a?

	// get xmp metadata

	// what is available under pdfAnalysis

}

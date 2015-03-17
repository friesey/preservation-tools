package preservingfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

public class ZbwFile {
	String path;
	String fileName;
	String checksumMD5;
	String mimetype;
	String fileExtension;
	long size;

	// size
	public ZbwFile() {
	}

	public String getPath() {
		return path;
	}

	public String getMD5Checksum(File file) {
		try {
			checksumMD5 = DigestUtils.md5Hex(new FileInputStream(file));
			return checksumMD5;
		} catch (Exception e) {
			return null;
		}
	}

	public void setPath(String newPath) {
		path = newPath;
	}

	public String getName(String path) {
		String[] parts = path.toString().split(Pattern.quote("\\"));
		fileName = parts[parts.length - 1]; // filename including extension
		String[] segs = fileName.split(Pattern.quote("."));
		fileName = segs[segs.length - 2];
		return fileName;
	}

	public File toFile(String newPath) { // get a file Object from String path
		File file = new File(newPath);
		return file;
	}

	public long getSizeinKB(File file) {
		long size = file.length();

		size = size / 1024;

		return size;
	}

	public String getFileMimeType(File file) throws IOException {
		mimetype = Files.probeContentType(file.toPath());
		return mimetype;
	}

	public String getFileExtension(String path) {

		String[] segs = path.split(Pattern.quote("\\"));		
		segs = segs[segs.length - 1].split(Pattern.quote("."));
		return segs[segs.length-1];
		
	}

	// TODO: Formaterkennungstool einbinden

}

package preservingfiles;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;

public class ZbwFile {
	String path;
	String name;
	String checksumMD5;

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
		name = parts[parts.length - 1]; // filename including extension
		String[] segs = name.split(Pattern.quote("."));
		name = segs[segs.length - 2];
		return name;
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

}

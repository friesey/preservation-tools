package preservetools.files;

import java.io.File;
import java.util.ArrayList;

public class ChecksumChecker {
	
	
	/**
	 * Checks known MD5 checksums for Adobe Acrobat Software to ignore the file if checksum indicates that file is e. g. Acrobat Reader SW.
	 * 
	 * @param File
	 * @return: boolean
	 * @throws
	 */

	public static boolean testIfChecksumisPdfReaderSoftware(File file) {
		ArrayList<String> checksumlist = new ArrayList<String>();
		checksumlist.add("bb7c1d820e2a2db263655a799590caab");

		//TODO: create MD5 checksum 
		String checksum = null;		
		
		if (checksumlist.contains(checksum)) {
			return false;
		} else {
			return true;
		}
	}

}

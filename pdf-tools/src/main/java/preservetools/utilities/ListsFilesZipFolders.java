package preservetools.utilities;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import preservetools.files.executables.CdRom_IsoImageChecker;

public class ListsFilesZipFolders {

	// TODO: Look into zip folders
	/*
	 * String extension = preservetools.files.GenericFileAnalysis
	 * .getFileExtension(f);
	 * 
	 * if (extension != null) {
	 * 
	 * // TODO: What about .tar-files etc? if (extension.equals("zip")) { try {
	 * ZipFile zf = new ZipFile(f.toString()); Enumeration<? extends ZipEntry> e
	 * = zf.entries(); ZipEntry ze; while (e.hasMoreElements()) { ze =
	 * e.nextElement();
	 * 
	 * if (!ze.isDirectory()) { File CompFile = new File(ze.toString());
	 * list.add(CompFile); }
	 * 
	 * } zf.close(); }
	 * 
	 * catch (Exception e) { // TODO: After this exception is caused, the adding
	 * // of the rest of the files in the zip folder stops
	 * CdRom_IsoImageChecker.filesExecutable.println(f + " causes an Exception"
	 * + e); } }
	 * 
	 * else {
	 * 
	 * list.add(f); } }
	 * 
	 * else { System.out .println("Extension of file could not be extracted " +
	 * f); list.add(f); }
	 */
}

package preservetools.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class GenericFileAnalysis {

	// TODO: If I do not know yet which file it is, just a generic analyse

	static BufferedReader fileReader;
	static String magicNumberPdf = "%PDF";
	static String magicNumberTiffIntel = "II";
	static String magicNumberTiffMotorola = "MM";

	private static final long DEFAULT_MAX_FILE_LENGTH = 1024 * 1024 * 16;

	/**
	 * Tests if the first line of the file contains the proper PDF-Header "%PDF"
	 * For Strings
	 *
	 * @param Creates
	 *            a PdfHeaderTest-Pdf-Reader and reads the first line of the
	 *            PDF-file like an editor would do. Overloaded with the data
	 *            type "String"
	 * @return: boolean false = no PDF-Header; true = first line contains
	 *          PDF-Header
	 */
	public static boolean testFileHeaderPdf(String file) throws IOException {
		fileReader = new BufferedReader(new FileReader(file));
		String FileHeader = fileReader.readLine();
		// System.out.println(FileHeader);
		if (FileHeader != null) {
			if (FileHeader.contains("magicNumberPdf")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean testFileHeaderPdf(File file) throws IOException {
		fileReader = new BufferedReader(new FileReader(file));
		String FileHeader = fileReader.readLine();
		// System.out.println(FileHeader);
		if (FileHeader != null) {
			if (FileHeader.contains("magicNumberPdf")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean testFileHeaderTiff(String file) throws IOException {
		fileReader = new BufferedReader(new FileReader(file));
		String FileHeader = fileReader.readLine();
		// System.out.println(FileHeader);
		if (FileHeader != null) {
			if ((FileHeader.contains(magicNumberTiffIntel))
					|| (FileHeader.contains(magicNumberTiffMotorola))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean testFileHeaderTiff(File file) throws IOException {
		fileReader = new BufferedReader(new FileReader(file));
		String FileHeader = fileReader.readLine();
		// System.out.println(FileHeader);
		if (FileHeader != null) {
			if ((FileHeader.contains(magicNumberTiffIntel))
					|| (FileHeader.contains(magicNumberTiffMotorola))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks the size of the Pdf-file, because some big Pdf Files might cause
	 * exceptions. *
	 * 
	 * @param file
	 *            (should be Pdf)
	 * @return: boolean
	 * @throws
	 */

	public static boolean checkFileSize(File file) {
		if (file.length() > DEFAULT_MAX_FILE_LENGTH) {
			return true;
		} else {
			return false;
		}
	}

	public static String getFileExtension(File file) throws IOException {
		String extension = Files.probeContentType(file.toPath());
		return extension;
	}
	
	/**
	 * Checks Extension for known Non-executable extensions like "pdf"
	 * PDF/A-3 and some higher PDF-Version might contain executables as Attachements.
	 * But that should not make an ISO image necessary. Beware of what for this method is used, however.
	 * @param String mimetype	           
	 * @return: boolean
	 * @throws
	 */

	public static boolean testIfExtensionCanbeExecutable(String extension) {
		ArrayList<String> extensionlist = new ArrayList<String>();
		extensionlist.add("pdf");
		extensionlist.add("doc");
		extensionlist.add("docx");
		extensionlist.add("docx");
		extensionlist.add("xls");
		extensionlist.add("cda");
		extensionlist.add("epub");
		extensionlist.add("opf");
		if (extensionlist.contains(extension)) {
			return false;
		} else {

			return true;
		}
	}

	
	/**
	 * Checks Mimetype. If not known (==null) it might be executable.
	 * If test, image, audio, video, it should not be executbable.
	 * Otherwise go ahead for testIfExtensionCanbeExecutable
	 * 
	 * @param String mimetype	           
	 * @return: boolean
	 * @throws
	 */
	
	public static boolean testIfMimeMightBeExecutable(String mimetype) {

		if (mimetype == null) {
			return true;
		} else if (!mimetype.contains("text")) {
			return false;
		} else if (!mimetype.contains("image")) {
			return false;
		} else if (!mimetype.contains("audio")) {
			return false;
		} else if (!mimetype.contains("video")) {
			return false;
		} else {
			return true;
		}
	}

}

package PdfHackerTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.itextpdf.text.pdf.PdfReader;

public class PdfUtilities {

	/**
	 * Variables and objects used within the whole package
	 */

	static BufferedReader PdfHeaderTest;

	/**
	 * Methods used within the whole package
	 */

	/**
	 * lists all files and directories in given directory
	 * 
	 * @param
	 * @return: ArrayList<File> of all found files and subfolders
	 * 
	 */
	static ArrayList<File> getPaths(File file, ArrayList<File> list) {
		if (file == null || list == null || !file.isDirectory())
			return null;
		File[] fileArr = file.listFiles();
		for (File f : fileArr) {  // still issues if no rights to scroll folder
			if (f.isDirectory()) {
				getPaths(f, list);
			}
			list.add(f);
		}
		return list;
	}

	/**
	 * Tests if the first line of the file contains the proper PDF-Header "%PDF"
	 * 
	 * @param Creates
	 *            a PdfHeaderTest-Pdf-Reader and reads the first line of the
	 *            PDF-file like an editor would do.
	 * @return: boolean false = no PDF-Header; true = first line contains
	 *          PDF-Header
	 */
	static boolean FileHeaderTest(File file) throws IOException {
		PdfHeaderTest = new BufferedReader(new FileReader(file));
		String FileHeader = PdfHeaderTest.readLine();
		System.out.println(FileHeader);
		if (FileHeader != null) {

			if (FileHeader.contains("%PDF")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Chooses the folder which is examined via a simple folder browser Dialog
	 * 
	 * @param Does
	 *            not need any to begin with.
	 * @return: string for folder path
	 */

	public static String ChooseFolder() throws FileNotFoundException {
		JFileChooser j = new JFileChooser();
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		j.showOpenDialog(j);					
		if (j.getSelectedFile() == null) {
			System.out.println("No folder was chosen");			
		} else {
			String folder = j.getSelectedFile().getPath();
			return folder;
		}
		return null;
		
	}

	/**
	 * Determines which PDF version it is. Can also detect PDF/A.
	 * 
	 * @param File
	 *            (should be PDF)
	 * @return: String PDF Version TODO: occasionally throws WARN about log4j
	 *          that I cannot understand or get rid of.
	 * @throws IOException
	 */

	public static String PdfAChecker(File file) throws IOException {

		String pdfType = "No XMP Metadata";
		String XmpMetadata;

		PdfReader reader;

		try {
			reader = new PdfReader(file.toString());

			// There is no PDF/A compliance before PDF 1.4
			if (reader.getPdfVersion() > 3) {
				if (reader.getMetadata() != null) {
					XmpMetadata = new String(reader.getMetadata()); // nullpointerException
					reader.close();
					if (XmpMetadata.contains("pdfaid:conformance")) {
						pdfType = "PDF/A";
					} else {
						pdfType = "PDF 1.4 or higher";
					}
				}
			} else {
				pdfType = "PDF 1.0 - 1.3";
			}
			return pdfType;

		} catch (java.lang.NullPointerException e) {			
			System.out.println(e);
			pdfType = "PDF cannot be read by PdfReader";
			return pdfType;
		}
	}

	/**
	 * Simple Encryption Test without reader, because encryption causes lots of
	 * exceptions.
	 * 
	 * @param PDDocument
	 *            (should be PDF)
	 * @return: boolean
	 * @throws IOException
	 */

	public static boolean EncryptionTest(PDDocument file) throws IOException {

		// PDDocumentInformation info =
		// PDDocument.load(file).getDocumentInformation();

		if (file.isEncrypted() == true) {
			System.out.println(file + " is encrypted");
			return true;
		} else {
			return false;
		}
	}
}

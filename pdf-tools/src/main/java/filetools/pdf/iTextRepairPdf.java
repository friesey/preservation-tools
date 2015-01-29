package filetools.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class iTextRepairPdf {

	static String examinedFolder;

	public static void main(String args[]) throws IOException {

		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();

			if (examinedFolder != null) {

				try {
					ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
					if (files == null)
						return;
					String mimetype;
					PdfReader reader;

					for (int i = 0; i < files.size(); i++) {
						System.out.println(files.get(i).getCanonicalPath());
						mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
						if (mimetype != null) {
							if (mimetype.equals("application/pdf")) {
								System.out.println(files.get(i));
								try {
									reader = new PdfReader(files.get(i).toString());
									//TODO: crashes if encrypted PDF
									if (!reader.isEncrypted()) {
										repairWithItext(reader, files.get(i).getName());
									}
								} catch (Exception e) {
									System.out.println(e);
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);		
		}

	}

	/**
	 * Makes a copy from each PDF-file in the folder and puts it in the same
	 * folder with the prefix"Mig_iText"
	 * 
	 * @param takes
	 *            in PdfReader and the filename as a string
	 * @return: void
	 * 
	 */

	@SuppressWarnings("rawtypes")
	static void repairWithItext(PdfReader reader, String filename) throws DocumentException, IOException {

		Map info = reader.getInfo();
		Document document = new Document();

		PdfCopy copy = new PdfCopy(document, new FileOutputStream(examinedFolder + "//" + "Mig_iText" + filename));

		// copy.setPDFXConformance(PdfWriter.PDF_VERSION_1_5);
		copy.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
		document.addTitle((String) info.get("Title"));
		if (info.get("Author") != null)
			document.addAuthor((String) info.get("Author"));
		if (info.get("Keywords") != null)
			document.addKeywords((String) info.get("Keywords"));
		// TODO: Is this the right Keyword?
		if (info.get("Date") != null)
			document.addKeywords((String) info.get("Date"));
		copy.createXmpMetadata();
		document.open();
		int n = reader.getNumberOfPages();
		for (int i = 0; i < n;) {
			copy.addPage(copy.getImportedPage(reader, ++i));
		}
		copy.freeReader(reader);
		document.close();
	}
}

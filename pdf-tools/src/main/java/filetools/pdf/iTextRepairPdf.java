package filetools.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class iTextRepairPdf {

	static String examinedFolder;

	public static void main(String args[]) throws IOException, DocumentException {

		examinedFolder = utilities.BrowserDialogs.chooseFolder();

		if (examinedFolder != null) {

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
			if (files == null)
				return;
			String mimetype;
			PdfReader reader;

			for (int i = 0; i < files.size(); i++) {
				mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
				if (mimetype != null) {
					if (mimetype.equals("application/pdf")) {
						try {
							reader = new PdfReader(files.get(i).toString());
							if (!reader.isEncrypted()) { // TODO: crashes if
															// encrypted PDF
								repairWithItext(reader, files.get(i));
							}
						} catch (Exception e) {
							System.out.println(files.get(i).toString() + e);
						}
					}
				}
			}
		}
		/*
		 * } catch (FileNotFoundException e) {
		 * JOptionPane.showMessageDialog(null, e, "error message",
		 * JOptionPane.ERROR_MESSAGE); }
		 */

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
	static void repairWithItext(PdfReader reader, File filename) throws DocumentException, IOException {

		Map<String, String> info = reader.getInfo();
		Document document = new Document();

		String filenameStr = filename.getName();

		PdfCopy copy = new PdfCopy(document, new FileOutputStream(examinedFolder + "//" + "Mig_iText" + filenameStr));

		int pdfVersion = filetools.pdf.PdfAnalysis.getPdfVersion(filename.toString());

		//TODO: But all the output PDF is PDF 1.4
		switch (pdfVersion) {
		case 2:
			copy.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
			break;
		case 3:
			copy.setPdfVersion(PdfWriter.PDF_VERSION_1_3);
			break;
		case 4:
			copy.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
			break;
		case 5:
			copy.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
			break;
		case 6:
			copy.setPdfVersion(PdfWriter.PDF_VERSION_1_6);
			break;
		case 7:
			copy.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
			break;
		}

		// TODO: there is a way to get all the metadata as described in one of
		// Bruno's books
		if (info.get("Title") != null)
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

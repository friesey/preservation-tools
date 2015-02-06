package filetools.pdf;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class PdfTwinTesterBulk {

	// TODO: To test whole folders, find a way to know which PDF files to
	// compare

	static String OrgPdf;
	static String MigPdf;
	static long filesizeOrg;
	static long filesizeMig;

	static String folder;

	static PrintWriter outputfile;

	public static void main(String args[]) throws IOException {

		JOptionPane.showMessageDialog(null, "Please choose the folder with PDF Files to compare.", "Enter String Mask", JOptionPane.QUESTION_MESSAGE);
		folder = utilities.BrowserDialogs.chooseFolder();

		if (folder != null) {
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
			ArrayList<File> pdffiles = new ArrayList<File>();
			boolean pdfok;
			for (int i = 0; i < files.size(); i++) {

				String extension = utilities.fileStringUtilities.getExtension(files.get(i).toString()).toLowerCase();

				if (extension.equals("pdf")) {
					pdfok = PdfTwinTest.analysePdfok(files.get(i).toString());
					if (pdfok == true) {
						pdffiles.add(files.get(i));
					}
				}
			}

			// ZbwPdfObject[] pdfobjectArr = new ZbwPdfObject[pdffiles.size()];

			ArrayList<ZbwPdfObject> pdfObjectList = new ArrayList<ZbwPdfObject>();

			for (int i = 0; i < pdffiles.size(); i++) {

				ZbwPdfObject temp = new ZbwPdfObject();
				PDDocument pd = new PDDocument();
				pd = PDDocument.load(pdffiles.get(i));
				temp.metaInfo = pd.getDocumentInformation();
				temp.filename = utilities.fileStringUtilities.getFileName(pdffiles.get(i).toString());
				temp.creationDate = getsomeMetadata(temp.metaInfo);
				temp.title = temp.metaInfo.getTitle();
				pdfObjectList.add(temp);
				// pdfobjectArr[i] = temp;
				pd.close();
			}

			ArrayList<ZbwPdfObject> copy = new ArrayList<ZbwPdfObject>();
			for (int i = 0; i < pdfObjectList.size(); i++) {
				copy.add(pdfObjectList.get(i));
			}

			for (int i = 0; i < pdfObjectList.size(); i++) {
				for (int j = 0; j < copy.size(); j++) {

					if (pdfObjectList.get(i).title.equals(copy.get(j).title)) {
						// PDF Dateien vergleichen
						if (!pdfObjectList.get(i).filename.equals(copy.get(j).filename)) {
							System.out.println(pdfObjectList.get(i).filename + "  " + copy.get(j).filename);
						}
					}
				}
				copy.remove(pdfObjectList.get(i));
			}
		}

		else {
			System.out.println("Please choose two files.");
		}
	}

	private static Date getsomeMetadata(PDDocumentInformation info) throws IOException {

		try {
			Calendar creationYear = info.getCreationDate();
			Date creationDate = creationYear.getTime();
			return creationDate;

		} catch (Exception e) {
			return null;
		}

	}

}

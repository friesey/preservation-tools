package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfTextExtraction {

	public static void main(String args[]) throws IOException {

		String examinedFolder = utilities.BrowserDialogs.chooseFolder();

		PrintWriter outputXml = new PrintWriter(new FileWriter(examinedFolder + "\\PdfTextEctraction.xml"));

		if (examinedFolder != null) {

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());
			if (files != null) {

				for (int i = 0; i < files.size(); i++) {

					String extension = FilenameUtils.getExtension(files.get(i).toString()).toLowerCase();

					if (extension.equals("pdf")) {
						outputXml.println (i+1);

						outputXml.println(files.get(i).toString());

						long filesizePdf = files.get(i).length();

						// PDDocument testfileOrg = PDDocument.load(OrgPdf);

						String[] linesPdf = PdfAnalysis.extractsPdfLines(files.get(i).toString());

						int lenPdf = linesPdf.length;

						for (int j = 0; j < lenPdf; j++) {

							outputXml.println(linesPdf[j]);
						}

					}
				}
			}

		}
		
		outputXml.close();
	}

}

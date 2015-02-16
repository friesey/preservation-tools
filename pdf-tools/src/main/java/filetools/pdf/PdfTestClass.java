package filetools.pdf;

import java.io.File;
import java.util.ArrayList;

public class PdfTestClass {
	public static void main(String args[]) throws Exception {
		String folder = utilities.BrowserDialogs.chooseFolder();
		if (folder != null) {
			output.XmlWriter outputXml = new output.XmlWriter(folder, "PdfAnalysis.xml");
			outputXml.printXml("<?xml-stylesheet type=\"text/xsl\" href=\"PdfAnalysis.xsl\"?>");
			output.XslStyleSheets.pdfAnalysis(folder + "//" + "PdfAnalysis.xsl");
			outputXml.printXml("<PdfAnalysis>");
			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
			for (int i = 0; i < files.size(); i++) {
				String extension = utilities.fileStringUtilities.getExtension(files.get(i).toString()).toLowerCase();
				if (extension.equals("pdf")) {
					outputXml.printXml("<Pdf>");
					String newPath = files.get(i).toString();
					PdfObject testPdf = new PdfObject(newPath);
					testPdf.setPath(newPath);
					// testPdf.pdfFile = testPdf.toFile(newPath);
					testPdf.pdfFile = files.get(i);
					try {
						outputXml.printXml("<ObjectId>" + testPdf.id + "</ObjectId>");
						outputXml.printXml("<FilePath><![CDATA[" + testPdf.getPath() + "]]></FilePath>");
						outputXml.printXml("<FileName><![CDATA[" + testPdf.getName(testPdf.getPath()) + "]]></FileName>");
						outputXml.printXml("<MD5Checksum>" + testPdf.getMD5Checksum(testPdf.pdfFile) + "</MD5Checksum>");
						outputXml.printXml("<PdfEncrypted>" + testPdf.isEncrypted(testPdf.pdfFile) + "</PdfEncrypted>");
						// //TODO: get rid of all the log4j Logging in the
						// Console
						outputXml.printXml("<PdfA>" + testPdf.isPdfA(testPdf.path) + "</PdfA>");
					} catch (Exception e) {
						// These information do not show up in xslt-version yet
						String parts[] = e.toString().split(":");
						outputXml.printXml("<Exception>" + parts[0] + "</Exception>");
						String text;
						StringBuilder build = new StringBuilder();
						for (int j = 1; j < parts.length; j++) {
							build.append(parts[j]);
						}
						text = build.toString();
						outputXml.printXml("<ExceptionText>" + text + "</ExceptionText>");
					}
					outputXml.printXml("</Pdf>");
				}
			}
			outputXml.printXml("<PdfAnalysed>" + PdfObject.count + "</PdfAnalysed>");
			outputXml.printXml("</PdfAnalysis>");
			outputXml.closeXmlWriter();
		}
	}
}
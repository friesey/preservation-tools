package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import com.itextpdf.text.pdf.PdfReader;

public class XmpMetadataExtractor {

	public static void main(String args[]) throws IOException {
		try {

			String pdfFolder = utilities.BrowserDialogs.chooseFolder();

			PdfReader reader;

			PrintWriter outputfile = new PrintWriter(new FileWriter(pdfFolder + "//" + "PdfMetadata.xml"));

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";
			String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"PdfMetadataStyle.xsl\"?>";

			String xsltLocation = (pdfFolder + "//" + "PdfMetadataStyle.xsl");

			output.XslStyleSheets.PdfMetadataCustomizedXsl(xsltLocation);

			outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			outputfile.println(xmlxslStyleSheet);
			outputfile.println("<PdfMetadata>");

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(pdfFolder), new ArrayList<File>());

			for (int i = 0; i < files.size(); i++) {

				String extension = utilities.fileStringUtilities.getExtension(files.get(i).toString());
				extension = extension.toLowerCase();
				if (extension.equals("pdf")) {
					outputfile.println("<File>");

					String name = utilities.fileStringUtilities.getFileName(files.get(i));

					name = name.replace("\"", "&quot;");
					name = name.replace("\'", "&apos;");
					name = name.replace("<", "&lt;");
					name = name.replace(">", "&gt;");
					name = name.replace("&", " &amp;");

					outputfile.println("<FileName>" + name + "</FileName>");

					boolean pdfok = filetools.pdf.PdfAnalysis.testPdfOk(files.get(i));

					if (pdfok == true) {

						reader = new PdfReader(files.get(i).toString());

						if (reader != null) {
							Map<String, String> metadata = reader.getInfo();
							int metaSize = metadata.size();
							outputfile.println("<MetadataEntries>" + metaSize + "</MetadataEntries>");

							String[] keys = (String[]) metadata.keySet().toArray(new String[metaSize]);
							String[] values = (String[]) metadata.values().toArray(new String[metaSize]);

							for (int j = 0; j < metaSize; j++) {

								values[j] = values[j].replace("\"", "&quot;");
								values[j] = values[j].replace("\'", "&apos;");
								values[j] = values[j].replace("<", "&lt;");
								values[j] = values[j].replace(">", "&gt;");
								values[j] = values[j].replace("&", " &amp;");

								// TODO: transform Umlaute "ue" usw. sonst hat
								// Travis wieder Probs

								outputfile.println("<InfoEntry name=\"" + keys[j] + "\">" + values[j] + "</InfoEntry>");

							}
							/*
							 * String xmpMeta = new
							 * String(reader.getMetadata()); // rdf, // dc //
							 * and // xmp // data outputfile.println("<XmpData>"
							 * + "<![CDATA[" + xmpMeta + "]]>" + "</XmpData>");
							 */
						}
					} else {

						outputfile.println("<PdfAnalysis>" + "false" + "</PdfAnalysis>");
					}
					outputfile.println("</File>");
				}

			}
			outputfile.println("</PdfMetadata>");
			outputfile.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}
}

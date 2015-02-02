package filetools.pdf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;

public class XmpMetadataReader {
	
	static PdfReader reader;

	public static void main(String args[]) throws IOException {

		try {

			String pdfFolder = utilities.BrowserDialogs.chooseFolder();

			String xmpMetadata;
		

			PrintWriter outputfile = new PrintWriter(new FileWriter("C://PresToolsTest//log.xml"));

			String xmlVersion = "xml version='1.0'";
			String xmlEncoding = "encoding='ISO-8859-1'";
			// String xmlxslStyleSheet =
			// "<?xml-stylesheet type=\"text/xsl\" href=\"PdfMetadata.xsl\"?>";

			outputfile.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
			// outputfile.println(xmlxslStyleSheet);
			outputfile.println("<PdfXmpMetadata>");

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(pdfFolder), new ArrayList<File>());

			for (int i = 0; i < files.size(); i++) {
				String mimetype = filetools.GenericFileAnalysis.getFileMimeType(files.get(i));
				if (mimetype != null) {
					if (mimetype.equals("application/pdf")) {
						outputfile.println("<File>");

						reader = new PdfReader(files.get(i).toString());
					
							outputfile.println("<FileName>" + files.get(i).toString() + "</FileName>");
							if (reader.getMetadata() != null) {
						//		System.out.println(files.get(i).toString());

								xmpMetadata = new String(reader.getMetadata());
							
								ArrayList<String> xmpMetadataArr = new ArrayList<String>();
								@SuppressWarnings("resource")
								Scanner input = new Scanner(xmpMetadata);
								input.useDelimiter("\n");
								while (input.hasNext()) {
									xmpMetadataArr.add(input.next());
								}

								outputfile.println("<XmpMetadataEntries>" + xmpMetadataArr.size() + "</XmpMetadataEntries>");

								for (int j = 0; j < xmpMetadataArr.size(); j++) {
									if (xmpMetadataArr.get(j).contains("CreatorTool")) {
										System.out.println(xmpMetadataArr.get(j));
										outputfile.println(xmpMetadataArr.get(j));
										//TODO: this does not work in the xml sheet yet
									}
								}
							
							}

							else {
								System.out.println("No XMP Metadata found");
							}
						}
					}
					outputfile.println("</File>");
					outputfile.println("</PdfXmpMetadata>");
					outputfile.close();
					reader.close();
				}

	
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
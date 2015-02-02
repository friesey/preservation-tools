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
					name = reduceXmlEscapors(name);
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
								
								values [j] = reduceXmlEscapors(values[j]);

								// TODO: transform Umlaute "ue" usw. sonst hat
								// Travis wieder Probs
								outputfile.println("<InfoEntry name=\"" + keys[j] + "\">" + values[j] + "</InfoEntry>");
							}
							
							// Editing for XSLT table Output
							
							if (metadata.get("CreationDate") != null) {
								if (metadata.get("CreationDate").length() > 10) {
									String creationDate = transformDate(metadata.get("CreationDate"));
									outputfile.println("<CreationDate>" + creationDate + "</CreationDate>");
								}
								else {
									outputfile.println("<CreationDate>" + metadata.get("CreationDate") + "</CreationDate>");
								}
							}
							
							if (metadata.get("ModDate") != null) {
								if (metadata.get("ModDate").length() > 10) {
									String creationDate = transformDate(metadata.get("ModDate"));
									outputfile.println("<ModificationDate>" + creationDate + "</ModificationDate>");
								}
								else {
									outputfile.println("<ModificationDate>" + metadata.get("ModDate") + "</ModificationDate>");
								}
							}
							
							if (metadata.get("Title") != null) {			
								String title = reduceXmlEscapors( metadata.get("Title") );
								outputfile.println("<Title>" + title + "</Title>");
							}

							if (metadata.get("Author") != null) {	
								String author = reduceXmlEscapors(metadata.get("Author") );
								outputfile.println("<Author>" + author + "</Author>");
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
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static String reduceXmlEscapors(String string) {
		string = string.replace("\"", "&quot;");
		string= string.replace("\'", "&apos;");
		string = string.replace("<", "&lt;");
		string= string.replace(">", "&gt;");
		string= string.replace("&", " &amp;");
		return string;
	}

	private static String transformDate(String creationDate) {

		creationDate = creationDate.replace("D:", "");
		String year = creationDate.substring(0, 4);
		String month = creationDate.substring(4, 6);
		String day = creationDate.substring(6, 8);

		switch (month) {
		case "01":
			month = "January";
			break;
		case "02":
			month = "February";
			break;
		case "03":
			month = "March";
			break;
		case "04":
			month = "April";
			break;
		case "05":
			month = "May";
			break;
		case "06":
			month = "June";
			break;
		case "07":
			month = "July";
			break;
		case "08":
			month = "August";
			break;
		case "09":
			month = "September";
			break;
		case "10":
			month = "October";
			break;
		case "11":
			month = "November";
			break;
		case "12":
			month = "December";
			break;
		}


		creationDate = (day + " " + month + " " + year);
		return creationDate;
	}		
}
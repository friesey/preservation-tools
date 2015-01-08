package filetools.tiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;

public class TiffFileAnalysis {

	static String examinedFolder;
	static PrintWriter xmlsummary;

	// mandatory in baseline tiff
	public static ArrayList<TiffTagZbw> listTiffTags = new ArrayList<TiffTagZbw>();

	public static void main(String args[]) throws IOException, ImageReadException {

		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {

				String outputfile = examinedFolder + "//XmlOutput.xml";
				PrintWriter xmlsummary = new PrintWriter(new FileWriter(outputfile));

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

				String xmlVersion = "xml version='1.0'";
				String xmlEncoding = "encoding='ISO-8859-1'";
				String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"TiffTagStyle.xsl\"?>";

				// TODO: create Stylesheet in the same folder

				xmlsummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
				xmlsummary.println(xsltStyleSheet);
				xmlsummary.println("<TiffTagAnalysis>");

				for (int i = 0; i < files.size(); i++) {
					System.out.println(files.get(i).getCanonicalPath());
					String tiffExtension = "TIF";

					String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath());
					if (extension.equals(tiffExtension)) {

						if (filetools.GenericFileAnalysis.testFileHeaderTiff(files.get(i))) {
							xmlsummary.println("<TiffFile>");
							xmlsummary.println("<FilePath>" + files.get(i).toString() + "</FilePath>");

							analyseTiffTags(files.get(i), xmlsummary);
							xmlsummary.println("</TiffFile>");
						}

						else {
							System.out.println("This file purports to be a tiff-file. It has a .TIF-extension, but no lacks the magic number.");
						}

						// how to get a certain tiff tag:
						// TiffField tileWidthField =
						// tiffDirectory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);

					}
				}

				// How often does each TiffTag occur?

				int tifftagscount = listTiffTags.size();

				// save all tifftags in ArrayList<String>
				ArrayList<String> alltifftags = new ArrayList<String>();
				for (int i = 0; i < tifftagscount; i++) {
					alltifftags.add(listTiffTags.get(i).tiffTagName);
				}

				Collections.sort(alltifftags);

				ArrayList<String> origintifftags = new ArrayList<String>();
				for (int i = 0; i < alltifftags.size(); i++) {
					// function for this
					origintifftags.add(alltifftags.get(i));
				}

				// get rid of redundant entries
				int n = 0;
				while (n < alltifftags.size() - 1) {
					if (alltifftags.get(n).equals(alltifftags.get(n + 1))) {
						alltifftags.remove(n);
					} else {
						n++;
					}
				}
				
				xmlsummary.println("<AnalysisSummary>");				
				xmlsummary.println("<DifferentTiffTagsInSample>" + alltifftags.size() + "</DifferentTiffTagsInSample>");
				
	
				// how often does each Tiff Tag occur?
				int j = 0;
				int temp;
				for (n = 0; n < alltifftags.size(); n++) {
					temp = 0;
					for (j = 0; j < origintifftags.size(); j++) {
						if (alltifftags.get(n).equals(origintifftags.get(j))) {
							temp++;
						}
					}				
					xmlsummary.println("<DifferentTiffTags>");	
					xmlsummary.println ("<TiffTag>" + alltifftags.get(n) + "</TiffTag>");
					xmlsummary.println ("<Occurance>" + temp + "</Occurance>");
					xmlsummary.println("</DifferentTiffTags>");	
				
				}
				
				xmlsummary.println("</AnalysisSummary>");
				xmlsummary.println("</TiffTagAnalysis>");
				xmlsummary.close();

			}
		} catch (FileNotFoundException e) {
		}
	}

	public static void analyseTiffTags(File file, PrintWriter xmlsummary) throws IOException, ImageReadException {

		IImageMetadata metadata = Sanselan.getMetadata(file);
		TiffDirectory tiffDirectory = ((TiffImageMetadata) metadata).findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT);

		ArrayList<TiffField> allEntries = tiffDirectory.getDirectoryEntrys();
		xmlsummary.println("<TiffTagsCount>" + allEntries.size() + "</TiffTagsCount>");
		xmlsummary.println("<TiffTags>");
		xmlsummary.println("<FileName>" + file.getName() + "</FileName>");

		for (int i = 0; i < allEntries.size(); i++) {
			// replace all the different separators with ','
			String editentry = allEntries.get(i).toString().replace(" (", ",");
			editentry = editentry.replace("): ", ",");
			editentry = editentry.replace(": ", ",");
			editentry = editentry.replace(")", "");

			String[] parts = editentry.split(",");

			TiffTagZbw temp = new TiffTagZbw();
			temp.decTiffTag = Integer.parseInt(parts[0]);
			temp.hexValue = parts[1];
			temp.tiffTagName = parts[2];
			temp.tiffTagContent = (parts[3] + parts[4]);
			listTiffTags.add(temp);

			xmlsummary.println("<decValue>" + temp.decTiffTag + "</decValue>");
			xmlsummary.println("<hexValue>" + temp.hexValue + "</hexValue>");
			xmlsummary.println("<TiffTagName>" + temp.tiffTagName + "</TiffTagName>");
			xmlsummary.println("<TiffTagContent>" + temp.tiffTagContent + "</TiffTagContent>");
		}

		xmlsummary.println("</TiffTags>");

		// how to get a certain tiff tag:
		// TiffField tileWidthField =
		// tiffDirectory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);

	}

}

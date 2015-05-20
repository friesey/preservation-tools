package filetools.tiff;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;

public class TiffFileAnalysis {

	public static String examinedFolder;
	static PrintWriter xmlsummary;
	static PrintWriter csvsummary;
	static int problematicTiffs;
	static String SEPARATOR = ";";

	public static ArrayList<TiffTagZbw> listTiffTags = new ArrayList<TiffTagZbw>();

	public static void main(String args[]) throws IOException, ImageReadException {

		try {
			examinedFolder = utilities.BrowserDialogs.chooseFolder();
			if (examinedFolder != null) {

				int examinedTiffs = 0;

				JFrame f = new JFrame();
				JButton but = new JButton("... Program is running ... ");
				f.add(but, BorderLayout.PAGE_END);
				f.pack();
				f.setVisible(true);

				problematicTiffs = 0;

				String name = JOptionPane.showInputDialog(null, "Please choose a name for the XML Outputfile.", "Enter String Mask", JOptionPane.PLAIN_MESSAGE);
				;
				String outputfile = examinedFolder + "//" + name + ".xml";
				String outputCsv =  examinedFolder + "//" + name + ".csv";
				File outputfileFile = new File(outputfile);
				
				int eingabe;

				if (outputfileFile.exists()) {
					eingabe = JOptionPane.showConfirmDialog(null, name + ".xml already exists in folder. Choosing \"Ja\" closes program. Choosing \"Nein\" overwrites existing File and start analysis.", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				}

				else {
					eingabe = 1;
				}

				if (eingabe == 1) {

					PrintWriter xmlsummary = new PrintWriter(new FileWriter(outputfile));
					csvsummary = new PrintWriter(new FileWriter(outputCsv));

					ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

					String xmlVersion = "xml version='1.0'";
					String xmlEncoding = "encoding='ISO-8859-1'";
					String xsltStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"TiffTagStyle.xsl\"?>";

					// TODO: create Stylesheet in the same folder

					output.XslStyleSheets.TiffTagAnalysisCustomizedXsl();

					xmlsummary.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
					xmlsummary.println(xsltStyleSheet);
					xmlsummary.println("<TiffTagAnalysis>");

					for (int i = 0; i < files.size(); i++) {

						String tiffExtension = "tif";

						String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath()).toLowerCase();
						if (extension.equals(tiffExtension)) {

							if (filetools.GenericFileAnalysis.testFileHeaderTiff(files.get(i))) {
								xmlsummary.println("<TiffFile>");
								xmlsummary.println("<FilePath>" + files.get(i).toString() + "</FilePath>");

								analyseTiffTags(files.get(i), xmlsummary);
								examinedTiffs++;
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
						xmlsummary.println("<TiffTag>" + alltifftags.get(n) + "</TiffTag>");
						for (int i = 0; i < listTiffTags.size(); i++) {
							if (listTiffTags.get(i).tiffTagName.equals(alltifftags.get(n))) {
								xmlsummary.println("<SourceOfTag>" + listTiffTags.get(i).tiffTagKind + "</SourceOfTag>");
								xmlsummary.println("<Description>" + listTiffTags.get(i).tiffTagDescription + "</Description>");
								break; // very important, otherwise the
										// description would be put out as often
										// as each tiff tag occurs in sample
							}
						}
						xmlsummary.println("<Occurance>" + temp + "</Occurance>");
						xmlsummary.println("</DifferentTiffTags>");
					}
					xmlsummary.println("<ProblematicTiffs>" + problematicTiffs + "</ProblematicTiffs>");
					xmlsummary.println("<ExaminedTiffs>" + examinedTiffs + "</ExaminedTiffs>");
					xmlsummary.println("</AnalysisSummary>");
					xmlsummary.println("</TiffTagAnalysis>");

					xmlsummary.close();
					csvsummary.close();
					f.dispose();
				}

				else {
					JOptionPane.showMessageDialog(null, "You have closed the program. No analysis was done.", "Information", JOptionPane.INFORMATION_MESSAGE);
					f.dispose();

				}
			}
		} catch (FileNotFoundException e) {
		}
	}

	public static void analyseTiffTags(File file, PrintWriter xmlsummary) throws IOException, ImageReadException {

		try {

			IImageMetadata metadata = Sanselan.getMetadata(file);
			TiffDirectory tiffDirectory = ((TiffImageMetadata) metadata).findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT);

			@SuppressWarnings("unchecked")
			ArrayList<TiffField> allEntries = tiffDirectory.getDirectoryEntrys();
			tiffDirectory.dump();
			xmlsummary.println("<TiffTagsCount>" + allEntries.size() + "</TiffTagsCount>");
			xmlsummary.println("<TiffTags>");
			xmlsummary.println("<FileName>" + file.getName() + "</FileName>");
			
			csvsummary.println ("File" + SEPARATOR + file.getName());
			csvsummary.println ("" + SEPARATOR + "");
			
			csvsummary.println ("Tiff Tag Name" + SEPARATOR + "Value");

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
				
				csvsummary.println (temp.tiffTagName + SEPARATOR + temp.tiffTagContent);
				
				temp.tiffTagContent = utilities.fileStringUtilities.reduceNULvalues(temp.tiffTagContent);
				temp.tiffTagContent = utilities.fileStringUtilities.reduceUnitSeparator(temp.tiffTagContent);

				/*
				 * xmlsummary.println("<decValue>" + temp.decTiffTag +
				 * "</decValue>"); xmlsummary.println("<hexValue>" +
				 * temp.hexValue + "</hexValue>");
				 * xmlsummary.println("<TiffTagName>" + temp.tiffTagName +
				 * "</TiffTagName>"); xmlsummary.println("<TiffTagContent>" +
				 * temp.tiffTagContent + "</TiffTagContent>");
				 */

				int privateTag = 32768;
				int reusabletagbeginn = 65000;
				int reusabletagend = 65535;

				if (temp.decTiffTag > privateTag) {

					if (temp.decTiffTag == 32995) {
						xmlsummary.println("<Matteing>" + temp.tiffTagContent + "</Matteing>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 32996) {
						xmlsummary.println("<DataType>" + temp.tiffTagContent + "</DataType>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 32997) {
						xmlsummary.println("<ImageDepth>" + temp.tiffTagContent + "</ImageDepth>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 32998) {
						xmlsummary.println("<TileDepth>" + temp.tiffTagContent + "</TileDepth>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 33432) {
						xmlsummary.println("<Copyright>" + temp.tiffTagContent + "</Copyright>");
						temp.tiffTagDescription = "Copyright notice.";
						temp.tiffTagKind = "Baseline";
					}

					else if (temp.decTiffTag == 34264) {
						xmlsummary.println("<ModelTransform>" + temp.tiffTagContent + "</ModelTransform>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 34735) {
						xmlsummary.println("<GeoTiffDirectory>" + temp.tiffTagContent + "</GeoTiffDirectory>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 34736) {
						xmlsummary.println("<GeoTiffDoubleParams>" + temp.tiffTagContent + "</GeoTiffDoubleParams>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 34736) {
						xmlsummary.println("<GeoTiffDoubleParams>" + temp.tiffTagContent + "</GeoTiffDoubleParams>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 34737) {
						xmlsummary.println("<GeoTiffAsciiParams>" + temp.tiffTagContent + "</GeoTiffAsciiParams>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 34665) {

						xmlsummary.println("<ExifIfd>" + temp.tiffTagContent + "</ExifIfd>");
						temp.tiffTagDescription = "A pointer to the Exif IFD. Private";
						temp.tiffTagKind = "Private";
					}

					else if (temp.decTiffTag == 34675) {
						xmlsummary.println("<InterColorProfile>" + temp.tiffTagContent + "</InterColorProfile>");
						temp.tiffTagDescription = "ICC profile data.";
						temp.tiffTagKind = "TIFF/EP spec, p. 47 Exif private IFD";
					}

					else if (temp.decTiffTag == 33723) {
						xmlsummary.println("<IPTC_NAA>" + temp.tiffTagContent + "</IPTC_NAA>");
						temp.tiffTagDescription = "IPTC-NAA (International Press Telecommunications Council-Newspaper Association of America) metadata.";
						temp.tiffTagKind = "TIFF/EP spec, p. 33";
					}

					else if (temp.decTiffTag == 33723) {
						xmlsummary.println("<IPTC_NAA>" + temp.tiffTagContent + "</IPTC_NAA>");
						temp.tiffTagDescription = "IPTC-NAA (International Press Telecommunications Council-Newspaper Association of America) metadata.";
						temp.tiffTagKind = "TIFF/EP spec, p. 33";
					}

					else if (temp.decTiffTag == 34377) {
						xmlsummary.println("<PhotoshopSettings>" + temp.tiffTagContent + "</PhotoshopSettings>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "private";
					}

					else if (temp.decTiffTag == 37439) {
						xmlsummary.println("<StoNits>" + temp.tiffTagContent + "</StoNits>");
						temp.tiffTagDescription = "NO DESCRIPTION YET.";
						temp.tiffTagKind = "private";
					} else {
						xmlsummary.println("<UnknownTiffTag>" + temp.tiffTagName + "</UnknownTiffTag>");
						temp.tiffTagKind = "Private";
					}
				}

				else if ((reusabletagbeginn < temp.decTiffTag) && (temp.decTiffTag < reusabletagend)) {
					xmlsummary.println("<ReusableTiffTag>");
					xmlsummary.println("<UnknownTiffTag>" + temp.tiffTagName + "</UnknownTiffTag>");
					temp.tiffTagKind = "Reusable";
					JOptionPane.showMessageDialog(null, "Found a reusable Tiff Tag:" + temp.tiffTagName + temp.tiffTagContent, "Information", JOptionPane.INFORMATION_MESSAGE);
					xmlsummary.println("</ReusableTiffTag>");
				}

				// TODO: if dec value > 32768, it is a private tag
				// TODO: reusable range <65000 & < 65535
				
				
		

				switch (temp.decTiffTag) {

				case 254:
					xmlsummary.println("<NewSubFileType>" + temp.tiffTagContent + "</NewSubFileType>");
					temp.tiffTagDescription = "Similar to SubFileType, but NewSubFileType.";
					temp.tiffTagKind = "Baseline";
					break;

				case 255:
					xmlsummary.println("<SubFileType>" + temp.tiffTagContent + "</SubFileType>");
					temp.tiffTagDescription = "A general indication of the kind of data contained in this subfile.";
					temp.tiffTagKind = "Baseline";
					break;

				case 256:
					xmlsummary.println("<ImageWidth>" + temp.tiffTagContent + "</ImageWidth>");
					temp.tiffTagDescription = "The number of columns in the image, i.e., the number of pixels per row.";
					temp.tiffTagKind = "Baseline";
					break;

				case 257:
					xmlsummary.println("<ImageLength>" + temp.tiffTagContent + "</ImageLength>");
					temp.tiffTagDescription = "The number of rows of pixels in the image.";
					temp.tiffTagKind = "Baseline";
					break;

				case 259:
					xmlsummary.println("<Compression>" + temp.tiffTagContent + "</Compression>");
					temp.tiffTagDescription = "Compression scheme used on the image data.";
					temp.tiffTagKind = "Baseline";
					break;

				case 262:
					xmlsummary.println("<Photometric>" + temp.tiffTagContent + "</Photometric>");
					temp.tiffTagDescription = "The color space of the image data.";
					temp.tiffTagKind = "Baseline";
					break;

				case 273:
					xmlsummary.println("<StripOffSets>" + temp.tiffTagContent + "</StripOffSets>");
					temp.tiffTagDescription = "For each strip, the byte offset of that strip.";
					temp.tiffTagKind = "Baseline";

					break;

				case 277:
					xmlsummary.println("<SamplesPerPixel>" + temp.tiffTagContent + "</SamplesPerPixel>");
					temp.tiffTagDescription = "The number of components per pixel.";
					temp.tiffTagKind = "Baseline";

					break;

				case 278:
					xmlsummary.println("<RowsPerStrip>" + temp.tiffTagContent + "</RowsPerStrip>");
					temp.tiffTagDescription = "The number of rows per strip.";
					temp.tiffTagKind = "Baseline";

					break;

				case 279:
					xmlsummary.println("<StripByteCounts>" + temp.tiffTagContent + "</StripByteCounts>");
					temp.tiffTagDescription = "For each strip, the number of bytes in the strip after compression.";
					temp.tiffTagKind = "Baseline";

					break;

				case 282:
					xmlsummary.println("<XResolution>" + temp.tiffTagContent + "</XResolution>");
					temp.tiffTagDescription = "The number of pixels per ResolutionUnit in the ImageWidth direction.";
					temp.tiffTagKind = "Baseline";
					break;

				case 283:
					xmlsummary.println("<YResolution>" + temp.tiffTagContent + "</YResolution>");
					temp.tiffTagDescription = "The number of pixels per ResolutionUnit in the ImageLength direction.";
					temp.tiffTagKind = "Baseline";
					break;

				case 296:
					xmlsummary.println("<ResolutionUnit>" + temp.tiffTagContent + "</ResolutionUnit>");
					temp.tiffTagDescription = "The unit of measurement for XResolution and YResolution.";
					temp.tiffTagKind = "Baseline";
					break;

				case 258:
					xmlsummary.println("<BitsPerSample>" + temp.tiffTagContent + "</BitsPerSample>");
					temp.tiffTagDescription = "Number of bits per component.";
					temp.tiffTagKind = "Baseline";
					break;

				// nice to have

				case 280:
					xmlsummary.println("<MinSampleValue>" + temp.tiffTagContent + "</MinSampleValue>");
					temp.tiffTagDescription = "The minimum component value used.";
					temp.tiffTagKind = "Extended";
					break;

				case 281:
					xmlsummary.println("<MaxSampleValue>" + temp.tiffTagContent + "</MaxSampleValue>");
					temp.tiffTagDescription = "The maximum component value used.";
					temp.tiffTagKind = "Extended";
					break;

				case 290:
					xmlsummary.println("<GrayResponseUnit>" + temp.tiffTagContent + "</GrayResponseUnit>");
					temp.tiffTagDescription = "The precision of the information contained in the GrayResponseCurve.";
					temp.tiffTagKind = "Baseline";
					break;

				case 291:
					xmlsummary.println("<GrayResponseCurve>" + temp.tiffTagContent + "</GrayResponseCurve>");
					temp.tiffTagDescription = "For grayscale data, the optical density of each possible pixel value.";
					temp.tiffTagKind = "Baseline";
					break;

				case 305:
					xmlsummary.println("<Software><![CDATA[" + temp.tiffTagContent + "]]></Software>");
					temp.tiffTagDescription = "Name and version number of the software package(s) used to create the image.";
					temp.tiffTagKind = "Baseline";
					break;

				case 306:
					xmlsummary.println("<DateTime><![CDATA[" + temp.tiffTagContent + "]]></DateTime>");
					temp.tiffTagDescription = "Date ad time of image creation.";
					temp.tiffTagKind = "Baseline";

					break;

				case 315:
					xmlsummary.println("<Artist>" + temp.tiffTagContent + "</Artist>");
					temp.tiffTagDescription = "Person who created the image.";
					temp.tiffTagKind = "Baseline";
					break;

				case 320:
					xmlsummary.println("<ColorMap>" + temp.tiffTagContent + "</ColorMap>");
					temp.tiffTagDescription = "A color map for palette color images.";
					temp.tiffTagKind = "Baseline";
					break;

				// not in Baseline, but nice to have for Digital Preservation

				case 338:
					xmlsummary.println("<ExtraSamples>" + temp.tiffTagContent + "</ExtraSamples>");
					temp.tiffTagDescription = "Description of extra components.";
					temp.tiffTagKind = "Baseline";
					break;

				// other known Tiff Tags

				case 292:
					xmlsummary.println("<T4Option>" + temp.tiffTagContent + "</T4Option>");
					temp.tiffTagDescription = "Options for Group 3 Fax compression. Extended";
					temp.tiffTagKind = "Extended";
					break;

				case 293:
					xmlsummary.println("<T6Option>" + temp.tiffTagContent + "</T6Option>");
					temp.tiffTagDescription = "Options for Group 4 Fax compression. Extended";
					temp.tiffTagKind = "Extended";
					break;

				case 297:
					xmlsummary.println("<PageNumber>" + temp.tiffTagContent + "</PageNumber>");
					temp.tiffTagDescription = "The page number of the page from which this image was scanned. Extended";
					temp.tiffTagKind = "Extended";
					break;

				case 266:
					xmlsummary.println("<FillOrder>" + temp.tiffTagContent + "</FillOrder>");
					temp.tiffTagDescription = "The logical order of bits within a byte.";
					temp.tiffTagKind = "Baseline";
					break;

				case 269:
					xmlsummary.println("<DocumentName>" + temp.tiffTagContent + "</DocumentName>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Baseline";
					break;

				case 270:
					xmlsummary.println("<ImageDescription>" + temp.tiffTagContent + "</ImageDescription>");
					temp.tiffTagDescription = "A string that describes the subject of the image.";
					temp.tiffTagKind = "Baseline";
					break;

				case 271:
					xmlsummary.println("<Make>" + temp.tiffTagContent + "</Make>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Baseline";
					break;

				case 272:
					xmlsummary.println("<Model><![CDATA[" + temp.tiffTagContent + "]]></Model>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Baseline";
					break;

				case 274:
					xmlsummary.println("<Orientation>" + temp.tiffTagContent + "</Orientation>");
					temp.tiffTagDescription = "The orientation of the image with respect to the rows and columns.";
					temp.tiffTagKind = "Baseline";
					break;

				case 284:
					xmlsummary.println("<PlanarConfiguration>" + temp.tiffTagContent + "</PlanarConfiguration>");
					temp.tiffTagDescription = "How the components of each pixel are stored.";
					temp.tiffTagKind = "Baseline";
					break;

				case 286:
					xmlsummary.println("<XPosition>" + temp.tiffTagContent + "</XPosition>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Baseline";
					break;

				case 287:
					xmlsummary.println("<YPosition>" + temp.tiffTagContent + "</YPosition>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Baseline";
					break;

				case 317:
					xmlsummary.println("<Predictor>" + temp.tiffTagContent + "</Predictor>");
					temp.tiffTagDescription = "A mathematical operator that is applied to the image data before an encoding scheme is applied.";
					temp.tiffTagKind = "Extended";
					break;

				case 318:
					xmlsummary.println("<WhitePoint>" + temp.tiffTagContent + "</WhitePoint>");
					temp.tiffTagDescription = "The chromaticity of the white point of the image.";
					temp.tiffTagKind = "Extended";
					break;

				case 319:
					xmlsummary.println("<PrimaryChromaticities>" + temp.tiffTagContent + "</PrimaryChromaticities>");
					temp.tiffTagDescription = "The chromaticities of the primaries of the image.";
					temp.tiffTagKind = "Extended";
					break;

				case 321:
					xmlsummary.println("<HalftoneHints>" + temp.tiffTagContent + "</HalftoneHints>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 322:
					xmlsummary.println("<TileWidth>" + temp.tiffTagContent + "</TileWidth>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 323:
					xmlsummary.println("<TileLength>" + temp.tiffTagContent + "</TileLength>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 324:
					xmlsummary.println("<TileOffsets>" + temp.tiffTagContent + "</TileOffsets>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 325:
					xmlsummary.println("<TileByteCounts>" + temp.tiffTagContent + "</TileByteCounts>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 326:
					xmlsummary.println("<BadFaxLines>" + temp.tiffTagContent + "</BadFaxLines>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 327:
					xmlsummary.println("<CleanFaxData>" + temp.tiffTagContent + "</CleanFaxData>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 328:
					xmlsummary.println("<ConsecutiveBadFaxLines>" + temp.tiffTagContent + "</ConsecutiveBadFaxLines>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 347:
					xmlsummary.println("<JPEGTables>" + temp.tiffTagContent + "</JPEGTables>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 513:
					xmlsummary.println("<JpgFromRawStart>" + temp.tiffTagContent + "</JpgFromRawStart>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 514:
					xmlsummary.println("<JpgFromRawLength>" + temp.tiffTagContent + "</JpgFromRawLength>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 515:
					xmlsummary.println("<JPEGRestartInterval>" + temp.tiffTagContent + "</JPEGRestartInterval>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 529:
					xmlsummary.println("<YCbCrCoefficients>" + temp.tiffTagContent + "</YCbCrCoefficients>");
					temp.tiffTagDescription = "The transformation from RGB to YCbCr image data.";
					temp.tiffTagKind = "Extended";
					break;

				case 530:
					xmlsummary.println("<YCbCrSubSampling>" + temp.tiffTagContent + "</YCbCrSubSampling>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 531:
					xmlsummary.println("<YCbCrPositioning>" + temp.tiffTagContent + "</YCbCrPositioning>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;

				case 532:
					xmlsummary.println("<ReferenceBlackWhite>" + temp.tiffTagContent + "</ReferenceBlackWhite>");
					temp.tiffTagDescription = "Specifies a pair of headroom and footroom image data values (codes) for each pixel component.";
					temp.tiffTagKind = "Extended";
					break;

				case 263:
					xmlsummary.println("<Threshholding>" + temp.tiffTagContent + "</Threshholding>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Baseline";
					break;
					
				case 330:
					xmlsummary.println("<SubIFD>" + temp.tiffTagContent + "</SubIFD>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;
										
				case 333:
					xmlsummary.println("<InkNames>" + temp.tiffTagContent + "</InkNames>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;
					
				case 336:
					xmlsummary.println("<DotRange>" + temp.tiffTagContent + "</DotRange>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;	

				case 339:
					xmlsummary.println("<SampleFormat>" + temp.tiffTagContent + "</SampleFormat>");
					temp.tiffTagDescription = "Specifies how to interpret each data sample in a pixel.";
					temp.tiffTagKind = "Extended";
					break;

				case 512:
					xmlsummary.println("<JPEGProc>" + temp.tiffTagContent + "</JPEGProc>");
					temp.tiffTagDescription = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
					temp.tiffTagKind = "Extended";
					break;

				case 519:
					xmlsummary.println("<JPEGQTables>" + temp.tiffTagContent + "</JPEGQTables>");
					temp.tiffTagDescription = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
					temp.tiffTagKind = "Extended";
					break;

				case 520:
					xmlsummary.println("<JPEGDCTables>" + temp.tiffTagContent + "</JPEGDCTables>");
					temp.tiffTagDescription = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
					temp.tiffTagKind = "Extended";
					break;

				case 521:
					xmlsummary.println("<JPEGACTables>" + temp.tiffTagContent + "</JPEGACTables>");
					temp.tiffTagDescription = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
					temp.tiffTagKind = "Extended";
					break;
					
				case 700:
					xmlsummary.println("<ApplicationNotes>" + temp.tiffTagContent + "</ApplicationNotes>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Extended";
					break;
					
					

				// known private tags

				case 34665:
					xmlsummary.println("<ExifIfd>" + temp.tiffTagContent + "</ExifIfd>");
					temp.tiffTagDescription = "A pointer to the Exif IFD. Private";
					temp.tiffTagKind = "Private";
					break;

				case 32995:
					xmlsummary.println("<Matteing>" + temp.tiffTagContent + "</Matteing>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 32996:
					xmlsummary.println("<DataType>" + temp.tiffTagContent + "</DataType>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";

				case 32997:
					xmlsummary.println("<ImageDepth>" + temp.tiffTagContent + "</ImageDepth>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 32998:
					xmlsummary.println("<TileDepth>" + temp.tiffTagContent + "</TileDepth>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 33432:
					xmlsummary.println("<Copyright>" + temp.tiffTagContent + "</Copyright>");
					temp.tiffTagDescription = "Copyright notice.";
					temp.tiffTagKind = "Baseline";
					break;

				case 34264:
					xmlsummary.println("<ModelTransform>" + temp.tiffTagContent + "</ModelTransform>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 34735:
					xmlsummary.println("<GeoTiffDirectory>" + temp.tiffTagContent + "</GeoTiffDirectory>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 34736:
					xmlsummary.println("<GeoTiffDoubleParams>" + temp.tiffTagContent + "</GeoTiffDoubleParams>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 34737:
					xmlsummary.println("<GeoTiffAsciiParams>" + temp.tiffTagContent + "</GeoTiffAsciiParams>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "Private";
					break;

				case 34675:
					xmlsummary.println("<InterColorProfile>" + temp.tiffTagContent + "</InterColorProfile>");
					temp.tiffTagDescription = "ICC profile data.";
					temp.tiffTagKind = "TIFF/EP spec, p. 47 Exif private IFD";
					break;

				case 33723:
					xmlsummary.println("<IPTC_NAA>" + temp.tiffTagContent + "</IPTC_NAA>");
					temp.tiffTagDescription = "IPTC-NAA (International Press Telecommunications Council-Newspaper Association of America) metadata.";
					temp.tiffTagKind = "TIFF/EP spec, p. 33";
					break;

				case 34377:
					xmlsummary.println("<PhotoshopSettings>" + temp.tiffTagContent + "</PhotoshopSettings>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "private";
					break;

				case 37439:
					xmlsummary.println("<StoNits>" + temp.tiffTagContent + "</StoNits>");
					temp.tiffTagDescription = "NO DESCRIPTION YET.";
					temp.tiffTagKind = "private";
					break;

				default:
					// xmlsummary.println("<UnknownTiffTag>" + temp.tiffTagName
					// + "</UnknownTiffTag>");
					String unknownTiffTag = temp.tiffTagName;
					unknownTiffTag = unknownTiffTag.replace(" ", ""); // get rid
																		// of
																		// spaces
																		// because
																		// XML
																		// cannot
																		// deal
																		// with
																		// them
					xmlsummary.println("<NewTag>");
					// xmlsummary.println("<" + unknownTiffTag + ">" +
					// temp.tiffTagContent + "</" + unknownTiffTag + ">");
					xmlsummary.println("<Tag>" + unknownTiffTag + "</Tag>");
					xmlsummary.println("<Content>" + temp.tiffTagContent + "</Content>");
					xmlsummary.println("<Dec>" + temp.decTiffTag + "</Dec>");
					xmlsummary.println("<Hex>" + temp.hexValue + "</Hex>");
					xmlsummary.println("</NewTag>");
				}

				listTiffTags.add(temp); // used to be placed before all the
										// cases,
										// but the description should be
										// included
			}
			
			csvsummary.println ("" + SEPARATOR + ""); //to have a new line between each File
			
			xmlsummary.println("</TiffTags>");

		}

		catch (Exception e) {
			xmlsummary.println("<ErrorMessage>" + e + "</ErrorMessage>");

			problematicTiffs++;
		}

		// how to get a certain tiff tag:
		// TiffField tileWidthField =
		// tiffDirectory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);

	}
}
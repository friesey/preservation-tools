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
				String outputCsv = examinedFolder + "//" + name + ".csv";
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

			csvsummary.println("File" + SEPARATOR + file.getName());
			csvsummary.println("" + SEPARATOR + "");

			csvsummary.println("Tiff Tag Name" + SEPARATOR + "Value" + SEPARATOR + "Description of Tag");

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
				temp.tiffTagDescription = getContent(temp.decTiffTag);

				csvsummary.println(temp.tiffTagName + SEPARATOR + temp.tiffTagContent + SEPARATOR + temp.tiffTagDescription);

				temp.tiffTagContent = utilities.fileStringUtilities.reduceNULvalues(temp.tiffTagContent);
				temp.tiffTagContent = utilities.fileStringUtilities.reduceUnitSeparator(temp.tiffTagContent);

				int privateTag = 32768;
				int reusabletagbegin = 65000;
				int reusabletagend = 65535;

				// TODO: Baseline and Extended is not as easy to determine by
				// the dec Value

				if ((temp.decTiffTag > reusabletagbegin) && (temp.decTiffTag < reusabletagend)) {
					temp.tiffTagKind = "reusable";
				}

				else if ((temp.decTiffTag > privateTag) && (temp.decTiffTag < reusabletagbegin)) {
					temp.tiffTagKind = "private";
				}

				else {
					temp.tiffTagKind = getTiffTagKind(temp.decTiffTag);
				}

				listTiffTags.add(temp);

				temp.tiffTagName = temp.tiffTagName.replace(" ", ""); // get rid
				// of
				// spaces
				// because
				// XML
				// cannot
				// deal
				// with
				// them

				xmlsummary.println("<" + temp.tiffTagName + ">" + temp.tiffTagContent + "</" + temp.tiffTagName + ">");

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

				if (temp.tiffTagDescription == null) {
					xmlsummary.println("<NewTag>");
					xmlsummary.println("<Tag>" + unknownTiffTag + "</Tag>");
					xmlsummary.println("<Content>" + temp.tiffTagContent + "</Content>");
					xmlsummary.println("<Dec>" + temp.decTiffTag + "</Dec>");
					xmlsummary.println("<Hex>" + temp.hexValue + "</Hex>");
					xmlsummary.println("</NewTag>");
				}

			}

			csvsummary.println("" + SEPARATOR + ""); // to have a new line
														// between each File
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

	private static String getTiffTagKind(int decTiffTag) {
		String tiffTagKind = null;
		Integer[] baselineArr = new Integer[] { 254, 255, 256, 257, 258, 259, 262, 263, 264, 265, 266, 270, 271, 272, 273, 277, 278, 279, 280, 281, 282, 283, 284, 288, 289, 290, 291, 296, 305, 306, 315, 316, 320 };

		for (int i = 0; i < baselineArr.length; i++) {
			if (baselineArr[i].equals(decTiffTag)) {
				tiffTagKind = "baseline";
				break; // sonst ist es nur baseline, wenn es das letzte in der
						// Liste ist
			} else {
				tiffTagKind = "extended";
			}
		}
		return tiffTagKind;
	}

	private static String getContent(int decTiffTag) {
		String description;
		switch (decTiffTag) {

		case 254:
			description = "Similar to SubFileType, but NewSubFileType.";
			break;

		case 255:
			description = "A general indication of the kind of data contained in this subfile.";
			break;

		case 256:
			description = "The number of columns in the image, i.e., the number of pixels per row.";
			break;

		case 257:
			description = "The number of rows of pixels in the image.";
			break;

		case 259:
			description = "Compression scheme used on the image data.";
			break;

		case 262:
			description = "The color space of the image data.";
			break;

		case 273:
			description = "For each strip, the byte offset of that strip.";
			break;

		case 277:
			description = "The number of components per pixel.";
			break;

		case 278:
			description = "The number of rows per strip.";
			break;

		case 279:
			description = "For each strip, the number of bytes in the strip after compression.";
			break;

		case 282:
			description = "The number of pixels per ResolutionUnit in the ImageWidth direction.";
			break;

		case 283:
			description = "The number of pixels per ResolutionUnit in the ImageLength direction.";
			break;

		case 296:
			description = "The unit of measurement for XResolution and YResolution.";
			break;

		case 258:
			description = "Number of bits per component.";
			break;

		case 280:
			description = "The minimum component value used.";
			break;

		case 281:
			description = "The maximum component value used.";
			break;

		case 290:
			description = "The precision of the information contained in the GrayResponseCurve.";
			break;

		case 291:
			description = "For grayscale data, the optical density of each possible pixel value.";
			break;

		case 305:
			description = "Name and version number of the software package(s) used to create the image.";
			break;

		case 306:
			description = "Date ad time of image creation.";
			break;

		case 315:
			description = "Person who created the image.";
			break;

		case 320:
			description = "A color map for palette color images.";
			break;

		case 338:
			description = "Description of extra components.";
			break;

		case 292:
			description = "Options for Group 3 Fax compression. Extended";
			break;

		case 293:
			description = "Options for Group 4 Fax compression. Extended";
			break;

		case 297:
			description = "The page number of the page from which this image was scanned. Extended";
			break;

		case 266:
			description = "The logical order of bits within a byte.";
			break;

		case 269:
			description = "Name of document which holds for image.";
			break;

		case 270:
			description = "A string that describes the subject of the image.";
			break;

		case 271:
			description = "Scanner manufacturer name.";
			break;

		case 272:
			description = "Scanner model name/number.";
			break;

		case 274:
			description = "The orientation of the image with respect to the rows and columns.";
			break;

		case 284:
			description = "How the components of each pixel are stored.";
			break;

		case 286:
			description = "X page offset of image lhs.";
			break;

		case 287:
			description = "Y page offset of image lhs.";
			break;

		case 317:
			description = "A mathematical operator that is applied to the image data before an encoding scheme is applied.";
			break;

		case 318:
			description = "The chromaticity of the white point of the image.";
			break;

		case 319:
			description = "The chromaticities of the primaries of the image.";
			break;

		case 321:
			description = "Highlight + shadow info.";
			break;

		case 322:
			description = "Tile width in pixels.";
			break;

		case 323:
			description = "Tile height in pixels.";
			break;

		case 324:
			description = "	Offsets to data tiles.";
			break;

		case 325:
			description = "Byte counts for tiles.";
			break;

		case 326:
			description = "Lines with wrong pixel count.";
			break;

		case 327:
			description = "Regenerated line info.";
			break;

		case 328:
			description = "Max consecutive bad lines.";
			break;

		case 347:
			description = "JPEG table stream.";
			break;

		case 513:
			description = "Obsoleted. Pointer to SOI marker. Also known as JPEGIFOFFSET";
			break;

		case 514:
			description = "Obsoleted: JFIF stream length. Also known as JPEGIFBYTECOUNT";
			break;

		case 515:
			description = "Obsoleted: Lossless proc predictor.";
			break;

		case 529:
			description = "The transformation from RGB to YCbCr image data.";
			break;

		case 530:
			description = "YCbCr subsampling factors.";
			break;

		case 531:
			description = "Subsample positioning.";
			break;

		case 532:
			description = "Specifies a pair of headroom and footroom image data values (codes) for each pixel component.";
			break;

		case 263:
			description = "Obsoleted: Thresholding used on data.";
			break;

		case 330:
			description = "Subimage descriptors.";
			break;

		case 333:
			description = "ASCII names of inks.";
			break;

		case 336:
			description = "0% and 100% dot codes.";
			break;

		case 339:
			description = "Specifies how to interpret each data sample in a pixel.";
			break;

		case 512:
			description = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
			break;

		case 519:
			description = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
			break;

		case 520:
			description = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
			break;

		case 521:
			description = "Old-style JPEG compression field. TechNote2 invalidates this part of the specification.";
			break;

		case 700:
			description = "Also known as XML packet.";
			break;

		case 34665:
			description = "A pointer to the Exif IFD. Private";
			break;

		case 32995:
			description = "Obsoleted by ExtraSamples tag.";
			break;

		case 32996:
			description = "Obsoleted by SampleFormat tag.";
			break;
			
		case 32997:
			description = "tile/strip calculations.";
			break;

		case 32998:
			description = "tile/strip calculations.";
			break;

		case 33432:
			description = "Copyright notice.";
			break;

		case 33550:
			description = "Used in interchangeable GeoTIFF_1_0 files.";
			break;

		case 33922:
			description = "Originally part of Intergraph's GeoTIFF tags, but now used in interchangeable GeoTIFF_1_0 files.";
			break;

		case 34016:
			description = "Site where image created.";
			break;

		case 34017:
			description = "Sequence of colors if other than CMYK.";
			break;

		case 34018:
			description = "Certain inherited headers.";
			break;

		case 34019:
			description = "Type of raster padding used, if any.";
			break;

		case 34020:
			description = "Number of bits for short run length encoding.";
			break;

		case 34021:
			description = "Number of bits for long run length encoding.";
			break;

		case 34022:
			description = "Color value in a color pallette.";
			break;

		case 34023:
			description = "Indicates if image (foreground) color or transparency is specified.";
			break;

		case 34024:
			description = "Background color specification.";
			break;

		case 34025:
			description = "Specifies image (foreground) color.";
			break;

		case 34026:
			description = "Specifies background color.";
			break;

		case 34027:
			description = "Specifies data values for 0 % and 100 % pixel intensity.";
			break;

		case 34028:
			description = "NO DESCRIPTION YET.";
			break;

		case 34029:
			description = "Specifies if transparency is used in HC file.";
			break;

		case 34030:
			description = "Indicates the type of information in an HC file.";
			break;

		case 34152:
			description = "Unknown.";
			break;

		case 34264:
			description = "GeoTiff: Specifies transformation matrix between raster space (and its dependent pixel-value space) and the (possibly 3D) model space.";
			break;

		case 34735:
			description = "	Stores GeoKey Directory, which defines and references the \"GeoKeys\".";
			break;

		case 34736:
			description = "Stores all DOUBLE value GeoKeys, references by the GeoKeyDirectoryTag.";
			break;

		case 34737:
			description = "Stores all ASCII value GeoKeys, references by the GeoKeyDirectoryTag.";
			break;

		case 34675:
			description = "ICC profile data.";
			break;

		case 33723:
			description = "IPTC-NAA (International Press Telecommunications Council-Newspaper Association of America) metadata.";
			break;

		case 34377:
			description = "Collection of Photoshop 'Image Resource Blocks'.";
			break;

		case 37439:
			description = "Sample value to Nits. Private tag registered to SGI.";
			break;

		case 37724:
			description = "Used by Adobe Photoshop.";
			break;

		case 40091:
			description = "Title tag used by Windows, encoded in UCS2.";
			break;

		case 40092:
			description = "Comment tag used by Windows, encoded in UCS2.";
			break;

		case 40093:
			description = "Author tag used by Windows, encoded in UCS2.";
			break;

		case 40094:
			description = "Keywords tag used by Windows, encoded in UCS2.";
			break;

		case 40095:
			description = "Subject tag used by Windows, encoded in UCS2.";
			break;

		case 50341:
			description = "PrintImageMatching.";
			break;

		default:
			description = null;
		}
		return description;
	}
}
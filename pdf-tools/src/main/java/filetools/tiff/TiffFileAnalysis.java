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

	public static String examinedFolder;
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

				output.XslStyleSheets.TiffTagAnalysisCustomizedXsl();

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
					xmlsummary.println("<TiffTag>" + alltifftags.get(n) + "</TiffTag>");
					for (int i = 0; i < listTiffTags.size(); i++) {

						if (listTiffTags.get(i).tiffTagName.equals(alltifftags.get(n))) {
							xmlsummary.println("<SourceOfTag>" + listTiffTags.get(i).tiffTagKind + "</SourceOfTag>");
							xmlsummary.println("<Description>" + listTiffTags.get(i).tiffTagDescription + "</Description>");

							// TODO: das geht, wird aber so oft
							// ausgegeben wie
							// tag in arraylist vorhanden ist
						}

					}

					xmlsummary.println("<Occurance>" + temp + "</Occurance>");
					xmlsummary.println("</DifferentTiffTags>");

				}

				xmlsummary.println("</AnalysisSummary>");
				xmlsummary.println("</iffTagAnalysis>");
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

			/*
			 * xmlsummary.println("<decValue>" + temp.decTiffTag +
			 * "</decValue>"); xmlsummary.println("<hexValue>" + temp.hexValue +
			 * "</hexValue>"); xmlsummary.println("<TiffTagName>" +
			 * temp.tiffTagName + "</TiffTagName>");
			 * xmlsummary.println("<TiffTagContent>" + temp.tiffTagContent +
			 * "</TiffTagContent>");
			 */

			switch (temp.decTiffTag) {

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
				xmlsummary.println("<Software>" + temp.tiffTagContent + "</Software>");
				temp.tiffTagDescription = "Name and version number of the software package(s) used to create the image.";
				temp.tiffTagKind = "Baseline";
				break;

			case 306:
				xmlsummary.println("<DateTime>" + temp.tiffTagContent + "</DateTime>");
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

			case 33432:
				xmlsummary.println("<Copyright>" + temp.tiffTagContent + "</Copyright>");
				temp.tiffTagDescription = "Copyright notice.";
				temp.tiffTagKind = "Baseline";
				break;

			// not in Baseline, but nice to have for Digital Preservation

			case 34665:
				xmlsummary.println("<ExifIfd>" + temp.tiffTagContent + "</ExifIfd>");
				temp.tiffTagDescription = "A pointer to the Exif IFD. Private";
				temp.tiffTagKind = "Private";
				break;

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

			// TODO: some default for not listed cases

			default:
				xmlsummary.println("<UnknownTiffTag>" + temp.tiffTagName + "</UnknownTiffTag>");

			}

			listTiffTags.add(temp); // used to be placed before all the cases,
									// but the description should be included

		}

		xmlsummary.println("</TiffTags>");

		// how to get a certain tiff tag:
		// TiffField tileWidthField =
		// tiffDirectory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);

	}

}

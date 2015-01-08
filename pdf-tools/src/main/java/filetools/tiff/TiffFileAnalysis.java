package filetools.tiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageReader;

import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.tiff.TiffDirectory;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageData;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.apache.xmlgraphics.image.writer.internal.TIFFImageWriter;

import com.drew.imaging.tiff.TiffMetadataReader;

import java.util.*;

import javax.imageio.*;

public class TiffFileAnalysis {

	static String examinedFolder;
	static PrintWriter outputfile;

	// mandatory in baseline tiff
	public static ArrayList<TiffTagZbw> listTiffTags = new ArrayList<TiffTagZbw>();
	public static TiffTagZbw imageWidth = new TiffTagZbw();
	public static TiffTagZbw imageLength = new TiffTagZbw();
	public static TiffTagZbw compression = new TiffTagZbw();
	public static TiffTagZbw photometric = new TiffTagZbw();
	public static TiffTagZbw stripOffSets = new TiffTagZbw();
	public static TiffTagZbw samplesPerPixel = new TiffTagZbw();
	public static TiffTagZbw rowsPerStrip = new TiffTagZbw();
	public static TiffTagZbw stripByteCounts = new TiffTagZbw();
	public static TiffTagZbw xResolution = new TiffTagZbw();
	public static TiffTagZbw yResolution = new TiffTagZbw();
	public static TiffTagZbw resolutionUnit = new TiffTagZbw();
	public static TiffTagZbw bitPerSample = new TiffTagZbw();

	// optional and possible in baseline tiff
	public static TiffTagZbw subFileType = new TiffTagZbw();
	public static TiffTagZbw newSubFileType = new TiffTagZbw();
	public static TiffTagZbw treshHolding = new TiffTagZbw();
	public static TiffTagZbw cellWidth = new TiffTagZbw();
	public static TiffTagZbw cellLength = new TiffTagZbw();
	public static TiffTagZbw fillOrder = new TiffTagZbw();
	public static TiffTagZbw imageDescription = new TiffTagZbw();
	public static TiffTagZbw make = new TiffTagZbw();
	public static TiffTagZbw model = new TiffTagZbw();
	public static TiffTagZbw orientation = new TiffTagZbw();
	public static TiffTagZbw minSampleValue = new TiffTagZbw();
	public static TiffTagZbw maxSampleValue = new TiffTagZbw();
	public static TiffTagZbw planarConfig = new TiffTagZbw();
	public static TiffTagZbw freeOffSets = new TiffTagZbw();
	public static TiffTagZbw freebyteCounts = new TiffTagZbw();
	public static TiffTagZbw grayResponseUnit = new TiffTagZbw();
	public static TiffTagZbw grayResponseCurve = new TiffTagZbw();
	public static TiffTagZbw software = new TiffTagZbw();
	public static TiffTagZbw datetime = new TiffTagZbw();
	public static TiffTagZbw artist = new TiffTagZbw();
	public static TiffTagZbw hostComputer = new TiffTagZbw();
	public static TiffTagZbw colorMap = new TiffTagZbw();
	public static TiffTagZbw extraSamples = new TiffTagZbw();
	public static TiffTagZbw copyright = new TiffTagZbw();

	// more tiff tags found in our tiff files
	public static TiffTagZbw t4Option = new TiffTagZbw();
	public static TiffTagZbw t6Option = new TiffTagZbw();
	public static TiffTagZbw pageNumber = new TiffTagZbw();

	public static void main(String args[]) throws IOException, ImageReadException {

		try {

			examinedFolder = utilities.BrowserDialogs.chooseFolder();

			// fill all known tifftags in an arraylist
			imageWidth.decTiffTag = 256;
			imageWidth.mandatoryTiffTag = true;
			listTiffTags.add(imageWidth);

			imageLength.decTiffTag = 257;
			imageLength.mandatoryTiffTag = true;
			listTiffTags.add(imageLength);

			compression.decTiffTag = 259;
			compression.mandatoryTiffTag = true;
			listTiffTags.add(compression);

			photometric.decTiffTag = 262;
			photometric.mandatoryTiffTag = true;
			listTiffTags.add(photometric);

			stripOffSets.decTiffTag = 273;
			stripOffSets.mandatoryTiffTag = true;
			listTiffTags.add(stripOffSets);

			samplesPerPixel.decTiffTag = 277;
			samplesPerPixel.mandatoryTiffTag = true;
			listTiffTags.add(samplesPerPixel);

			rowsPerStrip.decTiffTag = 278;
			rowsPerStrip.mandatoryTiffTag = true;
			listTiffTags.add(rowsPerStrip);

			stripByteCounts.decTiffTag = 279;
			stripByteCounts.mandatoryTiffTag = true;
			listTiffTags.add(stripByteCounts);

			xResolution.decTiffTag = 282;
			xResolution.mandatoryTiffTag = true;
			listTiffTags.add(xResolution);

			yResolution.decTiffTag = 283;
			yResolution.mandatoryTiffTag = true;
			listTiffTags.add(yResolution);

			resolutionUnit.decTiffTag = 296;
			resolutionUnit.mandatoryTiffTag = true;
			listTiffTags.add(resolutionUnit);

			bitPerSample.decTiffTag = 258;
			bitPerSample.mandatoryTiffTag = true;
			listTiffTags.add(bitPerSample);

			// optional and possible in baseline tiff

			subFileType.decTiffTag = 255;
			subFileType.mandatoryTiffTag = false;
			listTiffTags.add(subFileType);

			newSubFileType.decTiffTag = 254;
			newSubFileType.mandatoryTiffTag = false;
			listTiffTags.add(newSubFileType);

			treshHolding.decTiffTag = 263;
			treshHolding.mandatoryTiffTag = false;
			listTiffTags.add(treshHolding);
			cellWidth.decTiffTag = 264;
			cellWidth.mandatoryTiffTag = false;
			listTiffTags.add(cellWidth);

			cellLength.decTiffTag = 265;
			cellLength.mandatoryTiffTag = false;
			listTiffTags.add(cellLength);

			fillOrder.decTiffTag = 266;
			fillOrder.mandatoryTiffTag = false;
			listTiffTags.add(fillOrder);

			imageDescription.decTiffTag = 270;
			imageDescription.mandatoryTiffTag = false;
			listTiffTags.add(imageDescription);

			make.decTiffTag = 271;
			make.mandatoryTiffTag = false;
			listTiffTags.add(make);

			model.decTiffTag = 272;
			model.mandatoryTiffTag = false;
			listTiffTags.add(model);

			orientation.decTiffTag = 274;
			orientation.mandatoryTiffTag = false;
			listTiffTags.add(orientation);

			minSampleValue.decTiffTag = 280;
			minSampleValue.mandatoryTiffTag = false;
			listTiffTags.add(minSampleValue);

			maxSampleValue.decTiffTag = 281;
			maxSampleValue.mandatoryTiffTag = false;
			listTiffTags.add(maxSampleValue);

			planarConfig.decTiffTag = 284;
			planarConfig.mandatoryTiffTag = false;
			listTiffTags.add(planarConfig);

			freeOffSets.decTiffTag = 288;
			freeOffSets.mandatoryTiffTag = false;
			listTiffTags.add(freeOffSets);

			freebyteCounts.decTiffTag = 289;
			freebyteCounts.mandatoryTiffTag = false;
			listTiffTags.add(freebyteCounts);

			grayResponseUnit.decTiffTag = 290;
			grayResponseUnit.mandatoryTiffTag = false;
			listTiffTags.add(grayResponseUnit);

			grayResponseCurve.decTiffTag = 291;
			grayResponseCurve.mandatoryTiffTag = false;
			listTiffTags.add(grayResponseCurve);

			software.decTiffTag = 305;
			software.mandatoryTiffTag = false;
			listTiffTags.add(software);

			datetime.decTiffTag = 306;
			datetime.mandatoryTiffTag = false;
			listTiffTags.add(datetime);

			artist.decTiffTag = 315;
			artist.mandatoryTiffTag = false;
			listTiffTags.add(artist);

			hostComputer.decTiffTag = 316;
			hostComputer.mandatoryTiffTag = false;
			listTiffTags.add(hostComputer);

			colorMap.decTiffTag = 320;
			colorMap.mandatoryTiffTag = false;
			listTiffTags.add(colorMap);

			extraSamples.decTiffTag = 338;
			extraSamples.mandatoryTiffTag = false;
			listTiffTags.add(extraSamples);

			copyright.decTiffTag = 33432;
			copyright.mandatoryTiffTag = false;
			listTiffTags.add(copyright);

			// more tiff tags found in our tiff files

			t4Option.decTiffTag = 292;
			t4Option.mandatoryTiffTag = false;
			listTiffTags.add(t4Option);

			t6Option.decTiffTag = 293;
			t6Option.mandatoryTiffTag = false;
			listTiffTags.add(t6Option);

			pageNumber.decTiffTag = 297;
			pageNumber.mandatoryTiffTag = false;
			listTiffTags.add(pageNumber);

			if (examinedFolder != null) {

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(examinedFolder), new ArrayList<File>());

				for (int i = 0; i < files.size(); i++) {
					System.out.println(files.get(i).getCanonicalPath());
					String tiffExtension = "TIF";

					String extension = FilenameUtils.getExtension(files.get(i).getCanonicalPath());
					if (extension.equals(tiffExtension)) {

						if (filetools.GenericFileAnalysis.testFileHeaderTiff(files.get(i))) {
							analyseTiffTags(files.get(i));
							// TiffProperties.getTiffProperties(files.get(i).toString());

							System.out.println(files.get(i) + " is a tiff-file");

						} else {
							System.out.println("This file purports to be a tiff-file. It has a .TIF-extension, but no lacks the magic number.");

						}
					}
				}
			}
		} catch (FileNotFoundException e) {
		}
	}

	private static void analyseTiffTags(File file) throws IOException, ImageReadException {

		IImageMetadata metadata = Sanselan.getMetadata(file);
		TiffDirectory tiffDirectory = ((TiffImageMetadata) metadata).findDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT);

/*		ByteSourceFile byteSource = new ByteSourceFile(file);
		ArrayList<?> elements = tiffDirectory.getTiffRawImageDataElements();*/

/*		TiffImageData.Data data[] = new TiffImageData.Data[elements.size()];

		for (int i = 0; i < elements.size(); i++) {
			TiffDirectory.ImageDataElement element = (TiffDirectory.ImageDataElement) elements.get(i);
			byte bytes[] = byteSource.getBlock(element.offset, element.length);
			data[i] = new TiffImageData.Data(element.offset, element.length, bytes);

		}*/

		TiffField tileWidthField = tiffDirectory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);
		System.out.println(tileWidthField);

	}

}

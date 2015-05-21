package filetools.tiff;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import com.sun.media.jai.codec.FileCacheSeekableStream;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;



public class TiffTestClass {

	public static void main(String args[]) throws IOException, ImageProcessingException {
		String tiffstr = utilities.BrowserDialogs.chooseFile();
		InputStream is = new BufferedInputStream(new FileInputStream(tiffstr));
		SeekableStream seekstr = new FileCacheSeekableStream(is);
		TIFFDirectory tiffdir = new TIFFDirectory(seekstr, 0);
		for (int i = 0; i < tiffdir.getNumEntries(); i++) {
			// System.out.println(i);
			// System.out.println(tiffdir.getField(i));
		}

		boolean cSB = seekstr.canSeekBackwards();
		// System.out.println(cSB);
		File tifffile = new File(tiffstr);
		getImageTiffMetadata(tifffile);

		getExifTiffData(tifffile);
	}

	public static void getExifTiffData(File tifffile) {
	

		// com.drew.metadata.Metadata metaExif = new Metadata();

	}

	public static void getImageTiffMetadata(File tifffile) throws ImageProcessingException, IOException {
		// works for tiff but has many unknown tags
		com.drew.metadata.Metadata drewmetadata = null;
		drewmetadata = ImageMetadataReader.readMetadata(tifffile);
		if (drewmetadata != null) {
			for (Directory directory : drewmetadata.getDirectories()) {
				System.out.println("directory: " + directory);
				for (Tag tag : directory.getTags()) {
					System.out.println("  tag: " + tag);
				}
			}
		}
	}

	public static void getImageMetadata(File tifffile) throws FileNotFoundException, IOException {

		// does not seem to work for tiff
		ImageInputStream iis = ImageIO.createImageInputStream(new BufferedInputStream(new FileInputStream(tifffile)));

		String ext = FilenameUtils.getExtension(tifffile.toString()).toLowerCase();
		Iterator<ImageReader> imgReaders = ImageIO.getImageReaders(ext);

		// get the first image reader from the collection
		ImageReader reader = imgReaders.next();

		IIOImage image = null;

		reader.setInput(iis, true);
		image = reader.readAll(0, null);

		IIOMetadata metadata = image.getMetadata();
		String[] names = metadata.getMetadataFormatNames();
		for (int i = 0; i < names.length; i++) {

			System.out.println(("Format name: " + names[i]));
			System.out.println(i);
			System.out.println(metadata.getAsTree(names[i]));
		}
	}
}

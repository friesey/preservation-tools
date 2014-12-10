package filetools.tiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;


import org.apache.xmlgraphics.image.loader.util.ImageUtil;

//import com.sun.media.imageio.plugins.tiff.TIFFDirectory;
//import com.sun.media.imageio.plugins.tiff.TIFFField;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;

public class TiffProperties {

	public static void getTiffProperties(String inputTifImagePath) throws FileNotFoundException, IOException {
		// try {
//TODO This does not work yet
		Iterator<ImageReader> readersIterator = ImageIO.getImageReadersByFormatName("tif");
		ImageReader imageReader = (ImageReader) readersIterator.next();
		
	
		ImageInputStream imageInputStream;

		imageInputStream = new FileImageInputStream(new File(inputTifImagePath));

		imageReader.setInput(imageInputStream, false, true);

		/* Take a input from a file */
		FileSeekableStream fileSeekableStream;
		fileSeekableStream = new FileSeekableStream(inputTifImagePath);

		/* create ImageDecoder to count your pages from multi-page tiff */
		ImageDecoder iDecoder = ImageCodec.createImageDecoder("tiff", fileSeekableStream, null);

		/* count the number of pages inside the multi-page tiff */
		int pageCount = iDecoder.getNumPages();

		/* use first for loop to get pages one by one */
		for (int page = 0; page < pageCount; page++) {
			/* get image metadata for each page */
			IIOMetadata imageMetadata = imageReader.getImageMetadata(page);

			/*
			 * The root of all the tags for this image is the IFD (Image File
			 * Directory). Get the IFD from where we can get all the tags for
			 * the image.
			 */

			String[] mdString;

			mdString = imageMetadata.getMetadataFormatNames();

			for (int i = 0; i > mdString.length; i++) {
				System.out.println(i + mdString[i]);
			}

		}
		/*
		 * }
		 * 
		 * catch (Exception e) { JOptionPane.showMessageDialog(null,
		 * "An exception occured " + e); }
		 */
	}
}

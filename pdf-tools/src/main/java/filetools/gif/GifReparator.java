package filetools.gif;

//import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.formats.gif.GifImageParser;

import com.itextpdf.text.pdf.codec.GifImage;

//import ij.*;

public class GifReparator {

	public static void repairgif(File giffile) throws IOException {

		ImageInputStream iis = ImageIO.createImageInputStream(giffile);
		String ext = FilenameUtils.getExtension(giffile.toString()).toLowerCase();
		// all the files should have "gif"

		Iterator<?> imgReaders = ImageIO.getImageReadersByFormatName(ext);		

		// get the first image reader from the collection
		javax.imageio.ImageReader reader = (ImageReader) imgReaders.next();
	
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();	
					
		try {
			// read the image
			Image image = reader.read(0, param); // prop

			// To render the image
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

			// using "painter" we can draw in to "bufferedImage"
			Graphics2D painter = bufferedImage.createGraphics();

			// draw the "image" to the "bufferedImage"
			painter.drawImage(image, null, null);

			// the new image file

			System.out.println(giffile.toString());
			System.out.println(giffile.getParent().toString());

			File outputImg = new File(giffile.getParent().toString() + "//modifiedGif_" + giffile.getName().toString() + "." + ext);

			ImageIO.write(bufferedImage, ext, outputImg);
			
		} catch (Exception e) {
			System.out.println(e);
		}

		iis.close();
	}
	
	public static void convertToBmp(File gif) throws IOException {
		//this works fine for non-corrupted (gif)-files
		
		BufferedImage bufimg = ImageIO.read(gif);  
		File bmpfile = new File(gif.getParent().toString() + "//toBmp_" + gif.getName().toString() + ".bmp");
		ImageIO.write(bufimg, "bmp", bmpfile); 
	}
	
	public static void createNewGif (File gif) throws IOException {
		
		//this works for valid and invalid gif files, but the outputfile still is invalid
	
	File outputImg = new File(gif.getParent().toString() + "//modifiedGif_" + gif.getName().toString()/*
				 * +
				 * ".gif"
						 */);
		
	InputStream is = new BufferedInputStream(new FileInputStream(gif.toString()));
	GifImageParser parser = new GifImageParser();
	OutputStream stream = new FileOutputStream(outputImg);
	parser.copyStreamToStream(is, stream);
	}
	
	public static String getXmpMeta (File gif) throws ImageReadException, IOException {
		// TODO: getXmpXml works for intact files, returns null if no XMP
		// available
		
		String xmp = Sanselan.getXmpXml(gif); System.out.println(xmp);
		return xmp;
	}

}
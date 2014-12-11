package filetools.gif;

//import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
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
		
		readGifwithItext (giffile);
					
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

	@SuppressWarnings("deprecation")
	public static void readGifwithItext(File giffile) throws MalformedURLException, IOException {
		
		GifImage gif = new GifImage(giffile.toURL());	
		System.out.println("Test");
		
		
		
	}
}
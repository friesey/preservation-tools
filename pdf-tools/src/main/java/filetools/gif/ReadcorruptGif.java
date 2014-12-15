package filetools.gif;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sanselan.formats.gif.GifImageParser;

import ij.*;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String gifstr = utilities.BrowserDialogs.chooseFile();

		File gif = new File(gifstr);

		// TODO: getXmpXml works for intact files, returns null if no XMP
		// available
		/*
		 * String xmp = Sanselan.getXmpXml(gif); System.out.println(xmp);
		 */

		// try {

		// new file to be saved here:
		File outputImg = new File(gif.getParent().toString() + "//modifiedGif_" + gif.getName().toString()/*
																										 * +
																										 * ".gif"
																										 */);

		java.awt.Image toolkitImage = Toolkit.getDefaultToolkit().getImage(gifstr); // this
																					// works

		InputStream is = new BufferedInputStream(new FileInputStream(gifstr));
		GifImageParser parser = new GifImageParser();
		OutputStream stream = new FileOutputStream(outputImg);
		parser.copyStreamToStream(is, stream);
		
		convertToBmp (gif);

		/*
		 * } catch (Exception e) { System.out.println(e); }
		 */
	}

	public static void convertToBmp(File gif) throws IOException {
		//this works fine for non-corrupted (gif)-files
		
		BufferedImage bufimg = ImageIO.read(gif);  
		File bmpfile = new File(gif.getParent().toString() + "//toBmp_" + gif.getName().toString() + ".bmp");
		ImageIO.write(bufimg, "bmp", bmpfile); 
	}


}

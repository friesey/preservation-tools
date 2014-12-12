package filetools.gif;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sanselan.formats.gif.GifImageParser;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String giffile = utilities.BrowserDialogs.chooseFile();

		File gif = new File(giffile);
		
		//TODO: getXmpXml works for intact files, returns null if no XMP available
/*		String xmp = Sanselan.getXmpXml(gif); 
		System.out.println(xmp);*/

		// try {
	
		InputStream is = new BufferedInputStream(new FileInputStream(giffile));
	//	ImageInputStream iis = ImageIO.createImageInputStream(is);

	//	Image toolkitImage = Toolkit.getDefaultToolkit().getImage(giffile); // this works			
		
		GifImageParser parser = new GifImageParser();
		
		File outputImg = new File(gif.getParent().toString() + "//modifiedGif_" + gif.getName().toString() + ".gif");
		OutputStream stream = new FileOutputStream(outputImg);		
		parser.copyStreamToStream(is, stream);
		
		ImageInputStream iis = ImageIO.createImageInputStream(stream);

		BufferedImage bufImage = ImageIO.read(iis); // this only works for
													// intact images
		
		ImageIO.write(bufImage, "gif", outputImg);

		/*
		 * } catch (Exception e) { System.out.println(e); }
		 */
	}
}

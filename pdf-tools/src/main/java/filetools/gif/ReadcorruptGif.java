package filetools.gif;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.sanselan.Sanselan;
import org.apache.sanselan.formats.gif.GifImageParser;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String giffile = utilities.BrowserDialogs.chooseFile();

		File gif = new File(giffile);
		
		//TODO: getXmpXml works for intact files, returns null if no XMP available
/*		String xmp = Sanselan.getXmpXml(gif); 
		System.out.println(xmp);*/

		// try {

		File sourceimage = new File(giffile);

		InputStream is = new BufferedInputStream(new FileInputStream(giffile));
		ImageInputStream iis = ImageIO.createImageInputStream(is);

		Image toolkitImage = Toolkit.getDefaultToolkit().getImage(giffile); // this works	

		BufferedImage bufImage = ImageIO.read(iis); // this only works for
													// intact images

		File outputImg = new File(gif.getParent().toString() + "//modifiedGif_" + gif.getName().toString() + ".gif");
		ImageIO.write(bufImage, "gif", outputImg);

		/*
		 * } catch (Exception e) { System.out.println(e); }
		 */
	}
}

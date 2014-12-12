package filetools.gif;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String giffile = utilities.BrowserDialogs.chooseFile();

		File gif = new File(giffile);

	//	try {

			File sourceimage = new File(giffile);

			InputStream is = new BufferedInputStream(new FileInputStream(giffile));
			ImageInputStream iis = ImageIO.createImageInputStream(is);
			
			BufferedImage bufImage = ImageIO.read(iis);
			
			
		//	Image image = ImageIO.read(is);

			File outputImg = new File(gif.getParent().toString() + "//modifiedGif_" + gif.getName().toString() + ".gif");
			ImageIO.write(bufImage, "gif", outputImg);


	/*	} catch (Exception e) {
			System.out.println(e);
		}
		*/
	}
}

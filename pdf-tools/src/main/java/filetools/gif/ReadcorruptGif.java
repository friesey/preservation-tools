package filetools.gif;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String giffile = utilities.BrowserDialogs.chooseFile();

		File gif = new File(giffile);

		try {
			/*
			 * URL url = new URL("file://" + gif.getPath());
			 * System.out.println(url); BufferedImage img = ImageIO.read(url);
			 */

			BufferedImage gifbufimg = getBufferedImage(gif);

			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("gif");
			ImageWriter writer = (ImageWriter) iter.next();
			ImageWriteParam iwp = writer.getDefaultWriteParam();

			File outputImg = new File(gif.getParent().toString() + "//modifiedGif_" + gif.getName().toString() + ".gif");
			FileImageOutputStream output = null;

			output = new FileImageOutputStream(outputImg);
			writer.setOutput(output);
			IIOImage img = new IIOImage((RenderedImage) gifbufimg, null, null);
			writer.write(null, img, iwp);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static BufferedImage getBufferedImage(File gif) {
		Image gifimage = Toolkit.getDefaultToolkit().getImage(gif.getAbsolutePath());
		int width = gifimage.getWidth(null);
		System.out.println(width);
		int height = gifimage.getHeight(null);
		System.out.println(height);

		BufferedImage newImage = new BufferedImage(width, height, 
			BufferedImage.TYPE_INT_ARGB);
			Graphics g = newImage.getGraphics();
			g.drawImage(gifimage, width, height, null);
			g.dispose();
		
		gifimage.getGraphics();
		
		return newImage;

	}
}

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader {

	public static BufferedImage loadImage(String filepath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filepath)); // load image from file
		} catch (IOException e) {
			System.out.println(filepath + " was not found"); // if image loading fails, print this!!!
		}

		return image;
	}
	public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
		g.dispose();
		return resizedImage;
	}
}

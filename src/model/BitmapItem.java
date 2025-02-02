package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import style.Style;

/** <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 */
public class BitmapItem extends SlideItem {
	private BufferedImage bufferedImage;
	private String imageName;

	protected static final String FILE = "File ";
	protected static final String NOTFOUND = " not found";

	// Level indicates the item-level; name indicates the name of the file with the image
	public BitmapItem(int level, String name) {
		super(level);
		this.imageName = name;
		loadPicture();
	}

	/**
	 * Loads an image from images folder.
	 */
	private void loadPicture() {
		try {
			// Attempt to load as a resource (for packaged JARs)
			if (getClass().getClassLoader().getResource("images/" + this.imageName) != null) {
				bufferedImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/" + this.imageName));
				System.out.println("Loaded image as resource: images/" + this.imageName);
			}
			// Fallback to loading from file system (for local development)
			else {
				File imageFile = new File("resources/images/" + this.imageName);
				System.out.println("Trying to load image from: " + imageFile.getAbsolutePath());
				bufferedImage = ImageIO.read(imageFile);
			}
		} catch (IOException | NullPointerException e) {
			System.err.println(FILE + this.imageName + NOTFOUND);
			bufferedImage = createCoverPicture();
		}
	}

	/**
	 * Generates a placeholder image in case the requested image is missing.
	 */
	private BufferedImage createCoverPicture() {
		BufferedImage coverPicture = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphicC = coverPicture.createGraphics();

		graphicC.setColor(Color.RED);
		graphicC.fillOval(0, 0, 100, 100);

		graphicC.dispose();

		return coverPicture;
	}

	public String getName() {
		return imageName;
	}

	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle(
				(int) (myStyle.getIndent() * scale), 0,
				(int) (bufferedImage.getWidth(observer) * scale),
				((int) (myStyle.getLeading() * scale)) +
						(int) (bufferedImage.getHeight(observer) * scale)
		);
	}

	// Draws the image
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getLeading() * scale);
		g.drawImage(bufferedImage, width, height,
				(int) (bufferedImage.getWidth(observer) * scale),
				(int) (bufferedImage.getHeight(observer) * scale), observer);
	}

	@Override
	public String toString() {
		return "model.BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}

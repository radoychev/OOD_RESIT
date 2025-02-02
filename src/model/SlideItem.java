package model;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import style.Style;


/** <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public abstract class SlideItem {
	private int level = 0; //The level of the model.SlideItem

	public SlideItem(int lev) {
		this.level = lev;
	}

	public SlideItem() {
		this(0);
	}

//Returns the level
	public int getLevel() {
		return this.level;
	}

//Returns the bounding box
	public abstract Rectangle getBoundingBox(Graphics g, 
			ImageObserver observer, float scale, Style style);

//Draws the item
	public abstract void draw(int x, int y, float scale,
							  Graphics g, Style style, ImageObserver observer);
}

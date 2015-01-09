package game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class Square extends JButton {
	private static final long serialVersionUID = 1L; // make compiler happy
	private static final int IMAGE_SIZE = 33;
	
	private ImageIcon cover;
	private ImageIcon image;
	private boolean isClicked;
	private int value;
	private GridBagConstraints c;
	private boolean flag;
	private ImageIcon flagIcon;
	private URL imageURL;
	
	public static int squareCount = 0;
	public static int flagCount = 0;
	
	// construct Square to be covered
	public Square() {
		imageURL = Square.class.getResource("images/cover.png");
		cover = new ImageIcon(imageURL);
		this.setIcon(cover);
		this.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
		imageURL = Square.class.getResource("images/flag.png");
		flagIcon = new ImageIcon(imageURL);
	}
	
	public void setImage(String imagePath) {
		imageURL = Square.class.getResource(imagePath);
		image = new ImageIcon(imageURL);
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setClick() {
		isClicked = true;
		this.setIcon(image);
	}
	
	public boolean isClicked() {
		return isClicked;
	}
	
	public void setConstraints(int x, int y) {
		c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.fill = GridBagConstraints.NONE;
	}
	
	public GridBagConstraints getConstraints() {
		return c;
	}
	
	public void flagSquare() {
		if (flag) {
			// remove flag, change back to cover
			this.setIcon(cover);
			flag = false;
			Square.flagCount--;
		} else {
			// check if flags are available before flagging
			if (Square.flagCount < 10) {
				this.setIcon(flagIcon);
				flag = true;
				Square.flagCount++;
			}
		}
	}
	
	public boolean isFlagged() {
		return flag;
	}
	
	// implemented by each subclass
	public abstract void click();
}

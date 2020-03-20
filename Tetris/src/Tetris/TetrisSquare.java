package Tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This wrapper class is a subclass of the square class. It has access to all
 * the square shapes methods given by javaFX but makes square creation easier by
 * setting a starting height, width, Fill, Color. It also makes moving square
 * much easier with the setLocation method which factors out a lot of redundant
 * coding.
 */
public class TetrisSquare extends Rectangle {

	/**
	 * The constructor calls the constructor of the square parent class and sets
	 * a basic color height, width, and stroke for each square instantiated in
	 * the game.
	 */
	public TetrisSquare() {
		super();
		this.setHeight(Constants.SQUARE_SIZE);
		this.setWidth(Constants.SQUARE_SIZE);
		this.setFill(Color.DARKGREY);
		this.setStroke(Color.BLACK);
	}

	/**
	 * This method helps consolidate and make the code more clear: changing the
	 * squares position can be accomplished with one method instead of two.
	 */
	public void setLocation(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

}

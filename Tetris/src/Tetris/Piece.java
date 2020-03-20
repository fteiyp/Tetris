package Tetris;

import javafx.scene.paint.Color;

/**
 * This piece class is instantiated by the game class. It is a composite shape
 * composed of four squares: each piece is represented by a different
 * configuration of the four different squares with a different color. It takes
 * in the numbers that the makeNewPiece method passes it (a switch randomly
 * selects which piece and the correct positions of the individual square's x
 * and y are passed in). It then adds all of the squares to the gamePane.
 */
public class Piece {

	private TetrisSquare _square1;
	private TetrisSquare _square2;
	private TetrisSquare _square3;
	private TetrisSquare _square4;

	/**
	 * The constructor takes in all the values of x and y for all of its squares
	 * as arguments to its parameters. Although this is a long list, I thought
	 * storing these in an array that gets passed in as an argument was a much
	 * more complicated design so I have them all just plugged into the
	 * constructor. The color gets passed in too. This also makes checking if
	 * the piece can be made easier for the game class too!
	 */
	public Piece(Game game, int x1, int y1, int x2, int y2, int x3, int y3,
			int x4, int y4, Color color) {
		_square1 = new TetrisSquare();
		_square2 = new TetrisSquare();
		_square3 = new TetrisSquare();
		_square4 = new TetrisSquare();
		_square1.setFill(color);
		_square2.setFill(color);
		_square3.setFill(color);
		_square4.setFill(color);
		_square1.setLocation(x1 * Constants.SQUARE_SIZE, y1
				* Constants.SQUARE_SIZE);
		_square2.setLocation(x2 * Constants.SQUARE_SIZE, y2
				* Constants.SQUARE_SIZE);
		_square3.setLocation(x3 * Constants.SQUARE_SIZE, y3
				* Constants.SQUARE_SIZE);
		_square4.setLocation(x4 * Constants.SQUARE_SIZE, y4
				* Constants.SQUARE_SIZE);
		game.getGamePane().getChildren()
				.addAll(_square1, _square2, _square3, _square4);
	}

	/**
	 * These accessor methods return the individual squares instantiated by the
	 * piece so that their specific X and Y values can be accessed to calculate
	 * and carry out position changes and so that the squares can be added to
	 * the array of squares once the piece is no longer falling.
	 */
	public TetrisSquare getSquare1() {
		return _square1;
	}

	public TetrisSquare getSquare2() {
		return _square2;
	}

	public TetrisSquare getSquare3() {
		return _square3;
	}

	public TetrisSquare getSquare4() {
		return _square4;
	}

	/**
	 * The rotate method uses the math provided in the Tetris handout to
	 * implement rotation about the third square instantiated for each piece.
	 * Although the square piece could rotate, its rotation looks awkward so the
	 * gameClass restricts use of rotation to all other pieces.
	 */
	public void rotate() {
		_square1.setLocation(
				_square3.getX() - _square3.getY() + _square1.getY(),
				_square3.getY() + _square3.getX() - _square1.getX());
		_square2.setLocation(
				_square3.getX() - _square3.getY() + _square2.getY(),
				_square3.getY() + _square3.getX() - _square2.getX());
		_square4.setLocation(
				_square3.getX() - _square3.getY() + _square4.getY(),
				_square3.getY() + _square3.getX() - _square4.getX());

	}

	/**
	 * The shiftLeft method simply sets the location of every square in the
	 * piece to be equal to the same Y position that it was at (uses accessor)
	 * and the X-position minus the length of a square to shift the piece over
	 * to the left by one square unit. Checking move validity is carried out by
	 * the gameClass, which knows about the array of existing squares.
	 */
	public void shiftLeft() {
		_square1.setLocation(_square1.getX() - Constants.SQUARE_SIZE,
				_square1.getY());
		_square2.setLocation(_square2.getX() - Constants.SQUARE_SIZE,
				_square2.getY());
		_square3.setLocation(_square3.getX() - Constants.SQUARE_SIZE,
				_square3.getY());
		_square4.setLocation(_square4.getX() - Constants.SQUARE_SIZE,
				_square4.getY());
	}

	/**
	 * The shiftRight method sets the location of every square in the piece to
	 * be equal to the same Y position that it was at (uses accessor) and the
	 * X-position plus the length of a square to shift the piece over to the
	 * left by one square unit. Checking move validity is carried out by the
	 * gameClass, which knows about the array of existing squares.
	 */
	public void shiftRight() {
		_square1.setLocation(_square1.getX() + Constants.SQUARE_SIZE,
				_square1.getY());
		_square2.setLocation(_square2.getX() + Constants.SQUARE_SIZE,
				_square2.getY());
		_square3.setLocation(_square3.getX() + Constants.SQUARE_SIZE,
				_square3.getY());
		_square4.setLocation(_square4.getX() + Constants.SQUARE_SIZE,
				_square4.getY());
	}

	/**
	 * Similar to the shiftSideways methods, the dropDown method sets the
	 * location of every square in the piece to be equal to the same X position
	 * that it was at (uses accessor) and the Y-position plus the length of a
	 * square to shift the piece over to the left by one square unit. Checking
	 * move validity is carried out by the gameClass, which knows about the
	 * array of existing squares.
	 */
	public void dropDown() {
		_square1.setLocation(_square1.getX(), _square1.getY()
				+ Constants.SQUARE_SIZE);
		_square2.setLocation(_square2.getX(), _square2.getY()
				+ Constants.SQUARE_SIZE);
		_square3.setLocation(_square3.getX(), _square3.getY()
				+ Constants.SQUARE_SIZE);
		_square4.setLocation(_square4.getX(), _square4.getY()
				+ Constants.SQUARE_SIZE);
	}

}

package Tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * The game class contains the logic of Tetris. This class contains several
 * private classes: a timehandler, a keyhandler, and a clickhandler. The
 * timehandler is responsible for shifting the pieces down and checking whether
 * the top line is full - to end the game - or any other line is full - to clear
 * it and shift everything above it down 1 square. The keyhandler handles user
 * input: the arrow keys have various effects on the piece (shifting, rotating),
 * p pauses the game and space drops the piece. The clickHandler is for the
 * quitButton that is contained in the bottom pane. Methods in this class
 * include createBoard which makes the surrounding squares and instantiates the
 * array of squares that underlies the game's logic. MakeNewPiece, ShiftLeft,
 * ShiftRight, Rotate, etc are all called by the timehandler and keyhandler as
 * the game is played.
 */
public class Game {

	private TetrisSquare[][] _board;
	private Pane _gamePane;
	private KeyHandler _keyHandler;
	private Timeline _timeline;
	private Piece _piece;
	private Boolean _pieceIsSquare;
	private Boolean _gameIsPaused;
	private Label _gameIsPausedLabel;
	private VBox _bottomPane;
	private int _score;
	private Label _scoreLabel;

	/**
	 * The constructor of the game class calls several helper methods that set
	 * the game up by creating the board, making the timeLine, and getting the
	 * private classes like the keyHandler and click handler working properly.
	 */
	public Game() {
		_keyHandler = new KeyHandler();
		_gamePane = new Pane();
		_gamePane.addEventHandler(KeyEvent.KEY_PRESSED, _keyHandler);
		_gamePane.setFocusTraversable(true);
		this.createBoard();
		this.makeNewPiece();
		this.setupTimeline();
		this.makeBottomPane();
	}

	/**
	 * This method contains a loop that runs through each part of the array of
	 * squares and instantiates a square where the position is either in the
	 * first or last two positions of the rows or the columns. This creates a
	 * frame which the pieces cannot move past.
	 */
	private void createBoard() {
		_board = new TetrisSquare[14][24];
		for (int col = 0; col < 14; col++) {
			for (int row = 0; row < 24; row++) {
				if (row < 2 || row > 21 || col < 2 || col > 11) {
					_board[col][row] = new TetrisSquare();
					_board[col][row].setLocation(col * Constants.SQUARE_SIZE,
							row * Constants.SQUARE_SIZE);
					_gamePane.getChildren().add(_board[col][row]);
				}
			}
		}
	}

	/**
	 * The makeBottomPane method both creates and returns the bottom pane. It is
	 * called by the paneOrganizer so it can be added to the borderPane below
	 * the gamePane. This method also creates the different parts that go into
	 * the bottomPane, like the quitButton, the paused label, and the score
	 * counter.
	 */
	public VBox makeBottomPane() {
		_bottomPane = new VBox();
		_bottomPane.setAlignment(Pos.CENTER);
		_bottomPane.setStyle("-fx-background-color: grey;");
		_bottomPane.setPrefSize(7 * Constants.SQUARE_SIZE,
				4 * Constants.SQUARE_SIZE);
		_score = 0;
		_scoreLabel = new Label("Score: 0");
		_scoreLabel.setFont(new Font(20));
		_gameIsPausedLabel = new Label("PAUSED"); // put into _bottomPane when
													// key P is pressed see
													// keyHandler
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(new ClickHandler());
		_bottomPane.getChildren().addAll(_scoreLabel, quitButton);
		_bottomPane.setSpacing(10);
		_bottomPane.setAlignment(Pos.CENTER);
		return _bottomPane;
	}

	/**
	 * The setup timeLine method instantiates a keyFrame by which the _timeline
	 * operates. This timeLine initially has a keyFrame rate of 1 second, but
	 * this keyFrame is gradually made smaller to make the speed of the game
	 * faster (this is mediated by the updateGameScoreandSpeed method every time
	 * a line is cleared.
	 */
	private void setupTimeline() {
		KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new TimeHandler());
		_timeline = new Timeline(keyframe);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
		_gameIsPaused = false; // game starts out running, this is set true when
								// P is pressed and the timeLine is paused
	}

	/**
	 * This is my fav method omg. Although it's kind of long, breaking into
	 * helper method made it more complicated so I just put it back this way.
	 * The integers at the start are the x and y values of the individual
	 * squares that make up the pieces. The switch randomly selects which piece
	 * will be made by setting these integers equal to whatever values they
	 * would have to make the correct piece. The benefit of having these
	 * integers not just directly passed into the _piece instantiation is that
	 * before the piece is instantiated, the values can be used to check if a
	 * square already exists at one of the locations specified. If one does, the
	 * game is ended! It not, the piece can be generated and the game goes on.
	 */
	private void makeNewPiece() {
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		// in one line to save space
		Color color = null;
		int random = (int) (Math.random() * 7);
		switch (random) {
		case 0:
			_pieceIsSquare = false;
			x1 = 5;
			y1 = 2;
			x2 = 6;
			y2 = 2;
			x3 = 7;
			y3 = 2;
			x4 = 8;
			y4 = 2;
			color = Color.PURPLE;
			break;
		case 1:
			_pieceIsSquare = true;
			x1 = 6;
			y1 = 2;
			x2 = 7;
			y2 = 2;
			x3 = 6;
			y3 = 3;
			x4 = 7;
			y4 = 3;
			color = Color.PINK;
			break;
		case 2:
			_pieceIsSquare = false;
			x1 = 6;
			y1 = 2;
			x2 = 5;
			y2 = 3;
			x3 = 6;
			y3 = 3;
			x4 = 7;
			y4 = 3;
			color = Color.ORANGE;
			break;
		case 3:
			_pieceIsSquare = false;
			x1 = 5;
			y1 = 3;
			x2 = 6;
			y2 = 3;
			x3 = 7;
			y3 = 3;
			x4 = 7;
			y4 = 2;
			color = Color.YELLOW;
			break;
		case 4:
			_pieceIsSquare = false;
			x1 = 8;
			y1 = 3;
			x2 = 7;
			y2 = 3;
			x3 = 6;
			y3 = 3;
			x4 = 6;
			y4 = 2;
			color = Color.GREEN;
			break;
		case 5:
			_pieceIsSquare = false;
			x1 = 8;
			y1 = 2;
			x2 = 7;
			y2 = 2;
			x3 = 7;
			y3 = 3;
			x4 = 6;
			y4 = 3;
			color = Color.TEAL;
			break;
		case 6:
			_pieceIsSquare = false;
			x1 = 5;
			y1 = 2;
			x2 = 6;
			y2 = 2;
			x3 = 6;
			y3 = 3;
			x4 = 7;
			y4 = 3;
			color = Color.BLUE;
			break;
		}
		// checks if there are any squares in the new pieces way
		if (_board[x1][y1] != null || _board[x2][y2] != null
				|| _board[x3][y3] != null || _board[x4][y4] != null) {
			this.endGame();
		} else {
			// makes the piece and passes the correct coordinates for the
			// squares and the color!
			_piece = new Piece(this, x1, y1, x2, y2, x3, y3, x4, y4, color);
		}
	}

	/**
	 * This just tells the piece to shift left but it checks that there isn't
	 * anything there first by using the checkShift method.
	 */
	private void shiftPieceLeft() {
		if (this.checkShift(-1, 0) == true) {
			_piece.shiftLeft();
		}
	}

	/**
	 * This just tells the piece to shift left but it checks that there isn't
	 * anything there first by using the checkShift method.
	 */
	public void shiftPieceRight() {
		if (this.checkShift(1, 0) == true) {
			_piece.shiftRight();
		}
	}

	/**
	 * This method assumes the piece can shift but sets the canShift boolean to
	 * false if any of the spaces that the piece is shifting to end up not being
	 * equal null (are occupied by a square).
	 */
	private boolean checkShift(int col, int row) {
		boolean canShift = true;

		// made these local variables because it looked crazy before
		int x1 = (int) (_piece.getSquare1().getX() / Constants.SQUARE_SIZE);
		int y1 = (int) (_piece.getSquare1().getY() / Constants.SQUARE_SIZE);
		int x2 = (int) (_piece.getSquare2().getX() / Constants.SQUARE_SIZE);
		int y2 = (int) (_piece.getSquare2().getY() / Constants.SQUARE_SIZE);
		int x3 = (int) (_piece.getSquare3().getX() / Constants.SQUARE_SIZE);
		int y3 = (int) (_piece.getSquare3().getY() / Constants.SQUARE_SIZE);
		int x4 = (int) (_piece.getSquare4().getX() / Constants.SQUARE_SIZE);
		int y4 = (int) (_piece.getSquare4().getY() / Constants.SQUARE_SIZE);

		if ((_board[x1 + col][y1 + row] != null)
				|| (_board[x2 + col][y2 + row] != null)
				|| (_board[x3 + col][y3 + row] != null)
				|| (_board[x4 + col][y4 + row] != null)) {
			canShift = false;
		}
		return canShift;
	}

	/**
	 * If all of the conditions listed are satisfied, the piece is told to
	 * rotate. The conditions are that the positions the piece would rotate too
	 * must be free and the piece must not be a square.
	 */
	private void rotatePiece() {
		// same local variables to make code less crazy
		int x1 = (int) (_piece.getSquare1().getX() / Constants.SQUARE_SIZE);
		int y1 = (int) (_piece.getSquare1().getY() / Constants.SQUARE_SIZE);
		int x2 = (int) (_piece.getSquare2().getX() / Constants.SQUARE_SIZE);
		int y2 = (int) (_piece.getSquare2().getY() / Constants.SQUARE_SIZE);
		int x3 = (int) (_piece.getSquare3().getX() / Constants.SQUARE_SIZE);
		int y3 = (int) (_piece.getSquare3().getY() / Constants.SQUARE_SIZE);
		int x4 = (int) (_piece.getSquare4().getX() / Constants.SQUARE_SIZE);
		int y4 = (int) (_piece.getSquare4().getY() / Constants.SQUARE_SIZE);
		// pieceIsSquare is in there too! Automatically can't rotate if piece is
		// square
		if (_pieceIsSquare == false
				&& _board[x3 - y3 + y1][y3 + x3 - x1] == null
				&& _board[x3 - y3 + y2][y3 + x3 - x2] == null
				&& _board[x3 - y3 + y4][y3 + x3 - x4] == null) {
			_piece.rotate();
		}
	}

	/**
	 * This method is similar to the methods for the shiftLeft and shiftRight:
	 * it first checks if the positions the squares would shift too are free,
	 * then it tells the piece to shift down if they are. This is different in
	 * that having the piece be unable to shift down will result in the
	 * individual squares becoming part of the array and a new piece getting
	 * made.
	 */
	private void dropPieceDown() {
		// local variables not really needed here
		if (this.checkShift(0, 1) == false) {
			_board[(int) (_piece.getSquare1().getX() / Constants.SQUARE_SIZE)][(int) (_piece
					.getSquare1().getY() / Constants.SQUARE_SIZE)] = _piece
					.getSquare1();
			_board[(int) (_piece.getSquare2().getX() / Constants.SQUARE_SIZE)][(int) (_piece
					.getSquare2().getY() / Constants.SQUARE_SIZE)] = _piece
					.getSquare2();
			_board[(int) (_piece.getSquare3().getX() / Constants.SQUARE_SIZE)][(int) (_piece
					.getSquare3().getY() / Constants.SQUARE_SIZE)] = _piece
					.getSquare3();
			_board[(int) (_piece.getSquare4().getX() / Constants.SQUARE_SIZE)][(int) (_piece
					.getSquare4().getY() / Constants.SQUARE_SIZE)] = _piece
					.getSquare4();
			this.makeNewPiece();
		} else {
			_piece.dropDown();
		}
	}

	/**
	 * This is called by the keyhandler when space bar is pressed - it is a
	 * simple while loop that calls the piece to drop as long as it can.
	 */
	private void dropAllTheWay() {
		while (this.checkShift(0, -1) == true) {
			this.dropPieceDown();
		}
	}

	/**
	 * The clearLine method is complicated but I found that breaking into helper
	 * methods made it more complicated so here it is. The first part of this
	 * method loops through the array (bottom to top) checking to see if any
	 * line is full. The second part only happens when a line is full: it first
	 * graphically removes all the squares in this line, then it shifts
	 * everything down (top to bottom) logically and then graphically.
	 */
	private void clearLine() {
		// checking if line is full
		for (int row = 3; row <= 21; row++) {
			Boolean lineIsFull = true;
			for (int col = 2; col <= 11; col++) {
				if (_board[col][row] == null) {
					lineIsFull = false;
				}
			}
			// clear that line graphically (logically it will be replaced
			// anyway)
			if (lineIsFull == true) {
				this.updateScoreAndSpeed();
				for (int col = 2; col <= 11; col++) {
					_gamePane.getChildren().remove(_board[col][row]);
				}
				// updating board graphically and logically
				for (int row2 = row; row2 >= 3; row2--) {
					for (int col2 = 2; col2 <= 11; col2++) {
						_board[col2][row2] = _board[col2][row2 - 1];
						if (_board[col2][row2 - 1] != null) {
							_board[col2][row2 - 1].setLocation(
									_board[col2][row2 - 1].getX(),
									_board[col2][row2 - 1].getY()
											+ Constants.SQUARE_SIZE);
						}
					}
				}
			}
		}
	}

	/**
	 * This method is called by the clear line method every time a line is
	 * cleared! This gives the player a score and also speeds the game up as the
	 * score increases to make it harder.
	 */
	private void updateScoreAndSpeed() {
		_scoreLabel.setText("Score: " + ++_score);
		_timeline.setRate(1 + (.1 * _score));
	}

	/**
	 * This accessor method is for the piece class to add its individual squares
	 * to the gamePane so they can be displayed.
	 */
	public Pane getGamePane() {
		return _gamePane;
	}

	/**
	 * This method is called by the timeHandler at each key frame. It loops
	 * through only the first row of the game board checking for squares. If one
	 * is present, the game will end.
	 */
	private void checkTopLine() {
		for (int col = 2; col <= 11; col++) {
			if (_board[col][2] != null) {
				this.endGame();
			}
		}
	}

	/**
	 * This method is called when either of the two end game conditions are
	 * satisfied: when there is a square or squares in the way of the next piece
	 * or when there is a square in the top line of the game board.
	 */
	private void endGame() {
		// important logical stuff
		_timeline.stop();
		_gamePane.removeEventHandler(KeyEvent.KEY_PRESSED, _keyHandler);
		// this stuff just adds the gameover label
		Label gameOver = new Label("GAMEOVER");
		gameOver.setFont(new Font("Arial", 71));
		gameOver.setTextFill(Color.MAROON);
		gameOver.setPrefHeight(700);
		_gamePane.getChildren().add(gameOver);
	}

	/**
	 * This keyHandler private class carries out all the user input for the
	 * game. Pressing P pauses and un-pauses the game. When paused, the other
	 * keys will not respond and the timeLine is paused. Also, pausing adds the
	 * pause label to the bottom pane. The left key calls the shift left method,
	 * the right key calls the shift right method, the down key calls dropDown,
	 * and space calls the piece to Fall. The UP key rotates the piece by
	 * calling the rotate method.
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent keyEvent) {
			KeyCode keyPressed = keyEvent.getCode();
			switch (keyPressed) {
			case LEFT:
				if (_gameIsPaused == false) {
					shiftPieceLeft();
				}
				break;
			case RIGHT:
				if (_gameIsPaused == false) {
					shiftPieceRight();
				}
				break;
			case DOWN:
				if (_gameIsPaused == false) {
					dropPieceDown();
				}
				break;
			case UP:
				if (_gameIsPaused == false) {
					rotatePiece();
				}
				break;
			case SPACE:
				if (_gameIsPaused == false) {
					dropAllTheWay();
				}
				break;
			case P:
				if (_gameIsPaused == false) {
					_timeline.pause();
					_bottomPane.getChildren().add(_gameIsPausedLabel);
					_gameIsPaused = true;
					// Makes it so that the next time the key is pressed, the
					// else part is carried out (switches)
				} else {
					_timeline.play();
					_gameIsPaused = false;
					_bottomPane.getChildren().remove(_gameIsPausedLabel);
				}
				break;
			default:
				break;
			}
			keyEvent.consume();
		}
	}

	/**
	 * The timeHandler private class is called every second but speeds up as the
	 * player clears lines and updateGameSpeedAndScore is called. It checks the
	 * top line and clears any full lines with each keyFrame.
	 */
	private class TimeHandler implements EventHandler<ActionEvent> {

		/**
		 * The handle method is called every second at first but speeds up with
		 * each line cleared by the player as the clearLine method calls
		 * updateScoreandSpeed when a line is actually cleared.
		 */
		@Override
		public void handle(ActionEvent event) {
			checkTopLine();
			dropPieceDown();
			clearLine();
			event.consume();
		}
	}

	/**
	 * This clickHandler is for the quit button. Its handle method tells the
	 * platform to exit, quitting the application.
	 */
	private class ClickHandler implements EventHandler<ActionEvent> {

		/**
		 * This handle method is called every time the quitButton is clicked
		 * (the event is a mouse click). Quits the application with the
		 * Platform.exit command.
		 */
		@Override
		public void handle(ActionEvent event) {
			Platform.exit();
			event.consume();
		}
	}

}

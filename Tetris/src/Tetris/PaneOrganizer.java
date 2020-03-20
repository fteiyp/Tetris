package Tetris;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PaneOrganizer {

	private BorderPane _borderPane;
	private Game _game;

	/**
	 * The paneorganizer class contains the game class. It also creates the
	 * borderpane that lays out the gamepane and bottompane which are retrieved
	 * from the game class. The getBorderPane method is a very important method
	 * that is an accessor method that returns the borderpane to the app.
	 */
	public PaneOrganizer() {
		_borderPane = new BorderPane();
		_borderPane.setStyle("-fx-background-color: white;");
		_borderPane.setPrefSize(14 * Constants.SQUARE_SIZE,
				28 * Constants.SQUARE_SIZE);
		// includes space for bottomPane
		_game = new Game();
		_borderPane.setCenter(_game.getGamePane());
		_borderPane.setBottom(_game.makeBottomPane());
	}

	/**
	 * This accessor method allows the app class to pass in the borderpane for
	 * the scene. Returns the borderpane instance variable.
	 */
	public Pane getBorderPane() {
		return _borderPane;
	}
}

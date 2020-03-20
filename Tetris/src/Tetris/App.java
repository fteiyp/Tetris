package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This application models the game Tetris: pieces that are varying arrangements
 * of four squares are randomly generated at the top of grid. They fall and when
 * they are unable to fall any further are set in place and another piece is
 * generated. The pieces are able to be shifted sidways or rotated, and the game
 * can be paused. This app class instantiates the pane organizer which in turn
 * instantiates the game class that contains most of the other objects and some
 * private classes.
 */
public class App extends Application {

	/**
	 * This start method is passed the stage as an argument and sets its title
	 * to Tetris!. The PaneOrganizer is instantiated locally and the scene is
	 * passed _borderpane (root) that the pane organizer instantiates. The scene
	 * is set as the scene of the stage and the stage is displayed (show).
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("Tetris!");
		PaneOrganizer organizer = new PaneOrganizer();
		Scene scene = new Scene(organizer.getBorderPane());
		stage.setScene(scene);
		stage.show();
	}

	/*
	 * This is the main line.
	 */
	public static void main(String[] argv) {
		// launch is a method inherited from Application
		launch(argv);
	}
}

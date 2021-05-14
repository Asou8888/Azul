package comp1110.ass2.gui;

import comp1110.ass2.Azul;
import comp1110.ass2.member.*;
import gittest.C;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application {
    /*  board layout */
    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;

    private static final int TILE_SIZE = 48; // Tile size

    private static final long MOVE_THRESHOLD = 50; // Allow the next move after 50ms.
    private static final char NOT_PLACED = 255; // marker for unplaced tile. TODO: modified needed.

    /*  game information  */
    private static final int PLAYER_NUM = 2;
    private static final String[] PLAYER_CODE = new String[]{"A", "B", "C", "D"};
    private static final String[] DEFAULT_PLAYER_NAME = new String[]{"Alice", "Bob", "John", "Cathy"};
    // FIXME
    public static boolean isClick = false;
    public static Tile from;
    public static Tile to;
    public static int rowInStorage; // the information get from the storage.

    /*  nodes  */
    private final Group root = new Group();
    private final Group board = new Group();
    private final Group gTiles = new Group();
    private final Group controls = new Group();

    private final Group storages = new Group(); // view for storages(A & B)
    private final Group mosaics = new Group(); // view for mosaics
    private final Group floor = new Group(); // view for floor

    private final Group factories = new Group(); // view for factories(0 to 4)
    private final Group center = new Group(); // view for center
    private final Group discard = new Group(); // view for discard
    private final Group bag = new Group(); // view for bag

    /*  game state  */
    private static String[] gameState; // current game state

    /*  Azul game control methods */
    Azul azulGame;

    /*  [Reference: https://gitlab.cecs.anu.edu.au/comp1110/dinosaurs/-/blob/master/src/comp1110/ass1/gui/Game.java#L87]
     *  Define a drop shadow effect that we will apply to the tile
     *  TODO: improve and test the effects.
     */
    private static DropShadow dropshadow;
    /*  static initializer to initialize dropShadow  */ {
        dropshadow = new DropShadow();
        dropshadow.setOffsetX(2.0);
        dropshadow.setOffsetY(2.0);
        dropshadow.setColor(Color.color(0, 0, 0, 0.4));
    }

    /*  [Reference: https://gitlab.cecs.anu.edu.au/comp1110/dinosaurs/-/blob/master/src/comp1110/ass1/gui/Game.java#L388]
     *  Audio set up
     */
    private static final String URI_BASE = "assets/";
    private static final String LOOP_URI = Game.class.getResource(URI_BASE + "Song for a Poet(Acoustic) 1644.wav").toString();
    private AudioClip loop;

    /*  Game Variable  */
    private boolean loopPlaying = false;

    /**
     * Set up the sound loop.
     */
    private void setUpSoundLoop() {
        try {
            loop = new AudioClip(LOOP_URI);
            loop.setCycleCount(AudioClip.INDEFINITE);
        } catch (Exception e) {
            System.err.println(":-( something bad happened (" + LOOP_URI + "): " + e);
        }
    }

    /**
     * Turn the sound loop on or off.
     */
    private void toggleSoundLoop() {
        if (loopPlaying) {
            loop.stop();
        } else {
            loop.play();
        }
        loopPlaying = !loopPlaying;
    }

    private void makeBoard() {
        board.getChildren().clear();
        // TODO: add something to background.
        board.toBack();
    }

    /**
     * Set up the tiles
     */
    private void makeTiles() {
        // TODO
    }

    /**
     * Add storage, mosaic, floor, center, factories, discard, bag to board.
     */
    private void addObjectToBoard() {
        // TODO
    }

    /**
     * Check game completion and update states.
     */
    private void checkCompletion() {
        // TODO
    }

    /**
     * Put all of the tiles back in their home position.
     */
    private void resetPieces() {
        // TODO
    }

    /**
     * Create controls that allow the game restarted.
     */
    private void makeControls() {
        // TODO
    }

    /**
     * Create the message to be displayed when the player completes the puzzle.
     */
    private void makeCompletion() {
        // TODO
    }

    /**
     * Show the completion message.
     */
    private void showCompletion() {
        // TODO
    }

    /**
     * Hide the completion message.
     */
    private void hideCompletion() {
        // TODO
    }

    /**
     * Start a new game, reset everything.
     */
    private void newGame() {
        // TODO
    }

    @Override
    public void start(Stage stage) throws Exception {
        //  FIXME Task 12: Implement a basic playable Azul game in JavaFX that only allows tiles to be placed in valid places
        //  FIXME Task 14: Implement a computer opponent so that a human can play your game against the computer.
        stage.setTitle("Azul");
        // [Original Code] Group root = new Group();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(gTiles);
        root.getChildren().add(board);
        root.getChildren().add(controls);

        // setUpHandlers(scene);
        setUpSoundLoop();
        makeBoard();
        makeControls();
        makeCompletion();

        newGame();

        stage.setScene(scene);
        stage.show();
    }
}

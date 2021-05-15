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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    /* FIXME
    private static final String URI_BASE = "../assets/";
    private static final String LOOP_URI = Game.class.getResource(URI_BASE + "Song for a Poet(Acoustic) 1644.wav").toString();
    private AudioClip loop;

     */

    /*  Game Variable  */
    private boolean loopPlaying = false;

    /**
     * Set up the sound loop.
     */
    /* FIXME
    private void setUpSoundLoop() {
        try {
            loop = new AudioClip(LOOP_URI);
            loop.setCycleCount(AudioClip.INDEFINITE);
        } catch (Exception e) {
            System.err.println(":-( something bad happened (" + LOOP_URI + "): " + e);
        }
    }

     */

    /**
     * Turn the sound loop on or off.
     */
    /* FIXME
    private void toggleSoundLoop() {
        if (loopPlaying) {
            loop.stop();
        } else {
            loop.play();
        }
        loopPlaying = !loopPlaying;
    }

     */

    private void makeBoard() {
        board.getChildren().clear();
        // TODO: add something to background.
        board.toBack();
    }

    class GTile extends ImageView {
        private char colorChar; // the colour of the tile
        int positionX; // the X position of tile on board
        int positionY; // the Y position of tile on board
        int tileID; // graphical representations of tiles

        /**
         * Construct a particular playing tile
         *
         * @param tile The letter representing the tile to be created.
         */
        GTile(char tile) {
            if (tile > 'f' || tile < 'a') {
                throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
            }
            this.tileID = tile - 'a';
            setFitHeight(TILE_SIZE);
            setFitWidth(TILE_SIZE);
            setEffect(dropshadow);

        }


        /**
         * A constructor used to build the objective tile.
         *
         * @param tileID The tile to be displayed (one of 100 objectives)
         * @param x    The x position of the tile
         * @param y    The y position of the tile
         */
        GTile(int tileID, int x, int y) {
            if (!(tileID <= 100 && tileID >= 1)) {
                throw new IllegalArgumentException("Bad tile: \"" + tileID + "\"");
            }

            String t = String.format("%02d", tileID); //formatted as at least 2 decimal integers eg. 07
            // setImage(new Image(Game.class.getResource(URI_BASE + t + ".png").toString()));
            this.tileID = tileID;
            setFitHeight(TILE_SIZE); //set height TODO may change the value
            setFitWidth(TILE_SIZE); // set width
            setEffect(dropshadow);

            setLayoutX(x);
            setLayoutY(y);
        }
    }
    class DraggableTile extends GTile {
        int homeX; // last x position of the tile on board
        int homeY; // last y position of the tile on board
        double mouseX; // the last x position when dragging the tile on board
        double mouseY; // the last y position then dragging the tile on board

        /**
         * Construct a draggable tile
         *
         * @param tile The tile identifier ('a' - 'f')
         */
        DraggableTile(char tile) {
            super(tile);
            setLayoutX(homeX); // Set the x-axis coordinate of the starting point
            setLayoutY(homeY); //Set the y-axis coordinate of the starting point
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged
                hideCompletion();
                toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            setOnMouseReleased(event -> {     // drag is complete
                snapToGrid();
            });
        }

        /**
         * Snap the tile to the nearest grid position (if it is over the grid)
         */
        private void snapToGrid() {
            if (onBoard() && (!alreadyOccupied()) && isValidMember() && isValidMove()) {
                //TODO
                setPosition();
            } else {
                snapToHome();
            }
            updateGameState();
        }

        /**
         * @return true if the tile is on the board
         */
        private boolean onBoard() {
            //TODO
            return false;
        }
        /**
         * a function to check whether the current destination cell
         * is already occupied by another tile
         *
         * @return true if the destination cell for the current tile
         * is already occupied, and false otherwise
         */
        private boolean alreadyOccupied() {
            //TODO
            return false;
        }

        /**
         * a function to check whether the tile in current member destination is valid from last member
         * @return true if it is valid to move the tile from last to current member
         */
        private boolean isValidMember(){
            //TODO
            return false;
        }

        /**
         * @return if it is valid to move a specific current destination cell from last destination cell.
         */
        private boolean isValidMove(){
            //TODO
            return false;
        }

        /**
         * set the tile position by current position when mouse release.
         */
        private void setPosition() {
            // TODO
        }

        /**
         * set the tile back to last valid position before mouse pressing.
         */
        private void snapToHome(){
            //TODO
        }

        /**
         * the game state should be updated each time when there is a possible tile moving.
         */
        private void updateGameState() {
            //TODO
        }
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
        // setUpSoundLoop();
        makeBoard();
        makeControls();
        makeCompletion();

        newGame();

        stage.setScene(scene);
        stage.show();
    }
}

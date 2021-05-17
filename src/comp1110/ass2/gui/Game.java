package comp1110.ass2.gui;

import comp1110.ass2.Azul;
import comp1110.ass2.member.*;
import gittest.A;
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
import java.util.HashMap;

public class Game extends Application {
    /*  board layout */
    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;

    private static final int TILE_SIZE = 48; // Tile size

    private static final long MOVE_THRESHOLD = 50; // Allow the next move after 50ms.
    private static final char NOT_PLACED = 255; // marker for unplaced tile. TODO: modified needed.

    /*  game information  */
    private static final int PLAYER_NUM = 2;
    private static final int FACTORY_NUM = 5;
    private static final String[] PLAYER_CODE = new String[]{"A", "B", "C", "D"};
    private static final String[] DEFAULT_PLAYER_NAME = new String[]{"Alice", "Bob", "John", "Cathy"};
    private static final int COLOR_TILES_NUM = 20; // the number of tiles of each color.
    private static final int FIRST_PLAYER_TILE_NUM = 1; // the number of 'first player' tile.
    // FIXME
    public static boolean isClick = false;
    public static Tile from;
    public static Tile to;
    public static int rowInStorage; // the information get from the storage.

    /*  nodes  */
    private final Group root = new Group();
    private final Group gTiles = new Group();
    private final Group controls = new Group();

    private final Group[] storages = new Group[PLAYER_NUM]; // view for storages(A & B)
    private final Group[] mosaics = new Group[PLAYER_NUM]; // view for mosaics
    private final Group[] floor = new Group[PLAYER_NUM]; // view for floor

    private final Group[] factories = new Group[FACTORY_NUM]; // view for factories(0 to 4)
    private final Group center = new Group(); // view for center
    private final Group discard = new Group(); // view for discard
    private final Group bag = new Group(); // view for bag

    private static final int AMOSAIC_X_LAYOUT = 280; // XIndex of mosaic of player A ,player B +600
    private static final int AMOSAIC_Y_LAYOUT = 227; // YIndex of mosaic/storage of A and B player
    private static final int ASTORAGE_X_LAYOUT = 150; // XIndex of storage of A, B +600
    private static final int AFLOOR_X_LAYOUT = 120; //XIndex of floor of A, B+600
    private static final int AFLOOR_Y_LAYOUT = 550; //YIndex of floor of A and B
    private static final int CENTER_X_LAYOUT = 550; //XIndex of center
    private static final int CENTER_Y_LAYOUT = 180; //YIndex of center
    private static final int FACTORIES_X_LAYOUT = 310;
    private static final int FACTORIES_Y_LAYOUT = 20; //YIndex of Factories




    /*  game state  */
    private String[] gameState; // current game state
    private static final String startSharedState = "AFCfB2020202020D0000000000"; // shared state at the start of the game
    private static final String startPlayerState = "A0MSFB0MSF"; // player state at the start of the game

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


    class GTile extends Rectangle {
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
            setHeight(TILE_SIZE);
            setWidth(TILE_SIZE);
            setEffect(dropshadow);
            setFill(Color.GREY.brighter());

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
            setHeight(TILE_SIZE); //set height TODO may change the value
            setWidth(TILE_SIZE); // set width
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
            // set Color to draggable tile.
            switch (tile) {
                case 'a' -> setFill(Color.BLUE); // blue tile
                case 'b' -> setFill(Color.GREEN); // green tile
                case 'c' -> setFill(Color.ORANGE); // orange tile
                case 'd' -> setFill(Color.PURPLE); // purple tile
                case 'e' -> setFill(Color.RED); // red tile
                case 'f' -> setFill(Color.LIGHTGRAY); // first player tile
            }
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                setOpacity(0.6);
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
                setOpacity(1.0);
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
        // TODO test
        this.gTiles.getChildren().clear(); // clear all the previous tiles.
        this.gTiles.getChildren().add(new DraggableTile('f')); // add 'first player' tile.
        for (int i = 0; i < COLOR_TILES_NUM; i++) {
            this.gTiles.getChildren().add(new DraggableTile('a')); // add 'a' tiles.
            this.gTiles.getChildren().add(new DraggableTile('b')); // add 'b' tiles.
            this.gTiles.getChildren().add(new DraggableTile('c')); // add 'c' tiles.
            this.gTiles.getChildren().add(new DraggableTile('d')); // add 'd' tiles.
            this.gTiles.getChildren().add(new DraggableTile('e')); // add 'e' tiles.
        }
    }


    //add Mosaic to root
    private void addMosaicToRoot(){
        /**
         * written by Xiao Xu
         */
        for(int m = 0;m<PLAYER_NUM;m++){
            this.mosaics[m] = new Group();
            for (int i = 0; i < NewMosaic.MOSAIC_WIDTH; i++) {
                for (int j = 0; j < NewMosaic.MOSAIC_WIDTH; j++) {
                    GTile r = new GTile('a');
                    r.setLayoutX(i*TILE_SIZE);
                    r.setLayoutY(j*TILE_SIZE);
                    mosaics[m].getChildren().add(r);
                }
            }
        }
        //set location of mosaics
        mosaics[0].setLayoutX(AMOSAIC_X_LAYOUT);
        mosaics[0].setLayoutY(AMOSAIC_Y_LAYOUT);

        mosaics[1].setLayoutX(AMOSAIC_X_LAYOUT+660);
        mosaics[1].setLayoutY(AMOSAIC_Y_LAYOUT);

    }

    //add storage to root
    private void addStorageToRoot(){
        for(int m = 0;m<PLAYER_NUM;m++) {
            this.storages[m] = new Group();
            for (int i = 0; i < Storage.STORAGE_ROW_NUM; i++) {
                for (int j = 0; j <= i; j++) {
                    GTile r = new GTile('a');
                    r.setLayoutX((j - i + 1) * TILE_SIZE);
                    r.setLayoutY(i * TILE_SIZE);
                    storages[m].getChildren().add(r);
                }
            }
        }
        //set location of storages
        storages[0].setLayoutX(ASTORAGE_X_LAYOUT);
        storages[0].setLayoutY(AMOSAIC_Y_LAYOUT);

        storages[1].setLayoutX(ASTORAGE_X_LAYOUT+660);
        storages[1].setLayoutY(AMOSAIC_Y_LAYOUT);

    }

    //add floor to root
    private void addFloorToRoot(){
        for(int m = 0; m<PLAYER_NUM;m++){
            this.floor[m] = new Group();
            for(int i = 0;i < Floor.FLOOR_WIDTH;i++){
                GTile r = new GTile('a');
                r.setLayoutX(i*TILE_SIZE);
                r.setLayoutY(0);
                floor[m].getChildren().add(r);
            }
        }

        //set location
        floor[0].setLayoutX(AFLOOR_X_LAYOUT);
        floor[0].setLayoutY(AFLOOR_Y_LAYOUT);

        floor[1].setLayoutX(AFLOOR_X_LAYOUT+600);
        floor[1].setLayoutY(AFLOOR_Y_LAYOUT);
    }

    //add center to root
    private void addCenterToRoot(){
        for (int i = 0; i < Center.CENTER_HEIGHT; i++) {
            for (int j = 0; j < Center.CENTER_WIDTH; j++) {
                GTile r = new GTile('a');
                r.setLayoutX(j*TILE_SIZE);
                r.setLayoutY(i*TILE_SIZE);
                center.getChildren().add(r);
            }
        }
        //set location
        center.setLayoutX(CENTER_X_LAYOUT);
        center.setLayoutY(CENTER_Y_LAYOUT);
    }

    //add factories to root
    private void addFactoriesToRoot(){
        for(int m = 0;m<5;m++){
            this.factories[m] = new Group();
            for (int i = 0; i < Factory.MAX_FACTORY_TILES_NUM / 2; i++) {
                for (int j = 0; j < Factory.MAX_FACTORY_TILES_NUM / 2; j++) {
                    GTile r = new GTile('a');
                    r.setLayoutX(j*TILE_SIZE);
                    r.setLayoutY(i*TILE_SIZE);
                    factories[m].getChildren().add(r);
                }
            }
            //set location
            factories[m].setLayoutX(2.5*m*TILE_SIZE+FACTORIES_X_LAYOUT);
            factories[m].setLayoutY(FACTORIES_Y_LAYOUT);

        }

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
     * Reset the game state.
     */
    private void resetGameState() {
        this.gameState = new String[]{
                startSharedState, startPlayerState
        };
    }


    /**
     * update the factory view, according to the Characters input
     * @param factory index-th factory's state code.
     * @param index the index of factory to be updated.
     */
    private void updateFactoryView(String factory, int index) {
        // TODO
        this.factories[index].getChildren().clear();
        GTile[] tiles = new GTile[Factory.MAX_FACTORY_TILES_NUM];
        int cnt = 0; // the counter of draggable tiles.
        for (int i = 0; i < factory.length(); i++) {
            // first character of factory would be a digit, so skip it.
            tiles[cnt] = new DraggableTile(factory.charAt(i)); // create a draggable tile for each character.
            cnt++;
        }
        for (int i = cnt; i < tiles.length; i++) {
            // fill up the empty space in factory.
            tiles[i] = new GTile('a'); // create a GTile.
        }
        cnt = 0; // reset the counter.
        for (int i = 0; i < Factory.MAX_FACTORY_TILES_NUM / 2; i++) {
            for (int j = 0; j < Factory.MAX_FACTORY_TILES_NUM / 2; j++) {
                tiles[cnt].setLayoutX(j * TILE_SIZE);
                tiles[cnt].setLayoutY(i * TILE_SIZE);
                this.factories[index].getChildren().add(tiles[cnt]);
                cnt++;
            }
        }
    }

    /**
     * refill the factory according to current game State.
     */
    private void refillFactories() {
        System.out.println("Before Refill Factory: {" + gameState[0] + ", " + gameState[1] + "}");
        this.gameState = Azul.refillFactories(this.gameState); // refill factory, get the game state after refilling, then display it on the board.
        System.out.println("After Refill Factory: {" + gameState[0] + ", " + gameState[1] + "}");
        String[] sharedState = Azul.splitSharedState(this.gameState);
        String factory = sharedState[1];
        System.out.println("Factory: " + factory);
        Factories f = new Factories();
        String[] eachFactoryState = f.splitFactoryState(factory);
        for (int i = 0; i < eachFactoryState.length; i++) {
            updateFactoryView(eachFactoryState[i], i); // update i-th factory view.
        }
    }


    /**
     * Start a new game, reset everything.
     */
    private void newGame() {
        // TODO
        resetGameState(); // reset the game state.
        refillFactories(); // refill the factories.
    }

    @Override
    public void start(Stage stage) throws Exception {
        //  FIXME Task 12: Implement a basic playable Azul game in JavaFX that only allows tiles to be placed in valid places
        //  FIXME Task 14: Implement a computer opponent so that a human can play your game against the computer.
        stage.setTitle("Azul");
        // [Original Code] Group root = new Group();

        //add playerState
        addMosaicToRoot();
        addStorageToRoot();
        addFloorToRoot();

        //add sharedState
        addCenterToRoot();
        addFactoriesToRoot();

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);


        root.getChildren().add(gTiles);
        // add playerState
        for(Group m:mosaics){
            root.getChildren().add(m);
        }
        for(Group s:storages){
            root.getChildren().add(s);
        }
        for(Group f:floor){
            root.getChildren().add(f);
        }

        //add sharedState
        root.getChildren().add(center);

        for(Group f:factories){
            root.getChildren().add(f);
        }


        root.getChildren().add(controls);

        // setUpHandlers(scene);
        // setUpSoundLoop();
        makeControls();
        makeCompletion();

        newGame();

        stage.setScene(scene);
        stage.show();
    }
}

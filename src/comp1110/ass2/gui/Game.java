package comp1110.ass2.gui;

import comp1110.ass2.Azul;
import comp1110.ass2.member.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

import java.awt.*;
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
    private static final int CENTER_TILES_NUM = 16; // the maximum number of tiles in center.(View)
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

    private final Pane[] playerBoard = new Pane[PLAYER_NUM]; // view for player board
    private final Label[] playerLabel = new Label[PLAYER_NUM]; // included in player board.
    private final TextField[] scoreField = new TextField[PLAYER_NUM]; // included in player board.

    /*  parameters for player board  */
    private static final int PLAYER_LABEL_HEIGHT = 30; // the height of player label
    private static final int SCORE_FIELD_HEIGHT = 30; // the hieght of scoreField.
    private static final int PLAYER_LABEL_WIDTH = 60; // the width of player label.
    private static final int SCORE_FIELD_WIDTH = 60; // the width of scoreField.
    private static final int[] PLAYER_BOARD_LAYOUT_X = {40, 1020, 40, 1020}; // the layoutX of player board.
    private static final int[] PLAYER_BOARD_LAYOUT_Y = {10, 10, 50, 50}; // the layoutY of player board.
    private static final int PLAYER_LABEL_LAYOUT_X = 0; // the layoutX of player labels.
    private static final int SCORE_FIELD_LAYOUT_X = 80; // the layoutX of scoreField.
    private static final int PLAYER_LABEL_LAYOUT_Y = 0; // the layoutY of player labels.
    private static final int SCORE_FIELD_LAYOUT_Y = 0; // the layoutY of scoreField.

    private static final int AMOSAIC_X_LAYOUT = 280; // XIndex of mosaic of player A ,player B +600
    private static final int AMOSAIC_Y_LAYOUT = 227; // YIndex of mosaic/storage of A and B player
    private static final int ASTORAGE_X_LAYOUT = 198; // XIndex of storage of A, B +600
    private static final int ASTORAGE_Y_LAYOUT = 227; //YIndex of storage of A
    private static final int AFLOOR_X_LAYOUT = 120; //XIndex of floor of A, B+600
    private static final int AFLOOR_Y_LAYOUT = 550; //YIndex of floor of A and B
    private static final int BMOSAIC_X_LAYOUT = 940; // XIndex of mosaic of player B
    private static final int BMOSAIC_Y_LAYOUT = 227; // YIndex of mosaic of player B
    private static final int BSTORAGE_X_LAYOUT = 858; // XIndex of storage of B
    private static final int BSTORAGE_Y_LAYOUT = 227; //YIndex of storage of B
    private static final int BFLOOR_X_LAYOUT = 780; //XIndex of floor of B
    private static final int BFLOOR_Y_LAYOUT = 550; //YIndex of floor of B
    private static final int CENTER_X_LAYOUT = 550; //XIndex of center
    private static final int CENTER_Y_LAYOUT = 180; //YIndex of center
    private static final int FACTORIES_X_LAYOUT = 310;
    private static final int FACTORIES_Y_LAYOUT = 20; //YIndex of Factories

    private static final int NUMBER_OF_TILETYPE = 5;
    private final Label[] bagLable = new Label[NUMBER_OF_TILETYPE]; // included in player board.
    private final TextField[] bagField = new TextField[NUMBER_OF_TILETYPE]; // included in player board.
    private static final int BAG_TEXT_FIELD_X_LAYOUT = 120;
    private static final int BAG_TEXT_FIELD_Y_LAYOUT = 97;
    private static final int BAG_TEXT_FIELD_HEIGHT = 20;
    private static final int BAG_TEXT_FIELD_WEIGHT = 40;
    private static final int BAG_LABEL_X_LAYOUT = 20;
    private static final int BAG_LABEL_Y_LAYOUT = 100;
    private static final int BAG_X_LAYOUT = 20;
    private static final int BAG_Y_LAYOUT = 70;



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
        char colorChar; // the colour of the tile
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
                this.colorChar = NOT_PLACED;
            }
            this.colorChar = tile;
            this.tileID = tile - 'a';
            setHeight(TILE_SIZE);
            setWidth(TILE_SIZE);
            setEffect(dropshadow);
            setFill(Color.GREY.brighter());

        }

        GTile(int positionX, int positionY) {
            this.positionX = positionX;
            this.positionY = positionY;
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
        double homeX; // last x position of the tile on board
        double homeY; // last y position of the tile on board
        double mouseX; // the last x position when dragging the tile on board
        double mouseY; // the last y position then dragging the tile on board
        double layOutX;
        double layOutY;
        /**
         * Construct a draggable tile
         *
         * @param tile The tile identifier ('a' - 'f')
         */
        DraggableTile(char tile) {
            super(tile);
            if (tile > 'f' || tile < 'a') {
                throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
            }

            // set Color to draggable tile.
            switch (tile) {
                case 'a' -> setFill(Color.BLUE); // blue tile
                case 'b' -> setFill(Color.GREEN); // green tile
                case 'c' -> setFill(Color.ORANGE); // orange tile
                case 'd' -> setFill(Color.PURPLE); // purple tile
                case 'e' -> setFill(Color.RED); // red tile
                case 'f' -> setFill(Color.BLACK); // first player tile
            }
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                setOpacity(0.6);
                this.layOutX = getLayoutX();
                this.layOutY = getLayoutY();
                homeX = event.getSceneX();
                homeY = event.getSceneY();
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
            setOnMouseReleased(event -> {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY(); // drag is complete
                System.out.println("Release Position: " + mouseX + " " + mouseY);
                setOpacity(1.0);
                double[] pos = findPosition();
                System.out.println(pos[0] + " " + pos[1] + " " + pos[2]);
                snapToGrid();
            });
        }

        /**
         * Snap the tile to the nearest grid position (if it is over the grid)
         */
        private void snapToGrid() {
            if (isValidMove()) {
                //TODO
                System.out.println("1");
                updateGameState();
            } else {
                System.out.println("2");
                snapToHome();
                System.out.println(findMove());
            }
        }

        /**
         * @return true if the tile is on the board
         */
        private boolean onBoard() {
            return mouseX > 0 && mouseX < BOARD_WIDTH  &&
                    mouseY > 0 && mouseY < BOARD_HEIGHT;
        }

        /**
         * @return the x and y axis of the tile slots on board, and the member the tile in
         * (StorageA = 1, MosaicA = 2, FloorA = 3,StorageB = 4, MosaicB = 5, FloorB = 6.)
        */
        private double[] findPosition() {
            double[] xAndY = new double[]{-1.0,-1.0,-1.0};

            double x =  mouseX; // find the central x position of the draggable tile.
            double y =  mouseY; // find the central y position of the draggable tile.
            //if draggable tile released in Mosaic A
            if (x > AMOSAIC_X_LAYOUT && x < AMOSAIC_X_LAYOUT + (5 * TILE_SIZE) &&
                    y > AMOSAIC_Y_LAYOUT && y < AMOSAIC_Y_LAYOUT + (5 * TILE_SIZE)) {
                xAndY[2] = 2;
                for (int i = 0; i < 5; i++) {
                    if (x > AMOSAIC_X_LAYOUT + (i * TILE_SIZE) && x < AMOSAIC_X_LAYOUT + (i * TILE_SIZE) + TILE_SIZE) {
                        xAndY[0] = AMOSAIC_X_LAYOUT + (i * TILE_SIZE);
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (y > AMOSAIC_Y_LAYOUT + (j * TILE_SIZE) && y < AMOSAIC_Y_LAYOUT + (j * TILE_SIZE) + TILE_SIZE) {
                        xAndY[1] = AMOSAIC_Y_LAYOUT + (j * TILE_SIZE);
                    }
                }
            }
            //if draggable tile released in Mosaic B
            if (x > BMOSAIC_X_LAYOUT && x < BMOSAIC_X_LAYOUT + (5 * TILE_SIZE) &&
                    y > BMOSAIC_Y_LAYOUT && y < BMOSAIC_Y_LAYOUT + (5 * TILE_SIZE)) {
                xAndY[2] = 5;
                for (int i = 0; i < 5; i++) {
                    if (x > BMOSAIC_X_LAYOUT + (i * TILE_SIZE) && x < BMOSAIC_X_LAYOUT + (i * TILE_SIZE) + TILE_SIZE) {
                        xAndY[0] = BMOSAIC_X_LAYOUT + (i * TILE_SIZE);
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (y > BMOSAIC_Y_LAYOUT + (j * TILE_SIZE) && y < BMOSAIC_Y_LAYOUT + (j * TILE_SIZE) + TILE_SIZE) {
                        xAndY[1] = BMOSAIC_Y_LAYOUT + (j * TILE_SIZE);
                    }
                }
            }
            //if draggable tile released in Storage A
            if (x > ASTORAGE_X_LAYOUT - TILE_SIZE*4 && x < ASTORAGE_X_LAYOUT +TILE_SIZE &&
            y > ASTORAGE_Y_LAYOUT && y < ASTORAGE_Y_LAYOUT+(5*TILE_SIZE)) {
                int xRight = ASTORAGE_X_LAYOUT + TILE_SIZE;
                int YRight = 0;
                for (int i = 0; i < 5; i++) {
                    if (x < xRight - (i * TILE_SIZE) && x > xRight - (i * TILE_SIZE) - TILE_SIZE) {
                        xAndY[0] = xRight - (i * TILE_SIZE) - TILE_SIZE;
                        YRight = i;
                    }
                }
                int yMax = 5;
                while (YRight < yMax) {
                    if (y > ASTORAGE_Y_LAYOUT + TILE_SIZE * YRight &&
                            y < ASTORAGE_Y_LAYOUT + TILE_SIZE * YRight + TILE_SIZE) {
                        xAndY[1] = ASTORAGE_Y_LAYOUT + TILE_SIZE * YRight;
                        xAndY[2] = 1;
                    }
                    YRight++;
                }
            }
            //if draggable tile released in Storage B
            if (x > BSTORAGE_X_LAYOUT - TILE_SIZE*4 && x < BSTORAGE_X_LAYOUT +TILE_SIZE &&
                    y > BSTORAGE_Y_LAYOUT && y < BSTORAGE_Y_LAYOUT+(5*TILE_SIZE)) {
                int xRight = BSTORAGE_X_LAYOUT + TILE_SIZE;
                int YRight = 0;
                for (int i = 0; i < 5; i++) {
                    if (x < xRight - (i * TILE_SIZE) && x > xRight - (i * TILE_SIZE) - TILE_SIZE) {
                        xAndY[0] = xRight - (i * TILE_SIZE) - TILE_SIZE;
                        YRight = i;
                    }
                }
                int yMax = 5;
                while (YRight < yMax) {
                    if (y > BSTORAGE_Y_LAYOUT + TILE_SIZE * YRight &&
                            y < BSTORAGE_Y_LAYOUT + TILE_SIZE * YRight + TILE_SIZE) {
                        xAndY[1] = BSTORAGE_Y_LAYOUT + TILE_SIZE * YRight;
                        xAndY[2] = 4;
                    }
                    YRight++;
                }
            }
            //if draggable tile released in Floor A;
            if (x > AFLOOR_X_LAYOUT && x < AFLOOR_X_LAYOUT+7*TILE_SIZE &&
            y > AFLOOR_Y_LAYOUT && y < AFLOOR_Y_LAYOUT +TILE_SIZE){
                xAndY[2] = 3;
                for(int i = 0; i < 7;i++){
                    if (x > AFLOOR_X_LAYOUT + i*TILE_SIZE && x < AFLOOR_X_LAYOUT+i*TILE_SIZE +TILE_SIZE){
                        xAndY[0] = AFLOOR_X_LAYOUT + i*TILE_SIZE;
                        xAndY[1] = AFLOOR_Y_LAYOUT;
                    }
                }
            }
            //if draggable tile released in Floor B;
            if (x > BFLOOR_X_LAYOUT && x < BFLOOR_X_LAYOUT+7*TILE_SIZE &&
                    y > BFLOOR_Y_LAYOUT && y < BFLOOR_Y_LAYOUT +TILE_SIZE){
                xAndY[2] = 6;
                for(int i = 0; i < 7;i++){
                    if (x > BFLOOR_X_LAYOUT + i*TILE_SIZE && x < BFLOOR_X_LAYOUT+i*TILE_SIZE +TILE_SIZE){
                        xAndY[0] = BFLOOR_X_LAYOUT + i*TILE_SIZE;
                        xAndY[1] = BFLOOR_Y_LAYOUT;
                    }
                }
            }
            return xAndY;
        }
        /**
         * a function to check whether the current destination cell
         * is already occupied by another tile
         *
         * @return true if the destination cell for the current tile
         * is already occupied, and false otherwise
         */
        private boolean alreadyOccupied() {
            double x = findPosition()[0];
            double y = findPosition()[1];
            return this.colorChar != NOT_PLACED;
        }

        /**
         * @return if it is valid to move a specific current destination cell from last destination cell.
         */
        private boolean isValidMove(){

            return (Azul.isMoveValid(gameState,findMove()));
        }

        /**
         *
         * @return the string of the drafting move or tiling move
         */
        private String findMove(){
            String turn = Azul.whoseTurn(gameState);
            String move = "";
            double yAxis = findPosition()[1];
            if (findPosition()[2] == -1) return "";
            //find whose turn
            // add "C" if from Center
            if (homeX > CENTER_X_LAYOUT && homeX < CENTER_X_LAYOUT + 2* TILE_SIZE &&
                    homeY > CENTER_Y_LAYOUT && homeY < CENTER_Y_LAYOUT + 8 *TILE_SIZE){
                // if player A puts tile on StorageA
                if (turn.equals("A") && (findPosition()[2] == 1)){
                    move = move + "A" + "C" + this.colorChar + ((int)((yAxis - ASTORAGE_Y_LAYOUT) / TILE_SIZE));
                }
                // if player A puts tile on FloorA
                if (turn.equals("A") && (findPosition()[2] == 3)) {
                    move = move + "A" + "C" + this.colorChar + "F";
                }
                // if player B puts tile on StorageB
                if (turn.equals("B") && (findPosition()[2] == 4)){
                    move = move + "B" + "C" + this.colorChar + ((int)((yAxis - BSTORAGE_Y_LAYOUT) / TILE_SIZE));
                }
                // if player B puts tile on FloorB
                if (turn.equals("B") && (findPosition()[2] == 6)) {
                    move = move + "B" + "C" + this.colorChar + "F";
                }
            }
            //if draggable tile from factories
            if (homeX > FACTORIES_X_LAYOUT && homeX < FACTORIES_X_LAYOUT + 12*TILE_SIZE &&
                    homeY > FACTORIES_Y_LAYOUT && homeY < FACTORIES_Y_LAYOUT + 2*TILE_SIZE) {
                int fac = 0;
                for (int i = 0; i < 5; i++) {
                    if (homeX > FACTORIES_X_LAYOUT + i * 2.5 * TILE_SIZE &&
                            homeX < FACTORIES_X_LAYOUT + i * 2.5 * TILE_SIZE + 2 * TILE_SIZE) {
                        fac = i; // find which factory the draggable tile from
                    }
                }
                // if player A puts tile on StorageA
                if (turn.equals("A") && (findPosition()[2] == 1)) {
                    move = move + "A" + fac + this.colorChar + ((int)((yAxis - ASTORAGE_Y_LAYOUT) / TILE_SIZE ));
                }
                // if player A puts tile on FloorA
                if (turn.equals("A") && (findPosition()[2] == 3)) {
                    move = move + "A" + fac + this.colorChar + "F";
                }
                // if player B puts tile on StorageB
                if (turn.equals("B") && (findPosition()[2] == 4)) {
                    move = move + "B" + fac + this.colorChar + ((int)((yAxis - BSTORAGE_Y_LAYOUT) / TILE_SIZE));
                }
                // if player B puts tile on FloorB
                if (turn.equals("B") && (findPosition()[2] == 6)) {
                    move = move + "B" + fac + this.colorChar + "F";
                }
            }
            //if the draggable tile is from StorageA to MosaicA
            if (homeX > ASTORAGE_X_LAYOUT- 4*TILE_SIZE && homeX < ASTORAGE_X_LAYOUT + TILE_SIZE &&
                    homeY > ASTORAGE_Y_LAYOUT && homeY < ASTORAGE_Y_LAYOUT + 4* TILE_SIZE){
                if (turn.equals("A") && findPosition()[2] == 2){
                    double rowInStorage = (homeY - ASTORAGE_Y_LAYOUT) / TILE_SIZE;
                    double colInMosaic = ((findPosition()[0]) - AMOSAIC_X_LAYOUT)/TILE_SIZE;
                    move = move + "A" + ((int)rowInStorage) + ((int)colInMosaic);
                }
            }
            //if the draggable tile is from StorageB
            if (homeX > BSTORAGE_X_LAYOUT- 4*TILE_SIZE && homeX < BSTORAGE_X_LAYOUT + TILE_SIZE &&
                    homeY > BSTORAGE_Y_LAYOUT && homeY < BSTORAGE_Y_LAYOUT + 4*TILE_SIZE) {
                if (turn.equals("B") && findPosition()[2] == 5) {
                    double rowInStorage = (yAxis - BSTORAGE_Y_LAYOUT) / TILE_SIZE;
                    double colInMosaic = ((findPosition()[0]) - BMOSAIC_X_LAYOUT)/TILE_SIZE;
                    move = move + "B" +(int)(rowInStorage) + ((int)colInMosaic);
                }
            }
            return move;
        }


        /**
         * set the tile back to last valid position before mouse pressing.
         */
        private void snapToHome(){
            //TODO
            this.setLayoutX(this.layOutX);
            this.setLayoutY(this.layOutY);
        }

        /**
         * the game state should be updated each time when there is a possible tile moving.
         */
        private void updateGameState() {
            gameState = Azul.applyMove(gameState,findMove());
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

    private void addPlayerBoardToRoot() {
        // TODO refactor the code, define layout using playerBoard.
        for (int i = 0; i < PLAYER_NUM; i++) {
            this.playerBoard[i] = new Pane();
            /*  set up player label  */
            this.playerLabel[i] = new Label("Player " + PLAYER_CODE[i] + ": ");
            this.playerLabel[i].setPrefHeight(PLAYER_LABEL_HEIGHT);
            this.playerLabel[i].setPrefWidth(PLAYER_LABEL_WIDTH);
            this.playerLabel[i].setLayoutX(PLAYER_LABEL_LAYOUT_X);
            this.playerLabel[i].setLayoutY(PLAYER_LABEL_LAYOUT_Y);

            /*  set up player scoreField  */
            this.scoreField[i] = new TextField("0");
            this.scoreField[i].setPrefHeight(SCORE_FIELD_HEIGHT);
            this.scoreField[i].setPrefWidth(SCORE_FIELD_WIDTH);
            this.scoreField[i].setLayoutX(SCORE_FIELD_LAYOUT_X);
            this.scoreField[i].setLayoutY(SCORE_FIELD_LAYOUT_Y);
            this.scoreField[i].setDisable(true);

            /*  set up player board  */
            this.playerBoard[i].getChildren().add(this.playerLabel[i]);
            this.playerBoard[i].getChildren().add(this.scoreField[i]);

            this.playerBoard[i].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            this.playerBoard[i].setLayoutX(PLAYER_BOARD_LAYOUT_X[i]);
            this.playerBoard[i].setLayoutY(PLAYER_BOARD_LAYOUT_Y[i]);
            root.getChildren().add(this.playerBoard[i]);
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

        for(Group m:mosaics){
            root.getChildren().add(m);
        }

    }

    //add storage to root
    private void addStorageToRoot(){
        for(int m = 0;m<PLAYER_NUM;m++) {
            this.storages[m] = new Group();
            for (int i = 0; i < Storage.STORAGE_ROW_NUM; i++) {
                for (int j = 0; j <= i; j++) {
                    GTile r = new GTile('a');
                    r.setLayoutX((j - i) * TILE_SIZE);
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

        for(Group s:storages){
            root.getChildren().add(s);
        }

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

        for(Group f:floor){
            root.getChildren().add(f);
        }
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

        root.getChildren().add(center);
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

        for(Group f:factories){
            root.getChildren().add(f);
        }

    }
    private void addBagToRoot(){
        bagLable[0] = new Label("Tile a (Blue): ");
        bagLable[1] = new Label("Tile b (Green): ");
        bagLable[2] = new Label("Tile c (Orange): ");
        bagLable[3] = new Label("Tile d (Purple): ");
        bagLable[4] = new Label("Tile e (Red): ");

        TextField[] t = new TextField[NUMBER_OF_TILETYPE];
        for(int i = 0; i < NUMBER_OF_TILETYPE;i++){
            bagField[i] = new TextField("20");
            bag.getChildren().add(bagField[i]);
            bagField[i].setDisable(true);
            bagField[i].setLayoutX(BAG_TEXT_FIELD_X_LAYOUT);
            bagField[i].setLayoutY(BAG_TEXT_FIELD_Y_LAYOUT+30*i);
            bagField[i].setPrefHeight(BAG_TEXT_FIELD_HEIGHT);
            bagField[i].setPrefWidth(BAG_TEXT_FIELD_WEIGHT);
        }

        for(int i = 0;i < NUMBER_OF_TILETYPE;i++){
            bag.getChildren().add(bagLable[i]);
            bagLable[i].setLayoutX(BAG_LABEL_X_LAYOUT);
            bagLable[i].setLayoutY(BAG_LABEL_Y_LAYOUT+30*i);
        }
        Label b = new Label("Bag");

        b.setLayoutX(BAG_X_LAYOUT);
        b.setLayoutY(BAG_Y_LAYOUT);
        root.getChildren().add(b);
        root.getChildren().add(bag);

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
     * update scores of every players.
     */
    private void updateScoresView() {
        String[] scores = new String[PLAYER_NUM];
        String whoseTurn = Azul.whoseTurn(gameState); // decide whose turn.
        int index = whoseTurn.charAt(0) - 'A';
        for (int i = 0; i < PLAYER_NUM; i++) {
            if (i != index) {
                this.playerBoard[i].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                this.scoreField[i].setScaleX(1.0);
                this.scoreField[i].setScaleY(1.0);
                this.playerLabel[i].setScaleX(1.0);
                this.playerLabel[i].setScaleY(1.0);
            } else {
                this.playerBoard[i].setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), new Insets(-3))));
                this.scoreField[i].setScaleX(0.8);
                this.scoreField[i].setScaleY(0.8);
                this.playerLabel[i].setScaleX(0.8);
                this.playerLabel[i].setScaleY(0.8);
            }
        }
        for (int i = 0; i < scores.length; i++) {
            scores[i] = Azul.splitPlayerState(gameState).get(PLAYER_CODE[i])[0]; // get the scores of players from game state.
            System.out.println("Player " + PLAYER_CODE[i] + ", scores " + scores[i]);
            this.scoreField[i].setText(scores[i]); // set the score to the textField.
        }
    }

    /**
     * update the storage view, according to the current game state.
     */
    private void updateStorageView() {
        // TODO
        String player = Azul.whoseTurn(gameState); // get whose turn
        String[] playerState = Azul.splitPlayerState(gameState).get(player); // get the state of this player.
        String storage = playerState[2]; // get the storage state from the playerState.
        Storage s = new Storage(storage);
        Tile[][] tilesInStorage = s.getTiles(); // get tiles from storage.
        for (int i = 0; i < Storage.STORAGE_ROW_NUM; i++) {
            for (int j = 0; j <= i; j++) {
                if (tilesInStorage[i][j] != null) {
                    DraggableTile gtile = new DraggableTile(tilesInStorage[i][j].getCode().charAt(0)); // create graphic tile.
                    gtile.setLayoutX((j - i) * TILE_SIZE);
                    gtile.setLayoutY(i * TILE_SIZE);
                    this.storages[player.charAt(0) - 'A'].getChildren().add(gtile);
                }
            }
        }
    }

    /**
     * update the center view, according to the current game state.
     */
    private void updateCenterView() {
        // TODO test
        int cnt = 0; // the counter of draggable tiles.
        String center = Azul.splitSharedState(this.gameState)[2]; // split game state.
        System.out.println("Current Center: " + center); // print out center string in terminal.
        Center c = new Center(center);
        ArrayList<Tile> tileInCenter = c.getTiles();
        GTile[] tiles = new GTile[c.getCurrentNum()];
        for (Tile t: tileInCenter) {
            tiles[cnt] = new DraggableTile(t.getCode().charAt(0)); // get the colorChar from tile.
            cnt++; // update the counter.
        }
        cnt = 0; // reset counter
        for (int i = 0; i < Center.CENTER_HEIGHT && cnt < c.getCurrentNum(); i++) {
            for (int j = 0; j < Center.CENTER_WIDTH && cnt < c.getCurrentNum(); j++) {
                tiles[cnt].setLayoutX(j * TILE_SIZE);
                tiles[cnt].setLayoutY(i * TILE_SIZE);
                this.center.getChildren().add(tiles[cnt]);
                cnt++;
            }
        }


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

    private void updateFloorView() {
        HashMap<String,String[]> map = Azul.splitPlayerState(gameState);
        char player = gameState[0].charAt(0);
        String floorString = map.get(String.valueOf(player))[3];
        GTile[] tiles = new GTile[Floor.FLOOR_WIDTH];
        int cnt = 0;
        for (int i = 1; i < floorString.length(); i++) {
            // first character of factory would be a digit, so skip it.
            tiles[cnt] = new DraggableTile(floorString.charAt(i)); // create a draggable tile for each character.
            cnt++;
        }
        for(int i = 0;i < floorString.length()-1;i++){
            tiles[i].setLayoutX(i*TILE_SIZE);
            tiles[i].setLayoutY(0);
            if(player =='A') floor[0].getChildren().add(tiles[i]);
            else floor[1].getChildren().add(tiles[i]);
        }
    }

    private void updateMosaicView() {
        HashMap<String,String[]> map = Azul.splitPlayerState(gameState);
        char player = gameState[0].charAt(0);
        String mosaicString = map.get(String.valueOf(player))[1];
        GTile[] tiles = new GTile[NewMosaic.MOSAIC_WIDTH];
        int cnt = 0;
        for (int i = 1; i < mosaicString.length(); i = i + 3) {
            tiles[cnt] = new DraggableTile(mosaicString.charAt(i));
            int row = mosaicString.charAt(i+1) -'0';
            int column = mosaicString.charAt(i+2) -'0';
            tiles[cnt].setLayoutX((column)*TILE_SIZE);
            tiles[cnt].setLayoutY(row*TILE_SIZE);
            if(player =='A') mosaics[0].getChildren().add(tiles[cnt]);
            else mosaics[1].getChildren().add(tiles[cnt]);
            cnt++;
        }
    }

    private void updateBagView(){
        String bagString = Azul.splitSharedState(gameState)[3];
        for(int i = 0; i<NUMBER_OF_TILETYPE;i++){
            bagField[i].setText(bagString.substring(2*i+1,2*i+3));
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
        updateCenterView(); // update the center view.
        updateStorageView(); // update the storage view.
        updateScoresView(); // update the score view.
    }

    @Override
    public void start(Stage stage) throws Exception {
        //  FIXME Task 12: Implement a basic playable Azul game in JavaFX that only allows tiles to be placed in valid places
        //  FIXME Task 14: Implement a computer opponent so that a human can play your game against the computer.
        stage.setTitle("Azul");
        // [Original Code] Group root = new Group();

        // add player board to root
        addPlayerBoardToRoot();


        //add playerState
        addMosaicToRoot();
        addStorageToRoot();
        addFloorToRoot();

        //add sharedState
        addCenterToRoot();
        addFactoriesToRoot();
        addBagToRoot();

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);


        root.getChildren().add(gTiles);



        root.getChildren().add(controls);

        // setUpHandlers(scene);
        // setUpSoundLoop();
        makeControls();
        makeCompletion();

        newGame();
        //test
        /**
        gameState[0] = "AFCB1616181614D0000000000";
        gameState[1] = "A1Ma00e04c11d22b33S1b22c13a34a1FbeeeeB0MS0c11b12e13d4Ff";
        updateScoresView();
        updateStorageView();
        updateFloorView();
        updateMosaicView();
        updateBagView();
         **/

        stage.setScene(scene);
        stage.show();
    }
}

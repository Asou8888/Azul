package comp1110.ass2.gui;

import comp1110.ass2.member.*;
import gittest.C;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application {
    /* board layout */
    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;

    /* game information  */
    private static final int PLAYER_NUM = 2;
    private static final String[] PLAYER_CODE = new String[]{"A", "B", "C", "D"};
    private static final String[] DEFAULT_PLAYER_NAME = new String[]{"Alice", "Bob", "John", "Cathy"};
    // FIXME
    public static boolean isClick = false;
    public static Tile from;


    /* Members in the game */
    private Center center;
    private Factories factories;
    private Player[] players;

    private Group bagView = new Group();
    private Group playerView = new Group();

    private void createBoard(Group root) {
        // TODO: create the board.
        // Player Area, Center, Factories, Bag information and Discard.
        // TODO: add center(testing)
        this.center = new Center(525, 20);
        center.decode("Caaaabbcf");
        center.removeTiles(TileType.Blue);
        root.getChildren().add(center.getTilesView());

        // TODO: add factories
        this.factories = new Factories("F0abbe2ccdd");
        this.factories.setLocation(1, 20);
        Group[] fs = factories.getFactoriesView();
        for (Group g : fs) {
            root.getChildren().add(g);
        }
    }

    private void createPlayer(Group root) {
        this.players = new Player[PLAYER_NUM];
        for (int i = 0; i < PLAYER_NUM; i++) {
            // TODO: Allow player to enter their name.
            // create player according to their personal name and playercode.
            this.players[i] = new Player(DEFAULT_PLAYER_NAME[i], PLAYER_CODE[i]);
            // TODO: setLocation to each player's board.
            players[i].setLocation(600 * i, 300);
            root.getChildren().add(players[i].getViews());
        }
    }
    /** determine if the storage contains any rows that is full*/
    private boolean isStorageContainFull (Storage storage){
        String a = storage.getCode();
        for(int i = 1;i < a.length();i= i+3){
            if(storage.isRowFull(i))
                return true;
        }
        return false;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //  FIXME Task 12: Implement a basic playable Azul game in JavaFX that only allows tiles to be placed in valid places
        //  FIXME Task 14: Implement a computer opponent so that a human can play your game against the computer.
        stage.setTitle("Azul");
        Group root = new Group();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        // start game
        createBoard(root);
        createPlayer(root);
        stage.setScene(scene);
        stage.show();
    }
}

/**
 * // TODO: NewMosaic
 * this.mosaic = new NewMosaic("Mb00a02a13e42");
 * this.mosaic.setLocation(300, 300);
 * root.getChildren().add(this.mosaic.getMosaicView());
 * <p>
 * // TODO: Floor
 * this.floor = new Floor();
 * this.floor.decode("Faabbe");
 * this.floor.setLocation(100, 600);
 * root.getChildren().add(this.floor.getFloorView());
 * <p>
 * // TODO: Storage
 * this.storage = new Storage();
 * this.storage.decode("S2a13e44a1");
 * this.storage.setLocation(10, 300);
 * root.getChildren().add(this.storage.getStorageView());
 * <p>
 * // (add the player view)
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * // player board
 * stage.setScene(scene);
 * stage.show();
 * }
 * }
 */
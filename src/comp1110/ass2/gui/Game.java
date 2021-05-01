package comp1110.ass2.gui;

import comp1110.ass2.member.Center;
import comp1110.ass2.member.Player;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
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

    /* Members in the game */
    private Center center;
    private Group centerView;
    private Group factoriesView = new Group();
    private Group bagView = new Group();
    private Group playerView = new Group();


    @Override
    public void start(Stage stage) throws Exception {
        //  FIXME Task 12: Implement a basic playable Azul game in JavaFX that only allows tiles to be placed in valid places
        //  FIXME Task 14: Implement a computer opponent so that a human can play your game against the computer.
        stage.setTitle("Azul");
        Group root = new Group();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        // start game
        // (add the shared view)
        // root.getChildren().add(factoriesView);
        // root.getChildren().add(bagView);

        // TODO: add center(testing)
        this.center = new Center(100, 100);
        center.decode("Caaaabbcf");
        center.removeTiles(TileType.Blue);
        root.getChildren().add(center.getTilesView());

        // (add the player view)


        // player board
        stage.setScene(scene);
        stage.show();
    }
}

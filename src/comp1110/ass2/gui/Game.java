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

    /* Members in the game */
    private Center center;
    private Factories factories;
    private NewMosaic mosaic;
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

        // TODO: add factories
        this.factories = new Factories("F0abbe2ccdd");
        this.factories.setLocation(500, 500);
        Group[] fs = factories.getFactoriesView();
        for (Group g: fs) {
            root.getChildren().add(g);
        }

        // TODO: NewMosaic
        this.mosaic = new NewMosaic("Mb00a02a13e42");
        this.mosaic.setLocation(1000, 1000);
        root.getChildren().add(this.mosaic.getMosaicView());



        // (add the player view)


        // player board
        stage.setScene(scene);
        stage.show();
    }
}

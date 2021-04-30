package comp1110.ass2.gui;

import comp1110.ass2.member.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
    /* board layout */
    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;

    /* game information  */
    private static final int PLAYER_NUM = 2;

    @Override
    public void start(Stage stage) throws Exception {
        //  FIXME Task 12: Implement a basic playable Azul game in JavaFX that only allows tiles to be placed in valid places
        //  FIXME Task 14: Implement a computer opponent so that a human can play your game against the computer.
        stage.setTitle("Azul");
        Group root = new Group();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        // start game

        // Initialize players
        String[] name = new String[]{"Tom", "Alice"}; // set default
        Player[] players = new Player[PLAYER_NUM]; // set to 2 player
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(name[i], Character.toString('A' + i));
        }

        // player board


        stage.setScene(scene);
        stage.show();
    }
}

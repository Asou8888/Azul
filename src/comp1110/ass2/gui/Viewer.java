package comp1110.ass2.gui;

import com.sun.jdi.event.StepEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Locale;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField playerTextField;
    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     *              TASK 4
     */
    void displayState(String[] state) {
        /**
         -------- 1-5 sharedState --------
         1. String turnRound
         2. String Factories
         3. String Center
         4. String Bag
         5. String Discard
         --------  6-10 Player A --------
         6. String APLayer  (the whole state of A)
         7. Int Ascore  (score of A)
         8. String Amosaic  (code of mosaic of A)
         9. String Astorage  (code of storage of A)
         10. String Afloor  (code of floor of A)
         -------- 11-15 Player B --------
         11. String BPlayer
         12. int Bscore
         13. String Bmosaic
         14. String Bstorage
         15. String Bfloor
         */
        String sharedState = state[0];
        String playerState = state[1];
        /**
         * 1-5 sharedState
         */
        String turnRound = sharedState.substring(0,1);
        int Fac = 0;
        int Cen = 0;
        int B = 0;
        int Dis = 0;
        for (int i = 0; i < sharedState.length();i++){
            if(sharedState.toCharArray()[i] == 'F'){
                Fac = i;
            }
            if(sharedState.toCharArray()[i] == 'C'){
                Cen = i;

            }
            if(sharedState.toCharArray()[i] == 'B'){
                B = i;
            }
            if(sharedState.toCharArray()[i] == 'D'){
                Dis = i;
            }
        }

        String Factories = sharedState.substring(Fac,Cen);
        String Center = sharedState.substring(Cen,B);
        String Bag = sharedState.substring(B,Dis);
        String Discard = sharedState.substring(Dis);

        /**
         * 6-15 playerState
         */
        int BplayerIndex = 0;
        for(int i = 0; i < playerState.length();i++){
            if(playerState.toCharArray()[i] == 'B'){
                BplayerIndex = i;
            }

        }
        String APlayer = playerState.substring(0,B);
        String BPlayer = playerState.substring(B);

        //player A
        int Ma = 0; //index of mosaic of A
        int Sa = 0; //index of storage of A
        int Fa = 0; //index of floor of A
        for(int i =0;i < APlayer.length();i++){
            if(APlayer.toCharArray()[i] == 'M'){
                Ma = i;
            }
            if(APlayer.toCharArray()[i] == 'S'){
                Sa = i;
            }
            if(APlayer.toCharArray()[i] == 'F'){
                Fa = i;
            }
        }
        int Ascore = Integer.parseInt(APlayer.substring(1,Ma));
        String Amosaic = APlayer.substring(Ma,Sa);
        String Astorage = APlayer.substring(Sa,Fa);
        String Afloor = APlayer.substring(Fa);


        //player B
        int Mb = 0; //index of mosaic of B
        int Sb = 0; //index of storage of B
        int Fb = 0; //index of floor of B
        for(int i =0;i < BPlayer.length();i++){
            if(BPlayer.toCharArray()[i] == 'M'){
                Mb = i;
            }
            if(BPlayer.toCharArray()[i] == 'S'){
                Sb = i;
            }
            if(BPlayer.toCharArray()[i] == 'F'){
                Fb = i;
            }
        }
        int Bscore = Integer.parseInt(BPlayer.substring(1,Mb));
        String Bmosaic = BPlayer.substring(Mb,Sb);
        String Bstorage = BPlayer.substring(Sb,Fb);
        String Bfloor = BPlayer.substring(Fb);



        // FIXME Task 4: implement the simple state viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label playerLabel = new Label("Player State:");
        playerTextField = new TextField();
        playerTextField.setPrefWidth(100);
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(100);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(new String[]{playerTextField.getText(),
                        boardTextField.getText()});
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(playerLabel, playerTextField, boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Azul Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



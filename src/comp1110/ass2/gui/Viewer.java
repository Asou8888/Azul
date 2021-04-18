package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField playerTextField;
    private TextField boardTextField;

    // added by Ruizheng Shen
    private static final int FACTORIES_NUM = 5;
    private final Group sharedField = new Group();
    private final Group displays = new Group();


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
         2. String Factories   2.1-2.5 factory0 factory1 factory2 factory3 factory4 (null if factory is empty)
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

         written by Xiao Xu
         */
        String sharedState = state[0];
        String playerState = state[1];
        /**
         * 1-5 sharedState
         **/
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

        String factory0 = "";
        String factory1 = "";
        String factory2 = "";
        String factory3 = "";
        String factory4 = "";

        String numberOfFactories = "";
        if(Factories.trim() != null && !"".equals(Factories.trim())) {
            for (int i = 0; i < Factories.trim().length(); i++) {
                if (Factories.trim().charAt(i) >= 48 && Factories.trim().charAt(i) <= 57) {
                    numberOfFactories += Factories.trim().charAt(i);
                }
            }
        }
        int[] indexList = new int[numberOfFactories.length()];
        for(int i = 0; i < indexList.length;i++){
            for(int n = 0;n < Factories.length();n++){
                if(Factories.toCharArray()[n] == numberOfFactories.toCharArray()[i]){
                    indexList[i] = n;
                }
            }
        }

        String[] breakFactories = new String[indexList.length];
        for(int i = 0; i < indexList.length-1;i++){
            breakFactories[i] = Factories.substring(indexList[i],indexList[i+1]);
        }
        breakFactories[indexList.length-1] = Factories.substring(indexList[indexList.length-1]);

        for(int i = 0;i<breakFactories.length;i++){
            if(breakFactories[i].toCharArray()[0] == '0'){
                factory0 = breakFactories[i];
            }
            if(breakFactories[i].toCharArray()[0] == '1'){
                factory1 = breakFactories[i];
            }
            if(breakFactories[i].toCharArray()[0] == '2'){
                factory2 = breakFactories[i];
            }
            if(breakFactories[i].toCharArray()[0] == '3'){
                factory3 = breakFactories[i];
            }
            if(breakFactories[i].toCharArray()[0] == '4'){
                factory4 = breakFactories[i];
            }
        }
        if(factory0 == ""){
            factory0 = "null";
        }
        if(factory1 == ""){
            factory1 = "null";
        }
        if(factory2 == ""){
            factory2 = "null";
        }
        if(factory3 == ""){
            factory3 = "null";
        }
        if(factory4 == ""){
            factory4 = "null";
        }



        /**
         * 6-15 playerState
         **/
        int BplayerIndex = 0;
        for (int i = 0; i < playerState.length(); i++) {
            if (playerState.toCharArray()[i] == 'B') {
                BplayerIndex = i;
            }

        }
        String APlayer = playerState.substring(0, B);
        String BPlayer = playerState.substring(B);

        //player A
        int Ma = 0; //index of mosaic of A
        int Sa = 0; //index of storage of A
        int Fa = 0; //index of floor of A
        for (int i = 0; i < APlayer.length(); i++) {
            if (APlayer.toCharArray()[i] == 'M') {
                Ma = i;
            }
            if (APlayer.toCharArray()[i] == 'S') {
                Sa = i;
            }
            if (APlayer.toCharArray()[i] == 'F') {
                Fa = i;
            }
        }
        int Ascore = Integer.parseInt(APlayer.substring(1, Ma));
        String Amosaic = APlayer.substring(Ma, Sa);
        String Astorage = APlayer.substring(Sa, Fa);
        String Afloor = APlayer.substring(Fa);


        //player B
        int Mb = 0; //index of mosaic of B
        int Sb = 0; //index of storage of B
        int Fb = 0; //index of floor of B
        for (int i = 0; i < BPlayer.length(); i++) {
            if (BPlayer.toCharArray()[i] == 'M') {
                Mb = i;
            }
            if (BPlayer.toCharArray()[i] == 'S') {
                Sb = i;
            }
            if (BPlayer.toCharArray()[i] == 'F') {
                Fb = i;
            }
        }
        int Bscore = Integer.parseInt(BPlayer.substring(1, Mb));
        String Bmosaic = BPlayer.substring(Mb, Sb);
        String Bstorage = BPlayer.substring(Sb, Fb);
        String Bfloor = BPlayer.substring(Fb);

            // FIXME Task 4: implement the simple state viewer

            /*  Code written by Ruizheng Shen:
             *     1. Desperate the area for shared field and player field.
             *     2. the simple shared field.
             */
            Label sharedLabel = new Label("shared state: ");
            ArrayList<HBox> sharedLabels = new ArrayList<>();
            TextField[] factories = new TextField[FACTORIES_NUM];
            Label[] facLabels = new Label[FACTORIES_NUM];
            for (int i = 0; i < FACTORIES_NUM; i++) {
                factories[i] = new TextField("factory [" + i + "] code"); // TODO: put the i-th factory code here.
                facLabels[i] = new Label("factory [" + i + "]: ");
            }
            TextField center = new TextField("center code"); // TODO: put the center code here.
            Label centerLabel = new Label("Center: ");
            TextField bag = new TextField("bag code"); // TODO: put the bag code here.
            Label bagLabel = new Label("Bag: ");
            TextField discard = new TextField("discard code"); // TODO: put the discard code here.
            Label discardLabel = new Label("Discard: ");

            // adding horizon box for factories
            for (int i = 0; i < FACTORIES_NUM; i++)
                sharedLabels.add(new HBox(facLabels[i], factories[i]));
            // adding horizon box for center
            sharedLabels.add(new HBox(centerLabel, center));
            // adding horizon box for bag
            sharedLabels.add(new HBox(bagLabel, bag));
            // adding horizon box for discard
            sharedLabels.add(new HBox(discardLabel, discard));

            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.setLayoutX(50);
            vb.setLayoutY(10);
            vb.getChildren().add(sharedLabel);
            for (HBox hr : sharedLabels) {
                vb.getChildren().add(hr);
            }
            sharedField.getChildren().add(vb);
            displays.getChildren().add(sharedField);
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

        // test code by Ruizheng Shen(for testing displayState())
        String[] testStr = new String[] {
            "adada",
            "adadadadad"
        };
        displayState(testStr);
        root.getChildren().add(displays);
        // test code end.

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

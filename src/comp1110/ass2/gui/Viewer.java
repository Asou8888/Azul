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

    /*
     * Some added members:
     *     1. displays: The field displays shared state(factories, centers, bag, discard) and player state(storage, mosaic, etc)
     *     2. sharedField: the field displays shared field(in the 'displays').
     *     3. playerField: the field displays player field(in the 'display').
     */
    private static final int FACTORIES_NUM = 5;
    private final Group displays  = new Group();
    private final Group sharedField = new Group();
    private final Group playerField = new Group();
    private TextField[] factories;
    private TextField center;
    private TextField bag;
    private TextField discard;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     *              TASK 4
     */
    void displayState(String[] state) {
        // FIXME Task 4: implement the simple state viewer

        /*  Code written by Ruizheng Shen:
         *     1. Desperate the area for shared field and player field.
         *     2.
         */
        Label sharedLabel = new Label("shared state: ");
        ArrayList<HBox> sharedLabels = new ArrayList<>();
        this.factories = new TextField[FACTORIES_NUM];
        Label[] facLabels = new Label[FACTORIES_NUM];
        for (int i = 0; i < FACTORIES_NUM; i++) {
            factories[i] = new TextField("factory [" + i + "] code"); // TODO: put the i-th factory code here.
            facLabels[i] = new Label("factory [" + i + "]: ");
        }
        this.center = new TextField("center code"); // TODO: put the center code here.
        Label centerLabel = new Label("Center: ");
        this.bag = new TextField("bag code"); // TODO: put the bag code here.
        Label bagLabel = new Label("Bag: ");
        this.discard = new TextField("discard code"); // TODO: put the discard code here.
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
        for (HBox hr : sharedLabels) {
            vb.getChildren().add(hr);
        }
        displays.getChildren().add(vb);
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

        // test code by Ruizheng Shen
        String[] testStr = new String[] {
            "adada",
            "adadadadad"
        };
        displayState(testStr);
        root.getChildren().add(displays);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

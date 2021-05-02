package comp1110.ass2.gui;

import comp1110.ass2.member.NewMosaic;
import comp1110.ass2.member.Storage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("For Testing");
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 700);

        Storage storage = new Storage();
        storage.decode("S2a13e44a1");
        System.out.println(storage);
        storage.setLocation(100, 100);
        root.getChildren().add(storage.getStorageView());

        NewMosaic mosaic = new NewMosaic();
        mosaic.decode("Mb00a02a13e42");
        System.out.println(mosaic);
        mosaic.setLocation(500, 100);
        root.getChildren().add(mosaic.getMosaicView());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

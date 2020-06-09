package dbscan;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.core.Instances;

import java.io.IOException;

public class DBScanInterface {

    public DBScanInterface(Instances instances) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbscan/sample.fxml"));
        Parent root = loader.load();
        ((DBScanController)loader.getController()).instances = instances;
        primaryStage.setTitle("DBSCAN");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}

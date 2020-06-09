package knn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KnnInterface {

    public KnnInterface(String datapath) throws IOException {
        Stage s =  new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knn/knn.fxml"));
        Parent root = loader.load();
        ((Controller)loader.getController()).instances = datapath;
        s.setTitle("KNN Algorithm");
        s.setScene(new Scene(root, 600, 500));
        s.show();
    }
}

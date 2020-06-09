package application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.core.Instances;

import java.io.IOException;

public class BoxPlot {

    public BoxPlot(){}

    public void plot(String proc, String path){
        Thread t = new Thread(() -> {
            try {
                @SuppressWarnings("unused")
				Process p = Runtime.getRuntime().exec(proc + " plot.py " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public void plot(Instances instances) {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("boxplot.fxml"));

            Stage chartStage = new Stage();
            chartStage.setTitle("Box Plot");
            try {
                chartStage.setScene(new Scene(loader.load(), 500, 500));
            } catch (IOException e) {
                e.printStackTrace();
            }

            BoxPlotController controller = (BoxPlotController) loader.getController();
            controller.plot(instances);

            chartStage.show();
        });
    }
}

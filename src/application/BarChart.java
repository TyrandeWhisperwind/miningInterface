package application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.core.Instances;

import java.io.IOException;

public class BarChart {

    private Instances instances;

    public BarChart(Instances instances){this.instances = instances;}

    public void plot(int index) throws IOException {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("bar_chart.fxml"));

            Stage chartStage = new Stage();
            chartStage.setTitle("Histogram");
            try {
                chartStage.setScene(new Scene(loader.load(), 500, 500));
            } catch (IOException e) {
                e.printStackTrace();
            }

            BarChartController controller = (BarChartController) loader.getController();
            controller.plot(instances, index);

            chartStage.show();
        });
    }
}

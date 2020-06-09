package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import weka.core.*;

import java.net.URL;
import java.util.*;

public class BarChartController implements Initializable {


    @FXML
    private BarChart<String, Double> barChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        barChart.setBarGap(0);
        barChart.setCategoryGap(0);

    }

    public void plot(Instances instances, int index){

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.getData().clear();

        if (instances.attribute(index).isNominal() && !instances.attribute(index).isDate()){
            int[] count = instances.attributeStats(index).nominalCounts;

            for (int i=0; i<count.length; i++) {
                series.getData().add(new XYChart.Data<String, Double>(String.valueOf(i), Double.valueOf(count[i])));
            }
        } else if (instances.attribute(index).isNumeric() && !instances.attribute(index).isDate()){

            ArrayList<Double> vals = new ArrayList<>();
            int[] count = new int[]{0,0,0,0};
            for (double d : instances.attributeToDoubleArray(index)) vals.add(d);

            vals.sort(Double::compareTo);
            final double one_fourth, three_forths, half;
            if (vals.size() % 2 == 0) {
                half = (vals.get(vals.size()/2 - 1) + vals.get(vals.size()/2))/2;
                if ((vals.size()/2) % 2 == 0) {
                    one_fourth = (vals.get(vals.size()/4 - 1) + vals.get(vals.size()/4))/2;
                    three_forths = (vals.get(vals.size()-vals.size()/4 - 1) + vals.get(vals.size()-vals.size()/4))/2;
                } else {
                    one_fourth = vals.get(vals.size()/4);
                    three_forths = vals.get(vals.size()-vals.size()/4);
                }
            } else {
                half = vals.get(vals.size()/2);
                one_fourth = (vals.get(vals.size()/4 -1) + vals.get(vals.size()/4))/2;
                three_forths = (vals.get(vals.size() - vals.size()/4) + vals.get(vals.size() - vals.size()/4 + 1))/2;
            }

            vals.forEach(v -> {
                if(v < one_fourth) count[0]++;
                else if (v < half) count[1]++;
                else if (v < three_forths) count[2]++;
                else count[3]++;
            });

            series.getData().add(new XYChart.Data<>("[" + vals.get(0) + ", " + one_fourth + "[", Double.valueOf(count[0])));
            series.getData().add(new XYChart.Data<>("[" + one_fourth + ", " + half + "[", Double.valueOf(count[1])));
            series.getData().add(new XYChart.Data<>("[" + half + ", " + three_forths + "[", Double.valueOf(count[2])));
            series.getData().add(new XYChart.Data<>("[" + three_forths + ", " + vals.get(vals.size() - 1) + "[", Double.valueOf(count[3])));
        }


        barChart.getData().add(series);
    }
}

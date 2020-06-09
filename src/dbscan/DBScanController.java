package dbscan;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kotlin.Pair;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBScanController implements Initializable {

    @FXML
    private ListView<Point> instanceSet;
    @FXML
    private TextField epsField;
    @FXML
    private TextField populationField;
    @FXML
    private ComboBox<Integer> classeFilter;
    @FXML
    private Label intraLabel;
    @FXML
    private Label interLabel;

    public Instances instances = null;

    public ArrayList<Point> points;

    private ObservableList<Point> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instanceSet.setCellFactory(listView -> {
            return new ListCell<Point>(){
                @Override
                protected void updateItem(Point item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) setText("");
                    else setText(item.toString());
                }
            };
        });

        classeFilter.getSelectionModel().selectFirst();
        classeFilter.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (newValue == null) return;
                if (newValue == 0) instanceSet.setItems(FXCollections.observableArrayList(points));
                else instanceSet.setItems(FXCollections.observableArrayList(
                        points.stream().filter(point -> point.getPointClass() == newValue).collect(Collectors.toList())
                ));
            }
        });
        classeFilter.setCellFactory(view -> new ListCell<Integer>(){
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item == 0) setText("All Classes");
                else if (item != null) setText(item.toString());
            }
        });

        /*try {
            instances = new ConverterUtils.DataSource("/home/imad/M2SII/DM/cpu.arff").getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    private void runClick(ActionEvent event) {
        if (instances == null) return;
        new Thread(() -> {
            DBScan dbScan = new DBScan(instances, Double.valueOf(epsField.getText()), Integer.valueOf(populationField.getText()));
            dbScan.runAlgo(dbScan.getSet());
            /*dbScan.getSet().sort((a1, a2) -> {
                return a1.getPointClass() < a2.getPointClass() ? 1 : 0;
            });*/
            Platform.runLater(() -> classeFilter.getItems().clear());
            for (int i = 0; i <= dbScan.getC(); i++) {
                int fi = i;
                Platform.runLater(()-> classeFilter.getItems().add(fi));
            }
            points = dbScan.getSet();
            Pair<Double, Double> p = inertie(divideSet(points, dbScan.getC()));
            if (p == null) {
                Platform.runLater(()-> intraLabel.setText("NO CLUSTER FOUND"));
            }else Platform.runLater(() -> {
                instanceSet.setItems(FXCollections.observableArrayList(points));
                intraLabel.setText("Intraclass Inertia : " + p.getFirst());
                interLabel.setText("Interclass Inertia : " + p.getSecond());
            });
        }).start();
    }

    private ArrayList<ArrayList<Point>> divideSet(ArrayList<Point> l, int clusters) {
        ArrayList<ArrayList<Point>> list = new ArrayList<>();
        for (int i = 1; i <= clusters; i++) {
            ArrayList<Point> cluster = new ArrayList<>();
            int fi = i;
            l.forEach(point -> {
                if (point.getPointClass() == fi) cluster.add(point);
            });
            list.add(cluster);
        }
        return list;
    }

    private Pair<Double, Double> inertie(ArrayList<ArrayList<Point>> clusters) {
        ArrayList<double[]> gcs = new ArrayList<>();
        if (clusters.size() == 0 || clusters.get(0).size() == 0) return null;
        double[] gcg = new double[clusters.get(0).get(0).getInstance().numAttributes()-1];
        clusters.forEach(cluster -> {
            double[] gc = new double[cluster.get(0).getInstance().numAttributes()-1];
            cluster.forEach(point -> {
                double mean = 0;
                for (int i = 0; i < point.getInstance().numAttributes() - 1; i++) {
                    gc[i] += point.getInstance().value(i);
                }
            });
            for (int i = 0; i < gc.length; i++) {
                gcg[i] += gc[i];
                gc[i] /= cluster.size();
            }
            gcs.add(gc);
        });
        for (int i = 0; i < gcg.length; i++) {
            gcg[i] /= points.size();
        }
        double w = 0;
        for (int i = 0; i < clusters.size(); i++) {
            double ii = 0;
            for(Point point : clusters.get(i)) {
                ii += Math.pow(dist(point.getInstance(), gcs.get(i)), 2);
            }
            w += ii;
        }

        double t = 0;
        for (int i = 0; i < gcs.size(); i++) {
            t += Math.pow(distgc(gcs.get(i), gcg), 2);
        }

        return new Pair<>(w, t);
    }

    private double dist(Instance instance, double[] g) {
        double d = 0;
        for (int i = 0; i < g.length; i++) {
            d += Math.pow(g[i] - instance.value(i), 2);
        }
        return Math.sqrt(d);
    }
    private double distgc(double[] gi, double[] g) {
        double d = 0;
        for (int i = 0; i < g.length; i++) {
            d += Math.pow(g[i] - gi[i], 2);
        }
        return Math.sqrt(d);
    }
}

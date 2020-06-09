package knn;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import kotlin.Pair;
import weka.core.Instance;
import weka.core.Instances;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label vpLabel;
    @FXML
    private Label vnLabel;
    @FXML
    private TextField kField;
    @FXML
    private TextField divideField;
    @FXML
    private ListView<Instance> modelSet;
    @FXML
    private ListView<Instance> instanceSet;
    @FXML
    private ListView<String> classList;

    public String instances = null;
    private Pair<Instances, Instances> p;
    private KNNAlgo algo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algo = new KNNAlgo();

        modelSet.setCellFactory((v) -> {
            return new ListCell<Instance>(){
                @Override
                protected void updateItem(Instance item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) setText(item.toString());
                    else setText("");
                }
            };
        });
        instanceSet.setCellFactory(modelSet.getCellFactory());

    }

    @FXML
    private void splitData(ActionEvent e) {
        p = algo.splitTrainingData(instances, Integer.valueOf(divideField.getText())/100.0, false);
        instanceSet.getItems().clear();
        modelSet.getItems().clear();
        classList.getItems().clear();
        for (int i=0; i<p.getFirst().numInstances(); i++) instanceSet.getItems().add(p.getFirst().instance(i));
        for (int i=0; i<p.getSecond().numInstances(); i++) modelSet.getItems().add(p.getSecond().instance(i));
    }

    @FXML
    private void runKnn(ActionEvent e) {
        if (instances == null || kField.getText().isEmpty() || divideField.getText().isEmpty()) return;
        classList.getItems().clear();
        new Thread(() -> {
            String[] classes = algo.runAlgo(Integer.valueOf(kField.getText()), p.getFirst(), p.getSecond());
            Platform.runLater(() -> classList.getItems().addAll(classes));
            double vp = 0, vn = 0;
            for (int i = 0; i < classes.length; i++) {
                if (classes[i].equals(instanceSet.getItems().get(i).stringValue(instanceSet.getItems().get(i).numAttributes()-1)))
                    vp++;
                else vn++;
            }
            double finalVp = vp/instanceSet.getItems().size();
            double finalVn = vn/instanceSet.getItems().size();

            Platform.runLater(()->{
                vpLabel.setText("Predictions correctes: " + finalVp *100 + "%");
                vnLabel.setText("Predictions faux: " + (finalVn *100) + "%");
            });
        }).start();
    }
}

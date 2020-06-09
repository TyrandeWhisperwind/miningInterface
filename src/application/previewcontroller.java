package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class previewcontroller  implements Initializable  {

	@FXML
	TextArea t;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	

		//chemain du dossier contenant les benchmark
		DataSource a;
		try {
			a=controller.p;
			Instances q = a.getDataSet();
			t.setText(q.toString());

			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
	
}

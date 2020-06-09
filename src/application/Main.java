package application;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
public class Main extends Application  implements Initializable {
	
	
		@Override
		public void initialize(URL arg0, ResourceBundle arg1){}
		
		public static void main(String[] args) {launch(args);}
		//start the stage	
		@Override
	    public void start(Stage primaryStage) throws Exception{
	    	Parent root= FXMLLoader.load(getClass().getResource("HM.fxml"));
	        primaryStage.setTitle("Algerian Weka");
	        primaryStage.setScene(new Scene(root, 1400	,700)); // 3ord et toul
	        primaryStage.show();}
//=====================================================================================================================================
	

}

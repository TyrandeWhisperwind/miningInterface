package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import dbscan.DBScanInterface;
import knn.KnnInterface;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;


import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import weka.core.Instances;
public class controller implements Initializable  {
	
	@FXML
	TableView<leftTable> leftT;
	ObservableList<leftTable> dataLeftTable= FXCollections.observableArrayList();
	@FXML
	TableView<instances> instancesTable;
	ObservableList<instances> datainstances= FXCollections.observableArrayList();	
	@FXML
	TextField dataFile;
	@FXML
	Label attribut;
	@FXML
	Label instance;
	@FXML
	MenuItem normalizationItem;
	static DataSource p;
	static String id;
	static String [] q1 ;
	static String [] q3 ;
	static String [] midan ;
	static String [] md ;
	Instances i;
	
	
	
@SuppressWarnings("unchecked")
@Override
public void initialize(URL location, ResourceBundle resources) {
	
		//leftTable
		TableColumn<leftTable,String> c1 = new TableColumn<leftTable, String>("Id");
		c1.setCellValueFactory(new PropertyValueFactory<leftTable,String>("id"));
		TableColumn<leftTable,String> c2 = new TableColumn<leftTable, String>("Name/Type");
		c2.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableColumn<leftTable,String> c3 = new TableColumn<leftTable, String>("Mean(Mode)");
		c3.setCellValueFactory(new PropertyValueFactory<>("mean"));
		TableColumn<leftTable,String> c4 = new TableColumn<leftTable, String>("Min");
		c4.setCellValueFactory(new PropertyValueFactory<>("min"));
		TableColumn<leftTable,String> c5 = new TableColumn<leftTable, String>("Max");
		c5.setCellValueFactory(new PropertyValueFactory<>("max"));
		TableColumn<leftTable,String> c6 = new TableColumn<leftTable, String>("Q1");
		c6.setCellValueFactory(new PropertyValueFactory<>("q1"));
		TableColumn<leftTable,String> c7 = new TableColumn<leftTable, String>("Q3");
		c7.setCellValueFactory(new PropertyValueFactory<>("q3"));
		TableColumn<leftTable,String> c8 = new TableColumn<leftTable, String>("Median");
		c8.setCellValueFactory(new PropertyValueFactory<>("median"));
		TableColumn<leftTable,String> c9 = new TableColumn<leftTable, String>("Midrange");
		c9.setCellValueFactory(new PropertyValueFactory<>("midrange"));
		TableColumn<leftTable,String> c99 = new TableColumn<leftTable, String>("ModeNum");
		c99.setCellValueFactory(new PropertyValueFactory<>("popularnum"));
		TableColumn<leftTable,String> c10 = new TableColumn<leftTable, String>("Symetric");
		c10.setCellValueFactory(new PropertyValueFactory<>("last"));
		TableColumn<leftTable,String> c111 = new TableColumn<leftTable, String>("Asym.Right");
		c111.setCellValueFactory(new PropertyValueFactory<>("droite"));
		TableColumn<leftTable,String> c122 = new TableColumn<leftTable, String>("Asym.Left");
		c122.setCellValueFactory(new PropertyValueFactory<>("gauche"));
		c1.setMinWidth(5d);
		c2.setMinWidth(325d);
		c3.setMinWidth(125d);
		c4.setMinWidth(125d);
		c5.setMinWidth(125d);
		c6.setMinWidth(125d);
		c7.setMinWidth(125d);
		c8.setMinWidth(125d);
		c9.setMinWidth(125d);
		c99.setMinWidth(125d);
		c10.setMinWidth(100d);
		c111.setMinWidth(100d);
		c122.setMinWidth(100d);

		leftT.getColumns().addAll(c1,c2,c3,c4,c5,c6,c7,c8,c9,c99,c10,c111,c122);
	 	leftT.setItems(dataLeftTable);	 	
	 	//instancesTable
		TableColumn<instances,String> c11 = new TableColumn<instances, String>("Num");
		c11.setCellValueFactory(new PropertyValueFactory<instances,String>("num"));
		TableColumn<instances,String> c22 = new TableColumn<instances, String>("Instance");
		c22.setCellValueFactory(new PropertyValueFactory<>("instance"));
		c11.setMinWidth(1d);
		c22.setMinWidth(1000d);
		instancesTable.getColumns().addAll(c11,c22);
		instancesTable.setItems(datainstances);	
		

}
/***********************************Click event openFile
 * @throws IOException **************************************************************************************/
public void openFile(ActionEvent event)throws  IOException
{

/*File folder = new File("D:\\workEclip\\miningInterface");
    File fList[] = folder.listFiles();

    for (File f : fList)
    {
        if (f.getName().endsWith(".png"))
        	{f.delete();}
    }
*/
	
	

	 	leftT.getItems().clear();
	 	datainstances.clear();
	 	dataLeftTable.clear();
	 	instancesTable.getItems().clear();
		DecimalFormat df = new DecimalFormat("#.##");
		FileChooser fc= new FileChooser();
		//fc.setInitialDirectory(new File ("C:\\Program Files\\Weka-3-8\\data"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("ARFF files (*.arff)", "*.arff"));
		File selectedFile=fc.showOpenDialog(null);
		
		if (selectedFile!=null) 
		{
		
		try {
			p= new DataSource(selectedFile.getAbsolutePath());
			dataFile.setText(selectedFile.getAbsolutePath().toString());
			i = p.getDataSet();
			
			if(check()==true)
			{replace();}
				
			q1 =new String[i.numAttributes()];
			Q1(i,q1);	
			q3 =new String[i.numAttributes()];
			Q3(i,q3);
			midan =new String[i.numAttributes()];
			median(i,midan);
			md =new String[i.numAttributes()];
			midRange(i,md);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			int cpt=0;
			String min="---",max="---",fun ="---",name="---",fung="---",fund="---";
			 double []	vect = new double[i.numInstances()];

			//leftTable
			while(cpt<i.numAttributes())
			{	
				String[] split = i.attribute(cpt).toString().split(" ", 2);
				 
				 if(i.attribute(cpt).isNominal())
					{ 	
						name=i.attribute(cpt).value( (int) (i.meanOrMode(cpt)));
					}
				
				if(i.attribute(cpt).isNumeric())
				{ 	min=Double.toString(i.attributeStats(cpt).numericStats.min);
					max=Double.toString(i.attributeStats(cpt).numericStats.max);
					fun=Boolean.toString(mean_mode_median(cpt, i, i.meanOrMode(cpt)));
					name=df.format(i.meanOrMode(cpt));
					fung=Boolean.toString(asytrimiqueGauche(cpt, i, i.meanOrMode(cpt)));
					fund=Boolean.toString(asytrimiqueDroite(cpt, i, i.meanOrMode(cpt)));;
					
					
				}
				if (i.attribute(cpt).isNumeric()  && !(i.attribute(cpt).isDate()) )
				{
					  
					  for(int comp=0;comp<i.numInstances();comp++)
					  {vect[comp]=i.instance(comp).value(cpt);}			 
					  
				}
				
				 if(i.attribute(cpt).isDate() ) 
				{ min="---";  max="---";fun ="---";name="---"; fung="---";fund="---";}
				 
				 
				dataLeftTable.add(new leftTable(Integer.toString(cpt),split[1],name,min,max,q1[cpt],q3[cpt],midan[cpt],md[cpt],fun,Double.toString(getPopularElement(vect)),fund,fung) ); 
				 min="---";
				 max="---";
				 fun ="---";
				 name="---";
				 fung="---";fund="---";
				 vect = new double[i.numInstances()];
				 cpt++;
			}
			leftT.setItems(dataLeftTable);
		    //instancesTable 
	        cpt=0;
	    	while(cpt<i.numInstances())
			{datainstances.add(new instances(Integer.toString(cpt),i.instance(cpt).toString() )); 
				cpt++;
			}
	    	instancesTable.setItems(datainstances);
	        attribut.setText("Attributes : "+Integer.toString(i.numAttributes()));
	        instance.setText("Instances : "+Integer.toString(i.numInstances()));  

		}
						
}
/*************************************SelectItem Normalization*************************************************************************************/
public void clickNormalize(ActionEvent event)
{
	
		leftT.getItems().clear();
		dataLeftTable.clear();
		datainstances.clear();
		instancesTable.getItems().clear();
		DecimalFormat df = new DecimalFormat("#.##");
		
		normalization(i);
 	
		if(check()==true)
		{replace();}
			
		q1 =new String[i.numAttributes()];
		Q1(i,q1);	
		q3 =new String[i.numAttributes()];
		Q3(i,q3);
		midan =new String[i.numAttributes()];
		median(i,midan);
		md =new String[i.numAttributes()];
		midRange(i,md);
		

		int cpt=0;
		String min="---",max="---",fun ="---",name="---",fung="---",fund="---";
		 double []	vect = new double[i.numInstances()];

		//leftTable
		while(cpt<i.numAttributes())
		{	
			String[] split = i.attribute(cpt).toString().split(" ", 2);
			 
			 if(i.attribute(cpt).isNominal())
				{ 	
					name=i.attribute(cpt).value( (int) (i.meanOrMode(cpt)));
				}
			
			if(i.attribute(cpt).isNumeric())
			{ 	min=Double.toString(i.attributeStats(cpt).numericStats.min);
				max=Double.toString(i.attributeStats(cpt).numericStats.max);
				fun=Boolean.toString(mean_mode_median(cpt, i, i.meanOrMode(cpt)));
				name=df.format(i.meanOrMode(cpt));
				fung=Boolean.toString(asytrimiqueGauche(cpt, i, i.meanOrMode(cpt)));
				fund=Boolean.toString(asytrimiqueDroite(cpt, i, i.meanOrMode(cpt)));;
				
				
			}
			if (i.attribute(cpt).isNumeric()  && !(i.attribute(cpt).isDate()) )
			{
				  
				  for(int comp=0;comp<i.numInstances();comp++)
				  {vect[comp]=i.instance(comp).value(cpt);}			 
				  
			}
			
			 if(i.attribute(cpt).isDate() ) 
			{ min="---";  max="---";fun ="---";name="---"; fung="---";fund="---";}
			 
			 
			dataLeftTable.add(new leftTable(Integer.toString(cpt),split[1],name,min,max,q1[cpt],q3[cpt],midan[cpt],md[cpt],fun,Double.toString(getPopularElement(vect)),fund,fung) ); 
			 min="---";
			 max="---";
			 fun ="---";
			 name="---";
			 fung="---";fund="---";
			 vect = new double[i.numInstances()];
			 cpt++;
		}
		leftT.setItems(dataLeftTable);
	    //instancesTable 
        cpt=0;
    	while(cpt<i.numInstances())
		{datainstances.add(new instances(Integer.toString(cpt),i.instance(cpt).toString() )); 
			cpt++;
		}
    	instancesTable.setItems(datainstances);

}
/***************************************Normalization***********************************************************************************/
void normalization (Instances i)
{ 
	
	int cpt=0,index=0;
	boolean thereIs=false;
	//if there is one attribute numeric then normalize the dataset else say error 
	while (cpt<i.numAttributes()-1 && thereIs== false)
	{ if (i.attribute(cpt).isNumeric()) {thereIs=true; index=cpt;}  cpt++; }
	if(thereIs==true) {
	
	double min=i.attributeStats(index).numericStats.min,max=i.attributeStats(index).numericStats.max;
		cpt=0;
		//get min&max
	
		while(cpt<(i.numAttributes()-1))
			{					
				if(i.attribute(cpt).isNumeric())
				{
					if (min>i.attributeStats(cpt).numericStats.min)
					{min=i.attributeStats(cpt).numericStats.min;}
					if (max<i.attributeStats(cpt).numericStats.max)
					{max=i.attributeStats(cpt).numericStats.max;}
				}
				cpt ++;
			}
	cpt=0;
	int cpt2=0;

		while(cpt<i.numInstances())
			{	cpt2=0;
				while(cpt2<(i.numAttributes()-1))
				{
					if(i.attribute(cpt2).isNumeric())
					{i.instance(cpt).setValue(cpt2, (i.instance(cpt).value(cpt2) - min) / ( max - min ));}cpt2++;
				}
				cpt++;
			}
	}else {System.out.println("no numeric attribut to normalize");}
}
/******************************************Q1**************************************************************************************/
static void  Q1(Instances i, String [] q1)
{
	int cpt=0,pos=0;
	while(cpt<i.numAttributes())
	{    
		if (i.attribute(cpt).isNumeric() && !(i.attribute(cpt).isDate())  )
		{
			if(i.numInstances()%4 !=0)
			{
				pos= (i.numInstances()/4)+1;
				 //sort then get the element  that is situated at "pos"
				i.sort(cpt);
				q1[cpt]=Double.toString(i.instance(pos).value(cpt));
				
			}else{
				pos=i.numInstances()/4;
				 //sort then get the element  that is situated at "pos"
				i.sort(cpt);
				q1[cpt]=Double.toString(i.instance(pos).value(cpt));	
			}	
		}else {q1[cpt]="---"; }
		cpt++;
	}
}
/******************************************Q3**************************************************************************************/
static void  Q3(Instances i, String [] q3)
{
	int cpt=0,pos=0;
	while(cpt<i.numAttributes())
	{    
		if (i.attribute(cpt).isNumeric() && !(i.attribute(cpt).isDate()) )
		{	//odd
			if(i.numInstances()%2 !=0)
			{
				pos= (int) ((i.numInstances()*0.75)+1);
				 //sort then get the element  that is situated in "pos"
				i.sort(cpt);
				q3[cpt]=Double.toString(i.instance(pos).value(cpt));
			//even
			}else{
				pos=(int) (i.numInstances()*0.75);
				 //sort then get the element  that is situated in "pos"
				i.sort(cpt);
				q3[cpt]=Double.toString(i.instance(pos).value(cpt));	
			}	
		}else {q3[cpt]="---"; }
		cpt++;
	}
}
/******************************************MidRange**************************************************************************************/
static void  midRange(Instances i, String [] q3)
{ 	
	int cpt=0;
	DecimalFormat df = new DecimalFormat("#.##");

	while(cpt<i.numAttributes())
	{    
		if (i.attribute(cpt).isNumeric() && !(i.attribute(cpt).isDate()) )
		{
			q3[cpt]=df.format((i.attributeStats(cpt).numericStats.max - i.attributeStats(cpt).numericStats.min)/2);

		}else {q3[cpt]="---"; }
		cpt++;
	}
}
/******************************************Median**************************************************************************************/
static void  median(Instances i, String [] q3)
{
	int pos,pos1,cpt=0;
	DecimalFormat df = new DecimalFormat("#.##");
	
	while(cpt<i.numAttributes())
	{    
			if (i.attribute(cpt).isNumeric() && !(i.attribute(cpt).isDate()) )
			{
				if(i.numInstances()%2 !=0)
				{	//sort then get the element  that is situated in "pos"
					i.sort(cpt);
					q3[cpt]=df.format(i.instance((int) ((i.numInstances()/2))).value(cpt));
				//even
				}else{
					pos=(int) (i.numInstances()/2);
					pos1=(int) ((i.numInstances()/2)+1);
					 //sort then get the element  that is situated in "pos"
					i.sort(cpt);				
					q3[cpt]=df.format( (i.instance(pos).value(cpt) + i.instance(pos1).value(cpt))/2 );	
				}
			}else {q3[cpt]="---"; }
			cpt++;
			}
}
/***********************************************buttonBoxplot
 * @throws Exception ***************************************************************************/
@FXML
public void loadGraph(ActionEvent event) throws Exception 
{	
	
	BoxPlot bp = new BoxPlot();
	bp.plot(p.getDataSet());
	

   
}
/*************************************************selectedItemHisogram**********************************************************************/
@FXML
public void clickItem(MouseEvent event) throws SQLException, IOException
{	//imade
    if (event.getClickCount() == 2) //Checking one click
    {
    	id =leftT.getSelectionModel().getSelectedItem().getNombre();
		BarChart bc = new BarChart(i);
		bc.plot(Integer.valueOf(id));
    }
}
/***************************************************ModeCaseNotNumeric****************************************************************/
static double getPopularElement(double [] a)
{ 
  int count = 1, tempCount;
  double popular = a[0];
  double temp = 0;
  for (int i = 0; i < (a.length - 1); i++)
  {
    temp = a[i];
    tempCount = 0;
    for (int j = 1; j < a.length; j++)
    {
      if (temp == a[j])
        tempCount++;
    }
    if (tempCount > count)
    {
      popular = temp;
      count = tempCount;
    }
  }
  return popular;
}
/************************************************Verification Mean_mode_median**********************************************************/
 static boolean mean_mode_median(int indice, Instances i, double mean)
{
	 int pos,pos1,cpt=0;
	 double med=0;
	 
	 while(cpt<i.numInstances())
		{  
	 			if (i.attribute(indice).isNumeric() && !(i.attribute(indice).isDate()) )
	 			{
	 				if(i.numInstances()%2 !=0)
	 				{	//sort then get the element  that is situated in "pos"
	 					i.sort(indice);
	 					med=i.instance((int) ((i.numInstances()/2))).value(indice);
	 				//even
	 				}else{
	 					pos=(int) (i.numInstances()/2);
	 					pos1=(int) ((i.numInstances()/2)+1);
	 					 //sort then get the element  that is situated in "pos"
	 					i.sort(indice);				
	 					med= (i.instance(pos).value(indice) + i.instance(pos1).value(indice))/2 ;	
	 				}
	 			}else {med=0;}
	 			cpt++;	
		}
	 				 	
	boolean truth=false;

	if (i.attribute(indice).isNumeric()  && !(i.attribute(indice).isDate()) )
	{
		  double [] vect = new double[i.numInstances()];
		  
		  for(cpt=0;cpt<i.numInstances();cpt++)
		  {vect[cpt]=i.instance(cpt).value(indice);}			 
		  double mode = getPopularElement(vect);	  
		  if ( Math.abs(mean-mode)<= 0.25 )
		  { if(Math.abs(mean-med)<=0.25)
		  	{if(Math.abs(mode-med)<=0.25) 
		  	{truth= true;}
		  	}
		  }
	}
	return truth;
}

 /*******************************************Replace missing values*********************************************/
@FXML
public  void replace( )
{
	 int cpt=0,cpt2=0;
	 String nameclass="";
	 while(cpt<i.numInstances())
		{  
		 cpt2=0;
		nameclass=i.instance(cpt).getClass().toString();
		 while(cpt2<i.numAttributes())
			{  
			 if (i.instance(cpt).isMissing(cpt2))  
			 {
				 if (i.attribute(cpt2).isNumeric() && !(i.attribute(cpt2).isDate()) )
					{ i.instance(cpt).setValue(cpt2, getmode_mean(nameclass,i,cpt2));}
				 	else if(i.attribute(cpt2).isNominal() && !(i.attribute(cpt2).isDate()) )
						{i.instance(cpt).setValue(cpt2, i.attribute(cpt2).value( (int) (getmode_mean(nameclass,i,cpt2)))  );}
 
			 }
			 cpt2++;
			}
		 cpt++;
		 } 
}
 /***************************************method tht helps replace********************************************************/
public double getmode_mean(String classname,Instances i,int att)
 {
	 int cpt=0;
	 Instances iClass= new Instances(i);
	 iClass.delete();
	 while(cpt<i.numInstances())
	 {
		 if (i.instance(cpt).getClass().toString().equals(classname))
			 
		 {
			iClass.add(i.instance(cpt));
		 }
		 cpt++;

	 } 
	 return iClass.meanOrMode(att);	 
 }
/******************************************Click replace event************************************************/
public void clickReplace(ActionEvent event)
{
		int cpt=0;
		datainstances.clear();
		instancesTable.getItems().clear();
 	
 	replace();
 	cpt=0;
	while(cpt<i.numInstances())
	{	datainstances.add(new instances(Integer.toString(cpt),i.instance(cpt).toString() )); 
		cpt++;
	}
	instancesTable.setItems(datainstances);

}
/*******************************check if dataset has missing values****************************************************************/
public boolean check() 
{ 
	int cpt=0,cpt2=0;
	boolean checked=false;
	while(cpt<i.numInstances())
	{  
	 cpt2=0;
	 while(cpt2<i.numAttributes() && checked==false)
		{  
		 if (i.instance(cpt).isMissing(cpt2))  
		 {
			checked=true;

		 }
		 cpt2++;
		}
	 cpt++;
	 } 
return checked;
}
/************************************Show DataSet without replacing*****************************************************************/
@FXML
public void preview(ActionEvent event) throws IOException 
{ 
    	//retour button
    	Parent home= FXMLLoader.load(getClass().getResource("preview.fxml"));
    	Scene homeScene = new Scene (home, 800	, 500);
    	Stage app=new Stage();
    	app.setScene(homeScene);
    	app.show();
}
/*******************************************AsymtriqueGauche***************************************************/
public boolean asytrimiqueGauche(int indice, Instances i, double mean) 
{ 
	 int pos,pos1,cpt=0;
	 double med=0;
	 
	 while(cpt<i.numInstances())
		{  
	 			if (i.attribute(indice).isNumeric() && !(i.attribute(indice).isDate()) )
	 			{
	 				if(i.numInstances()%2 !=0)
	 				{	//sort then get the element  that is situated in "pos"
	 					i.sort(indice);
	 					med=i.instance((int) ((i.numInstances()/2))).value(indice);
	 				//even
	 				}else{
	 					pos=(int) (i.numInstances()/2);
	 					pos1=(int) ((i.numInstances()/2)+1);
	 					 //sort then get the element  that is situated in "pos"
	 					i.sort(indice);				
	 					med= (i.instance(pos).value(indice) + i.instance(pos1).value(indice))/2 ;	
	 				}
	 			}else {med=0;}
	 			cpt++;	
		}
	 				 	
	boolean truth=false;

	if (i.attribute(indice).isNumeric()  && !(i.attribute(indice).isDate()) )
	{
		  double [] vect = new double[i.numInstances()];
		  
		  for(cpt=0;cpt<i.numInstances();cpt++)
		  {vect[cpt]=i.instance(cpt).value(indice);}			 
		  double mode = getPopularElement(vect);	  
		  if ( (mean<med) ) {if ((med<mode)) {truth=true;}}
	}
	return truth;
    	
}

/*******************************************AsymtriqueDauche***************************************************/
public boolean asytrimiqueDroite(int indice, Instances i, double mean) 
{ 
	 int pos,pos1,cpt=0;
	 double med=0;
	 
	 while(cpt<i.numInstances())
		{  
	 			if (i.attribute(indice).isNumeric() && !(i.attribute(indice).isDate()) )
	 			{
	 				if(i.numInstances()%2 !=0)
	 				{	//sort then get the element  that is situated in "pos"
	 					i.sort(indice);
	 					med=i.instance((int) ((i.numInstances()/2))).value(indice);
	 				//even
	 				}else{
	 					pos=(int) (i.numInstances()/2);
	 					pos1=(int) ((i.numInstances()/2)+1);
	 					 //sort then get the element  that is situated in "pos"
	 					i.sort(indice);				
	 					med= (i.instance(pos).value(indice) + i.instance(pos1).value(indice))/2 ;	
	 				}
	 			}else {med=0;}
	 			cpt++;	
		}
	 				 	
	boolean truth=false;

	if (i.attribute(indice).isNumeric()  && !(i.attribute(indice).isDate()) )
	{
		  double [] vect = new double[i.numInstances()];
		  
		  for(cpt=0;cpt<i.numInstances();cpt++)
		  {vect[cpt]=i.instance(cpt).value(indice);}			 
		  double mode = getPopularElement(vect);	  
		  if ( (mean>med) ){if ((med>mode)) {truth=true;}}
	}
	return truth;
    	
}
/*********************************************************************************************************************************************/
@FXML
public void AprioriItem(ActionEvent event) throws IOException 
{ 
    	//retour button
    	Parent home= FXMLLoader.load(getClass().getResource("apriori.fxml"));
    	Scene homeScene = new Scene (home, 800	, 500);
    	Stage app=new Stage();
    	app.setScene(homeScene);
    	app.show();
}
/**KNN Algorithm******************************************************************************************************************************/
@FXML
public void runKNN(ActionEvent event) throws Exception {
	ConverterUtils.DataSink.write("./tmp.arff", i);
	KnnInterface knnInterface = new KnnInterface("./tmp.arff");
}
/*********************************************************************************************************************************************/
@FXML
public void runDBSAN(ActionEvent event) throws IOException {
    new DBScanInterface(new Instances(i));
}

/*********************************************************************************************************************************************/
@SuppressWarnings("resource")
@FXML
public void save(ActionEvent event) throws IOException 
{ 
	
	 BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\\\workEclip\\\\miningInterface\\\\copieData.txt"));
     writer.write(i.toString());// instance i to a file txt named copieData
     writer.flush();
     writer.close();     
     
     try {
    	 BufferedReader reader= new BufferedReader(new FileReader("D:\\workEclip\\miningInterface\\copieData.txt"));
  
     PrintWriter writer11 = new PrintWriter("C:\\Users\\HP\\Desktop\\D.Mining\\Tips\\savedData2.txt", "UTF-8");
     int shitt=0;
     String aLine = null;
     while( ( aLine = reader.readLine() ) != null ) {
         // Check each line for the string, and write if it doesn't have it:
         if( !aLine.startsWith("@") ) {
             writer11.println( aLine );
             shitt+=1;
         }
     }
     shitt=shitt-2;
     reader.close();
     writer11.flush();
     writer11.close(); 
     
	 BufferedReader reader1= new BufferedReader(new FileReader("C:\\\\Users\\\\HP\\\\Desktop\\\\D.Mining\\\\Tips\\\\savedData2.txt"));
     PrintWriter writer1 = new PrintWriter("C:\\Users\\HP\\Desktop\\D.Mining\\Tips\\savedData.txt", "UTF-8");
     String joined2 ;
     reader1.readLine();
     reader1.readLine();
     for(int crap=0;crap<shitt;crap++ )//write it in a proper way in another file txt which ill use to extract patterns
     {
    	
     	String b[]= reader1.readLine().split(",");
     	for(int ii=0;ii<b.length;ii++)
     	{ b[ii]=   i.attribute(ii).name()+"__"+b[ii]; }
     	joined2 = String.join(" ", b);
     	writer1.println(joined2);
     }
    

     writer1.close();
     reader1.close();
     } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
}

}
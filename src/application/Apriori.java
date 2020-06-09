package application;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class Apriori   implements Initializable {
	
	@FXML
	TextField t2;
	@FXML
    TableView<elementApriori > table = new TableView<>();
    @FXML
    TextArea tt;
    @FXML
    TableView<items > table2 = new TableView<>();
    @FXML
    Label l;
    @FXML
	TextField t3;
    @FXML
    TableView<rules > table3 = new TableView<>();
    
	ObservableList<elementApriori > Dtable1= FXCollections.observableArrayList();
	ObservableList<items > Dtable2= FXCollections.observableArrayList();
	ObservableList<rules > Dtable3= FXCollections.observableArrayList();


	int NOfT =0;
	ArrayList<ArrayList<String>> transactions = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> _transactions = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> PSWM = new ArrayList<ArrayList<String>>();
	final long startTime = 0;

/*************************************************************************************************************************************/
	public ArrayList<String> GUI (ArrayList<ArrayList<String>> data) 
	{
		ArrayList<String> toReturn = new ArrayList<String>();
		for (ArrayList<String> transaction : data) 
		{for (String item : transaction) {if (!toReturn.contains(item)) toReturn.add(item);}}

		Collections.sort(toReturn);
		return toReturn;
	}
/*************************************************************************************************************************************/
	public ArrayList<ArrayList<String>> getItemSets (ArrayList<String> items, int number) 
	{
		if (number == 1) 
		{
			ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
			for (String item : items) 
			{	ArrayList<String> aList = new ArrayList<String>();
				aList.add(item);
				toReturn.add(aList);
			}
			return toReturn;
		}else{	int size = items.size();
				ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < size; i++) 
			{	
				ArrayList<String> _items = new ArrayList<String>();
				for (String item : items) 
				{_items.add(item);}
				String thisItem = items.get(i);
				for (int j = 0; j <= i; j++) 
				{_items.remove(0);}
				ArrayList<ArrayList<String>> PB = getItemSets(_items, number - 1);
				for (ArrayList<String> aList : PB) 
				{	aList.add(thisItem);
					Collections.sort(aList);
					toReturn.add(aList);
				}

			}
			return toReturn;

		}
	}
/*************************************************************************************************************************************/
	public boolean existsInTransaction (ArrayList<String> items, ArrayList<String> transaction) {
		for (String item : items) 
		{if (!transaction.contains(item)) return false;}
		return true;
	}
/*************************************************************************************************************************************/
public ArrayList<ArrayList<String>> GSW (ArrayList<ArrayList<String>> itemSets, ArrayList<Integer> count, int minSupportCount) 
	{
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < count.size(); i++) 
		{int c = count.get(i);
		if (c >= minSupportCount)
		{toReturn.add(itemSets.get(i));}
		}
		return toReturn;	
	}
/*************************************************************************************************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		TableColumn<elementApriori ,String> c1 = new TableColumn<elementApriori ,String>("Id");
		c1.setCellValueFactory(new PropertyValueFactory<elementApriori ,String>("num"));
		TableColumn<elementApriori ,String> c2= new TableColumn< elementApriori ,String>("Itemset");
		c2.setCellValueFactory(new PropertyValueFactory<elementApriori ,String>("instance"));
		c1.setMinWidth(1d);
		c2.setMinWidth(1000d);
		table.getColumns().addAll(c1,c2);
	 	table.setItems(Dtable1);	
	 	
	 	
	 	TableColumn<items ,String> c11 = new TableColumn<items ,String>("Id");
		c11.setCellValueFactory(new PropertyValueFactory<items ,String>("num"));
		TableColumn<items,String> c22= new TableColumn<items ,String>("Patterns");
		c22.setCellValueFactory(new PropertyValueFactory<items ,String>("instance"));
		c11.setMinWidth(1d);
		c22.setMinWidth(1000d);
		table2.getColumns().addAll(c11,c22);
	 	table2.setItems(Dtable2);	
	 	
	 	
		TableColumn<rules ,String> c111 = new TableColumn<rules ,String>("Id");
		c111.setCellValueFactory(new PropertyValueFactory<rules ,String>("num"));	
		TableColumn<rules,String> c222= new TableColumn<rules ,String>("Rule");
		c222.setCellValueFactory(new PropertyValueFactory<rules ,String>("instance"));
		c111.setMinWidth(1d);
		c222.setMinWidth(1000d);
		table3.getColumns().addAll(c111,c222);
	 	table3.setItems(Dtable3);	
	}
/*************************************************************************************************************************************/
	@FXML
	public void open(ActionEvent event)throws  IOException{
		 	NOfT =0;
		 	transactions = new ArrayList<ArrayList<String>>();
			_transactions = new ArrayList<ArrayList<String>>();
			PSWM = new ArrayList<ArrayList<String>>();
		table.getItems().clear();
	 	Dtable1.clear();
	 	l.setText("Runing Time:");
	 	
			//get number of lines
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("C:\\Users\\HP\\Desktop\\D.Mining\\Tips\\savedData.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int lines = 0;
			try {
				while (reader.readLine() != null) lines++;
				reader.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Open the file
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream("C:\\\\Users\\\\HP\\\\Desktop\\\\D.Mining\\\\Tips\\\\savedData.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine = null;
			NOfT = lines;
			for (int i = 0; i < NOfT; i++) {
				try {
					strLine = br.readLine();
					Dtable1.add(new elementApriori ( Integer.toString(i),strLine));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<String> transaction = new ArrayList<String>();
				String str = strLine;//put the line here
				String arr[] = str.split(" ");
				for (int j = 0; j < arr.length; j++) transaction.add(arr[j]);
				transactions.add(transaction);
				_transactions.add(transaction);
			}
			table.setItems(Dtable1);
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
/*************************************************************************************************************************************/	
	public void search(ActionEvent event)throws  IOException
	{				   		
		ArrayList<String> ruless= new ArrayList<String>();
		table2.getItems().clear();
		table3.getItems().clear();
		Dtable3.clear();
		Dtable2.clear();
	 	table3.setItems(Dtable3);	
	 	table2.setItems(Dtable2);	
	 	l.setText("Runing Time:");
	 	PSWM.clear();
	 
		final long startTime = System.nanoTime();
		int minSupportCount = Integer.parseInt(t2.getText() );
		ArrayList<String> items = GUI(transactions);
		int x = 0;
		while (true) {
			x++;
			ArrayList<Integer> supportCountList = new ArrayList<Integer>();
			ArrayList<ArrayList<String>> itemSets = getItemSets(items, x);
			for (ArrayList<String> itemSet : itemSets) 
			{	int count = 0;
				for (ArrayList<String> transaction : transactions) 
				{if (existsInTransaction(itemSet, transaction)) count++;}
				supportCountList.add(count);
			}
			ArrayList<ArrayList<String>> ISWMC = GSW(itemSets, supportCountList, minSupportCount);
			if (ISWMC.size() == 0) 
			{
				for (int i = 0; i < PSWM.size(); i++) 
				{
					ArrayList <String> elemnt=PSWM.get(i);
			   		Dtable2.add(new items ( Integer.toString(i),elemnt.toString()));
			   		getAllSubsetsRules(elemnt,ruless);
			   		
				}

				break;
			}
			items = GUI(ISWMC);
			PSWM = ISWMC;
		}
		Iterator<String> itr=ruless.iterator();
		int iii=0;
        while(itr.hasNext())
        {
        	
	   		Dtable3.add(new rules (Integer.toString(iii),itr.next()));
	   		iii=iii+1;
        }
       
        table2.setItems(Dtable2);	
        table3.setItems(Dtable3);	
        
		final long duration = System.nanoTime() - startTime;
		l.setText(l.getText()+ Long.toString(duration / 1000000000)+" seconds");	
	 
		
	}
	
/*******************************************************************************************************************************************/
private  boolean thereIs(List<String> elm, List<String> elm2) {
	
	boolean there= false;
	int i = 0;
	
	while( i < elm.size() && there == false) 
    { 	String getElm=elm.get(i);
		for (int j= 0; j < elm2.size(); j++) 
	    {  if(elm2.contains( getElm)){there=true; break;} }
		 i++;
    }
	return there;
}
/*******************************************************************************************************************************************/
public  void getAllSubsetsRules(ArrayList<String> input,ArrayList<String> rules) {
		double min_conf=Double.parseDouble(t3.getText());
    int allMasks = 1 << input.size();
    List<List<String>> output = new ArrayList<List<String>>();

    for(int i=0;i<allMasks;i++) {
        List<String> sub = new ArrayList<String>();
        for(int j=0;j<input.size();j++) {
            if((i & (1 << j)) > 0) {
                sub.add(input.get(j));
            }
        }
        output.add(sub);
    }
    output.remove(0);
    
    for (int i = 0; i < output.size(); i++) 
    { 
    	List<String> elm=output.get(i);
    	
    	for(int k = i+1;k<output.size() ; k++)
    	{	
	    	List<String> elm2=output.get(k);

    	if(!thereIs(elm,elm2))
    	   {
    		 double min_fond1= (getRulesConfident2(elm,elm2) / getRulesConfident1(elm));
    		 double min_fond2=(getRulesConfident2(elm,elm2) / getRulesConfident1(elm2));
    		
    		if ( min_fond1 >= min_conf)
    		{ rules.add(elm.toString()+"->"+elm2.toString());}
    		if (  min_fond2 >= min_conf )
    		{ rules.add(elm2.toString()+"->"+elm.toString());}
    	   
    	   }
    		
    	}
    }
}
/*****************************************************************************************************************************************/
public  double getRulesConfident2(List<String> elm,List<String> elm2 )
{ 	double cpt=0;
	for (int i = 0; i < transactions.size(); i++) 
	{	ArrayList <String> elemnt=transactions.get(i);	
		if (elemnt.containsAll(elm))
		{
			if (elemnt.containsAll(elm2))
				{cpt = cpt+1;}
		}
	}
	return cpt;
}
/*******************************************************************************************************************************************/
public  double getRulesConfident1(List<String> elm )
{ 	double cpt=0;
	for (int i = 0; i < transactions.size(); i++) 
	{	ArrayList <String> elemnt=transactions.get(i);	
		if (elemnt.containsAll(elm))
		{cpt = cpt+1;}
	}
	return cpt;
}
}

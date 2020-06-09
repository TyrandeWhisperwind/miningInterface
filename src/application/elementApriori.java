package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class  elementApriori {
    private StringProperty num = new SimpleStringProperty();
    private StringProperty instance = new SimpleStringProperty();
   


    public StringProperty numProperty() {return num;};
    public StringProperty instanceProperty() {return instance;};
 
    public elementApriori (String num, String instance) {
        this.num.set(num);
        this.instance.set(instance);
    }

    public String getinstancebre() {return num.get();}
    public String getApellnumo() {return instance.get();}



}

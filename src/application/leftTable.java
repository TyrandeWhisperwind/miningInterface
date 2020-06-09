package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class leftTable {
    private StringProperty id = new SimpleStringProperty();
    private StringProperty nom = new SimpleStringProperty();
    private StringProperty mean = new SimpleStringProperty();
    private StringProperty min = new SimpleStringProperty();
    private StringProperty max = new SimpleStringProperty();
    private StringProperty q1= new SimpleStringProperty();
    private StringProperty q3= new SimpleStringProperty();
    private StringProperty median = new SimpleStringProperty();
    private StringProperty midrange= new SimpleStringProperty();
    private StringProperty last= new SimpleStringProperty();
    private StringProperty popularnum= new SimpleStringProperty();

    private StringProperty droite= new SimpleStringProperty();
    private StringProperty gauche= new SimpleStringProperty();


    public StringProperty idProperty() {return id;};
    public StringProperty nomProperty() {return nom;};
    public StringProperty meanProperty() {return mean;};
    public StringProperty minProperty() {return min;};
    public StringProperty maxProperty() {return max;};
    public StringProperty q1Property() {return q1;};
    public StringProperty q3Property() {return q3;};
    public StringProperty medianProperty() {return median;};
    public StringProperty midrangeProperty() {return midrange;};
    public StringProperty lastProperty() {return last;};
    public StringProperty popularnumProperty() {return popularnum;};
    public StringProperty droiteProperty() {return droite;};
    public StringProperty gaucheProperty() {return gauche;};



 
    public leftTable(String id, String nom,String mean,String min,String max,String q1,String q3,String median,String midrange,String last,String popularnum,String droite,String gauche) {
        this.id.set(id);
        this.nom.set(nom);
        this.mean.set(mean);
        this.min.set(min);
        this.max.set(max);
        this.q1.set(q1);
        this.q3.set(q3);
        this.median.set(median);
        this.midrange.set(midrange);
        this.last.set(last);
        this.popularnum.set(popularnum);
        this.droite.set(droite);
        this.gauche.set(gauche);



    }

    public String getNombre() {return id.get();}
    public String getApellido() {return nom.get();}
    public String getmean() {return mean.get();}
    public String getmin() {return min.get();}
    public String getmax() {return max.get();}
    public String getq1() {return q1.get();}
    public String getq3() {return q3.get();}
    public String getmedian() {return median.get();}
    public String getmidrange() {return midrange.get();}
    public String getlast() {return last.get();}
    public String getpopularnum (){return popularnum.get();}
    public String getdroite() {return droite.get();}
    public String getgauche (){return gauche.get();}



    




}

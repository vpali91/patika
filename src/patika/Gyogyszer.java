package patika;

import javafx.beans.property.SimpleStringProperty;


public class Gyogyszer {
    
    private final SimpleStringProperty gyNev;
    private final SimpleStringProperty mennyiseg;
    private final SimpleStringProperty hasznalat;
    private final SimpleStringProperty ellenJav;
    private final SimpleStringProperty szavatossag;
    private final SimpleStringProperty id;
    
    public Gyogyszer() {
        this.gyNev = new SimpleStringProperty("");
        this.mennyiseg = new SimpleStringProperty("");
        this.hasznalat = new SimpleStringProperty("");
        this.ellenJav = new SimpleStringProperty("");
        this.szavatossag = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }
 
    public Gyogyszer(String gNev, String menny, String haszn, String ellen, String date) {
        this.gyNev = new SimpleStringProperty(gNev);
        this.mennyiseg = new SimpleStringProperty(menny);
        this.hasznalat = new SimpleStringProperty(haszn);
        this.ellenJav = new SimpleStringProperty(ellen);
        this.szavatossag = new SimpleStringProperty(date);
        this.id = new SimpleStringProperty("");
    }
    
    public Gyogyszer(Integer id, String gNev, String menny, String haszn, String ellen, String date) {
        this.gyNev = new SimpleStringProperty(gNev);
        this.mennyiseg = new SimpleStringProperty(menny);
        this.hasznalat = new SimpleStringProperty(haszn);
        this.ellenJav = new SimpleStringProperty(ellen);
        this.szavatossag = new SimpleStringProperty(date);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }
    
    public String getGyogyszer() {
        return gyNev.get();
    }
    public void setGyogyszer(String g) {
        gyNev.set(g);
    }
        
    public String getMennyiseg() {
        return mennyiseg.get();
    }
    public void setMennyiseg(String menny) {
        mennyiseg.set(menny);
    }
    
    public String getHasznalat() {
        return hasznalat.get();
    }
    public void setHasznalat(String haszn) {
        hasznalat.set(haszn);
    }
    public String getEllenJav() {
        return ellenJav.get();
    }
    public void setEllenJav(String ellen) {
        ellenJav.set(ellen);
    }
            public String getSzavatossag() {
        return szavatossag.get();
    }
    public void setSzavatossag(String sz) {
        szavatossag.set(sz);
    }
    
    public void setMennyiseg1(){
        int a=1;
        int szav;
        String k;
        szav= Integer.parseInt(mennyiseg.getValue());
        szav=szav-a;
        mennyiseg.set(""+szav);
    }
    
    public String getId(){
        return id.get();
    }
    
    public void setId(String fId){
        id.set(fId);
    }
    
}
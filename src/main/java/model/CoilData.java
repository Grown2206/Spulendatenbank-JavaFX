package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CoilData {

    // --- Allgemeine Felder ---
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty lfdNr = new SimpleIntegerProperty();
    private final SimpleStringProperty typ = new SimpleStringProperty();
    private final SimpleStringProperty artikelnummerSpule = new SimpleStringProperty();
    private final SimpleStringProperty zeichnungsnummer = new SimpleStringProperty();
    private final SimpleStringProperty materialbezeichnung = new SimpleStringProperty();
    private final SimpleDoubleProperty durchlassbreiteDB = new SimpleDoubleProperty();
    private final SimpleDoubleProperty durchlasshoeheDH = new SimpleDoubleProperty();
    private final SimpleDoubleProperty spulenlaenge = new SimpleDoubleProperty();
    private final SimpleStringProperty artikelnummerSchacht = new SimpleStringProperty();
    private final SimpleStringProperty bemerkung = new SimpleStringProperty();
    private final SimpleStringProperty empfaengerSkizzePath = new SimpleStringProperty(); // NEU
    private final SimpleStringProperty senderSkizzePath = new SimpleStringProperty(); // NEU

    // --- Empfängerwicklung Felder ---
    private final SimpleDoubleProperty el1_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty ew1_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty el2_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty ew2_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty el3_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty ew3_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty el4_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty ew4_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty el5_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty ew5_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty el6_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty ew6_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty el7_mm = new SimpleDoubleProperty();
    // private final SimpleIntegerProperty ew7_grad = new SimpleIntegerProperty(); // Entfernt
    private final SimpleDoubleProperty gesamtlaengeEmpfaenger = new SimpleDoubleProperty();

    // --- Senderwicklung Felder ---
    private final SimpleDoubleProperty sl1_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw1_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl2_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw2_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl3_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw3_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl4_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw4_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl5_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw5_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl6_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw6_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl7_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw7_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl8_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw8_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty sl9_mm = new SimpleDoubleProperty();
    private final SimpleIntegerProperty sw9_grad = new SimpleIntegerProperty();
    private final SimpleDoubleProperty gesamtlaengeSender = new SimpleDoubleProperty();

    // --- Properties für TableView ---
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty lfdNrProperty() { return lfdNr; }
    public SimpleStringProperty typProperty() { return typ; }
    public SimpleStringProperty artikelnummerSpuleProperty() { return artikelnummerSpule; }
    public SimpleStringProperty zeichnungsnummerProperty() { return zeichnungsnummer; }
    public SimpleStringProperty materialbezeichnungProperty() { return materialbezeichnung; }
    public SimpleDoubleProperty durchlassbreiteDBProperty() { return durchlassbreiteDB; }
    public SimpleDoubleProperty durchlasshoeheDHProperty() { return durchlasshoeheDH; }

    // --- Normale Getter/Setter für alle Felder ---
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public int getLfdNr() { return lfdNr.get(); }
    public void setLfdNr(int lfdNr) { this.lfdNr.set(lfdNr); }
    public String getTyp() { return typ.get(); }
    public void setTyp(String typ) { this.typ.set(typ); }
    public String getArtikelnummerSpule() { return artikelnummerSpule.get(); }
    public void setArtikelnummerSpule(String artikelnummerSpule) { this.artikelnummerSpule.set(artikelnummerSpule); }
    public String getZeichnungsnummer() { return zeichnungsnummer.get(); }
    public void setZeichnungsnummer(String zeichnungsnummer) { this.zeichnungsnummer.set(zeichnungsnummer); }
    public String getMaterialbezeichnung() { return materialbezeichnung.get(); }
    public void setMaterialbezeichnung(String materialbezeichnung) { this.materialbezeichnung.set(materialbezeichnung); }
    public double getDurchlassbreiteDB() { return durchlassbreiteDB.get(); }
    public void setDurchlassbreiteDB(double durchlassbreiteDB) { this.durchlassbreiteDB.set(durchlassbreiteDB); }
    public double getDurchlasshoeheDH() { return durchlasshoeheDH.get(); }
    public void setDurchlasshoeheDH(double durchlasshoeheDH) { this.durchlasshoeheDH.set(durchlasshoeheDH); }
    public double getSpulenlaenge() { return spulenlaenge.get(); }
    public void setSpulenlaenge(double spulenlaenge) { this.spulenlaenge.set(spulenlaenge); }
    public String getArtikelnummerSchacht() { return artikelnummerSchacht.get(); }
    public void setArtikelnummerSchacht(String artikelnummerSchacht) { this.artikelnummerSchacht.set(artikelnummerSchacht); }
    public String getBemerkung() { return bemerkung.get(); }
    public void setBemerkung(String bemerkung) { this.bemerkung.set(bemerkung); }
    public String getEmpfaengerSkizzePath() { return empfaengerSkizzePath.get(); }
    public void setEmpfaengerSkizzePath(String path) { this.empfaengerSkizzePath.set(path); }
    public String getSenderSkizzePath() { return senderSkizzePath.get(); }
    public void setSenderSkizzePath(String path) { this.senderSkizzePath.set(path); }

    // Getter/Setter für Empfängerwicklung
    public double getEl1_mm() { return el1_mm.get(); }
    public void setEl1_mm(double value) { this.el1_mm.set(value); }
    public int getEw1_grad() { return ew1_grad.get(); }
    public void setEw1_grad(int value) { this.ew1_grad.set(value); }
    public double getEl2_mm() { return el2_mm.get(); }
    public void setEl2_mm(double value) { this.el2_mm.set(value); }
    public int getEw2_grad() { return ew2_grad.get(); }
    public void setEw2_grad(int value) { this.ew2_grad.set(value); }
    public double getEl3_mm() { return el3_mm.get(); }
    public void setEl3_mm(double value) { this.el3_mm.set(value); }
    public int getEw3_grad() { return ew3_grad.get(); }
    public void setEw3_grad(int value) { this.ew3_grad.set(value); }
    public double getEl4_mm() { return el4_mm.get(); }
    public void setEl4_mm(double value) { this.el4_mm.set(value); }
    public int getEw4_grad() { return ew4_grad.get(); }
    public void setEw4_grad(int value) { this.ew4_grad.set(value); }
    public double getEl5_mm() { return el5_mm.get(); }
    public void setEl5_mm(double value) { this.el5_mm.set(value); }
    public int getEw5_grad() { return ew5_grad.get(); }
    public void setEw5_grad(int value) { this.ew5_grad.set(value); }
    public double getEl6_mm() { return el6_mm.get(); }
    public void setEl6_mm(double value) { this.el6_mm.set(value); }
    public int getEw6_grad() { return ew6_grad.get(); }
    public void setEw6_grad(int value) { this.ew6_grad.set(value); }
    public double getEl7_mm() { return el7_mm.get(); }
    public void setEl7_mm(double value) { this.el7_mm.set(value); }
    // public int getEw7_grad() { return ew7_grad.get(); } // Entfernt
    // public void setEw7_grad(int value) { this.ew7_grad.set(value); } // Entfernt
    public double getGesamtlaengeEmpfaenger() { return gesamtlaengeEmpfaenger.get(); }
    public void setGesamtlaengeEmpfaenger(double value) { this.gesamtlaengeEmpfaenger.set(value); }

    // Getter/Setter für Senderwicklung
    public double getSl1_mm() { return sl1_mm.get(); }
    public void setSl1_mm(double value) { this.sl1_mm.set(value); }
    public int getSw1_grad() { return sw1_grad.get(); }
    public void setSw1_grad(int value) { this.sw1_grad.set(value); }
    public double getSl2_mm() { return sl2_mm.get(); }
    public void setSl2_mm(double value) { this.sl2_mm.set(value); }
    public int getSw2_grad() { return sw2_grad.get(); }
    public void setSw2_grad(int value) { this.sw2_grad.set(value); }
    public double getSl3_mm() { return sl3_mm.get(); }
    public void setSl3_mm(double value) { this.sl3_mm.set(value); }
    public int getSw3_grad() { return sw3_grad.get(); }
    public void setSw3_grad(int value) { this.sw3_grad.set(value); }
    public double getSl4_mm() { return sl4_mm.get(); }
    public void setSl4_mm(double value) { this.sl4_mm.set(value); }
    public int getSw4_grad() { return sw4_grad.get(); }
    public void setSw4_grad(int value) { this.sw4_grad.set(value); }
    public double getSl5_mm() { return sl5_mm.get(); }
    public void setSl5_mm(double value) { this.sl5_mm.set(value); }
    public int getSw5_grad() { return sw5_grad.get(); }
    public void setSw5_grad(int value) { this.sw5_grad.set(value); }
    public double getSl6_mm() { return sl6_mm.get(); }
    public void setSl6_mm(double value) { this.sl6_mm.set(value); }
    public int getSw6_grad() { return sw6_grad.get(); }
    public void setSw6_grad(int value) { this.sw6_grad.set(value); }
    public double getSl7_mm() { return sl7_mm.get(); }
    public void setSl7_mm(double value) { this.sl7_mm.set(value); }
    public int getSw7_grad() { return sw7_grad.get(); }
    public void setSw7_grad(int value) { this.sw7_grad.set(value); }
    public double getSl8_mm() { return sl8_mm.get(); }
    public void setSl8_mm(double value) { this.sl8_mm.set(value); }
    public int getSw8_grad() { return sw8_grad.get(); }
    public void setSw8_grad(int value) { this.sw8_grad.set(value); }
    public double getSl9_mm() { return sl9_mm.get(); }
    public void setSl9_mm(double value) { this.sl9_mm.set(value); }
    public int getSw9_grad() { return sw9_grad.get(); }
    public void setSw9_grad(int value) { this.sw9_grad.set(value); }
    public double getGesamtlaengeSender() { return gesamtlaengeSender.get(); }
    public void setGesamtlaengeSender(double value) { this.gesamtlaengeSender.set(value); }
}

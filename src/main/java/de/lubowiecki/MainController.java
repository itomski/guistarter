package de.lubowiecki;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextArea beschreibung;

    @FXML
    private Spinner<Integer> anzahl;

    @FXML
    private TextField preis;

    @FXML
    private DatePicker imBestandSeit;

    @FXML
    private ListView<Produkt> produkte;

//    private List<Produkt> produktListe;
//    private static final String SER_FILE = "data.ser";

    private ProduktRepository repository;


    @FXML
    private void save() {
        Produkt produkt = new Produkt();
        produkt.setName(name.getText()); // TODO: Validierung
        produkt.setBeschreibung(beschreibung.getText());
        produkt.setAnzahl(anzahl.getValue());
        produkt.setPreis(Double.parseDouble(preis.getText())); // TODO: Validierung
        produkt.setImBestandSeit(imBestandSeit.getValue());

//        produktListe.add(produkt);
//        saveToFile(SER_FILE, produktListe);

        try {
            repository.save(produkt);
        }
        catch (SQLException e) {
            // TODO: Ausgabe in die GUI verlagern
            System.out.println("Probleme beim Speichern.");
            System.out.println(e.getMessage());
        }

        updateAusgabe();
        clearFormular();
    }

    @FXML
    void deleteProdukt(KeyEvent event) {
        if(event.getCode() == KeyCode.BACK_SPACE) { // Wurde die Delete-Taste gedruckt
            Produkt ausgewaehlt = produkte.getSelectionModel().getSelectedItem();

            if (ausgewaehlt == null) return; // Methode verlassen, wenn nichts ausgewählt wurde

            try {
                repository.delete(ausgewaehlt);
                updateAusgabe();
            }
            catch (SQLException e) {
                System.out.println("Probleme beim Löschen!");
            }
        }
    }


    private void updateAusgabe() {
        //produkte.setItems(FXCollections.observableList(produktListe));

        try {
            List<Produkt> fromDB = repository.findAll();
            produkte.setItems(FXCollections.observableList(fromDB));
        }
        catch(SQLException e) {
            // TODO: Ausgabe in die GUI verlagern
            System.out.println("Problem beim Abfragen der Produkte");
            System.out.println(e.getMessage());
        }
    }

    private void clearFormular() {
        name.clear();
        beschreibung.clear();
        anzahl.getEditor().clear();
        preis.clear();
        imBestandSeit.getEditor().clear();
    }

//    private List<Produkt> readFromFile(String FILE) {
//        List<Produkt> produkte = new ArrayList<>();
//
//        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
//            produkte = (List<Produkt>) in.readObject();
//        }
//        catch (ClassNotFoundException | IOException e) {
//            e.printStackTrace();
//        }
//        return produkte;
//    }
//
//    private void saveToFile(String FILE, List<Produkt> produkte) {
//        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
//            out.writeObject(produkte);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // Wird beim Start der Oberfläche automatisch ausgeführt
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //produktListe = readFromFile(SER_FILE);
        try {
            repository = new ProduktRepository();
        }
        catch (SQLException e) {
            // TODO: Ausgabe in die GUI verlagern
            System.out.println("Problem mit der Datenbank.");
            System.out.println(e.getMessage());
        }

        updateAusgabe();
    }
}

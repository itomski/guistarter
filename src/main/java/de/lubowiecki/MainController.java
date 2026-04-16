package de.lubowiecki;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    private List<Produkt> produktListe;

    private static final String SER_FILE = "data.ser";

    @FXML
    private void save() {
        Produkt produkt = new Produkt();
        produkt.setName(name.getText()); // TODO: Validierung
        produkt.setBeschreibung(beschreibung.getText());
        produkt.setAnzahl(anzahl.getValue());
        produkt.setPreis(Double.parseDouble(preis.getText())); // TODO: Validierung
        produkt.setImBestandSeit(imBestandSeit.getValue());
        produktListe.add(produkt);

        saveToFile(SER_FILE, produktListe);

        updateAusgabe();
        clearFormular();
    }

    private void updateAusgabe() {
        produkte.setItems(FXCollections.observableList(produktListe));
    }

    private void clearFormular() {
        name.clear();
        beschreibung.clear();
        anzahl.getEditor().clear();
        preis.clear();
        imBestandSeit.getEditor().clear();
    }

    private List<Produkt> readFromFile(String FILE) {
        List<Produkt> produkte = new ArrayList<>();

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            produkte = (List<Produkt>) in.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return produkte;
    }

    private void saveToFile(String FILE, List<Produkt> produkte) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(produkte);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Wird beim Start der Oberfläche automatisch ausgeführt
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        produktListe = readFromFile(SER_FILE);
        updateAusgabe();

    }
}

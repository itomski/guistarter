package de.lubowiecki;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

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

    private List<Produkt> produktListe = new ArrayList<>();

    @FXML
    private void save() {
        Produkt produkt = new Produkt();
        produkt.setName(name.getText()); // TODO: Validierung
        produkt.setBeschreibung(beschreibung.getText());
        produkt.setAnzahl(anzahl.getValue());
        produkt.setPreis(Double.parseDouble(preis.getText())); // TODO: Validierung
        produkt.setImBestandSeit(imBestandSeit.getValue());
        produktListe.add(produkt);

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
}

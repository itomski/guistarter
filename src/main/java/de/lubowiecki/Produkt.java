package de.lubowiecki;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

// POJO - Plain Old Java Object
// Damit ein Objekt serialisiert werden KANN, muss die Klasse Serializable implementieren!!!!
public class Produkt implements Serializable {

    private int id;
    private String name;
    private String beschreibung;
    private int anzahl;
    private double preis;
    private LocalDate imBestandSeit;

    public Produkt() {
    }

    public Produkt(String name, String beschreibung, int anzahl, double preis) {
        this(name, beschreibung, anzahl, preis, LocalDate.now());
    }

    public Produkt(String name, String beschreibung, int anzahl, double preis, LocalDate imBestandSeit) {
        this.name = name;
        this.beschreibung = beschreibung;
        this.anzahl = anzahl;
        this.preis = preis;
        this.imBestandSeit = imBestandSeit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public LocalDate getImBestandSeit() {
        return imBestandSeit;
    }

    public void setImBestandSeit(LocalDate imBestandSeit) {
        this.imBestandSeit = imBestandSeit;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append(id).append("\n")
            .append(name).append("\n")
            .append(beschreibung).append("\n")
            .append(anzahl).append("\n")
            .append(preis).append(" €\n")
            .append(imBestandSeit)
            .toString();
    }
}

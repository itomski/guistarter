package de.lubowiecki;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduktRepository {

    private static final String URL = "jdbc:sqlite:" + System.getProperty("user.home") + "/produkte.db";

    public ProduktRepository() throws SQLException {
        createTable();
    }

    public List<Produkt> findAll() throws SQLException {

        final String SQL = "SELECT * FROM produkte";

        try(Connection connection = DriverManager.getConnection(URL); Statement stmt = connection.createStatement()) {
            ResultSet results = stmt.executeQuery(SQL); // Abfrage von Daten
            List<Produkt> produktList = new ArrayList<>();

            while(results.next()) {
                produktList.add(create(results));
            }

            return produktList;
        }
    }

    public boolean delete(int id) throws SQLException {

        final String SQL = "DELETE FROM produkte WHERE id = " + id;

        try(Connection connection = DriverManager.getConnection(URL); Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(SQL) > 0;
        }
    }

    public boolean delete(Produkt p) throws SQLException {
        return delete(p.getId());
    }

    private Produkt create(ResultSet result) throws SQLException {
        Produkt p = new Produkt();
        p.setId(result.getInt("id"));
        p.setName(result.getString("name"));
        p.setBeschreibung(result.getString("beschreibung"));
        p.setAnzahl(result.getInt("anzahl"));
        p.setPreis(result.getDouble("preis"));
        p.setImBestandSeit(result.getDate("im_bestand_seit").toLocalDate());
        return p;
    }

    public boolean save(Produkt p) throws SQLException {
        if(p.getId() == 0) {
            return insert(p); // Noch nicht in der DB
        }
        else {
            return update(p); // Bereits in der DB vorhanden
        }
    }

    private boolean insert(Produkt p) throws SQLException {

        final String SQL = "INSERT INTO produkte (name, beschreibung, anzahl, preis, im_bestand_seit) " +
                                "VALUES(?,?,?,?,?)";

        // Mit PreparedStatements ist eine SQLInjection nicht möglich!

        try(Connection connection = DriverManager.getConnection(URL); PreparedStatement stmt = connection.prepareStatement(SQL)) {
            // ? im Befehl werden ersetzt
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getBeschreibung());
            stmt.setInt(3, p.getAnzahl());
            stmt.setDouble(4, p.getPreis());
            // Date.valueOf(p.getImBestandSeit()) wandelt ein LocalDate aus dem Produkt in ein java.sql.Date für die Datenbank
            stmt.setDate(5, Date.valueOf(p.getImBestandSeit()));
            // Anweisung wird ausgeführt
            return stmt.executeUpdate() > 0;
        }
    }

    private boolean update(Produkt p) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public boolean createTable() throws SQLException {

        final String SQL = "CREATE TABLE IF NOT EXISTS produkte (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "beschreibung TEXT," +
                                "anzahl INTEGER NOT NULL," +
                                "preis DOUBLE NOT NULL," +
                                "im_bestand_seit DATE NOT NULL" +
                            ")";

        try(Connection connection = DriverManager.getConnection(URL); Statement stmt = connection.createStatement()) {
            return stmt.execute(SQL);
        }
    }
}

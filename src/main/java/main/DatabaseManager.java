package main;

import model.CoilData;
import model.TodoItem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.AppConfig.*;

public class DatabaseManager {

    private static final Path dbFilePath = Paths.get(System.getProperty("user.home"), DATA_FOLDER_NAME, DATABASE_FILE_NAME);
    private static final String DB_URL = "jdbc:sqlite:" + dbFilePath.toString();

    public static void initializeDatabase() {
        try {
            Files.createDirectories(dbFilePath.getParent());
        } catch (Exception e) {
            System.err.println("Konnte das Datenverzeichnis nicht erstellen: " + e.getMessage());
        }

        String coilTableSql = "CREATE TABLE IF NOT EXISTS coil_data (" +
                "id INTEGER PRIMARY KEY, lfdNr INTEGER UNIQUE, typ TEXT, artikelnummerSpule TEXT, zeichnungsnummer TEXT, " +
                "materialbezeichnung TEXT, durchlassbreiteDB REAL, durchlasshoeheDH REAL, spulenlaenge REAL, artikelnummerSchacht TEXT, " +
                "el1_mm REAL, ew1_grad INTEGER, el2_mm REAL, ew2_grad INTEGER, el3_mm REAL, ew3_grad INTEGER, el4_mm REAL, ew4_grad INTEGER, " +
                "el5_mm REAL, ew5_grad INTEGER, el6_mm REAL, ew6_grad INTEGER, el7_mm REAL, gesamtlaengeEmpfaenger REAL, " + // ew7_grad entfernt
                "sl1_mm REAL, sw1_grad INTEGER, sl2_mm REAL, sw2_grad INTEGER, sl3_mm REAL, sw3_grad INTEGER, sl4_mm REAL, sw4_grad INTEGER, " +
                "sl5_mm REAL, sw5_grad INTEGER, sl6_mm REAL, sw6_grad INTEGER, sl7_mm REAL, sw7_grad INTEGER, sl8_mm REAL, sw8_grad INTEGER, " +
                "sl9_mm REAL, sw9_grad INTEGER, gesamtlaengeSender REAL, bemerkung TEXT, empfaengerSkizzePath TEXT, senderSkizzePath TEXT" +
                ");";

        String todoTableSql = "CREATE TABLE IF NOT EXISTS todos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task TEXT NOT NULL, " +
                "is_done INTEGER NOT NULL DEFAULT 0" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(coilTableSql);
            stmt.execute(todoTableSql);
        } catch (SQLException e) {
            System.err.println("Fehler bei der Initialisierung der Datenbank: " + e.getMessage());
        }
    }

    public static List<CoilData> getAllCoilData() {
        List<CoilData> data = new ArrayList<>();
        String sql = "SELECT * FROM coil_data";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                data.add(mapResultSetToCoilData(rs));
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Daten aus der Datenbank: " + e.getMessage());
        }
        return data;
    }

    public static void insertCoilData(CoilData coil) {
        String sql = "INSERT INTO coil_data(id, lfdNr, typ, artikelnummerSpule, zeichnungsnummer, materialbezeichnung, " +
                "durchlassbreiteDB, durchlasshoeheDH, spulenlaenge, artikelnummerSchacht, bemerkung, empfaengerSkizzePath, senderSkizzePath, " +
                "el1_mm, ew1_grad, el2_mm, ew2_grad, el3_mm, ew3_grad, el4_mm, ew4_grad, el5_mm, ew5_grad, el6_mm, ew6_grad, el7_mm, gesamtlaengeEmpfaenger, " +
                "sl1_mm, sw1_grad, sl2_mm, sw2_grad, sl3_mm, sw3_grad, sl4_mm, sw4_grad, sl5_mm, sw5_grad, sl6_mm, sw6_grad, sl7_mm, sw7_grad, sl8_mm, sw8_grad, sl9_mm, sw9_grad, gesamtlaengeSender) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // Platzhalter angepasst
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPreparedStatementFromCoilData(pstmt, coil, true);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen des Datensatzes: " + e.getMessage());
        }
    }

    public static void updateCoilData(CoilData coil) {
        String sql = "UPDATE coil_data SET lfdNr = ?, typ = ?, artikelnummerSpule = ?, zeichnungsnummer = ?, materialbezeichnung = ?, " +
                "durchlassbreiteDB = ?, durchlasshoeheDH = ?, spulenlaenge = ?, artikelnummerSchacht = ?, bemerkung = ?, empfaengerSkizzePath = ?, senderSkizzePath = ?, " +
                "el1_mm = ?, ew1_grad = ?, el2_mm = ?, ew2_grad = ?, el3_mm = ?, ew3_grad = ?, el4_mm = ?, ew4_grad = ?, el5_mm = ?, ew5_grad = ?, el6_mm = ?, ew6_grad = ?, el7_mm = ?, gesamtlaengeEmpfaenger = ?, " +
                "sl1_mm = ?, sw1_grad = ?, sl2_mm = ?, sw2_grad = ?, sl3_mm = ?, sw3_grad = ?, sl4_mm = ?, sw4_grad = ?, sl5_mm = ?, sw5_grad = ?, sl6_mm = ?, sw6_grad = ?, sl7_mm = ?, sw7_grad = ?, sl8_mm = ?, sw8_grad = ?, sl9_mm = ?, sw9_grad = ?, gesamtlaengeSender = ? " +
                "WHERE id = ?"; // Platzhalter angepasst
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setPreparedStatementFromCoilData(pstmt, coil, false);
            pstmt.setInt(46, coil.getId()); // Index angepasst
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler beim Aktualisieren des Datensatzes: " + e.getMessage());
        }
    }

    public static void deleteCoilData(int id) {
        String sql = "DELETE FROM coil_data WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des Datensatzes mit ID " + id + ": " + e.getMessage());
        }
    }

    public static void insertBatch(List<CoilData> data) {
        String sql = "INSERT INTO coil_data(id, lfdNr, typ, artikelnummerSpule, zeichnungsnummer, materialbezeichnung, " +
                "durchlassbreiteDB, durchlasshoeheDH, spulenlaenge, artikelnummerSchacht, bemerkung, empfaengerSkizzePath, senderSkizzePath, " +
                "el1_mm, ew1_grad, el2_mm, ew2_grad, el3_mm, ew3_grad, el4_mm, ew4_grad, el5_mm, ew5_grad, el6_mm, ew6_grad, el7_mm, gesamtlaengeEmpfaenger, " +
                "sl1_mm, sw1_grad, sl2_mm, sw2_grad, sl3_mm, sw3_grad, sl4_mm, sw4_grad, sl5_mm, sw5_grad, sl6_mm, sw6_grad, sl7_mm, sw7_grad, sl8_mm, sw8_grad, sl9_mm, sw9_grad, gesamtlaengeSender) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // Platzhalter angepasst
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (CoilData coil : data) {
                    setPreparedStatementFromCoilData(pstmt, coil, true);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Fehler beim Batch-Einfügen: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Fehler bei der Datenbankverbindung für Batch-Insert: " + e.getMessage());
        }
    }

    public static int getMaxId(String columnName) {
        // Whitelist-Validierung zur Verhinderung von SQL-Injection
        if (!"id".equals(columnName) && !"lfdNr".equals(columnName)) {
            System.err.println("Ungültiger Spaltenname für getMaxId: " + columnName);
            throw new IllegalArgumentException("Invalid column name provided.");
        }
        String sql = "SELECT MAX(" + columnName + ") FROM coil_data";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Abrufen der maximalen ID: " + e.getMessage());
        }
        return 0;
    }

    public static List<CoilData> searchCoilData(String searchTerm) {
        List<CoilData> data = new ArrayList<>();
        String sql = "SELECT * FROM coil_data WHERE " +
                "artikelnummerSpule LIKE ? OR " +
                "zeichnungsnummer LIKE ? OR " +
                "typ LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(mapResultSetToCoilData(rs));
            }
        } catch (SQLException e) {
            System.err.println("Fehler bei der Datensuche: " + e.getMessage());
        }
        return data;
    }

    public static Map<String, Integer> getStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT typ, COUNT(*) as count FROM coil_data GROUP BY typ";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stats.put(rs.getString("typ"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Statistik: " + e.getMessage());
        }
        return stats;
    }

    public static List<TodoItem> getTodos() {
        List<TodoItem> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos ORDER BY id";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                todos.add(new TodoItem(
                        rs.getInt("id"),
                        rs.getString("task"),
                        rs.getInt("is_done") == 1
                ));
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der To-Do-Liste: " + e.getMessage());
        }
        return todos;
    }

    public static void addTodo(String task) {
        String sql = "INSERT INTO todos(task) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler beim Hinzufügen eines To-Dos: " + e.getMessage());
        }
    }

    public static void updateTodoStatus(int id, boolean isDone) {
        String sql = "UPDATE todos SET is_done = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, isDone ? 1 : 0);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fehler beim Aktualisieren des To-Do-Status: " + e.getMessage());
        }
    }

    public static void deleteCompletedTodos() {
        String sql = "DELETE FROM todos WHERE is_done = 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen erledigter To-Dos: " + e.getMessage());
        }
    }

    private static CoilData mapResultSetToCoilData(ResultSet rs) throws SQLException {
        CoilData coil = new CoilData();
        coil.setId(rs.getInt("id"));
        coil.setLfdNr(rs.getInt("lfdNr"));
        coil.setTyp(rs.getString("typ"));
        coil.setArtikelnummerSpule(rs.getString("artikelnummerSpule"));
        coil.setZeichnungsnummer(rs.getString("zeichnungsnummer"));
        coil.setMaterialbezeichnung(rs.getString("materialbezeichnung"));
        coil.setDurchlassbreiteDB(rs.getDouble("durchlassbreiteDB"));
        coil.setDurchlasshoeheDH(rs.getDouble("durchlasshoeheDH"));
        coil.setSpulenlaenge(rs.getDouble("spulenlaenge"));
        coil.setArtikelnummerSchacht(rs.getString("artikelnummerSchacht"));
        coil.setBemerkung(rs.getString("bemerkung"));
        coil.setEmpfaengerSkizzePath(rs.getString("empfaengerSkizzePath"));
        coil.setSenderSkizzePath(rs.getString("senderSkizzePath"));
        // Empfänger
        coil.setEl1_mm(rs.getDouble("el1_mm")); coil.setEw1_grad(rs.getInt("ew1_grad"));
        coil.setEl2_mm(rs.getDouble("el2_mm")); coil.setEw2_grad(rs.getInt("ew2_grad"));
        coil.setEl3_mm(rs.getDouble("el3_mm")); coil.setEw3_grad(rs.getInt("ew3_grad"));
        coil.setEl4_mm(rs.getDouble("el4_mm")); coil.setEw4_grad(rs.getInt("ew4_grad"));
        coil.setEl5_mm(rs.getDouble("el5_mm")); coil.setEw5_grad(rs.getInt("ew5_grad"));
        coil.setEl6_mm(rs.getDouble("el6_mm")); coil.setEw6_grad(rs.getInt("ew6_grad"));
        coil.setEl7_mm(rs.getDouble("el7_mm")); // ew7_grad entfernt
        coil.setGesamtlaengeEmpfaenger(rs.getDouble("gesamtlaengeEmpfaenger"));
        // Sender
        coil.setSl1_mm(rs.getDouble("sl1_mm")); coil.setSw1_grad(rs.getInt("sw1_grad"));
        coil.setSl2_mm(rs.getDouble("sl2_mm")); coil.setSw2_grad(rs.getInt("sw2_grad"));
        coil.setSl3_mm(rs.getDouble("sl3_mm")); coil.setSw3_grad(rs.getInt("sw3_grad"));
        coil.setSl4_mm(rs.getDouble("sl4_mm")); coil.setSw4_grad(rs.getInt("sw4_grad"));
        coil.setSl5_mm(rs.getDouble("sl5_mm")); coil.setSw5_grad(rs.getInt("sw5_grad"));
        coil.setSl6_mm(rs.getDouble("sl6_mm")); coil.setSw6_grad(rs.getInt("sw6_grad"));
        coil.setSl7_mm(rs.getDouble("sl7_mm")); coil.setSw7_grad(rs.getInt("sw7_grad"));
        coil.setSl8_mm(rs.getDouble("sl8_mm")); coil.setSw8_grad(rs.getInt("sw8_grad"));
        coil.setSl9_mm(rs.getDouble("sl9_mm")); coil.setSw9_grad(rs.getInt("sw9_grad"));
        coil.setGesamtlaengeSender(rs.getDouble("gesamtlaengeSender"));
        return coil;
    }

    private static void setPreparedStatementFromCoilData(PreparedStatement pstmt, CoilData coil, boolean includeId) throws SQLException {
        int offset = 1;
        if (includeId) {
            pstmt.setInt(1, coil.getId());
            offset = 2;
        }
        pstmt.setInt(offset, coil.getLfdNr());
        pstmt.setString(offset + 1, coil.getTyp());
        pstmt.setString(offset + 2, coil.getArtikelnummerSpule());
        pstmt.setString(offset + 3, coil.getZeichnungsnummer());
        pstmt.setString(offset + 4, coil.getMaterialbezeichnung());
        pstmt.setDouble(offset + 5, coil.getDurchlassbreiteDB());
        pstmt.setDouble(offset + 6, coil.getDurchlasshoeheDH());
        pstmt.setDouble(offset + 7, coil.getSpulenlaenge());
        pstmt.setString(offset + 8, coil.getArtikelnummerSchacht());
        pstmt.setString(offset + 9, coil.getBemerkung());
        pstmt.setString(offset + 10, coil.getEmpfaengerSkizzePath());
        pstmt.setString(offset + 11, coil.getSenderSkizzePath());
        // Empfänger
        pstmt.setDouble(offset + 12, coil.getEl1_mm()); pstmt.setInt(offset + 13, coil.getEw1_grad());
        pstmt.setDouble(offset + 14, coil.getEl2_mm()); pstmt.setInt(offset + 15, coil.getEw2_grad());
        pstmt.setDouble(offset + 16, coil.getEl3_mm()); pstmt.setInt(offset + 17, coil.getEw3_grad());
        pstmt.setDouble(offset + 18, coil.getEl4_mm()); pstmt.setInt(offset + 19, coil.getEw4_grad());
        pstmt.setDouble(offset + 20, coil.getEl5_mm()); pstmt.setInt(offset + 21, coil.getEw5_grad());
        pstmt.setDouble(offset + 22, coil.getEl6_mm()); pstmt.setInt(offset + 23, coil.getEw6_grad());
        pstmt.setDouble(offset + 24, coil.getEl7_mm()); // ew7_grad entfernt
        pstmt.setDouble(offset + 25, coil.getGesamtlaengeEmpfaenger()); // Index angepasst
        // Sender
        pstmt.setDouble(offset + 26, coil.getSl1_mm()); pstmt.setInt(offset + 27, coil.getSw1_grad());
        pstmt.setDouble(offset + 28, coil.getSl2_mm()); pstmt.setInt(offset + 29, coil.getSw2_grad());
        pstmt.setDouble(offset + 30, coil.getSl3_mm()); pstmt.setInt(offset + 31, coil.getSw3_grad());
        pstmt.setDouble(offset + 32, coil.getSl4_mm()); pstmt.setInt(offset + 33, coil.getSw4_grad());
        pstmt.setDouble(offset + 34, coil.getSl5_mm()); pstmt.setInt(offset + 35, coil.getSw5_grad());
        pstmt.setDouble(offset + 36, coil.getSl6_mm()); pstmt.setInt(offset + 37, coil.getSw6_grad());
        pstmt.setDouble(offset + 38, coil.getSl7_mm()); pstmt.setInt(offset + 39, coil.getSw7_grad());
        pstmt.setDouble(offset + 40, coil.getSl8_mm()); pstmt.setInt(offset + 41, coil.getSw8_grad());
        pstmt.setDouble(offset + 42, coil.getSl9_mm()); pstmt.setInt(offset + 43, coil.getSw9_grad());
        pstmt.setDouble(offset + 44, coil.getGesamtlaengeSender());
    }
}

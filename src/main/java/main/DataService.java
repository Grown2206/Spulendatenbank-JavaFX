package main;

import gui.util.AlertHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gui.util.CoilDataParser; // <-- KORRIGIERTER IMPORT
import model.CoilData;
import model.TodoItem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static main.AppConfig.*;

public class DataService {

    static {
        DatabaseManager.initializeDatabase();
        migrateFromCsvIfNeeded();
    }

    public static ObservableList<CoilData> loadData() {
        List<CoilData> dataFromDb = DatabaseManager.getAllCoilData();
        return FXCollections.observableArrayList(dataFromDb);
    }

    public static ObservableList<CoilData> searchData(String searchTerm) {
        List<CoilData> dataFromDb = DatabaseManager.searchCoilData(searchTerm);
        return FXCollections.observableArrayList(dataFromDb);
    }

    public static Map<String, Integer> loadStatistics() {
        return DatabaseManager.getStatistics();
    }

    public static void saveNewRecord(CoilData newCoilData) {
        int maxId = DatabaseManager.getMaxId("id");
        int maxLfdNr = DatabaseManager.getMaxId("lfdNr");
        newCoilData.setId(maxId + 1);
        newCoilData.setLfdNr(maxLfdNr + 1);
        DatabaseManager.insertCoilData(newCoilData);
    }

    public static void updateRecord(CoilData updatedCoil) {
        DatabaseManager.updateCoilData(updatedCoil);
    }

    public static void deleteRecord(CoilData coilToDelete) {
        DatabaseManager.deleteCoilData(coilToDelete.getId());
    }

    public static void importRecords(List<CoilData> records) {
        int maxId = DatabaseManager.getMaxId("id");
        int maxLfdNr = DatabaseManager.getMaxId("lfdNr");
        for (CoilData record : records) {
            record.setId(++maxId);
            record.setLfdNr(++maxLfdNr);
        }
        DatabaseManager.insertBatch(records);
    }

    // --- To-Do Methoden ---
    public static List<TodoItem> getTodos() {
        return DatabaseManager.getTodos();
    }

    public static void addTodo(String task) {
        DatabaseManager.addTodo(task);
    }

    public static void updateTodoStatus(int id, boolean isDone) {
        DatabaseManager.updateTodoStatus(id, isDone);
    }

    public static void deleteCompletedTodos() {
        DatabaseManager.deleteCompletedTodos();
    }


    private static void migrateFromCsvIfNeeded() {
        Path csvPath = Paths.get(System.getProperty("user.home"), DATA_FOLDER_NAME, DATA_FILE_NAME);
        Path dbPath = Paths.get(System.getProperty("user.home"), DATA_FOLDER_NAME, DATABASE_FILE_NAME);
        boolean dbExistsAndHasData = Files.exists(dbPath) && DatabaseManager.getMaxId("id") > 0;

        if (Files.exists(csvPath) && !dbExistsAndHasData) {
            System.out.println("Alte CSV-Datei gefunden. Starte einmalige Migration zur Datenbank...");
            List<CoilData> dataFromCsv = loadDataFromCsv(csvPath);

            if (!dataFromCsv.isEmpty()) {
                DatabaseManager.insertBatch(dataFromCsv);
                System.out.println(dataFromCsv.size() + " Datensätze erfolgreich in die Datenbank migriert.");
                try {
                    Path renamedCsvPath = csvPath.resolveSibling(DATA_FILE_NAME + ".migrated");
                    Files.move(csvPath, renamedCsvPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Migration abgeschlossen. CSV-Datei umbenannt nach: " + renamedCsvPath);
                    AlertHelper.showInfo("Datenmigration", "Ihre bestehenden Daten wurden erfolgreich in das neue Datenbankformat übertragen.");
                } catch (IOException e) {
                    e.printStackTrace();
                    AlertHelper.showError("Migrationsfehler", "Die alte CSV-Datei konnte nicht umbenannt werden.", e.getMessage());
                }
            }
        }
    }

    private static List<CoilData> loadDataFromCsv(Path path) {
        List<CoilData> data = new ArrayList<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8)) {
            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(',').withTrim().parse(reader);
            for (CSVRecord record : parser) {
                data.add(CoilDataParser.parseCoilDataFromCsvRecord(record));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

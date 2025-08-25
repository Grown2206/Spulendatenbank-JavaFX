package gui;

import gui.util.AlertHelper;
import gui.util.NavigationService;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.AppConfig;
import main.CsvService;
import main.DataService;
import model.CoilData;

import java.io.File;
import java.util.List;

public class DataViewController {

    @FXML private TableView<CoilData> dataTable;
    @FXML private TableColumn<CoilData, Number> idCol;
    @FXML private TableColumn<CoilData, Number> lfdNrCol;
    @FXML private TableColumn<CoilData, String> typCol;
    @FXML private TableColumn<CoilData, String> artikelNrCol;
    @FXML private TableColumn<CoilData, String> zeichnungsNrCol;
    @FXML private TableColumn<CoilData, String> materialCol;
    @FXML private TableColumn<CoilData, Number> dbCol;
    @FXML private TableColumn<CoilData, Number> dhCol;
    @FXML private ProgressIndicator loadingIndicator;
    @FXML private TextField searchField;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        lfdNrCol.setCellValueFactory(cellData -> cellData.getValue().lfdNrProperty());
        typCol.setCellValueFactory(cellData -> cellData.getValue().typProperty());
        artikelNrCol.setCellValueFactory(cellData -> cellData.getValue().artikelnummerSpuleProperty());
        zeichnungsNrCol.setCellValueFactory(cellData -> cellData.getValue().zeichnungsnummerProperty());
        materialCol.setCellValueFactory(cellData -> cellData.getValue().materialbezeichnungProperty());
        dbCol.setCellValueFactory(cellData -> cellData.getValue().durchlassbreiteDBProperty());
        dhCol.setCellValueFactory(cellData -> cellData.getValue().durchlasshoeheDHProperty());

        dataTable.setPlaceholder(new Label("Keine Datensätze gefunden."));

        dataTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !dataTable.getSelectionModel().isEmpty()) {
                CoilData selectedData = dataTable.getSelectionModel().getSelectedItem();
                openEditWindow(selectedData);
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));

        loadAndRefreshTable();
    }

    private void filterTable(String searchTerm) {
        Task<ObservableList<CoilData>> filterTask = new Task<>() {
            @Override
            protected ObservableList<CoilData> call() {
                if (searchTerm == null || searchTerm.isEmpty()) {
                    return DataService.loadData();
                } else {
                    return DataService.searchData(searchTerm);
                }
            }
        };

        loadingIndicator.visibleProperty().bind(filterTask.runningProperty());
        dataTable.disableProperty().bind(filterTask.runningProperty());

        filterTask.setOnSucceeded(e -> dataTable.setItems(filterTask.getValue()));
        filterTask.setOnFailed(e -> filterTask.getException().printStackTrace());

        new Thread(filterTask).start();
    }

    @FXML
    private void handleCreateReport() {
        CoilData selectedData = dataTable.getSelectionModel().getSelectedItem();
        if (selectedData == null) {
            AlertHelper.showWarning("Keine Auswahl", "Bitte wählen Sie zuerst einen Datensatz aus der Tabelle aus.");
            return;
        }
        NavigationService.showReport(selectedData);
    }

    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CSV-Datei zum Importieren auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Dateien", "*.csv"));
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            List<CoilData> importedData = CsvService.importData(file);
            if (!importedData.isEmpty()) {
                DataService.importRecords(importedData);
                AlertHelper.showInfo("Import erfolgreich", importedData.size() + " Datensätze wurden erfolgreich importiert.");
                loadAndRefreshTable();
            }
        }
    }

    @FXML
    private void handleExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Speicherort für den Export auswählen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Dateien", "*.csv"));
        fileChooser.setInitialFileName("spulendaten_export.csv");
        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            CsvService.exportData(file);
        }
    }

    @FXML
    private void handleDelete() {
        CoilData selectedData = dataTable.getSelectionModel().getSelectedItem();
        if (selectedData == null) {
            AlertHelper.showWarning("Keine Auswahl", "Bitte wählen Sie zuerst einen Datensatz zum Löschen aus.");
            return;
        }

        boolean confirmed = AlertHelper.showConfirmation("Löschen bestätigen",
                "Möchten Sie den Datensatz wirklich löschen?",
                "ID: " + selectedData.getId() + "\nArtikelnummer: " + selectedData.getArtikelnummerSpule());

        if (confirmed) {
            DataService.deleteRecord(selectedData);
            loadAndRefreshTable();
        }
    }

    private void openEditWindow(CoilData coilData) {
        NavigationService.<MainViewController>openWindow(
                AppConfig.MAIN_VIEW_PATH,
                "Datensatz bearbeiten",
                AppConfig.MAIN_VIEW_STYLE_PATH,
                stage -> loadAndRefreshTable(),
                controller -> controller.initDataForEdit(coilData)
        );
    }

    private void loadAndRefreshTable() {
        filterTable(searchField.getText());
    }

    private Stage getStage() {
        return (Stage) dataTable.getScene().getWindow();
    }
}

package gui;

import gui.control.NumericInputController;
import gui.util.AlertHelper;
import gui.util.CoilDataFormMapper;
import gui.util.ValidationService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.AppConfig;
import main.DataService;
import main.PresetService;
import model.CoilData;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainViewController {

    // --- Allgemeine Felder ---
    @FXML private TextField lfdNrField;
    @FXML private ComboBox<String> typComboBox;
    @FXML private TextField materialbezeichnungField;
    @FXML private TextField artikelnummerSpuleField;
    @FXML private TextField zeichnungsnummerField;
    @FXML private TextField artikelnummerSchachtField;
    @FXML private TextArea bemerkungArea;

    // --- Container für dynamische Sichtbarkeit ---
    @FXML private TitledPane empfaengerTitledPane;

    // --- Bilder ---
    @FXML private ImageView empfaengerSkizzeImageView;
    @FXML private ImageView senderSkizzeImageView;

    // --- Eingebettete Controller für numerische Felder ---
    @FXML private NumericInputController dbFieldController;
    @FXML private NumericInputController dhFieldController;
    @FXML private NumericInputController spulenlaengeFieldController;
    @FXML private NumericInputController el1Controller, ew1Controller, el2Controller, ew2Controller, el3Controller, ew3Controller, el4Controller, ew4Controller;
    @FXML private NumericInputController el5Controller, ew5Controller, el6Controller, ew6Controller, el7Controller, gesamtlaengeEmpfaengerController;
    @FXML private NumericInputController sl1Controller, sw1Controller, sl2Controller, sw2Controller, sl3Controller, sw3Controller, sl4Controller, sw4Controller;
    @FXML private NumericInputController sl5Controller, sw5Controller, sl6Controller, sw6Controller, sl7Controller, sw7Controller, sl8Controller, sw8Controller;
    @FXML private NumericInputController sl9Controller, sw9Controller, gesamtlaengeSenderController;

    private List<NumericInputController> senderControllersToHide;
    private List<TextField> allNumericTextFields;
    private List<NumericInputController> empfaengerLengthControllers;
    private List<NumericInputController> senderLengthControllers;


    private CoilData coilBeingEdited = null;
    private String empfaengerSkizzePath = "";
    private String senderSkizzePath = "";

    @FXML
    public void initialize() {
        typComboBox.getItems().addAll("Intuity", "GLS", "GLS-MF", "MD-NG");
        initializeNumericLabels();

        senderControllersToHide = Arrays.asList(
                sl6Controller, sw6Controller, sl7Controller, sw7Controller,
                sl8Controller, sw8Controller, sl9Controller, sw9Controller
        );

        allNumericTextFields = getAllNumericTextFields();
        setupLiveCalculations();


        typComboBox.valueProperty().addListener((obs, oldVal, newVal) -> handleTypeSelection(newVal));
        // Verzögert den ersten Aufruf, um sicherzustellen, dass alle untergeordneten Controller initialisiert sind
        Platform.runLater(() -> handleTypeSelection(typComboBox.getValue()));
    }

    private void initializeNumericLabels() {
        dbFieldController.setLabel("DB [mm]:");
        dhFieldController.setLabel("DH [mm]:");
        spulenlaengeFieldController.setLabel("Länge [mm]:");
        el1Controller.setLabel("EL1:"); ew1Controller.setLabel("EW1 [°]:");
        el2Controller.setLabel("EL2:"); ew2Controller.setLabel("EW2 [°]:");
        el3Controller.setLabel("EL3:"); ew3Controller.setLabel("EW3 [°]:");
        el4Controller.setLabel("EL4:"); ew4Controller.setLabel("EW4 [°]:");
        el5Controller.setLabel("EL5:"); ew5Controller.setLabel("EW5 [°]:");
        el6Controller.setLabel("EL6:"); ew6Controller.setLabel("EW6 [°]:");
        el7Controller.setLabel("EL7:");
        gesamtlaengeEmpfaengerController.setLabel("Gesamt:");
        sl1Controller.setLabel("SL1:"); sw1Controller.setLabel("SW1 [°]:");
        sl2Controller.setLabel("SL2:"); sw2Controller.setLabel("SW2 [°]:");
        sl3Controller.setLabel("SL3:"); sw3Controller.setLabel("SW3 [°]:");
        sl4Controller.setLabel("SL4:"); sw4Controller.setLabel("SW4 [°]:");
        sl5Controller.setLabel("SL5:"); sw5Controller.setLabel("SW5 [°]:");
        sl6Controller.setLabel("SL6:"); sw6Controller.setLabel("SW6 [°]:");
        sl7Controller.setLabel("SL7:"); sw7Controller.setLabel("SW7 [°]:");
        sl8Controller.setLabel("SL8:"); sw8Controller.setLabel("SW8 [°]:");
        sl9Controller.setLabel("SL9:"); sw9Controller.setLabel("SW9 [°]:");
        gesamtlaengeSenderController.setLabel("Gesamt:");
    }

    private List<TextField> getAllNumericTextFields() {
        return Stream.of(
                dbFieldController, dhFieldController, spulenlaengeFieldController,
                el1Controller, ew1Controller, el2Controller, ew2Controller,
                el3Controller, ew3Controller, el4Controller, ew4Controller,
                el5Controller, ew5Controller, el6Controller, ew6Controller,
                el7Controller, gesamtlaengeEmpfaengerController,
                sl1Controller, sw1Controller, sl2Controller, sw2Controller,
                sl3Controller, sw3Controller, sl4Controller, sw4Controller,
                sl5Controller, sw5Controller, sl6Controller, sw6Controller,
                sl7Controller, sw7Controller, sl8Controller, sw8Controller,
                sl9Controller, sw9Controller, gesamtlaengeSenderController
        ).map(NumericInputController::getTextField).toList();
    }

    private void setupLiveCalculations() {
        empfaengerLengthControllers = Arrays.asList(el1Controller, el2Controller, el3Controller, el4Controller, el5Controller, el6Controller, el7Controller);
        senderLengthControllers = Arrays.asList(sl1Controller, sl2Controller, sl3Controller, sl4Controller, sl5Controller, sl6Controller, sl7Controller, sl8Controller, sl9Controller);

        empfaengerLengthControllers.forEach(c -> c.getTextField().textProperty().addListener((obs, o, n) -> calculateTotalReceiverLength()));
        senderLengthControllers.forEach(c -> c.getTextField().textProperty().addListener((obs, o, n) -> calculateTotalSenderLength()));
    }

    private void calculateTotalReceiverLength() {
        double total = empfaengerLengthControllers.stream()
                .mapToDouble(c -> parseDoubleSafe(c.getText()))
                .sum();
        gesamtlaengeEmpfaengerController.setText(String.format("%.2f", total));
    }

    private void calculateTotalSenderLength() {
        double total = senderLengthControllers.stream()
                .mapToDouble(c -> parseDoubleSafe(c.getText()))
                .sum();
        gesamtlaengeSenderController.setText(String.format("%.2f", total));
    }

    private boolean validateInput() {
        String errorMessage = ValidationService.validateCoilDataInput(typComboBox, allNumericTextFields);
        if (!errorMessage.isEmpty()) {
            AlertHelper.showError("Validierungsfehler", "Bitte korrigieren Sie die folgenden Fehler:", errorMessage);
            return false;
        }
        return true;
    }

    private void handleTypeSelection(String selectedType) {
        // 1. Sichtbarkeit der Felder anpassen
        boolean isMdNg = "MD-NG".equals(selectedType);
        empfaengerTitledPane.setVisible(!isMdNg);
        empfaengerTitledPane.setManaged(!isMdNg);
        senderControllersToHide.forEach(controller -> {
            boolean isVisible = isMdNg || "Intuity".equals(selectedType);
            Node node = controller.getRoot();
            if (node != null) {
                node.setVisible(isVisible);
                node.setManaged(isVisible);
            }
        });

        // Nur ausführen, wenn wir einen neuen Datensatz anlegen (nicht beim Bearbeiten)
        if (coilBeingEdited == null) {
            // 2. Standard-Skizzen laden
            loadDefaultSketches(selectedType);
            // 3. Felder mit Standardwerten vorbefüllen
            loadPresetsForType(selectedType);
        }
    }

    private void loadDefaultSketches(String typ) {
        if (typ == null) return;

        String empfaengerSketchPath = "";
        String senderSketchPath = "";

        switch (typ) {
            case "GLS":
                empfaengerSketchPath = AppConfig.SKIZZE_GLS_EMPFAENGER_PATH;
                senderSketchPath = AppConfig.SKIZZE_GLS_SENDER_PATH;
                break;
            case "GLS-MF":
                empfaengerSketchPath = AppConfig.SKIZZE_GLS_MF_EMPFAENGER_PATH;
                senderSketchPath = AppConfig.SKIZZE_GLS_MF_SENDER_PATH;
                break;
            case "INTUITY":
                empfaengerSketchPath = AppConfig.SKIZZE_INTUITY_EMPFAENGER_PATH;
                senderSketchPath = AppConfig.SKIZZE_INTUITY_SENDER_PATH;
                break;
            case "MD-NG":
                senderSketchPath = AppConfig.SKIZZE_MD_NG_SENDER_PATH;
                break;
        }

        URL empfaengerUrl = getClass().getResource(empfaengerSketchPath);
        if (empfaengerUrl != null) {
            this.empfaengerSkizzePath = empfaengerUrl.toExternalForm();
            loadImageFromPath(empfaengerSkizzeImageView, this.empfaengerSkizzePath);
        } else {
            empfaengerSkizzeImageView.setImage(null);
            this.empfaengerSkizzePath = "";
        }

        URL senderUrl = getClass().getResource(senderSketchPath);
        if (senderUrl != null) {
            this.senderSkizzePath = senderUrl.toExternalForm();
            loadImageFromPath(senderSkizzeImageView, this.senderSkizzePath);
        } else {
            senderSkizzeImageView.setImage(null);
            this.senderSkizzePath = "";
        }
    }

    private void loadPresetsForType(String type) {
        CoilData presets = PresetService.getPresetsForType(type);
        CoilDataFormMapper.populateFieldsFromData(this, presets);
        // Nach dem Setzen der Presets die Summen neu berechnen
        calculateTotalReceiverLength();
        calculateTotalSenderLength();
    }

    public void initDataForEdit(CoilData coil) {
        this.coilBeingEdited = coil;
        lfdNrField.setText(String.valueOf(coil.getLfdNr()));
        typComboBox.setValue(coil.getTyp());
        artikelnummerSpuleField.setText(coil.getArtikelnummerSpule());
        zeichnungsnummerField.setText(coil.getZeichnungsnummer());
        materialbezeichnungField.setText(coil.getMaterialbezeichnung());
        artikelnummerSchachtField.setText(coil.getArtikelnummerSchacht());
        bemerkungArea.setText(coil.getBemerkung());

        dbFieldController.setText(String.valueOf(coil.getDurchlassbreiteDB()));
        dhFieldController.setText(String.valueOf(coil.getDurchlasshoeheDH()));
        spulenlaengeFieldController.setText(String.valueOf(coil.getSpulenlaenge()));

        empfaengerSkizzePath = coil.getEmpfaengerSkizzePath();
        senderSkizzePath = coil.getSenderSkizzePath();
        loadImageFromPath(empfaengerSkizzeImageView, empfaengerSkizzePath);
        loadImageFromPath(senderSkizzeImageView, senderSkizzePath);

        CoilDataFormMapper.populateFieldsFromData(this, coil);
    }

    @FXML
    private void handleSpeichern() {
        if (!validateInput()) {
            return;
        }
        CoilData dataToSave = (coilBeingEdited == null) ? new CoilData() : coilBeingEdited;

        dataToSave.setTyp(typComboBox.getValue());
        dataToSave.setArtikelnummerSpule(artikelnummerSpuleField.getText());
        dataToSave.setZeichnungsnummer(zeichnungsnummerField.getText());
        dataToSave.setMaterialbezeichnung(materialbezeichnungField.getText());
        dataToSave.setArtikelnummerSchacht(artikelnummerSchachtField.getText());
        dataToSave.setBemerkung(bemerkungArea.getText());
        dataToSave.setEmpfaengerSkizzePath(empfaengerSkizzePath);
        dataToSave.setSenderSkizzePath(senderSkizzePath);

        CoilDataFormMapper.populateDataFromFields(this, dataToSave);

        if (coilBeingEdited == null) {
            DataService.saveNewRecord(dataToSave);
            AlertHelper.showInfo("Erfolg", "Neuer Datensatz wurde erfolgreich gespeichert.");
        } else {
            DataService.updateRecord(dataToSave);
            AlertHelper.showInfo("Erfolg", "Datensatz wurde erfolgreich aktualisiert.");
        }
        handleAbbrechen();
    }

    @FXML
    private void handleAbbrechen() {
        Stage stage = (Stage) lfdNrField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleEmpfaengerSkizzeLaden() {
        File selectedFile = showFileChooser();
        if (selectedFile != null) {
            empfaengerSkizzePath = selectedFile.toURI().toString();
            loadImageFromPath(empfaengerSkizzeImageView, empfaengerSkizzePath);
        }
    }

    @FXML
    private void handleSenderSkizzeLaden() {
        File selectedFile = showFileChooser();
        if (selectedFile != null) {
            senderSkizzePath = selectedFile.toURI().toString();
            loadImageFromPath(senderSkizzeImageView, senderSkizzePath);
        }
    }

    private File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Skizze auswählen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) lfdNrField.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    private void loadImageFromPath(ImageView imageView, String path) {
        if (path != null && !path.isEmpty()) {
            try {
                Image image = new Image(path);
                imageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Bild konnte nicht geladen werden: " + path);
                imageView.setImage(null);
            }
        } else {
            imageView.setImage(null);
        }
    }

    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.trim().replace(',', '.'));
        } catch (NumberFormatException e) { return 0.0; }
    }

    // Getter für die Controller, damit der Mapper darauf zugreifen kann
    public NumericInputController getDbFieldController() { return dbFieldController; }
    public NumericInputController getDhFieldController() { return dhFieldController; }
    public NumericInputController getSpulenlaengeFieldController() { return spulenlaengeFieldController; }
    public NumericInputController getEl1Controller() { return el1Controller; }
    public NumericInputController getEw1Controller() { return ew1Controller; }
    public NumericInputController getEl2Controller() { return el2Controller; }
    public NumericInputController getEw2Controller() { return ew2Controller; }
    public NumericInputController getEl3Controller() { return el3Controller; }
    public NumericInputController getEw3Controller() { return ew3Controller; }
    public NumericInputController getEl4Controller() { return el4Controller; }
    public NumericInputController getEw4Controller() { return ew4Controller; }
    public NumericInputController getEl5Controller() { return el5Controller; }
    public NumericInputController getEw5Controller() { return ew5Controller; }
    public NumericInputController getEl6Controller() { return el6Controller; }
    public NumericInputController getEw6Controller() { return ew6Controller; }
    public NumericInputController getEl7Controller() { return el7Controller; }
    public NumericInputController getGesamtlaengeEmpfaengerController() { return gesamtlaengeEmpfaengerController; }
    public NumericInputController getSl1Controller() { return sl1Controller; }
    public NumericInputController getSw1Controller() { return sw1Controller; }
    public NumericInputController getSl2Controller() { return sl2Controller; }
    public NumericInputController getSw2Controller() { return sw2Controller; }
    public NumericInputController getSl3Controller() { return sl3Controller; }
    public NumericInputController getSw3Controller() { return sw3Controller; }
    public NumericInputController getSl4Controller() { return sl4Controller; }
    public NumericInputController getSw4Controller() { return sw4Controller; }
    public NumericInputController getSl5Controller() { return sl5Controller; }
    public NumericInputController getSw5Controller() { return sw5Controller; }
    public NumericInputController getSl6Controller() { return sl6Controller; }
    public NumericInputController getSw6Controller() { return sw6Controller; }
    public NumericInputController getSl7Controller() { return sl7Controller; }
    public NumericInputController getSw7Controller() { return sw7Controller; }
    public NumericInputController getSl8Controller() { return sl8Controller; }
    public NumericInputController getSw8Controller() { return sw8Controller; }
    public NumericInputController getSl9Controller() { return sl9Controller; }
    public NumericInputController getSw9Controller() { return sw9Controller; }
    public NumericInputController getGesamtlaengeSenderController() { return gesamtlaengeSenderController; }
}

package gui;

import gui.util.AlertHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.SchematicDrawer;
import model.CoilData;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static main.AppConfig.*;

public class ReportViewController {

    @FXML private VBox reportRoot;
    @FXML private GridPane headerGrid;
    @FXML private GridPane headerDataGrid;
    @FXML private VBox empfaengerBox;
    @FXML private VBox senderBox;
    @FXML private TextArea bemerkungArea;
    @FXML private ImageView empfaengerSkizzeImageView;
    @FXML private Pane empfaengerZeichenPane;
    @FXML private ImageView senderSkizzeImageView;
    @FXML private Pane senderZeichenPane;
    @FXML private VBox empfaengerWerteBox;
    @FXML private VBox senderWerteBox;

    public void initData(CoilData data, Stage stage) {
        stage.setTitle("Bericht für: " + data.getArtikelnummerSpule() + " | Lfd. Nr.: " + data.getLfdNr());
        stage.setMaximized(true);

        headerDataGrid.getColumnConstraints().addAll(headerGrid.getColumnConstraints());

        headerDataGrid.add(new Label(data.getTyp()), 0, 0);
        headerDataGrid.add(new Label(data.getArtikelnummerSpule()), 1, 0);
        headerDataGrid.add(new Label(data.getZeichnungsnummer()), 2, 0);
        headerDataGrid.add(new Label(data.getMaterialbezeichnung()), 3, 0);
        headerDataGrid.add(new Label(data.getArtikelnummerSchacht()), 4, 0);
        headerDataGrid.add(new Label(String.valueOf(data.getLfdNr())), 5, 0);

        bemerkungArea.setText(data.getBemerkung());

        bindPaneToImageView(empfaengerZeichenPane, empfaengerSkizzeImageView);
        bindPaneToImageView(senderZeichenPane, senderSkizzeImageView);

        String typ = data.getTyp() != null ? data.getTyp().toUpperCase() : "";
        boolean isMdNg = "MD-NG".equals(typ);

        switch (typ) {
            case "GLS":
            case "GLS-MF":
            case "INTUITY":
                empfaengerWerteBox.getChildren().add(ReportTableFactory.createReceiverValuesTable(data));
                senderWerteBox.getChildren().add(ReportTableFactory.createSenderValuesTable(data, false));

                loadSketch(empfaengerSkizzeImageView, data.getEmpfaengerSkizzePath(), getTemplatePath(typ, true));
                SchematicDrawer.drawGlsReceiverLabels(empfaengerZeichenPane, data);
                loadSketch(senderSkizzeImageView, data.getSenderSkizzePath(), getTemplatePath(typ, false));
                SchematicDrawer.drawGlsSenderLabels(senderZeichenPane, data);
                break;
            case "MD-NG":
                empfaengerBox.setVisible(false);
                empfaengerBox.setManaged(false);
                senderWerteBox.getChildren().add(ReportTableFactory.createSenderValuesTable(data, true));
                loadSketch(senderSkizzeImageView, data.getSenderSkizzePath(), SKIZZE_MD_NG_SENDER_PATH);
                SchematicDrawer.drawMdNgLabels(senderZeichenPane, data);
                break;
            default:
                empfaengerBox.setVisible(false);
                empfaengerBox.setManaged(false);
                senderBox.setVisible(false);
                senderBox.setManaged(false);
        }
    }

    @FXML
    private void handleExportReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Bericht speichern unter...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Bild", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Bild", "*.jpg")
        );
        File file = fileChooser.showSaveDialog(reportRoot.getScene().getWindow());

        if (file != null) {
            WritableImage image = reportRoot.snapshot(new SnapshotParameters(), null);
            String extension = fileChooser.getSelectedExtensionFilter().getExtensions().get(0).substring(2);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), extension, file);
                AlertHelper.showInfo("Export erfolgreich", "Der Bericht wurde erfolgreich als Bild gespeichert.");
            } catch (IOException e) {
                e.printStackTrace();
                AlertHelper.showError("Exportfehler", "Der Bericht konnte nicht gespeichert werden.", e.getMessage());
            }
        }
    }

    private String getTemplatePath(String typ, boolean isReceiver) {
        if (isReceiver) {
            return switch (typ) {
                case "GLS" -> SKIZZE_GLS_EMPFAENGER_PATH;
                case "GLS-MF" -> SKIZZE_GLS_MF_EMPFAENGER_PATH;
                case "INTUITY" -> SKIZZE_INTUITY_EMPFAENGER_PATH;
                default -> "";
            };
        } else {
            return switch (typ) {
                case "GLS" -> SKIZZE_GLS_SENDER_PATH;
                case "GLS-MF" -> SKIZZE_GLS_MF_SENDER_PATH;
                case "INTUITY" -> SKIZZE_INTUITY_SENDER_PATH;
                default -> "";
            };
        }
    }

    private void loadSketch(ImageView imageView, String customPath, String templatePath) {
        String pathToLoad = (customPath != null && !customPath.isEmpty() && !customPath.startsWith(INTERNAL_ASSET_PREFIX))
                ? customPath
                : getClass().getResource(templatePath).toExternalForm();
        try {
            Image image = new Image(pathToLoad);
            imageView.setImage(image);
        } catch (Exception e) {
            AlertHelper.showError("Bildfehler", "Bild konnte nicht geladen werden", "Der Pfad '" + pathToLoad + "' ist ungültig.");
            e.printStackTrace();
        }
    }

    private void bindPaneToImageView(Pane pane, ImageView imageView) {
        pane.prefWidthProperty().bind(imageView.fitWidthProperty());
        pane.prefHeightProperty().bind(imageView.fitHeightProperty());
        pane.maxWidthProperty().bind(imageView.fitWidthProperty());
        pane.maxHeightProperty().bind(imageView.fitHeightProperty());
    }
}

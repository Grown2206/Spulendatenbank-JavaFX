package gui.util;

import gui.ReportViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.AppConfig;
import model.CoilData;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class NavigationService {

    /**
     * Öffnet ein neues modales Fenster.
     */
    public static <T> void openWindow(String fxmlPath, String title, String cssPath, Consumer<Stage> onHiddenAction, Consumer<T> controllerSetup) {
        try {
            URL fxmlUrl = NavigationService.class.getResource(fxmlPath);
            if (fxmlUrl == null) {
                AlertHelper.showError("Fehler", "Layout-Datei nicht gefunden", "Die Datei unter '" + fxmlPath + "' konnte nicht geladen werden.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            T controller = loader.getController();

            if (controllerSetup != null) {
                controllerSetup.accept(controller);
            }

            Stage stage = new Stage();
            stage.setTitle(AppConfig.APP_TITLE + " - " + title);
            Scene scene = new Scene(root);

            URL themeUrl = NavigationService.class.getResource("/gui/theme.css");
            if (themeUrl != null) {
                scene.getStylesheets().add(themeUrl.toExternalForm());
            }

            URL cssUrl = NavigationService.class.getResource(cssPath);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            if (onHiddenAction != null) {
                stage.setOnHidden(e -> onHiddenAction.accept(stage));
            }

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showError("Fehler beim Laden", "Fenster konnte nicht geöffnet werden", "Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    /**
     * Erstellt und zeigt das Berichtsfenster für einen gegebenen Datensatz.
     */
    public static void showReport(CoilData coilData) {
        openWindow(
                AppConfig.REPORT_VIEW_PATH,
                "Bericht für " + coilData.getArtikelnummerSpule(),
                AppConfig.REPORT_STYLE_PATH,
                null,
                (ReportViewController controller) -> controller.initData(coilData, new Stage())
        );
    }
}

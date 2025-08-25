package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHelper {

    /**
     * Zeigt einen Fehler-Dialog an.
     * @param title   Der Titel des Fensters.
     * @param header  Die Kopfzeile der Nachricht.
     * @param content Die eigentliche Fehlermeldung.
     */
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Zeigt einen Informations-Dialog an.
     * @param title   Der Titel des Fensters.
     * @param content Die anzuzeigende Information.
     */
    public static void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Zeigt einen Warn-Dialog an.
     * @param title   Der Titel des Fensters.
     * @param content Die Warnmeldung.
     */
    public static void showWarning(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Zeigt einen Bestätigungs-Dialog an.
     * @param title   Der Titel des Fensters.
     * @param header  Die Frage oder Kopfzeile.
     * @param content Zusätzlicher Kontext oder Information.
     * @return true, wenn der Benutzer OK klickt, sonst false.
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}

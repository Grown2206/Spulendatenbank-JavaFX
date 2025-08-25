package gui.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class ValidationService {

    /**
     * Validiert die Eingabefelder für die Spulendaten.
     * @param typComboBox Die ComboBox für den Spulentyp.
     * @param numericFields Eine Liste aller Felder, die numerische Werte enthalten sollen.
     * @return Eine Fehlermeldung als String. Ist der String leer, war die Validierung erfolgreich.
     */
    public static String validateCoilDataInput(ComboBox<String> typComboBox, List<TextField> numericFields) {
        StringBuilder errors = new StringBuilder();

        // 1. Validierung des Typs
        if (typComboBox.getValue() == null || typComboBox.getValue().isEmpty()) {
            errors.append("- Bitte wählen Sie einen 'Typ' aus.\n");
        }

        // 2. Validierung aller numerischen Felder
        for (TextField field : numericFields) {
            // Nur sichtbare und aktive Felder validieren
            if (field.isVisible() && !field.isDisabled()) {
                String text = field.getText();
                if (text != null && !text.trim().isEmpty()) {
                    try {
                        // Ersetze Komma durch Punkt für die Konvertierung
                        Double.parseDouble(text.trim().replace(',', '.'));
                    } catch (NumberFormatException e) {
                        String fieldName = field.getPromptText() != null && !field.getPromptText().isEmpty()
                                ? field.getPromptText()
                                : (field.getId() != null ? field.getId() : "Ein Feld");
                        errors.append("- Ungültige Zahl im Feld: ").append(fieldName).append("\n");
                    }
                }
            }
        }

        return errors.toString();
    }
}

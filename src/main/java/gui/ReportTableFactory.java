package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.CoilData;

public class ReportTableFactory {

    /**
     * Erstellt eine GridPane, die die Werte der Empfängerwicklung anzeigt.
     */
    public static GridPane createReceiverValuesTable(CoilData data) {
        GridPane grid = createStyledGridPane();

        addHeaderRow(grid, "Label", "Länge [mm]", "Label", "Winkel [°]");

        addRow(grid, 1, "EL1", data.getEl1_mm(), "EW1", data.getEw1_grad());
        addRow(grid, 2, "EL2", data.getEl2_mm(), "EW2", data.getEw2_grad());
        addRow(grid, 3, "EL3", data.getEl3_mm(), "EW3", data.getEw3_grad());
        addRow(grid, 4, "EL4", data.getEl4_mm(), "EW4", data.getEw4_grad());
        addRow(grid, 5, "EL5", data.getEl5_mm(), "EW5", data.getEw5_grad());
        addRow(grid, 6, "EL6", data.getEl6_mm(), "EW6", data.getEw6_grad());
        // --- EW7 entfernt ---
        addSingleValueRow(grid, 7, "EL7", data.getEl7_mm());
        addTotalRow(grid, 8, "Gesamt:", data.getGesamtlaengeEmpfaenger());

        return grid;
    }

    /**
     * Erstellt eine GridPane, die die Werte der Senderwicklung anzeigt.
     */
    public static GridPane createSenderValuesTable(CoilData data, boolean isMdNg) {
        GridPane grid = createStyledGridPane();

        addHeaderRow(grid, "Label", "Länge [mm]", "Label", "Winkel [°]");

        addRow(grid, 1, "SL1", data.getSl1_mm(), "SW1", data.getSw1_grad());
        addRow(grid, 2, "SL2", data.getSl2_mm(), "SW2", data.getSw2_grad());
        addRow(grid, 3, "SL3", data.getSl3_mm(), "SW3", data.getSw3_grad());
        addRow(grid, 4, "SL4", data.getSl4_mm(), "SW4", data.getSw4_grad());
        addRow(grid, 5, "SL5", data.getSl5_mm(), "SW5", data.getSw5_grad());

        if (isMdNg) {
            addRow(grid, 6, "SL6", data.getSl6_mm(), "SW6", data.getSw6_grad());
            addRow(grid, 7, "SL7", data.getSl7_mm(), "SW7", data.getSw7_grad());
            addRow(grid, 8, "SL8", data.getSl8_mm(), "SW8", data.getSw8_grad());
            addRow(grid, 9, "SL9", data.getSl9_mm(), "SW9", data.getSw9_grad());
        }

        addTotalRow(grid, isMdNg ? 10 : 6, "Gesamt:", data.getGesamtlaengeSender());

        return grid;
    }

    private static GridPane createStyledGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        return grid;
    }

    private static void addHeaderRow(GridPane grid, String c1, String c2, String c3, String c4) {
        Label lbl1 = new Label(c1);
        lbl1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label lbl2 = new Label(c2);
        lbl2.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label lbl3 = new Label(c3);
        lbl3.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label lbl4 = new Label(c4);
        lbl4.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        grid.addRow(0, lbl1, lbl2, new Label("   "), lbl3, lbl4);
    }

    private static void addRow(GridPane grid, int rowIndex, String label1, double value1, String label2, int value2) {
        grid.addRow(rowIndex,
                new Label(label1), new Label(String.valueOf(value1)), new Label("   "),
                new Label(label2), new Label(String.valueOf(value2)));
    }

    private static void addSingleValueRow(GridPane grid, int rowIndex, String label, double value) {
        grid.addRow(rowIndex, new Label(label), new Label(String.valueOf(value)));
    }

    private static void addTotalRow(GridPane grid, int rowIndex, String label, double value) {
        Label totalLabel = new Label(label);
        totalLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        Label totalValue = new Label(String.valueOf(value));
        totalValue.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        grid.addRow(rowIndex, totalLabel, totalValue);
    }
}

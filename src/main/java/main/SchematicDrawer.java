package main;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.CoilData;

public class SchematicDrawer {

    private static final Color LENGTH_COLOR = Color.DEEPPINK;
    private static final Color ANGLE_COLOR = Color.BLACK;
    private static final Color INFO_COLOR = Color.BLACK;

    public static void drawGlsReceiverLabels(Pane pane, CoilData data) {
        pane.getChildren().clear(); // Zeichenfläche leeren
        // drawDebugGrid(pane); // Bei Bedarf für die Positionierung einkommentieren

        // Längen
        addLabelAndValue(pane, "EL1", String.valueOf(data.getEl1_mm()), 220, 40, LENGTH_COLOR);
        addLabelAndValue(pane, "EL2", String.valueOf(data.getEl2_mm()), 230, 240, LENGTH_COLOR);
        addLabelAndValue(pane, "EL3", String.valueOf(data.getEl3_mm()), 140, 400, LENGTH_COLOR);
        addLabelAndValue(pane, "EL4", String.valueOf(data.getEl4_mm()), 330, 630, LENGTH_COLOR);
        addLabelAndValue(pane, "EL5", String.valueOf(data.getEl5_mm()), 560, 400, LENGTH_COLOR);
        addLabelAndValue(pane, "EL6", String.valueOf(data.getEl6_mm()), 440, 240, LENGTH_COLOR);
        addLabelAndValue(pane, "EL7", String.valueOf(data.getEl7_mm()), 440, 40, LENGTH_COLOR);

        // Winkel
        addLabelAndValue(pane, "EW1", data.getEw1_grad() + "°", 230, 160, ANGLE_COLOR);
        addLabelAndValue(pane, "EW2", data.getEw2_grad() + "°", 150, 180, ANGLE_COLOR);
        addLabelAndValue(pane, "EW3", data.getEw3_grad() + "°", 150, 630, ANGLE_COLOR);
        addLabelAndValue(pane, "EW4", data.getEw4_grad() + "°", 550, 630, ANGLE_COLOR);
        addLabelAndValue(pane, "EW5", data.getEw5_grad() + "°", 550, 180, ANGLE_COLOR);
        addLabelAndValue(pane, "EW6", data.getEw6_grad() + "°", 440, 160, ANGLE_COLOR);

        // Gesamtlänge
        addLabelAndValue(pane, "Gesamtlänge\nEmpfänger", data.getGesamtlaengeEmpfaenger() + " mm", 340, 400, INFO_COLOR);
    }

    public static void drawGlsSenderLabels(Pane pane, CoilData data) {
        pane.getChildren().clear();
        // drawDebugGrid(pane);

        // Längen
        addLabelAndValue(pane, "SL1", String.valueOf(data.getSl1_mm()), 360, 40, LENGTH_COLOR);
        addLabelAndValue(pane, "SL2", String.valueOf(data.getSl2_mm()), 330, 240, LENGTH_COLOR);
        addLabelAndValue(pane, "SL3", String.valueOf(data.getSl3_mm()), 140, 400, LENGTH_COLOR);
        addLabelAndValue(pane, "SL4", String.valueOf(data.getSl4_mm()), 330, 630, LENGTH_COLOR);
        addLabelAndValue(pane, "SL5", String.valueOf(data.getSl5_mm()), 560, 400, LENGTH_COLOR);

        // Winkel
        addLabelAndValue(pane, "SW1", data.getSw1_grad() + "°", 370, 150, ANGLE_COLOR);
        addLabelAndValue(pane, "SW2", data.getSw2_grad() + "°", 150, 170, ANGLE_COLOR);
        addLabelAndValue(pane, "SW3", data.getSw3_grad() + "°", 150, 630, ANGLE_COLOR);
        addLabelAndValue(pane, "SW4", data.getSw4_grad() + "°", 550, 630, ANGLE_COLOR);

        // Gesamtlänge
        addLabelAndValue(pane, "Gesamtlänge\nSenderdraht", data.getGesamtlaengeSender() + " mm", 340, 400, INFO_COLOR);
    }

    public static void drawMdNgLabels(Pane pane, CoilData data) {
        pane.getChildren().clear();
        // drawDebugGrid(pane);

        // Längen
        addLabelAndValue(pane, "SL1", String.valueOf(data.getSl1_mm()), 600, 80, LENGTH_COLOR);
        addLabelAndValue(pane, "SL2", String.valueOf(data.getSl2_mm()), 500, 160, LENGTH_COLOR);
        addLabelAndValue(pane, "SL3", String.valueOf(data.getSl3_mm()), 320, 210, LENGTH_COLOR);
        addLabelAndValue(pane, "SL4", String.valueOf(data.getSl4_mm()), 50, 380, LENGTH_COLOR);
        addLabelAndValue(pane, "SL5", String.valueOf(data.getSl5_mm()), 320, 530, LENGTH_COLOR);
        addLabelAndValue(pane, "SL6", String.valueOf(data.getSl6_mm()), 570, 400, LENGTH_COLOR);
        addLabelAndValue(pane, "SL7", String.valueOf(data.getSl7_mm()), 590, 315, LENGTH_COLOR);
        addLabelAndValue(pane, "SL8", String.valueOf(data.getSl8_mm()), 670, 250, LENGTH_COLOR);
        addLabelAndValue(pane, "SL9", String.valueOf(data.getSl9_mm()), 700, 170, LENGTH_COLOR);

        // Winkel
        addLabelAndValue(pane, "SW1", data.getSw1_grad() + "°", 540, 130, ANGLE_COLOR);
        addLabelAndValue(pane, "SW2", data.getSw2_grad() + "°", 500, 210, ANGLE_COLOR);
        addLabelAndValue(pane, "SW3", data.getSw3_grad() + "°", 15, 210, ANGLE_COLOR);
        addLabelAndValue(pane, "SW4", data.getSw4_grad() + "°", 15, 530, ANGLE_COLOR);
        addLabelAndValue(pane, "SW5", data.getSw5_grad() + "°", 570, 530, ANGLE_COLOR);
        addLabelAndValue(pane, "SW6", data.getSw6_grad() + "°", 510, 290, ANGLE_COLOR);
        addLabelAndValue(pane, "SW7", data.getSw7_grad() + "°", 630, 290, ANGLE_COLOR);
        addLabelAndValue(pane, "SW8", data.getSw8_grad() + "°", 630, 210, ANGLE_COLOR);
    }

    private static void addLabelAndValue(Pane pane, String label, String value, double x, double y, Color color) {
        Text labelText = new Text(label);
        labelText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
        labelText.setFill(color);
        labelText.setTextAlignment(TextAlignment.CENTER);
        labelText.setX(x - labelText.getLayoutBounds().getWidth() / 2);
        labelText.setY(y);

        Text valueText = new Text(value);
        valueText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        valueText.setFill(color);
        valueText.setTextAlignment(TextAlignment.CENTER);
        valueText.setX(x - valueText.getLayoutBounds().getWidth() / 2);

        // --- ÄNDERUNG START ---
        // Positioniert den Wert unterhalb des Labels, egal ob ein- oder mehrzeilig.
        // y ist die Basislinie der ERSTEN Zeile des Labels.
        // Wir nehmen die Höhe des gesamten Label-Textblocks und addieren einen festen Abstand.
        double valueY = y + labelText.getLayoutBounds().getHeight() + 1; // 1px Abstand
        valueText.setY(valueY);
        // --- ÄNDERUNG ENDE ---

        pane.getChildren().addAll(labelText, valueText);
    }

    private static void drawDebugGrid(Pane pane) {
        double spacing = 20.0;
        double width = pane.getWidth();
        double height = pane.getHeight();
        if (width == 0) width = 700; // Fallback-Werte
        if (height == 0) height = 700;

        Color gridColor = Color.LIGHTGREY;
        double gridWidth = 1.0;

        for (double i = 0; i < width; i += spacing) {
            Line line = new Line(i, 0, i, height);
            line.setStroke(gridColor);
            line.setStrokeWidth(gridWidth);
            line.getStrokeDashArray().addAll(2d, 5d);
            pane.getChildren().add(line);

            if (i > 0 && (int)i % 100 == 0) {
                Text text = new Text(i + 2, 12, String.valueOf((int)i));
                text.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
                text.setFill(Color.BLACK);
                pane.getChildren().add(text);
            }
        }

        for (double i = 0; i < height; i += spacing) {
            Line line = new Line(0, i, width, i);
            line.setStroke(gridColor);
            line.setStrokeWidth(gridWidth);
            line.getStrokeDashArray().addAll(2d, 5d);
            pane.getChildren().add(line);

            if (i > 0 && (int)i % 100 == 0) {
                Text text = new Text(2, i - 2, String.valueOf((int)i));
                text.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
                text.setFill(Color.BLACK);
                pane.getChildren().add(text);
            }
        }
    }
}

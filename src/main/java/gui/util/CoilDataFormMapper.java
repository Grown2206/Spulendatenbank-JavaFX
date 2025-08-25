package gui.util;

import gui.MainViewController;
import model.CoilData;

public class CoilDataFormMapper {

    /**
     * Befüllt die Formularfelder im MainViewController mit den Werten aus einem CoilData-Objekt.
     * @param controller Die Instanz des MainViewControllers.
     * @param coil Das CoilData-Objekt mit den Werten.
     */
    public static void populateFieldsFromData(MainViewController controller, CoilData coil) {
        controller.getEl1Controller().setText(String.valueOf(coil.getEl1_mm()));
        controller.getEw1Controller().setText(String.valueOf(coil.getEw1_grad()));
        controller.getEl2Controller().setText(String.valueOf(coil.getEl2_mm()));
        controller.getEw2Controller().setText(String.valueOf(coil.getEw2_grad()));
        controller.getEl3Controller().setText(String.valueOf(coil.getEl3_mm()));
        controller.getEw3Controller().setText(String.valueOf(coil.getEw3_grad()));
        controller.getEl4Controller().setText(String.valueOf(coil.getEl4_mm()));
        controller.getEw4Controller().setText(String.valueOf(coil.getEw4_grad()));
        controller.getEl5Controller().setText(String.valueOf(coil.getEl5_mm()));
        controller.getEw5Controller().setText(String.valueOf(coil.getEw5_grad()));
        controller.getEl6Controller().setText(String.valueOf(coil.getEl6_mm()));
        controller.getEw6Controller().setText(String.valueOf(coil.getEw6_grad()));
        controller.getEl7Controller().setText(String.valueOf(coil.getEl7_mm()));
        // controller.getEw7Controller().setText(String.valueOf(coil.getEw7_grad())); // Entfernt
        controller.getGesamtlaengeEmpfaengerController().setText(String.valueOf(coil.getGesamtlaengeEmpfaenger()));

        controller.getSl1Controller().setText(String.valueOf(coil.getSl1_mm()));
        controller.getSw1Controller().setText(String.valueOf(coil.getSw1_grad()));
        controller.getSl2Controller().setText(String.valueOf(coil.getSl2_mm()));
        controller.getSw2Controller().setText(String.valueOf(coil.getSw2_grad()));
        controller.getSl3Controller().setText(String.valueOf(coil.getSl3_mm()));
        controller.getSw3Controller().setText(String.valueOf(coil.getSw3_grad()));
        controller.getSl4Controller().setText(String.valueOf(coil.getSl4_mm()));
        controller.getSw4Controller().setText(String.valueOf(coil.getSw4_grad()));
        controller.getSl5Controller().setText(String.valueOf(coil.getSl5_mm()));
        controller.getSw5Controller().setText(String.valueOf(coil.getSw5_grad()));
        controller.getSl6Controller().setText(String.valueOf(coil.getSl6_mm()));
        controller.getSw6Controller().setText(String.valueOf(coil.getSw6_grad()));
        controller.getSl7Controller().setText(String.valueOf(coil.getSl7_mm()));
        controller.getSw7Controller().setText(String.valueOf(coil.getSw7_grad()));
        controller.getSl8Controller().setText(String.valueOf(coil.getSl8_mm()));
        controller.getSw8Controller().setText(String.valueOf(coil.getSw8_grad()));
        controller.getSl9Controller().setText(String.valueOf(coil.getSl9_mm()));
        controller.getSw9Controller().setText(String.valueOf(coil.getSw9_grad()));
        controller.getGesamtlaengeSenderController().setText(String.valueOf(coil.getGesamtlaengeSender()));
    }

    /**
     * Befüllt ein CoilData-Objekt mit den Werten aus den Formularfeldern des MainViewControllers.
     * @param controller Die Instanz des MainViewControllers.
     * @param coil Das CoilData-Objekt, das befüllt werden soll.
     */
    public static void populateDataFromFields(MainViewController controller, CoilData coil) {
        coil.setDurchlassbreiteDB(parseDoubleSafe(controller.getDbFieldController().getText()));
        coil.setDurchlasshoeheDH(parseDoubleSafe(controller.getDhFieldController().getText()));
        coil.setSpulenlaenge(parseDoubleSafe(controller.getSpulenlaengeFieldController().getText()));

        coil.setEl1_mm(parseDoubleSafe(controller.getEl1Controller().getText()));
        coil.setEw1_grad(parseIntSafe(controller.getEw1Controller().getText()));
        coil.setEl2_mm(parseDoubleSafe(controller.getEl2Controller().getText()));
        coil.setEw2_grad(parseIntSafe(controller.getEw2Controller().getText()));
        coil.setEl3_mm(parseDoubleSafe(controller.getEl3Controller().getText()));
        coil.setEw3_grad(parseIntSafe(controller.getEw3Controller().getText()));
        coil.setEl4_mm(parseDoubleSafe(controller.getEl4Controller().getText()));
        coil.setEw4_grad(parseIntSafe(controller.getEw4Controller().getText()));
        coil.setEl5_mm(parseDoubleSafe(controller.getEl5Controller().getText()));
        coil.setEw5_grad(parseIntSafe(controller.getEw5Controller().getText()));
        coil.setEl6_mm(parseDoubleSafe(controller.getEl6Controller().getText()));
        coil.setEw6_grad(parseIntSafe(controller.getEw6Controller().getText()));
        coil.setEl7_mm(parseDoubleSafe(controller.getEl7Controller().getText()));
        // coil.setEw7_grad(parseIntSafe(controller.getEw7Controller().getText())); // Entfernt
        coil.setGesamtlaengeEmpfaenger(parseDoubleSafe(controller.getGesamtlaengeEmpfaengerController().getText()));

        coil.setSl1_mm(parseDoubleSafe(controller.getSl1Controller().getText()));
        coil.setSw1_grad(parseIntSafe(controller.getSw1Controller().getText()));
        coil.setSl2_mm(parseDoubleSafe(controller.getSl2Controller().getText()));
        coil.setSw2_grad(parseIntSafe(controller.getSw2Controller().getText()));
        coil.setSl3_mm(parseDoubleSafe(controller.getSl3Controller().getText()));
        coil.setSw3_grad(parseIntSafe(controller.getSw3Controller().getText()));
        coil.setSl4_mm(parseDoubleSafe(controller.getSl4Controller().getText()));
        coil.setSw4_grad(parseIntSafe(controller.getSw4Controller().getText()));
        coil.setSl5_mm(parseDoubleSafe(controller.getSl5Controller().getText()));
        coil.setSw5_grad(parseIntSafe(controller.getSw5Controller().getText()));
        coil.setSl6_mm(parseDoubleSafe(controller.getSl6Controller().getText()));
        coil.setSw6_grad(parseIntSafe(controller.getSw6Controller().getText()));
        coil.setSl7_mm(parseDoubleSafe(controller.getSl7Controller().getText()));
        coil.setSw7_grad(parseIntSafe(controller.getSw7Controller().getText()));
        coil.setSl8_mm(parseDoubleSafe(controller.getSl8Controller().getText()));
        coil.setSw8_grad(parseIntSafe(controller.getSw8Controller().getText()));
        coil.setSl9_mm(parseDoubleSafe(controller.getSl9Controller().getText()));
        coil.setSw9_grad(parseIntSafe(controller.getSw9Controller().getText()));
        coil.setGesamtlaengeSender(parseDoubleSafe(controller.getGesamtlaengeSenderController().getText()));
    }

    private static int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(value.trim().replace(',', '.'));
        } catch (NumberFormatException e) { return 0; }
    }

    private static double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.trim().replace(',', '.'));
        } catch (NumberFormatException e) { return 0.0; }
    }
}

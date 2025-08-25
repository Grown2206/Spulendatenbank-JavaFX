package main;

import model.CoilData;

/**
 * Dieser Service liefert Standard-Vorgabewerte (Presets) für verschiedene Spulentypen.
 * Die Werte können hier zentral an einer Stelle gepflegt werden.
 */
public class PresetService {

    /**
     * Gibt ein CoilData-Objekt mit vordefinierten Standardwerten für den angegebenen Typ zurück.
     * Wenn für einen Typ keine Vorgaben existieren, wird ein leeres Objekt zurückgegeben.
     *
     * @param type Der Spulentyp (z.B. "GLS", "Intuity").
     * @return Ein CoilData-Objekt mit den Standardwerten.
     */
    public static CoilData getPresetsForType(String type) {
        CoilData presets = new CoilData();
        if (type == null) {
            return presets;
        }

        switch (type) {
            case "GLS":
                // HIER WERTE FÜR GLS EINTRAGEN
                presets.setEw1_grad(90);
                presets.setEw2_grad(90);
                presets.setEw3_grad(90);
                presets.setEw4_grad(90);
                presets.setEw5_grad(90);
                presets.setEw6_grad(90);
                presets.setSw1_grad(90);
                presets.setSw2_grad(90);
                presets.setSw3_grad(90);
                presets.setSw4_grad(90);
                break;

            case "GLS-MF":
                // HIER WERTE FÜR GLS-MF EINTRAGEN
                presets.setEw1_grad(90);
                presets.setEw2_grad(90);
                presets.setEw3_grad(90);
                presets.setEw4_grad(90);
                presets.setEw5_grad(90);
                presets.setEw6_grad(90);
                presets.setSw1_grad(90);
                presets.setSw2_grad(90);
                presets.setSw3_grad(90);
                presets.setSw4_grad(90);
                break;

            case "Intuity":
                // HIER WERTE FÜR Intuity EINTRAGEN
                presets.setEw1_grad(90);
                presets.setEw2_grad(90);
                presets.setEw3_grad(90);
                presets.setEw4_grad(90);
                presets.setEw5_grad(90);
                presets.setEw6_grad(90);
                presets.setSw1_grad(90);
                presets.setSw2_grad(90);
                presets.setSw3_grad(90);
                presets.setSw4_grad(90);
                break;

            case "MD-NG":
                // HIER WERTE FÜR MD-NG EINTRAGEN
                presets.setSw1_grad(123);
                presets.setSw2_grad(90);
                presets.setSw3_grad(90);
                presets.setSw4_grad(90);
                presets.setSw5_grad(90);
                presets.setSw6_grad(125);
                presets.setSw7_grad(55);
                presets.setSw8_grad(123);
                break;
        }

        return presets;
    }
}

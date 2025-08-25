package gui.util;

import model.CoilData;
import org.apache.commons.csv.CSVRecord;

public class CoilDataParser {

    /**
     * Wandelt einen CSVRecord in ein CoilData-Objekt um.
     * @param record Der CSVRecord, der die Daten einer Zeile enthält.
     * @return Ein befülltes CoilData-Objekt.
     */
    public static CoilData parseCoilDataFromCsvRecord(CSVRecord record) {
        CoilData coil = new CoilData();
        coil.setId(parseIntSafe(record.get("ID")));
        coil.setLfdNr(parseIntSafe(record.get("Lfd Nr")));
        coil.setTyp(record.get("Typ"));
        coil.setArtikelnummerSpule(record.get("Artikelnummer Spule"));
        coil.setZeichnungsnummer(record.get("Zeichnungsnummer"));
        coil.setMaterialbezeichnung(record.get("Materialbezeichnung"));
        coil.setDurchlassbreiteDB(parseDoubleSafe(record.get("Durchlassbreite (DB)")));
        coil.setDurchlasshoeheDH(parseDoubleSafe(record.get("Durchlasshöhe(DH)")));
        coil.setSpulenlaenge(parseDoubleSafe(record.get("Spulenlänge")));
        coil.setArtikelnummerSchacht(record.get("Artikelnummer Schacht"));
        coil.setBemerkung(record.get("Bemerkung"));
        coil.setEmpfaengerSkizzePath(record.get("EmpfaengerSkizze"));
        coil.setSenderSkizzePath(record.get("SenderSkizze"));
        // Empfänger
        coil.setEl1_mm(parseDoubleSafe(record.get("EL1 [mm]"))); coil.setEw1_grad(parseIntSafe(record.get("EW1 [°]")));
        coil.setEl2_mm(parseDoubleSafe(record.get("EL2 [mm]"))); coil.setEw2_grad(parseIntSafe(record.get("EW2 [°]")));
        coil.setEl3_mm(parseDoubleSafe(record.get("EL3 [mm]"))); coil.setEw3_grad(parseIntSafe(record.get("EW3 [°]")));
        coil.setEl4_mm(parseDoubleSafe(record.get("EL4 [mm]"))); coil.setEw4_grad(parseIntSafe(record.get("EW4 [°]")));
        coil.setEl5_mm(parseDoubleSafe(record.get("EL5 [mm]"))); coil.setEw5_grad(parseIntSafe(record.get("EW5 [°]")));
        coil.setEl6_mm(parseDoubleSafe(record.get("EL6 [mm]"))); coil.setEw6_grad(parseIntSafe(record.get("EW6 [°]")));
        coil.setEl7_mm(parseDoubleSafe(record.get("EL7 [mm]")));
        // coil.setEw7_grad(parseIntSafe(record.get("EW7 [°]"))); // Entfernt
        coil.setGesamtlaengeEmpfaenger(parseDoubleSafe(record.get("Gesamtlänge Empfänger")));
        // Sender
        coil.setSl1_mm(parseDoubleSafe(record.get("SL1 [mm]"))); coil.setSw1_grad(parseIntSafe(record.get("SW1 [°]")));
        coil.setSl2_mm(parseDoubleSafe(record.get("SL2 [mm]"))); coil.setSw2_grad(parseIntSafe(record.get("SW2 [°]")));
        coil.setSl3_mm(parseDoubleSafe(record.get("SL3 [mm]"))); coil.setSw3_grad(parseIntSafe(record.get("SW3 [°]")));
        coil.setSl4_mm(parseDoubleSafe(record.get("SL4 [mm]"))); coil.setSw4_grad(parseIntSafe(record.get("SW4 [°]")));
        coil.setSl5_mm(parseDoubleSafe(record.get("SL5 [mm]"))); coil.setSw5_grad(parseIntSafe(record.get("SW5 [°]")));
        coil.setSl6_mm(parseDoubleSafe(record.get("SL6 [mm]"))); coil.setSw6_grad(parseIntSafe(record.get("SW6 [°]")));
        coil.setSl7_mm(parseDoubleSafe(record.get("SL7 [mm]"))); coil.setSw7_grad(parseIntSafe(record.get("SW7 [°]")));
        coil.setSl8_mm(parseDoubleSafe(record.get("SL8 [mm]"))); coil.setSw8_grad(parseIntSafe(record.get("SW8 [°]")));
        coil.setSl9_mm(parseDoubleSafe(record.get("SL9 [mm]"))); coil.setSw9_grad(parseIntSafe(record.get("SW9 [°]")));
        coil.setGesamtlaengeSender(parseDoubleSafe(record.get("Gesamtlänge Sender")));
        return coil;
    }

    /**
     * Parst einen String sicher in einen Integer.
     * @param value Der zu parsende String.
     * @return Der Integer-Wert oder 0 bei einem Fehler.
     */
    private static int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try { return Integer.parseInt(value.trim()); } catch (NumberFormatException e) { return 0; }
    }

    /**
     * Parst einen String sicher in einen Double.
     * @param value Der zu parsende String.
     * @return Der Double-Wert oder 0.0 bei einem Fehler.
     */
    private static double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        try { return Double.parseDouble(value.trim().replace(',', '.')); } catch (NumberFormatException e) { return 0.0; }
    }
}

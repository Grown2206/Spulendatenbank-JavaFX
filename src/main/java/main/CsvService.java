package main;

import gui.util.AlertHelper;
import gui.util.CoilDataParser;
import model.CoilData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

    private static final String[] HEADERS = {
            "ID", "Lfd Nr", "Typ", "Artikelnummer Spule", "Zeichnungsnummer",
            "Materialbezeichnung", "Durchlassbreite (DB)", "Durchlasshöhe(DH)", "Spulenlänge", "Artikelnummer Schacht",
            "EL1 [mm]", "EW1 [°]", "EL2 [mm]", "EW2 [°]", "EL3 [mm]", "EW3 [°]", "EL4 [mm]", "EW4 [°]",
            "EL5 [mm]", "EW5 [°]", "EL6 [mm]", "EW6 [°]", "EL7 [mm]", "Gesamtlänge Empfänger", // EW7 [°] entfernt
            "SL1 [mm]", "SW1 [°]", "SL2 [mm]", "SW2 [°]", "SL3 [mm]", "SW3 [°]", "SL4 [mm]", "SW4 [°]",
            "SL5 [mm]", "SW5 [°]", "SL6 [mm]", "SW6 [°]", "SL7 [mm]", "SW7 [°]", "SL8 [mm]", "SW8 [°]",
            "SL9 [mm]", "SW9 [°]", "Gesamtlänge Sender", "Bemerkung", "EmpfaengerSkizze", "SenderSkizze"
    };

    public static void exportData(File file) {
        List<CoilData> data = DatabaseManager.getAllCoilData();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))) {

            for (CoilData coil : data) {
                printer.printRecord(
                        coil.getId(), coil.getLfdNr(), coil.getTyp(), coil.getArtikelnummerSpule(),
                        coil.getZeichnungsnummer(), coil.getMaterialbezeichnung(),
                        coil.getDurchlassbreiteDB(), coil.getDurchlasshoeheDH(), coil.getSpulenlaenge(), coil.getArtikelnummerSchacht(),
                        coil.getEl1_mm(), coil.getEw1_grad(), coil.getEl2_mm(), coil.getEw2_grad(), coil.getEl3_mm(), coil.getEw3_grad(),
                        coil.getEl4_mm(), coil.getEw4_grad(), coil.getEl5_mm(), coil.getEw5_grad(), coil.getEl6_mm(), coil.getEw6_grad(),
                        coil.getEl7_mm(), coil.getGesamtlaengeEmpfaenger(), // ew7_grad entfernt
                        coil.getSl1_mm(), coil.getSw1_grad(), coil.getSl2_mm(), coil.getSw2_grad(), coil.getSl3_mm(), coil.getSw3_grad(),
                        coil.getSl4_mm(), coil.getSw4_grad(), coil.getSl5_mm(), coil.getSw5_grad(), coil.getSl6_mm(), coil.getSw6_grad(),
                        coil.getSl7_mm(), coil.getSw7_grad(), coil.getSl8_mm(), coil.getSw8_grad(), coil.getSl9_mm(), coil.getSw9_grad(),
                        coil.getGesamtlaengeSender(), coil.getBemerkung(), coil.getEmpfaengerSkizzePath(), coil.getSenderSkizzePath()
                );
            }
            AlertHelper.showInfo("Export erfolgreich", data.size() + " Datensätze wurden erfolgreich exportiert.");
        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showError("Exportfehler", "Die Daten konnten nicht exportiert werden.", e.getMessage());
        }
    }

    public static List<CoilData> importData(File file) {
        List<CoilData> importedData = new ArrayList<>();
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            // Erstelle ein CSVFormat, das fehlende Spalten ignoriert
            CSVParser parser = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withDelimiter(',')
                    .withTrim()
                    .withAllowMissingColumnNames(true) // KORREKTUR: Tippfehler behoben (withIgnoreMissingColums -> withIgnoreMissingColumns)
                    .parse(reader);

            for (CSVRecord record : parser) {
                importedData.add(CoilDataParser.parseCoilDataFromCsvRecord(record));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertHelper.showError("Importfehler", "Die CSV-Datei konnte nicht gelesen werden.", e.getMessage());
            return new ArrayList<>(); // Leere Liste bei Fehler zurückgeben
        }
        return importedData;
    }
}

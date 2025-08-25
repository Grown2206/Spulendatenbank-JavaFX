# Spulendatenbank-Anwendung

## Übersicht

Dies ist eine Desktop-Anwendung zur Verwaltung von Spulendaten und Biegelängen, entwickelt mit JavaFX und Maven. Die Anwendung ermöglicht das Erstellen, Anzeigen und Bearbeiten von Datensätzen sowie die Generierung von visuellen Berichten basierend auf den eingegebenen Daten.

## Funktionen

-   **Hauptmenü:** Zentrale Navigation zu allen Programmfunktionen.
-   **Datensätze anlegen:** Ein detailliertes Formular zur Eingabe aller relevanten Spulendaten, inklusive Längen- und Winkelwerten für Empfänger- und Senderwicklungen.
-   **Datensätze anzeigen & verwalten:** Eine Tabellenansicht aller gespeicherten Datensätze. Datensätze können per Doppelklick zur Bearbeitung geöffnet werden.
-   **Berichte erstellen:** Generiert einen visuellen Bericht für einen ausgewählten Datensatz, der die Daten und eine schematische Zeichnung der Wicklung anzeigt.
-   **Dynamische Formulare & Berichte:** Die Benutzeroberfläche passt sich an den ausgewählten Spulentyp an (z.B. werden für den Typ "MD-NG" nicht benötigte Felder ausgeblendet).
-   **Datenpersistenz:** Alle Daten werden lokal in einer CSV-Datei im Benutzerverzeichnis gespeichert.

## Technologien

-   **Sprache:** Java 17
-   **UI-Framework:** JavaFX 17
-   **Build-Tool:** Apache Maven
-   **Bibliotheken:**
    -   Apache Commons CSV (zum Lesen/Schreiben von CSV-Dateien)
    -   Ikonli (für Icons in der Benutzeroberfläche)

## Setup & Start

1.  **Voraussetzungen:** JDK 17 und Apache Maven müssen installiert sein.
2.  **Klonen:** `git clone <repository-url>`
3.  **Bauen & Starten:** Navigiere in das Projektverzeichnis und führe den folgenden Maven-Befehl aus:
    ```bash
    mvn clean javafx:run
    ```

## Datenablage

Die Anwendungsdaten (`data.csv`) werden im Benutzerverzeichnis unter `C:\Users\<DeinBenutzername>\Spulendatenbank\` gespeichert.


package main;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static main.AppConfig.*;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource(MAIN_MENU_VIEW_PATH);
            if (fxmlUrl == null) {
                System.err.println("--> FEHLER: Die FXML-Datei für das Hauptmenü konnte nicht gefunden werden!");
                System.err.println("--> Bitte prüfen: Existiert die Datei unter 'src/main/resources" + MAIN_MENU_VIEW_PATH + "'?");
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root);

            // Lade das zentrale Theme und das spezifische Stylesheet
            URL themeUrl = getClass().getResource("/gui/theme.css");
            if (themeUrl != null) {
                scene.getStylesheets().add(themeUrl.toExternalForm());
            }
            URL cssUrl = getClass().getResource(MAIN_MENU_STYLE_PATH);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.out.println("--> Warnung: CSS-Datei nicht gefunden unter 'src/main/resources" + MAIN_MENU_STYLE_PATH + "'");
            }

            primaryStage.setTitle(APP_TITLE + " - Hauptmenü");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Hier könnte man den AlertHelper verwenden, aber die GUI ist evtl. noch nicht bereit.
            System.err.println("Ein kritischer Fehler ist beim Starten der Anwendung aufgetreten.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

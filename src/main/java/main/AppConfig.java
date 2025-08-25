package main;

public class AppConfig {

    // --- Anwendungsinformationen ---
    public static final String APP_TITLE = "Spulendatenbank";

    // --- Datenablage ---
    public static final String DATA_FOLDER_NAME = "Spulendatenbank";
    public static final String DATA_FILE_NAME = "data.csv";
    public static final String DATABASE_FILE_NAME = "spulendatenbank.db"; // Für zukünftige Datenbank-Phase

    // --- FXML-Layouts ---
    public static final String MAIN_MENU_VIEW_PATH = "/gui/MainMenu.fxml";
    public static final String MAIN_VIEW_PATH = "/gui/MainView.fxml";
    public static final String DATA_VIEW_PATH = "/gui/DataView.fxml";
    public static final String REPORT_VIEW_PATH = "/gui/ReportView.fxml";

    // --- CSS-Stylesheets ---
    public static final String MAIN_MENU_STYLE_PATH = "/gui/main-menu-style.css";
    public static final String DATA_VIEW_STYLE_PATH = "/gui/data-view-style.css";
    public static final String REPORT_STYLE_PATH = "/gui/report-style.css";
    public static final String MAIN_VIEW_STYLE_PATH = "/gui/style.css";

    // --- Standard-Skizzen ---
    public static final String SKIZZE_GLS_EMPFAENGER_PATH = "/assets/Skizze_GLS_Empfängerdraht.jpg";
    public static final String SKIZZE_GLS_SENDER_PATH = "/assets/Skizze_GLS_Senderdraht.jpg";
    public static final String SKIZZE_GLS_MF_EMPFAENGER_PATH = "/assets/Skizze_GLS_MF_Empfängerdraht.jpg";
    public static final String SKIZZE_GLS_MF_SENDER_PATH = "/assets/Skizze_GLS_MF_Senderdraht.jpg";
    public static final String SKIZZE_INTUITY_EMPFAENGER_PATH = "/assets/Skizze_Intuity_Empfängerdraht.jpg";
    public static final String SKIZZE_INTUITY_SENDER_PATH = "/assets/Skizze_Intuity_Senderdraht.jpg";
    public static final String SKIZZE_MD_NG_SENDER_PATH = "/assets/Skizze_MD_NG_Senderdraht.png";

    // --- Interne Ressourcen ---
    public static final String INTERNAL_DATA_CSV_PATH = "/data/DatenTabelleBiegelängen.csv";
    public static final String INTERNAL_ASSET_PREFIX = "internal:/assets/";
}
package gui;

import gui.util.AlertHelper;
import gui.util.NavigationService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.DataService;
import model.TodoItem;

import java.util.List;
import java.util.Map;

import static main.AppConfig.*;

public class MainMenuController {

    @FXML private Label glsCountLabel;
    @FXML private Label glsMfCountLabel;
    @FXML private Label intuityCountLabel;
    @FXML private Label mdNgCountLabel;

    @FXML private ListView<CheckBox> todoListView;
    @FXML private TextField todoTextField;

    @FXML
    public void initialize() {
        loadStats();
        loadTodos();
    }

    private void loadStats() {
        Task<Map<String, Integer>> statsTask = new Task<>() {
            @Override
            protected Map<String, Integer> call() {
                return DataService.loadStatistics();
            }
        };

        statsTask.setOnSucceeded(e -> {
            Map<String, Integer> stats = statsTask.getValue();
            glsCountLabel.setText(String.valueOf(stats.getOrDefault("GLS", 0)));
            glsMfCountLabel.setText(String.valueOf(stats.getOrDefault("GLS-MF", 0)));
            intuityCountLabel.setText(String.valueOf(stats.getOrDefault("Intuity", 0)));
            mdNgCountLabel.setText(String.valueOf(stats.getOrDefault("MD-NG", 0)));
        });

        statsTask.setOnFailed(e -> {
            statsTask.getException().printStackTrace();
            glsCountLabel.setText("Fehler");
        });

        new Thread(statsTask).start();
    }

    private void loadTodos() {
        todoListView.getItems().clear();
        Task<List<TodoItem>> loadTodosTask = new Task<>() {
            @Override
            protected List<TodoItem> call() {
                return DataService.getTodos();
            }
        };

        loadTodosTask.setOnSucceeded(e -> {
            for (TodoItem item : loadTodosTask.getValue()) {
                CheckBox cb = new CheckBox(item.getTask());
                cb.setSelected(item.isDone());
                cb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    DataService.updateTodoStatus(item.getId(), newVal);
                    if (newVal) {
                        cb.getStyleClass().add("done");
                    } else {
                        cb.getStyleClass().remove("done");
                    }
                });
                if (item.isDone()) {
                    cb.getStyleClass().add("done");
                }
                todoListView.getItems().add(cb);
            }
        });
        new Thread(loadTodosTask).start();
    }

    @FXML
    private void handleAddTodo() {
        String task = todoTextField.getText();
        if (task != null && !task.trim().isEmpty()) {
            DataService.addTodo(task.trim());
            todoTextField.clear();
            loadTodos();
        }
    }

    @FXML
    private void handleDeleteCompleted() {
        boolean confirmed = AlertHelper.showConfirmation("Bestätigen", "Erledigte To-Dos löschen?",
                "Möchten Sie wirklich alle als erledigt markierten Aufgaben endgültig entfernen?");
        if (confirmed) {
            DataService.deleteCompletedTodos();
            loadTodos();
        }
    }

    private void refreshData() {
        loadStats();
        loadTodos();
    }

    @FXML
    private void handleNewData(ActionEvent event) {
        NavigationService.openWindow(MAIN_VIEW_PATH, "Neuen Datensatz anlegen", MAIN_VIEW_STYLE_PATH, stage -> refreshData(), null);
    }

    @FXML
    private void handleManageData(ActionEvent event) {
        NavigationService.openWindow(DATA_VIEW_PATH, "Datensätze anzeigen/verwalten", DATA_VIEW_STYLE_PATH, stage -> refreshData(), null);
    }

    @FXML
    private void handleShowData(ActionEvent event) {
        NavigationService.openWindow(DATA_VIEW_PATH, "Datensätze anzeigen/verwalten", DATA_VIEW_STYLE_PATH, stage -> refreshData(), null);
    }

    @FXML
    private void handleCreateReport(ActionEvent event) {
        NavigationService.openWindow(DATA_VIEW_PATH, "Datensatz für Bericht auswählen", DATA_VIEW_STYLE_PATH, stage -> refreshData(), null);
    }
}

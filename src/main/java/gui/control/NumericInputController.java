package gui.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumericInputController {

    @FXML private HBox root;
    @FXML private Label label;
    @FXML private TextField textField;

    public void setLabel(String text) {
        label.setText(text);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public TextField getTextField() {
        return textField;
    }

    public HBox getRoot() {
        return root;
    }
}

package seedu.address.ui;

import static seedu.address.ui.KeyBindController.handleKeyEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.security.PasswordManager;


/**
 * Controller class for the PasswordPromptDialog.
 */
public class PasswordPromptDialogController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;
    private Stage dialog;
    private String existingPassword;

    public void setDialog(Stage dialog) {
        this.dialog = dialog;
    }

    public void setExistingPassword(String existingPassword) {
        this.existingPassword = existingPassword;
    }

    public void setPasswordLabel() {
        if (existingPassword == null) {
            passwordLabel.setText("Please set a new password:");
        } else {
            passwordLabel.setText("Please enter your password:");
        }
    }


    /**
     * Handles the confirm button press.
     */
    @FXML
    private void handleConfirm() {
        String inputPassword = passwordField.getText();
        if (existingPassword == null) {
            PasswordManager.savePassword(inputPassword, null);
            dialog.close();
        } else if (PasswordManager.isPasswordCorrect(inputPassword, null)) {
            dialog.close();
        } else {
            showAlert("Incorrect Password!", "Please try again.");
        }
    }

    @FXML
    private void handleCancel() {
        dialog.close();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        KeyBind confirmPasswordKeyBind = new KeyBind(KeyCode.ENTER, ()
                -> handleConfirm());
        handleKeyEvent(event, confirmPasswordKeyBind);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);

        // Apply font styling to the alert's dialog pane
        alert.getDialogPane().setStyle(
                "-fx-font-family: 'Consolas'; -fx-font-size: 13pt; -fx-font-weight: bold;"
        );
        alert.showAndWait();
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

}

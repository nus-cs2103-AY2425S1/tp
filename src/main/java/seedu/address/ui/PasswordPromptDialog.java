package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.security.PasswordManager;

/**
 * The PasswordPromptDialog class provides a modal dialog for users to enter
 * or set their password. It interacts with the PasswordManager to handle
 * password storage and validation.
 */
public class PasswordPromptDialog {
    /**
     * Displays the password prompt dialog, allowing the user to enter or set
     * their password.
     *
     * @param owner The owner stage of the dialog.
     * @return True if the user successfully entered the correct password or set a new password; false otherwise.
     */
    public static boolean display(Stage owner) {
        // Read the existing password, if any
        String existingPassword = PasswordManager.readPassword();

        // Dialog window.
        // If no existing password set, create a new password.
        String title = existingPassword != null ? "Enter your password"
                                                    : "Please set a new password";
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle(title);

        // Layout for the dialog
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        Scene scene = new Scene(grid, 300, 150);
        dialog.setScene(scene);

        // Labels and password field
        Label label = new Label("Enter Password:");
        PasswordField passwordField = new PasswordField();
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");

        // Grid elements
        grid.add(label, 0, 0);
        grid.add(passwordField, 0, 1);
        grid.add(confirmButton, 0, 2);
        grid.add(cancelButton, 1, 2);

        // If no existing password, prompt for a new password
        confirmButton.setOnAction(e -> {
            String inputPassword = passwordField.getText();
            if (existingPassword == null) {
                // Save new password
                PasswordManager.savePassword(inputPassword);
                dialog.close();
            } else {
                // Check existing password
                if (PasswordManager.isPasswordCorrect(inputPassword)) {
                    dialog.close();
                } else {
                    showAlert("Incorrect Password", "Please try again.");
                }
            }
        });

        // Handle cancel button click
        cancelButton.setOnAction(e -> {
            dialog.close(); // Close the dialog
        });

        dialog.showAndWait(); // Wait for the dialog to close

        // Return true if the password is correct or if setting new password, otherwise false
        return existingPassword == null || PasswordManager.isPasswordCorrect(passwordField.getText());
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

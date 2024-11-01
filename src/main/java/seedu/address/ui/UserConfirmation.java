package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Represents a dialog box that shows a confirmation message to the user.
 */
public class UserConfirmation {

    /**
     * Shows a confirmation dialog with a custom message. Waits for the user to confirm or cancel.
     * @param message The confirmation message to display.
     * @return true if user confirms (presses OK), false if canceled.
     */
    public static boolean getConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Required");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}

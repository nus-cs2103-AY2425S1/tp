package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Confirmation Window Ui to obtain users' confirmation.
 */
public class ConfirmationWindow {
    // Singleton instance
    private static ConfirmationWindow theConfirmationWindow = null;
    private ConfirmationWindow() {
    }

    /**
     * Returns the singleton instance of the Confirmation class.
     *
     * @return the single instance of Confirmation
     */
    public static ConfirmationWindow getInstance() {
        if (theConfirmationWindow == null) {
            theConfirmationWindow = new ConfirmationWindow();
        }
        return theConfirmationWindow;
    }

    /**
     * Displays a confirmation alert dialog and waits for the user's response.
     *
     * @param title Title of the alert dialog
     * @param contentText Content text to display in the alert dialog
     * @return true if the user clicks OK; false otherwise
     */
    public boolean showAlertDialogAndWait(String title, String contentText) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        // Show dialog and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}

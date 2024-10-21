package seedu.address.logic;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Confirmation to execute commands.
 */
public class Confirmation {
    // Singleton instance
    private static Confirmation theConfirmation = null;
    private Confirmation() {
    }

    /**
     * Returns the singleton instance of the Confirmation class.
     *
     * @return the single instance of Confirmation
     */
    public static Confirmation getInstance() {
        if (theConfirmation == null) {
            theConfirmation = new Confirmation();
        }
        return theConfirmation;
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

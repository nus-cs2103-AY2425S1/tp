package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 * Represents a confirmation dialog used to get user confirmation for certain actions,
 * such as deleting a client.
 */
public class ConfirmationDialog {

    /**
     * Displays a confirmation dialog to the user with the given client name.
     *
     * @param clientName The name of the client that the user is being asked to confirm deletion for.
     * @return {@code true} if the user confirms the action by pressing OK, {@code false} otherwise.
     */
    public static boolean showDeleteConfirmation(String clientName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete " + clientName + "?");
        alert.setContentText(clientName + " has active listings!");

        alert.initModality(Modality.APPLICATION_MODAL);

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.setOnShowing(e -> alert.getDialogPane().lookupButton(yesButton).requestFocus());

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }
}

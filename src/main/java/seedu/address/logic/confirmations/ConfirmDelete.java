package seedu.address.logic.confirmations;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;

/**
 * Prompts the user to confirm the deletion of a person
 */
public class ConfirmDelete {
    public static final String MESSAGE_CONFIRM_DELETE = "Are you sure you want to delete Person: %1$s ?";

    /**
     * Displays a confirmation dialog to prompt the user to confirm the deletion.
     * @param personToDelete The person the user wants to delete
     * @return Whether the deletion proceeds or not
     */
    public static boolean showConfirmationDialog(Person personToDelete) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(String.format(MESSAGE_CONFIRM_DELETE, Messages.format(personToDelete)));

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}

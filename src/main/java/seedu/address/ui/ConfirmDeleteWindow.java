package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;

/**
 * Handles the prompt to confirm a deletion
 */
public class ConfirmDeleteWindow extends UiPart<Stage> {
    public static final String MESSAGE_CONFIRM_DELETE = "Are you sure you want to delete Person: %1$s?";
    private static final Logger logger = LogsCenter.getLogger(ConfirmDeleteWindow.class);
    private static final String FXML = "ConfirmDeleteWindow.fxml";

    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label messageLabel;

    private boolean isConfirmed;

    public ConfirmDeleteWindow(Stage root) {
        super(FXML, root);
    }

    public ConfirmDeleteWindow() {
        this(new Stage());
    }

    /**
     * Displays the confirmation dialog
     * @param personToDelete The person to be deleted
     */
    public void show(Person personToDelete) {
        logger.fine("Showing delete confirmation page for: " + personToDelete.getName());
        String message = String.format(MESSAGE_CONFIRM_DELETE, Messages.format(personToDelete))
                + "\n" + Messages.MESSAGE_CANNOT_UNDO;
        messageLabel.setText(message);
        getRoot().showAndWait();
        getRoot().centerOnScreen();
    }

    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    @FXML
    private void handleYes() {
        this.isConfirmed = true;
        getRoot().hide();
    }

    @FXML
    private void handleNo() {
        this.isConfirmed = false;
        getRoot().hide();
    }
}

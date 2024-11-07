package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;

/**
 * Handles the prompt to confirm a deletion
 */
public class ConfirmClearWindow extends UiPart<Stage> {
    public static final String MESSAGE_CONFIRM_CLEAR = "Are you sure you want to clear the FART Book?";
    private static final Logger logger = LogsCenter.getLogger(ConfirmClearWindow.class);
    private static final String FXML = "ConfirmClearWindow.fxml";

    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label messageLabel;

    private boolean isConfirmed;

    public ConfirmClearWindow(Stage root) {
        super(FXML, root);
    }

    public ConfirmClearWindow() {
        this(new Stage());
    }

    /**
     * Displays the confirmation dialog
     */
    public void show() {
        logger.fine("Showing clear confirmation page");
        String message = MESSAGE_CONFIRM_CLEAR + "\n" + Messages.MESSAGE_CANNOT_UNDO;
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

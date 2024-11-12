package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Person;

/**
 * Handles the prompt to confirm an addition of a duplicate person
 */
public class DuplicateWarningWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(DuplicateWarningWindow.class);
    private static final String FXML = "DuplicateWarningWindow.fxml";

    private boolean isConfirmed;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;
    @FXML
    private Label messageLabel;

    public DuplicateWarningWindow(Stage root) {
        super(FXML, root);
    }

    public DuplicateWarningWindow() {
        this(new Stage());
    }

    /**
     * Displays the confirmation dialog
     * @param duplicatePerson The person to be added
     */
    public void show(Person duplicatePerson) {
        logger.fine("Showing duplicate contact warning for: " + duplicatePerson.getName());
        String message = String.format(AddCommand.MESSAGE_DUPLICATE_PERSON, duplicatePerson.getName());
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

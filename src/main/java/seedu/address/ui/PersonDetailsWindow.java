package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Controller for the person details window.
 * This window allows users to view and edit the details of a selected person.
 * It provides editable text fields for the person's name, email, phone number, and address,
 * along with "Save" and "Cancel" buttons to handle user actions.
 */

public class PersonDetailsWindow extends UiPart<Stage> {

    private static final String FXML = "PersonDetailsWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Logic logic;
    private Person person;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    /**
     * Creates a new PersonDetailsWindow.
     *
     * @param root Stage to use as the root of the PersonDetailsWindow.
     */
    public PersonDetailsWindow(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;
        getRoot().setAlwaysOnTop(true);
    }

    /**
     * Create a new PersonDetailsWindow。
     *
     * @param logic to execute instructions
     */
    public PersonDetailsWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Creates a new PersonDetailsWindow.
     */
    public void show(Person person) {
        logger.fine("Showing detailed page about this person");
        this.person = person;
        nameField.setText(person.getName().fullName);
        emailField.setText(person.getEmail().value);
        phoneField.setText(person.getPhone().value);
        addressField.setText(person.getAddress().value);
        if (!getRoot().isShowing()) {
            getRoot().show();
        } else {
            getRoot().toFront();
            getRoot().requestFocus();
        }
    }

    /**
     * Deal with the operation with Save button
     */
    @FXML
    private void handleSave() {
        try {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPhone = phoneField.getText().trim();
            String newAddress = addressField.getText().trim();

            if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("All fields are required.");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;
            }

            int index = logic.getFilteredPersonList().indexOf(person);

            if (index == -1) {
                logger.warning("Person not found in the list.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Person Not Found");
                alert.setHeaderText("The person you are editing was not found.");
                alert.setContentText("The person may have been deleted.");
                alert.showAndWait();
                return;
            }

            String commandText = String.format("edit %d n/%s p/%s e/%s a/%s",
                    index + 1, newName, newPhone, newEmail, newAddress);
            logic.execute(commandText);

            getRoot().hide();

        } catch (CommandException | ParseException e) {
            logger.severe("Failed to edit person: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to save changes.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Deal with cancel button operation
     */
    @FXML
    private void handleCancel() {
        getRoot().hide();
    }

    /**
     * if the window is showing, return true
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * focus on the current window
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * hide personal detail window
     */
    public void hide() {
        getRoot().hide();
    }
}

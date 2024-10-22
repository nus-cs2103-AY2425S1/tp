package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Controller for the person details window.
 * <p>
 * This window allows users to view and edit the details of a selected person.
 * It provides editable text fields for the person's name, email, phone number, and address.
 * Users can navigate between fields using keyboard shortcuts and perform actions like saving or
 * canceling via keyboard inputs.
 * </p>
 */
public class PersonDetailsWindow extends UiPart<Stage> {

    private static final String FXML = "PersonDetailsWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Logic logic;
    private Person person;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    /**
     * Creates a new {@code PersonDetailsWindow} with the given {@code Stage} and {@code Logic}.
     *
     * @param root  Stage to use as the root of the PersonDetailsWindow.
     * @param logic The logic component to execute commands.
     */
    public PersonDetailsWindow(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;
        initializeKeyHandlers();
    }

    /**
     * Creates a new {@code PersonDetailsWindow} with the given {@code Logic}.
     * A new {@code Stage} is created as the root.
     *
     * @param logic The logic component to execute commands.
     */
    public PersonDetailsWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Initializes keyboard event handlers for navigation and actions.
     */
    private void initializeKeyHandlers() {
        // Add key handlers to each text field
        addKeyHandlers(nameField);
        addKeyHandlers(phoneField);
        addKeyHandlers(emailField);
        addKeyHandlers(addressField);
    }

    /**
     * Adds keyboard event handlers to a specific {@code TextField}.
     *
     * @param textField The TextField to which the handlers are added.
     */
    private void addKeyHandlers(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DOWN) {
                moveFocusToNext(textField);
                event.consume();
            } else if (event.getCode() == KeyCode.UP) {
                moveFocusToPrevious(textField);
                event.consume();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                handleCancel();
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                handleSave();
                event.consume();
            }
        });
    }

    /**
     * Moves focus to the next {@code TextField} in the form.
     *
     * @param current The current TextField.
     */
    private void moveFocusToNext(TextField current) {
        assert current != null : "Current TextField must not be null";
        if (current == nameField) {
            phoneField.requestFocus();
        } else if (current == phoneField) {
            emailField.requestFocus();
        } else if (current == emailField) {
            addressField.requestFocus();
        } else if (current == addressField) {
            nameField.requestFocus();
        }
    }

    /**
     * Moves focus to the previous {@code TextField} in the form.
     *
     * @param current The current TextField.
     */
    private void moveFocusToPrevious(TextField current) {
        if (current == addressField) {
            emailField.requestFocus();
        } else if (current == emailField) {
            phoneField.requestFocus();
        } else if (current == phoneField) {
            nameField.requestFocus();
        } else if (current == nameField) {
            addressField.requestFocus();
        }
    }

    /**
     * Displays the person details window with the information of the specified {@code Person}.
     *
     * @param person The person whose details are to be displayed and edited.
     */
    public void show(Person person) {
        assert person != null : "Person must not be null";
        logger.fine("Showing detailed page about this person.");
        this.person = person;
        populateFields(person);

        if (!getRoot().isShowing()) {
            getRoot().show();
        } else {
            getRoot().toFront();
            getRoot().requestFocus();
        }

        // Set focus to the first input field
        nameField.requestFocus();
    }

    /**
     * Populates the text fields with the details of the specified {@code Person}.
     *
     * @param person The person whose details are to be displayed.
     */
    private void populateFields(Person person) {
        assert person.getName() != null : "Person name must not be null";
        assert person.getPhone() != null : "Person phone must not be null";
        assert person.getEmail() != null : "Person email must not be null";
        assert person.getAddress() != null : "Person address must not be null";
        nameField.setText(person.getName().fullName);
        phoneField.setText(person.getPhone().value);
        emailField.setText(person.getEmail().value);
        addressField.setText(person.getAddress().value);
    }

    /**
     * Handles the save action triggered by pressing the ENTER key.
     * Validates the input, constructs an edit command, and executes it to update the person's information.
     * If successful, the window is closed. Otherwise, an error alert is shown to the user.
     */
    private void handleSave() {
        try {
            String newName = nameField.getText().trim();
            String newPhone = phoneField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newAddress = addressField.getText().trim();

            if (newName.isEmpty() || newPhone.isEmpty() || newEmail.isEmpty() || newAddress.isEmpty()) {
                showErrorAlert("Invalid Input", "All fields are required.", "Please fill in all fields.");
                return;
            }

            int index = logic.getFilteredPersonList().indexOf(person);

            if (index == -1) {
                logger.warning("Person not found in the list.");
                showErrorAlert("Person Not Found", "The person you are editing was not found.",
                        "The person may have been deleted.");
                return;
            }

            String commandText = String.format("edit %d n/%s p/%s e/%s a/%s",
                    index + 1, escapeSpecialCharacters(newName), escapeSpecialCharacters(newPhone),
                    escapeSpecialCharacters(newEmail), escapeSpecialCharacters(newAddress));

            logic.execute(commandText);

            getRoot().hide();

        } catch (CommandException | ParseException e) {
            logger.severe("Failed to edit person: " + e.getMessage());
            showErrorAlert("Error", "Failed to save changes.", e.getMessage());
        }
    }

    /**
     * Handles the cancel action triggered by pressing the ESC key.
     * Closes the person details window without saving any changes.
     */
    private void handleCancel() {
        getRoot().hide();
    }

    /**
     * Displays an error alert with the specified title, header, and content.
     *
     * @param title   The title of the alert dialog.
     * @param header  The header text of the alert dialog.
     * @param content The content text of the alert dialog.
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(getRoot());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Escapes special characters in the input string to prevent command parsing issues.
     *
     * @param input The input string to escape.
     * @return The escaped string.
     */
    private String escapeSpecialCharacters(String input) {
        return input.replace("\\", "\\\\").replace("/", "\\/");
    }

    /**
     * Returns true if the person details window is currently being shown.
     *
     * @return {@code true} if the window is showing, {@code false} otherwise.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Brings the person details window to focus.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Hides the person details window.
     */
    public void hide() {
        getRoot().hide();
    }
}

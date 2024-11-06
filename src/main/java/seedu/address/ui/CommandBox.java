package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        if (isAddLogCommand(commandText) && !commandText.contains("l/")) {
            // Disable commandTextField and trigger the popup for log entry
            commandTextField.setDisable(true);
            AddLogPopup.display(
                    logEntry -> {
                        // Replace actual newline characters with \n
                        String encodedLogEntry = logEntry.replace("\n", "\\n");
                        String commandWithLog = commandText + " l/" + encodedLogEntry;
                        executeCommand(commandWithLog);
                        commandTextField.setDisable(false);
                    },
                    () -> {
                        // Callback for cancel action
                        commandTextField.setDisable(false);
                    }
            );
        } else {
            // Handle command normally if l/ is present or if it's not an addlog command
            executeCommand(commandText);
        }
    }

    /**
     * Executes the given command text through the command executor and handles exceptions.
     */
    private void executeCommand(String commandText) {
        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Validates if the input command is an addlog command with the required format.
     * Basic regex can be customized based on the exact expected format.
     */
    private boolean isAddLogCommand(String input) {

        //TODO: Instead of using a hardcoded regex, change it to use actual validation of both date and NRIC
        // Regex to match "addlog i/NRIC d/DATE" format where DATE is in "dd MMM yyyy" format
        return input.matches("addlog i/S7783844i d/21 jan 2024");
    }


    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

package seedu.address.ui;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private final CommandCompleter commandCompleter;

    @FXML
    private TextField commandTextField;

    @FXML
    private Label commandTextAutocomplete;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, CommandCompleter commandCompleter) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandCompleter = commandCompleter;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.textProperty().addListener((unused1, unused2, commandText) -> handleTextChanged(commandText));

        // handle tab keypress event to auto-complete command
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                handleAutocomplete();
                event.consume();
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles when the command input text is changed.
     */
    private void handleTextChanged(String commandText) {
        commandTextAutocomplete.setText("");
        if (commandText.isEmpty() || commandText.contains(" ")) {
            return;
        }

        String[] commandWords = commandText.split("\\s+");
        if (commandWords.length != 1) {
            return;
        }

        String commandName = commandWords[0];
        Optional<String> possibleCommandName = commandCompleter.completeCommand(commandName);

        if (possibleCommandName.isEmpty()) {
            return;
        }

        commandTextAutocomplete.setText(possibleCommandName.get());
    }

    /**
     * Updates the command text on autocompletion of command name.
     */
    private void handleAutocomplete() {
        String commandText = commandTextField.getText();
        if (commandText.isEmpty() || commandText.contains(" ")) {
            return;
        }

        Optional<String> possibleCommandName = commandCompleter.completeCommand(commandText);

        if (possibleCommandName.isEmpty()) {
            return;
        }
        commandTextField.setText(possibleCommandName.get() + " ");
        commandTextField.end(); // set cursor to the end
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

    /**
     * Represents a function that can give completed commands from a command prefix.
     */
    public interface CommandCompleter {
        Optional<String> completeCommand(String commandText);
    }
}

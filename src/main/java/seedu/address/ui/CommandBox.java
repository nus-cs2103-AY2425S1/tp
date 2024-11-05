package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    // Attributes for command history
    private int commandHistoryIndex = -1;
    private List<String> commandHistory = new ArrayList<>();

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // Listens for key presses on the command box.
        commandTextField.setOnKeyPressed(this::handleKeyPress);
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            navigateToPreviousCommand();
            keyEvent.consume();
            break;
        case DOWN:
            navigateToNextCommand();
            keyEvent.consume();
            break;
        default:
                // do nothing
        }
    }

    private void navigateToPreviousCommand() {
        if (commandHistoryIndex > 0) {
            commandHistoryIndex--;
            commandTextField.setText(commandHistory.get(commandHistoryIndex));
            commandTextField.positionCaret(commandTextField.getText().length());
        } else if (commandHistoryIndex == 0) {
            commandTextField.setText(commandHistory.get(0));
            commandTextField.positionCaret(commandTextField.getText().length());
        } else {
            commandTextField.clear();
        }
    }

    private void navigateToNextCommand() {
        if (commandHistoryIndex < commandHistory.size() - 1) {
            commandHistoryIndex++;
            commandTextField.setText(commandHistory.get(commandHistoryIndex));
            commandTextField.positionCaret(commandTextField.getText().length());
        } else if (commandHistoryIndex == commandHistory.size() - 1) {
            commandHistoryIndex = commandHistory.size();
            commandTextField.clear();
        } else {
            commandTextField.clear();
        }
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

        } finally {
            // Update command history
            commandHistory.add(commandText);
            commandHistoryIndex = commandHistory.size();
        }
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

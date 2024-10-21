package seedu.address.ui;

import java.util.ArrayList;

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

    private final ArrayList<String> history = new ArrayList<>();
    private int historyIndex = 0;

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

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered(KeyEvent event) {
        String commandText = commandTextField.getText();
        switch(event.getCode()) {
        case ENTER:
            if (commandText.equals("")) {
                return;
            }

            history.add(commandText);
            try {
                commandExecutor.execute(commandText);
                commandTextField.setText("");
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }

            historyIndex = history.size();
            break;
        case UP:
            historyIndex -= 1;
            setCommandFieldToHistory();
            break;
        case DOWN:
            historyIndex += 1;
            setCommandFieldToHistory();
            break;
        default:
            break; // do nothing on default
            // can even add effects or anything or update statusbar
        }
    }

    private void setCommandFieldToHistory() {
        if (history.isEmpty()) {
            return;
        }

        if (historyIndex < 0) {
            historyIndex = 0;
        }

        if (historyIndex > history.size()) {
            historyIndex = history.size();
        }

        if (historyIndex == history.size()) {
            commandTextField.setText("");
        } else {
            commandTextField.setText(history.get(historyIndex));
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

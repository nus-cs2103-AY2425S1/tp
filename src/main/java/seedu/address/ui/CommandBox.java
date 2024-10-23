package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.CommandGetterResult;
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
    private final CommandGetter getEarlierCommandGetterResult;
    private final CommandGetter getLaterCommandGetterResult;

    private CommandGetterResult commandGetterResult;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor,
                      CommandGetter getEarlierCommandGetterResult, CommandGetter getLaterCommandGetterResult) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.getEarlierCommandGetterResult = getEarlierCommandGetterResult;
        this.getLaterCommandGetterResult = getLaterCommandGetterResult;
        this.commandGetterResult = new CommandGetterResult("", false);
        setArrowKeyHandler(commandTextField, getEarlierCommandGetterResult, getLaterCommandGetterResult, this);
        setUserTypeHandler(commandTextField, this);
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
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
     * Sets the handler for the up or down arrow key pressed event.
     */
    private static void setArrowKeyHandler(TextField textField, CommandGetter getEarlierCommand,
                                    CommandGetter getLaterCommand, CommandBox cb) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                cb.commandGetterResult = getEarlierCommand
                        .getCommandGetterResult(cb.commandGetterResult.updateStringToDisplay(textField.getText()));
                cb.commandTextField.setText(cb.commandGetterResult.getStringToDisplay());
                textField.positionCaret(textField.getText().length());
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                cb.commandGetterResult = getLaterCommand
                        .getCommandGetterResult(cb.commandGetterResult.updateStringToDisplay(textField.getText()));
                cb.commandTextField.setText(cb.commandGetterResult.getStringToDisplay());
                textField.positionCaret(textField.getText().length());
            }
        });
    }

    /**
     * Sets the handler for when the user types valid unicode.
     */
    private static void setUserTypeHandler(TextField textField, CommandBox cb) {
        textField.setOnKeyTyped(keyEvent -> {
            if (!keyEvent.getCharacter().equals(KeyEvent.CHAR_UNDEFINED)) {
                cb.commandGetterResult = cb.commandGetterResult.updateIsModified(true);
            }
        });
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
     * Represents a function that simply returns a String.
     * Used primarily for command history.
     */
    @FunctionalInterface
    public interface CommandGetter {
        CommandGetterResult getCommandGetterResult(CommandGetterResult commandGetterResult);
    }

}

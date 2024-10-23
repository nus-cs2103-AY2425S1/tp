package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
        this.commandGetterResult = new CommandGetterResult("", "");
        setArrowKeyHandler(commandTextField, getEarlierCommandGetterResult, getLaterCommandGetterResult);
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
     * Handles the up or down arrow key pressed event.
     */
    private void setArrowKeyHandler(TextField textField, CommandGetter getEarlierCommand,
                                    CommandGetter getLaterCommand) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP) {
                CommandGetterResult result = commandGetterResult.updateOriginalTypedString(textField.getText());
                result = getEarlierCommand.getCommandGetterResult(result);
                commandTextField.setText(result.getStringToDisplay());
            } else if (keyEvent.getCode() == KeyCode.DOWN) {
                CommandGetterResult result = commandGetterResult.updateOriginalTypedString(textField.getText());
                result = getLaterCommand.getCommandGetterResult(result);
                commandTextField.setText(result.getStringToDisplay());
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

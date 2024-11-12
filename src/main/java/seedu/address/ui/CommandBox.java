package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
    private CommandBuffer commandBuffer;

    private boolean isCurrentViewingHistory = false;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandBuffer = new CommandBuffer();
        // create a listener for the up and down key events
        commandTextField.setOnKeyPressed(event -> {
            setStyleToDefault();
            if (event.getCode() == KeyCode.UP) {
                handleUpKey();
            } else if (event.getCode() == KeyCode.DOWN) {
                handleDownKey();
            }
        });
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
            commandBuffer.addCommand(commandText);
            commandExecutor.execute(commandText);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        } finally {
            commandTextField.setText("");
            isCurrentViewingHistory = false;
        }
    }

    /**
     * Handles the Up Arrow button pressed event.
     */
    private void handleUpKey() {
        String legacyCommand = commandBuffer.handleUpInput(isCurrentViewingHistory);
        commandTextField.setText(legacyCommand);
        isCurrentViewingHistory = true;
    }

    /**
     * Handles the Down Arrow button pressed event.
     */
    private void handleDownKey() {
        String legacyCommand = commandBuffer.handleDownInput();
        commandTextField.setText(legacyCommand);
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

package bizbook.ui;

import bizbook.logic.commands.exceptions.CommandException;
import bizbook.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandHistory commandHistory;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor} and {@code CommandHistory}.
     */
    public CommandBox(CommandExecutor commandExecutor, CommandHistory commandHistory) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = commandHistory;

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
            commandHistory.addCommand(commandText);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Populates command box with {@param text}.
     */
    private void autoCompleteText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length()); // Move cursor to end
    }

    /**
     * Handles the key press event, {@code KeyEvent}.
     */
    @FXML
    private void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            autoCompleteText(commandHistory.getPreviousCommand());
            break;
        case DOWN:
            autoCompleteText(commandHistory.getNextCommand());
            break;
        default:
            break;
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
}

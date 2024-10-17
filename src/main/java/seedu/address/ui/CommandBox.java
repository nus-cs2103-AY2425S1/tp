package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextArea commandTextArea;

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        commandTextArea.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        // handle "Enter" key press
        commandTextArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
                event.consume(); // prevent newline (\n character) from being entered in the TextArea automatically
            }
        });
    }

    private void handleCommandEntered() {
        String commandText = commandTextArea.getText().trim();
        if (commandText.isEmpty()) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextArea.setText(""); // Clear the command text area after execution
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    private void setStyleToDefault() {
        commandTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();
        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
    }

    @FunctionalInterface
    public interface CommandExecutor {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}

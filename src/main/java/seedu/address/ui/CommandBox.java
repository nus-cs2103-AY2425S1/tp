package seedu.address.ui;

import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.commandpopup.AutoSuggestionTextField;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private CommandHistory commandHistory = new CommandHistory();
    private final SortedSet<String> entries;

    @FXML
    private AutoSuggestionTextField commandTextField;



    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.entries = new TreeSet<>();
        commandTextField.setCommandBox(this);
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * initialize() method from javaFx, used to check for arrow inputs inside the commandTextField
     */
    @FXML
    public void initialize() {
        commandTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                String previousCommand = commandHistory.getPreviousCommand();
                if (previousCommand != null) {
                    commandTextField.setText(previousCommand);
                    commandTextField.positionCaret(previousCommand.length()); // Move cursor to end
                }
            } else if (event.getCode() == KeyCode.DOWN) {
                String nextCommand = commandHistory.getNextCommand();
                if (nextCommand != null) {
                    commandTextField.setText(nextCommand);
                    commandTextField.positionCaret(nextCommand.length()); // Move cursor to end
                }
            }
        });
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    public void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
            return;
        }
        commandHistory.addCommand(commandText);
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
        commandTextField.hidePopup();
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

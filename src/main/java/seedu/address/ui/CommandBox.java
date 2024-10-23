package seedu.address.ui;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final double MAX_HEIGHT = 200;
    private static final double INITIAL_HEIGHT = 40;

    private final CommandExecutor commandExecutor;

    @FXML
    private TextArea commandTextArea;
    @FXML
    private FontIcon commandTextIcon;
    @FXML
    private HBox commandTextContainer;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextArea.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
        });

        commandTextArea.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                setStyleToFocused();
            } else {
                setStyleToUnfocused();
            }
        });

        initialiseEventHandler();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextArea.getText();
        if (commandText.equals("")) {
            return;
        }
        try {
            commandExecutor.execute(commandText);
            commandTextArea.setText("");
        } catch (CommandException | ParseException e) {
            commandTextArea.setText(commandText.replace("\n", ""));
            setStyleToIndicateCommandFailure();
            commandTextArea.positionCaret(commandText.length() - 1);
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextIcon.setIconColor(Color.WHITE);
        commandTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
        commandTextArea.setStyle("-fx-text-fill: white;");
    }

    /** Sets the command box style to indicate the command box is focused. */
    private void setStyleToFocused() {
        commandTextIcon.setIconColor(Color.WHITE);
        if (commandTextArea.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            return;
        }
        commandTextArea.setStyle("-fx-text-fill: white;");
    }

    /** Sets the command box style to indicate the command box is not focused. */
    private void setStyleToUnfocused() {
        commandTextIcon.setIconColor(Color.GREY);
        if (commandTextArea.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            return;
        }
        commandTextArea.setStyle("-fx-text-fill: grey;");
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();
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
     * Initialises the Event Handler for the input of commands.
     */
    private void initialiseEventHandler() {
        commandTextArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case ENTER:
                handleCommandEntered();
                event.consume();
                break;
            default:
                break;
            }
        });
    }
    /**
     * Adjusts the height of the TextArea based on its content.
     */
    private void adjustTextAreaHeight() {
        // To be implemented
    }
}

package seedu.address.ui;

import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AutocompleteParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final AutocompleteParser autocompleteParser;

    @FXML
    private TextField commandTextField;
    @FXML
    private ContextMenu autoComplete;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, Logic logic) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.autocompleteParser = new AutocompleteParser();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
            // Call method to update and show the autocomplete suggestions
            updateAutoComplete(autocompleteParser.parseCommand(commandTextField.getText(), logic.getAddressBook(),
                    newPosition.intValue()));
        });
    }

    /**
     * Updates the autocomplete context menu with suggestions based on the current input.
     */
    private void updateAutoComplete(HashMap<String, String> suggestions) {
        autoComplete.getItems().clear();

        if (suggestions.isEmpty()) {
            autoComplete.hide();
            return;
        }

        for (HashMap.Entry<String, String> suggestion : suggestions.entrySet()) {
            MenuItem item = new MenuItem(suggestion.getKey());
            item.setOnAction(e -> {
                commandTextField.setText(suggestion.getValue());
                commandTextField.positionCaret(suggestion.getValue().length());
                autoComplete.hide();
            });
            autoComplete.getItems().add(item);
        }

        autoComplete.show(commandTextField, Side.BOTTOM, 0, 0);
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

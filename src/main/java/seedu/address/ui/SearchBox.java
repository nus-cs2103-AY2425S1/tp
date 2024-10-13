package seedu.address.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving find command
 * without requiring command word.
 */
public class SearchBox extends UiPart<Region> {

    private static final String FXML = "SearchBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField searchBoxField;

    /**
     * Creates a {@code SearchBox} with the given {@code CommandExecutor}.
     */
    public SearchBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        searchBoxField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleCommandUpdate();
            }
        });
    }

    /**
     * Handles the text field being updated.
     */
    @FXML
    private void handleCommandUpdate() {
        String commandText = searchBoxField.getText();

        try {
            commandExecutor.execute(String.format("%s %s",
                    FindCommand.COMMAND_WORD, commandText));
        } catch (CommandException | ParseException e) {
            return;
        }
    }

    /**
     * Clears search box input.
     */
    public void clearSearchBox() {
        searchBoxField.setText("");
    }
}

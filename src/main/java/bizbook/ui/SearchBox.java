package bizbook.ui;

import bizbook.logic.commands.FindCommand;
import bizbook.logic.commands.ListCommand;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.logic.parser.exceptions.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving find command
 * without requiring command word.
 */
public class SearchBox extends UiPart<Region> {

    private static final String FXML = "SearchBox.fxml";

    private final CommandExecutor commandExecutor;
    private final ChangeListener<String> searchBoxChangeListener;

    @FXML
    private TextField searchBoxField;

    /**
     * Creates a {@code SearchBox} with the given {@code CommandExecutor}.
     */
    public SearchBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        searchBoxChangeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleCommandUpdate();
            }
        };
        searchBoxField.textProperty().addListener(searchBoxChangeListener);
    }

    /**
     * Handles the text field being updated.
     */
    @FXML
    private void handleCommandUpdate() {
        String commandText = searchBoxField.getText();

        if (commandText.isEmpty()) {
            try {
                commandExecutor.execute(ListCommand.COMMAND_WORD);
            } catch (CommandException | ParseException e) {
                return;
            }
        }

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
        // temporarily disable listener before clearing field
        searchBoxField.textProperty().removeListener(searchBoxChangeListener);
        searchBoxField.setText("");
        searchBoxField.textProperty().addListener(searchBoxChangeListener);
    }
}

package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
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
    private final Suggestions suggestions;

    @FXML
    private TextField commandTextField;
    @FXML
    private TextField suggestionTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.suggestions = new Suggestions();
        setupTextFields();
    }

    private void setupTextFields() {
        suggestionTextField.setMouseTransparent(true);
        suggestionTextField.setFocusTraversable(false);

        // Make suggestion text field transparent and match command text field
        suggestionTextField.setStyle("-fx-background-color: transparent;");
        suggestionTextField.fontProperty().bind(commandTextField.fontProperty());

        // Bind suggestion width to command width
        suggestionTextField.prefWidthProperty().bind(commandTextField.widthProperty());

        // Add listeners for real-time command detection and positioning
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            setStyleToDefault();
            updateSuggestion(newValue);
        });

        // This ensures suggestion updates when the text layout changes
        commandTextField.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            updateSuggestionPosition();
        });
    }

    private void updateSuggestion(String currentText) {
        String fullSuggestion = suggestions.checkAllCommands(currentText);

        if (fullSuggestion.isEmpty() || !fullSuggestion.startsWith(currentText)) {
            suggestionTextField.setText("");
            return;
        }

        // Only show the remaining part of the suggestion
        String remainingSuggestion = fullSuggestion.substring(currentText.length());
        suggestionTextField.setText(remainingSuggestion);

        updateSuggestionPosition();
    }

    private void updateSuggestionPosition() {
        String currentText = commandTextField.getText();

        // Get the text width using a Text node for accurate measurement
        Text text = new Text(currentText);
        text.setFont(commandTextField.getFont());
        double textWidth = text.getLayoutBounds().getWidth();

        // Position the suggestion text field
        suggestionTextField.setTranslateX( textWidth);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.isEmpty()) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.clear();
            suggestionTextField.clear();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        suggestionTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
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

package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final String ADD_COMMAND = "add";
    private static final String ADD_COMMAND_FORMAT = "n/Betsy t/friend e/betsycrowe@example.com " +
            "a/Newgate Prison p/1234567 t/criminal";
    private static final String[] formatPrefixes = {"n/", "p/", "e/", "a/", "t/"};
    private final CommandExecutor commandExecutor;
    private TextField suggestionTextField;
    private FormatSuggestion recomm;

    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        setupTextFields();
        Suggestions recommendations = new Suggestions();
        // Add listeners for real-time command detection and caret position
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            setStyleToDefault();
            recommendations.checkAllCommands(commandTextField, suggestionTextField, newValue);
        });
    }

    private void setupTextFields() {
        suggestionTextField = new TextField();
        suggestionTextField.setMouseTransparent(true);
        suggestionTextField.setFocusTraversable(false);

        // Set styles to match except for color
        String commonStyle = "-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 5px;";
        suggestionTextField.setStyle(commonStyle + "-fx-text-fill: #808080;"); // Light gray for suggestion
        commandTextField.setStyle(commonStyle + "-fx-text-fill: #000000;"); // Black for command

        // Make command text field transparent
        commandTextField.setStyle(commandTextField.getStyle() + "-fx-background-color: transparent;");

        // Get the parent StackPane
        if (commandTextField.getParent() != null) {
            ((StackPane) commandTextField.getParent()).getChildren().remove(commandTextField);
        }

        // Add both text fields to the placeholder with suggestion behind
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().addAll(suggestionTextField, commandTextField);

        // Set proper size bindings
        suggestionTextField.prefWidthProperty().bind(commandBoxPlaceholder.widthProperty());
        suggestionTextField.prefHeightProperty().bind(commandBoxPlaceholder.heightProperty());
        commandTextField.prefWidthProperty().bind(commandBoxPlaceholder.widthProperty());
        commandTextField.prefHeightProperty().bind(commandBoxPlaceholder.heightProperty());
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
            suggestionTextField.setText("");
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

package careconnect.ui;

import careconnect.logic.Logic;
import careconnect.logic.LogicManager;
import careconnect.logic.autocompleter.exceptions.AutocompleteException;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.logic.Logic.ValidateSyntaxResultEnum;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final String VALID_COMMAND_STYLE_CLASS = "valid-command";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final SyntaxValidator syntaxValidator;
    private final CommandAutocompleter commandAutocompleter;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, CommandAutocompleter commandAutocompleter,
                      SyntaxValidator syntaxValidator) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandAutocompleter = commandAutocompleter;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        this.syntaxValidator = syntaxValidator;
    }

    /**
     * Initializes the tab button listener.
     */
    @FXML
    public void initialize() {
        // Add a key listener for the Tab key
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                handleTabPressed();
                event.consume(); // Prevents default behavior
            }
        });
    }

    /**
     * Handles the Tab button pressed event.
     */
    private void handleTabPressed() {
        String commandText = commandTextField.getText();

        try {
            String autocompletedCommand = commandAutocompleter.autocompleteCommand(commandText);
            String autocompletedCommandWithSpace = autocompletedCommand + " ";
            commandTextField.setText(autocompletedCommandWithSpace);
            commandTextField.positionCaret(commandTextField.getText().length());
        } catch (AutocompleteException e) {
            setStyleToIndicateCommandFailure();
        }

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
     * Validates command typed on every key press.
     */
    @FXML
    private void handleCommandTyped() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        ValidateSyntaxResultEnum result = syntaxValidator.validateSyntax(commandText);
        System.out.println(result);
        System.out.println(commandTextField.getStyleClass());
        switch (result) {
        case VALID_COMMAND_WORD:
            this.setStyleToIndicateValidCommandWord();
            break;
        case VALID_FULL_COMMAND:
            this.setStyleToDefault();
            break;
        case INVALID_COMMAND:
            this.setStyleToIndicateCommandFailure();
            break;
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        // logic should assert there exists at max 1 occurence of
        // error_style_class or valid_command_class at any time
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        commandTextField.getStyleClass().remove(VALID_COMMAND_STYLE_CLASS);
        assert(!commandTextField.getStyleClass().contains(VALID_COMMAND_STYLE_CLASS));
        assert(!commandTextField.getStyleClass().contains(ERROR_STYLE_CLASS));
    }

    /**
     * Sets the command box style to indicate a valid command word
     */
    private void setStyleToIndicateValidCommandWord() {
        // logic should assert there exists at max 1 occurence of
        // error_style_class or valid_command_style_class at any time
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        // remove error style if exist
        styleClass.remove(ERROR_STYLE_CLASS);

        // add valid command style if dont exist
        if (styleClass.contains(VALID_COMMAND_STYLE_CLASS)) {
            return;
        }

        styleClass.add(VALID_COMMAND_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a incorrect / failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        // remove valid style class if exist
        styleClass.remove(VALID_COMMAND_STYLE_CLASS);

        // add error style if dont exist
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
        assert(styleClass.contains(ERROR_STYLE_CLASS));
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that can autocomplete commands.
     */
    @FunctionalInterface
    public interface CommandAutocompleter {
        /**
         * Autocompletes the command and returns the suggestion.
         *
         * @see Logic#autocompleteCommand(String)
         */
        String autocompleteCommand(String commandText) throws AutocompleteException;
    }

    /**
     * Represents a function that can validate syntax
     */
    @FunctionalInterface
    public interface SyntaxValidator {
        /**
         * Checks if the string provided is valid syntax
         *
         * @see Logic#validateSyntax(String)
         */
        ValidateSyntaxResultEnum validateSyntax(String syntax);
    }

}

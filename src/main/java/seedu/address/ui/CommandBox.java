package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final ObservableList<String> COMMANDS = FXCollections.observableArrayList(
            "cadd", "cedit", "clear", "exit", "find", "radd", "redit", "rview"
    );
    private static final ObservableList<String> NO_PARAMETERS = FXCollections.observableArrayList();
    private static final ObservableList<String> CADD_PARAMETERS = FXCollections.observableArrayList(
            PREFIX_EMAIL.getPrefix(), PREFIX_NAME.getPrefix(), PREFIX_PHONE.getPrefix()
    );
    private static final ObservableList<String> CEDIT_PARAMETERS = FXCollections.observableArrayList(
            PREFIX_EMAIL.getPrefix(), PREFIX_NAME.getPrefix(), PREFIX_PHONE.getPrefix()
    );
    private static final ObservableList<String> FIND_PARAMETERS = FXCollections.observableArrayList(
            PREFIX_KEYWORD.getPrefix()
    );
    private static final ObservableList<String> RADD_PARAMETERS = FXCollections.observableArrayList(
            PREFIX_ADDRESS.getPrefix(), PREFIX_CUSTOMER_LIST.getPrefix(), PREFIX_DEPOSIT.getPrefix(),
            PREFIX_RENT_DUE_DATE.getPrefix(), PREFIX_RENTAL_END_DATE.getPrefix(), PREFIX_MONTHLY_RENT.getPrefix(),
            PREFIX_RENTAL_START_DATE.getPrefix()
    );
    private static final ObservableList<String> REDIT_PARAMETERS = FXCollections.observableArrayList(
            PREFIX_ADDRESS.getPrefix(), PREFIX_CLIENT_INDEX.getPrefix(), PREFIX_CUSTOMER_LIST.getPrefix(),
            PREFIX_DEPOSIT.getPrefix(), PREFIX_RENT_DUE_DATE.getPrefix(), PREFIX_RENTAL_END_DATE.getPrefix(),
            PREFIX_MONTHLY_RENT.getPrefix(), PREFIX_RENTAL_INDEX.getPrefix(), PREFIX_RENTAL_START_DATE.getPrefix()
    );
    private static final ObservableList<String> VALUES = FXCollections.observableArrayList(
            "Ang Mo Kio", "Bishan", "Jurong", "99999999", "12345678", "Steven Tan", "Sebastian"
    );

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    private int currentIndex = -1; // To track the current matching command
    private String lastInput = ""; // To track the last input

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        addKeyPressedEventForCommandTextField();
        addKeyReleasedEventForCommandTextField();
    }

    /**
     * Adds an event filter to the commandTextField (TextField) to handle key pressed events.
     */
    private void addKeyPressedEventForCommandTextField() {
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume(); // Prevent the default tab behavior
                if (commandTextField.getCaretPosition() == commandTextField.getText().length()) {
                    autocomplete();
                }
            } else if (event.getCode() == KeyCode.SPACE) {
                // Reset currentIndex and lastInput when space is pressed
                currentIndex = -1;
                lastInput = "";
                System.out.println("[" + lastInput + "]");
            }
        });
    }

    /**
     * Adds an event filter to the commandTextField (TextField) to handle key released events.
     */
    private void addKeyReleasedEventForCommandTextField() {
        commandTextField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (!isAllowedKey(event.getCode())) {
                //event.consume(); // Prevent the event if the character is not allowed
                return;
            }

            if (event.getCode() == KeyCode.BACK_SPACE) {
                // Reset currentIndex and lastInput when space is pressed
                currentIndex = -1;
            }
            lastInput = getLastInput();
            System.out.println("[" + lastInput + "]");
        });
    }

    /**
     * Returns the (list of) parameters associated with the specified command.
     *
     * @param command The command String for which to return the associated parameters.
     * @return An ObserableList of parameters associated to the specified command.
     */
    private ObservableList<String> getParameters(String command) {
        switch (command) {
        case "cadd":
            return CADD_PARAMETERS;
        case "cedit":
            return CEDIT_PARAMETERS;
        case "find":
            return FIND_PARAMETERS;
        case "radd":
            return RADD_PARAMETERS;
        case "redit":
            return REDIT_PARAMETERS;
        case "clear", "exit":
            return NO_PARAMETERS;
        default:
            return FXCollections.observableArrayList();
        }
    }

    /**
     * Checks if the lastInput starts withs a prefix of any known prefixes.
     *
     * @return True if lastInput starts with any known prefixes; false otherwise.
     */
    private boolean isLastInputPrefix() {
        HashSet<String> allPrefixes = new HashSet<>(Arrays.asList(PREFIX_EMAIL.getPrefix(), PREFIX_NAME.getPrefix(),
                PREFIX_PHONE.getPrefix(), PREFIX_KEYWORD.getPrefix(), PREFIX_CLIENT_INDEX.getPrefix(),
                PREFIX_RENTAL_INDEX.getPrefix(), PREFIX_ADDRESS.getPrefix(), PREFIX_RENTAL_START_DATE.getPrefix(),
                PREFIX_RENTAL_END_DATE.getPrefix(), PREFIX_RENT_DUE_DATE.getPrefix(), PREFIX_MONTHLY_RENT.getPrefix(),
                PREFIX_DEPOSIT.getPrefix(), PREFIX_CUSTOMER_LIST.getPrefix()));

        for (String prefix : allPrefixes) {
            if (lastInput.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a list of matches (String) based on the specified input and parameters.
     *
     * @param input The input String use for finding matches.
     * @param parameters The list of parameters use for finding matches (if needed).
     * @return An ObservableList list of matches (String) based on the specified input and parameters.
     */
    private ObservableList<String> getMatches(String input, ObservableList<String> parameters) {
        ObservableList<String> matches = FXCollections.observableArrayList();

        if (input.isEmpty() || input.isBlank()) {
            matches = COMMANDS;
        } else if (input.contains(" ")) {
            if (lastInput.isEmpty()) {
                matches = parameters;
            } else if (parameters.contains(lastInput + "/")) {
                matches = parameters.filtered(parameter -> parameter.startsWith(lastInput));
            } else {
                if (isLastInputPrefix()) {
                    String[] parts = lastInput.split("/", 2);
                    String content = parts[1].isEmpty() ? "" : parts[1];
                    matches = content.isEmpty() ? parameters : VALUES.filtered(value -> value.startsWith(content));
                } else {
                    return matches;
                }
            }
        } else {
            matches = COMMANDS.filtered(command -> command.startsWith(lastInput));
        }

        return matches;
    }

    /**
     * Processes the list of matches (String) using parameters and words.
     * Then update words to reflect the matches (if any).
     *
     * @param words The list of words to be updated with the matches.
     * @param parameters The list of parameters use to check the existence of prefix.
     * @param matches The list of matches (String) use to update words.
     */
    private void processMatches(ArrayList<String> words, ObservableList<String> parameters,
                                             ObservableList<String> matches) {
        boolean isParameter = false;

        for (int i = words.size() - 1; i >= 0; i--) {
            String currentWord = words.get(i);
            if (currentWord.length() >= 3) {
                if (parameters.contains(currentWord.substring(0, 2))) {
                    String newWord = currentWord.substring(0, 2) + matches.get(currentIndex);
                    words.subList(i + 1, words.size()).clear();
                    words.set(i, newWord);
                    isParameter = true;
                    break;
                } else if (parameters.contains(currentWord.substring(0, 3))) {
                    String newWord = currentWord.substring(0, 3) + matches.get(currentIndex);
                    words.subList(i + 1, words.size()).clear();
                    words.set(i, newWord);
                    isParameter = true;
                    break;
                }
            }
        }

        if (!isParameter) {
            words.set(words.size() - 1, matches.get(currentIndex));
        }
    }

    /**
     * Returns the last word from commandTextField.
     *
     * @return The last word from commandTextField, or an empty String if commandTextField ends with space or invalid.
     */
    private String getLastInput() {
        String input = commandTextField.getText();

        if (input.endsWith(" ")) {
            return "";
        }

        String[] words = input.trim().split("\\s+");
        String lastWord = words.length > 0 ? words[words.length - 1] : "";

        return lastWord;
    }

    /**
     * Performs autocompletion for the commandTextField based on the value of commandTextField.
     */
    private void autocomplete() {
        String input = commandTextField.getText();
        ArrayList<String> words = new ArrayList<>(List.of(input.trim().split("\\s+")));
        String currentCommand = words.get(0);
        ObservableList<String> parameters = getParameters(currentCommand);
        ObservableList<String> matches = getMatches(input, parameters);

        if (!matches.isEmpty()) {
            currentIndex = (currentIndex + 1) % matches.size();

            if (input.isEmpty() || input.isBlank()) {
                words = new ArrayList<>(List.of(matches.get(currentIndex)));
            } else if (COMMANDS.contains(input) && !input.endsWith(" ")) {
                words.set(0, matches.get(currentIndex));
            } else if (!input.contains(" ")) {
                words.set(0, matches.get(currentIndex));
            } else if (parameters.contains(words.get(words.size() - 1))
                    || parameters.contains(words.get(words.size() - 1) + "/")) {
                words.set(words.size() - 1, matches.get(currentIndex));
            } else if (input.endsWith(" ")) {
                words.add(matches.get(currentIndex));
            } else {
                processMatches(words, parameters, matches);
            }
            // Reconstruct the input text
            commandTextField.setText(String.join(" ", words));
            commandTextField.positionCaret(commandTextField.getText().length()); // Move caret to the end
        }
    }

    /**
     * Checks if the given key code is alphanumeric or an allowed symbol.
     *
     * @param code The key to be checked against.
     * @return True if the key is allowed; false otherwise.
     */
    private boolean isAllowedKey(KeyCode code) {
        return code.isLetterKey() || code.isDigitKey() || isAllowedSymbolKey(code);
    }

    /**
     * Checks if the given key code is an allowed symbol key.
     *
     * @param code The key to be checked against.
     * @return True if the key is allowed; false otherwise.
     */
    private boolean isAllowedSymbolKey(KeyCode code) {
        switch (code) {
        case ENTER, BACK_SPACE, MINUS, EQUALS, SEMICOLON, COMMA, PERIOD, SLASH, BACK_SLASH, QUOTE:
            return true;
        default:
            return false;
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

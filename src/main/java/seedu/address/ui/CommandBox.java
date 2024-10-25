package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HousingType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_NO_PROPERTIES_TO_DELETE;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final Logger logger = Logger.getLogger(CommandBox.class.getName());
    private static final ArrayList<String> commandHistory = new ArrayList<>();
    private static int historyIndex = 0;

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
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
            commandHistory.add(commandText);
            historyIndex = commandHistory.size();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
            highlightErrorLocation(e, commandText);
        }
    }

    /**
     * Highlights the error location in the command box.
     */
    private void highlightErrorLocation(Exception e, String input) {
        String commandText = input + " ";
        String errorMessage = e.getMessage();
        int errorIndexStart = 0;
        int errorLength = 0;
        switch (errorMessage) {
        case HousingType.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("ht/");
            break;
        case Price.MESSAGE_CONSTRAINTS:
            if (commandText.contains("sp/")) {
                errorIndexStart = commandText.indexOf("sp/");
            } else {
                errorIndexStart = commandText.indexOf("bp/");
            }
            break;
        case PostalCode.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("pc/");
            break;
        case UnitNumber.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("un/");
            break;
        case Email.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("e/");
            break;
        case Name.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("n/");
            break;
        case Address.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("a/");
            break;
        case Phone.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("p/");
            break;
        case Tag.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf("t/");
            break;
        case MESSAGE_INVALID_PERSON_DISPLAYED_INDEX:
            errorIndexStart = commandText.indexOf(" ") + 1;
            break;
        case MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX:
            errorIndexStart = commandText.trim().lastIndexOf(" ") + 1;
            break;
        case SortCommand.MESSAGE_AVAILABLE_FIELDS, SortIndividualCommand.MESSAGE_AVAILABLE_FIELDS:
            errorIndexStart = commandText.indexOf("f/");
            break;
        case SortCommand.MESSAGE_INVALID_ORDER:
            errorIndexStart = commandText.indexOf("o/");
            break;
        case MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS:
            errorIndexStart = commandText.indexOf("findp") + 6;
            break;
        default:
            errorIndexStart = commandText.length();
        }
        errorLength = commandText.substring(errorIndexStart).indexOf(" ");
        commandTextField.selectRange(errorIndexStart, errorIndexStart + errorLength);
    }

    /**
     * Handles the key pressed event to get Command History.
     */
    @FXML
    public void handleOnKeyPressed() {
        commandTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
                if (commandHistory.isEmpty() || historyIndex <= 0) {
                    break;
                }
                assert !commandHistory.isEmpty() : "commandHistory should not be empty here";
                assert historyIndex > 0 : "historyIndex should be positive here before decrementing";
                historyIndex--;
                commandTextField.setText(commandHistory.get(historyIndex));
                break;
            case DOWN:
                if (commandHistory.isEmpty() || historyIndex >= commandHistory.size() || historyIndex < 0) {
                    break;
                }
                historyIndex++;
                assert !commandHistory.isEmpty() : "commandHistory should not be empty here";
                assert historyIndex >= 0 : "historyIndex should not be negative here";
                assert historyIndex <= commandHistory.size()
                        : "historyIndex should not exceed the size of commandHistory";
                if (historyIndex == commandHistory.size()) {
                    commandTextField.setText("");
                } else {
                    commandTextField.setText(commandHistory.get(historyIndex));
                }
                break;
            default:
                break;
            }
            logger.log(Level.FINE, "Key pressed: " + event.getCode());
        });
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

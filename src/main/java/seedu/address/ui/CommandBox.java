package seedu.address.ui;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_PREAMBLE_NOT_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

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
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

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
     * Splits the command input by tags and returns a list of tag contents.
     */
    public ArrayList<String> getTagContentList(String input) {
        String[] splitByTag = input.split(" t/");
        ArrayList<String> tagContents = new ArrayList<>();
        for (int i = 1; i < splitByTag.length; i++) {
            tagContents.add(splitByTag[i].split("\\b\\w{1,2}/")[0]);
        }
        return tagContents;
    }

    /**
     * Returns true if the command input is a sold or bought command.
     */
    public boolean isSoldOrBoughtCommand(String input) {
        return (input.contains("sold ") && input.contains(" ap/"))
                || (input.contains("bought ") && input.contains(" ap/"));
    }

    /**
     * Returns the tag content that exceeds the length limit.
     */
    public String moreThanTagLengthLimit(ArrayList<String> tagContents) {
        assert !tagContents.isEmpty() : "tagContents should not be empty here";
        for (String tagContent : tagContents) {
            assert !tagContent.isEmpty() : "tagContent should not be empty here";
            if (tagContent.length() > 10) {
                return tagContent;
            }
        }
        return "";
    }

    /**
     * Highlights the error location in the command box.
     */
    public void highlightErrorLocation(Exception e, String input) {
        String commandText = input + " ";
        String errorMessage = e.getMessage();

        int errorIndexStart = 0;
        int errorLength;

        boolean isTagError = false;
        int tagLength = 0;

        ArrayList<String> tagContents = getTagContentList(commandText);

        switch (errorMessage) {
        case HousingType.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" ht/") + 1;
            break;
        case Price.MESSAGE_CONSTRAINTS, Price.MESSAGE_PRICE_TOO_HIGH:
            if (commandText.contains(" sp/")) {
                errorIndexStart = commandText.indexOf(" sp/") + 1;
            } else if (commandText.contains(" bp/")) {
                errorIndexStart = commandText.indexOf(" bp/") + 1;
            } else if (commandText.contains(" ap/")) {
                errorIndexStart = commandText.indexOf(" ap/") + 1;
            }
            break;
        case PostalCode.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" pc/") + 1;
            break;
        case UnitNumber.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" un/") + 1;
            break;
        case Email.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" e/") + 1;
            break;
        case Name.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" n/") + 1;
            break;
        case Address.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" a/") + 1;
            break;
        case Phone.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" p/") + 1;
            break;
        case Tag.MESSAGE_CONSTRAINTS:
            errorIndexStart = commandText.indexOf(" t/") + 1;
            break;
        case MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, MESSAGE_PREAMBLE_NOT_EMPTY:
            errorIndexStart = commandText.indexOf(" ") + 1;
            break;
        case MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX:
            if (isSoldOrBoughtCommand(commandText)) {
                errorIndexStart = commandText.substring(0, commandText.trim().lastIndexOf(" "))
                        .trim().lastIndexOf(" ") + 1;
            } else {
                errorIndexStart = commandText.trim().lastIndexOf(" ") + 1;
            }
            break;
        case SortCommand.MESSAGE_AVAILABLE_FIELDS, SortIndividualCommand.MESSAGE_AVAILABLE_FIELDS:
            errorIndexStart = commandText.indexOf(" f/") + 1;
            break;
        case SortCommand.MESSAGE_INVALID_ORDER:
            errorIndexStart = commandText.indexOf(" o/") + 1;
            break;
        case MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS:
            errorIndexStart = commandText.indexOf("findp ") + 6;
            break;
        case Property.MESSAGE_PROPERTY_TAG_LIMIT:
            assert !tagContents.isEmpty() : "tagContents should not be empty here";
            isTagError = true; // To handle tags with multiple words
            // Highlight first tag that appears in the command
            errorIndexStart = commandText.indexOf(tagContents.get(0)) - 2;
            tagLength = tagContents.get(0).length();
            break;
        case Property.MESSAGE_PROPERTY_TAG_LENGTH_LIMIT:
            assert !tagContents.isEmpty() : "tagContents should not be empty here";
            isTagError = true;
            // Highlight the tag that exceeds the length limit
            String tagContent = moreThanTagLengthLimit(tagContents);
            errorIndexStart = commandText.indexOf(tagContent) - 2;
            tagLength = tagContent.length();
            break;
        case MESSAGE_UNKNOWN_COMMAND:
            break;
        default:
            errorIndexStart = commandText.length();
            break;
        }

        // To handle tags with multiple words when there is a tag error
        if (isTagError) {
            commandTextField.selectRange(errorIndexStart, errorIndexStart + tagLength + 2);
        } else {
            errorLength = commandText.substring(errorIndexStart).indexOf(" ");
            commandTextField.selectRange(errorIndexStart, errorIndexStart + errorLength);
        }
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
                this.previousCommand();
                break;
            case DOWN:
                if (commandHistory.isEmpty() || historyIndex >= commandHistory.size() || historyIndex < 0) {
                    break;
                }
                historyIndex++;
                this.nextCommand();
                break;
            default:
                break;
            }
            logger.log(Level.FINE, "Key pressed: " + event.getCode());
        });
    }

    /**
     * Handles the previous command in the command history.
     */
    private void previousCommand() {
        assert !commandHistory.isEmpty() : "commandHistory should not be empty here";
        assert historyIndex > 0 : "historyIndex should be positive here before decrementing";
        historyIndex--;
        commandTextField.setText(commandHistory.get(historyIndex));
    }

    /**
     * Handles the next command in the command history.
     */
    private void nextCommand() {
        assert !commandHistory.isEmpty() : "commandHistory should not be empty here";
        assert historyIndex >= 0 : "historyIndex should not be negative here";
        assert historyIndex <= commandHistory.size()
                : "historyIndex should not exceed the size of commandHistory";
        if (historyIndex == commandHistory.size()) {
            commandTextField.setText("");
        } else {
            commandTextField.setText(commandHistory.get(historyIndex));
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

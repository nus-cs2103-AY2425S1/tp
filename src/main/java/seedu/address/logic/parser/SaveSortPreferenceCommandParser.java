package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SaveSortPreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SaveSortPreference} object
 */
public class SaveSortPreferenceCommandParser implements Parser<SaveSortPreferenceCommand> {
    @Override
    public SaveSortPreferenceCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String args = userInput.trim().toLowerCase();

        switch (args) {
        case "high":
            return new SaveSortPreferenceCommand("high");
        case "low":
            return new SaveSortPreferenceCommand("low");
        case "default":
            // empty string denotes the original
            return new SaveSortPreferenceCommand("default");
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveSortPreferenceCommand.MESSAGE_USAGE));
        }
    }
}

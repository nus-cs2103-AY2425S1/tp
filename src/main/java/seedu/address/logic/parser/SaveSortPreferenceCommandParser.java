package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SaveSortPreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.SortPreference;

/**
 * Parses input arguments and creates a new {@code SaveSortPreference} object
 */
public class SaveSortPreferenceCommandParser implements Parser<SaveSortPreferenceCommand> {
    @Override
    public SaveSortPreferenceCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        try {
            SortPreference sortPreference = ParserUtil.parseSortPreference(userInput.toLowerCase());
            return new SaveSortPreferenceCommand(sortPreference);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveSortPreferenceCommand.MESSAGE_USAGE));
        }
    }
}

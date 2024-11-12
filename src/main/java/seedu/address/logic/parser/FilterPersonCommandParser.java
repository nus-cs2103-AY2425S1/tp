package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagMatchesKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterPersonCommand object
 */
public class FilterPersonCommandParser implements Parser<FilterPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPersonCommand
     * and returns a FilterPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPersonCommand.MESSAGE_USAGE));
        }

        return new FilterPersonCommand(new TagMatchesKeywordsPredicate(trimmedArgs));
    }
}

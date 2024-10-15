package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FilterEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventCelebrityMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new ViewEventCommand object
 */
public class FilterEventCommandParser implements Parser<FilterEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterEventCommand
     * and returns a FilterEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterEventCommand.MESSAGE_USAGE));
        }

        return new FilterEventCommand(new EventCelebrityMatchesKeywordPredicate(trimmedArgs));
    }
}

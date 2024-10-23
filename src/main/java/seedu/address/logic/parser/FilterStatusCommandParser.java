package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterStatusCommand object
 */
public class FilterStatusCommandParser implements Parser<FilterStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterStatusCommand
     * and returns a FilterStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterStatusCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterStatusCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FilterStatusCommand(new StatusContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}

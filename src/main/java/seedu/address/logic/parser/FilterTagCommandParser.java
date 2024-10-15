package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterTagCommand object
 */
public class FilterTagCommandParser implements Parser<FilterTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterTagCommand
     * and returns a FilterTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FilterTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterByJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.JobContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterByJobCommand object
 */
public class FilterByJobCommandParser implements Parser<FilterByJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterByJobCommand
     * and returns a FilterByJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterByJobCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByJobCommand.MESSAGE_USAGE));
        }

        String[] jobKeywords = trimmedArgs.split("\\s+");

        return new FilterByJobCommand(new JobContainsKeywordsPredicate(Arrays.asList(jobKeywords)));
    }
}

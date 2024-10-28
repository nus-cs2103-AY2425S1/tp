package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameOrJobContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = {};
        String[] jobKeywords = {};

        if (trimmedArgs.startsWith("n/")) {
            nameKeywords = trimmedArgs.substring(2).split("\\s+");
        } else if (trimmedArgs.startsWith("j/")) {
            jobKeywords = trimmedArgs.substring(2).split("\\s+");
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywordList = Arrays.stream(nameKeywords).collect(Collectors.toList());
        List<String> jobKeywordList = Arrays.stream(jobKeywords).collect(Collectors.toList());

        return new FilterCommand(new NameOrJobContainsKeywordsPredicate(nameKeywordList, jobKeywordList));
    }
}

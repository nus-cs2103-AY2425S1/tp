package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private static final Prefix TAG_PREFIX = CliSyntax.PREFIX_ROLE;

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

        String[] tagKeywords = Arrays.stream(trimmedArgs.split("\\s+"))
                .filter(arg -> arg.startsWith(TAG_PREFIX.getPrefix()))
                .map(arg -> arg.substring(TAG_PREFIX.getPrefix().length()))
                .toArray(String[]::new);

        // Check if keywords are empty
        if (tagKeywords.length == 0 || Arrays.stream(tagKeywords).anyMatch(String::isEmpty)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(new RoleContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}

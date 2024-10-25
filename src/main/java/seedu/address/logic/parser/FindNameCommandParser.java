package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindNameCommand object
 */
public class FindNameCommandParser implements Parser<FindNameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindNameCommand
     * and returns a FindNameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNameCommand.MESSAGE_USAGE));
        }
        if (!trimmedArgs.matches("^[a-zA-Z()\\s]+$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}

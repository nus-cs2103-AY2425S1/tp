package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.GetIdCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new GetIdCommand object
 */
public class GetIdCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the GetIdCommand
     * and returns a GetIdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetIdCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetIdCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new GetIdCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}

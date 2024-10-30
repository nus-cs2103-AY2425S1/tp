package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindClientTypeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindClientTypeCommand object
 */
public class FindClientTypeCommandParser implements Parser<FindClientTypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindClientTypeCommand
     * and returns a FindClientTypeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindClientTypeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientTypeCommand.MESSAGE_USAGE));
        }

        // Check for special characters - only allow alphanumeric and whitespace
        if (!trimmedArgs.matches("^[a-zA-Z0-9\\s]+$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientTypeCommand.MESSAGE_USAGE));
        }

        String[] clientTypeKeywords = trimmedArgs.split("\\s+");

        return new FindClientTypeCommand(new ClientTypeContainsKeywordsPredicate(Arrays.asList(clientTypeKeywords)));
    }

}

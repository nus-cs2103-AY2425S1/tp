package seedu.address.logic.parser;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindByMajorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MajorContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByMajorCommand object.
 */
public class FindByMajorCommandParser implements Parser<FindByMajorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindByMajorCommand
     * and returns a FindByMajorCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindByMajorCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        // Check if the input is empty or doesn't start with the correct format ("m/")
        if (trimmedArgs.isEmpty() || !trimmedArgs.startsWith("m/")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    FindByMajorCommand.MESSAGE_USAGE));
        }
        String keyword = trimmedArgs.substring(2).trim();
        if (keyword.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindByMajorCommand.MESSAGE_USAGE));
        }

        // Only a single keyword will be used, so pass it directly
        return new FindByMajorCommand(new MajorContainsKeywordsPredicate(keyword));
    }
}


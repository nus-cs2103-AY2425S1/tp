package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FinddelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.ItemNameContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FinddelCommand object
 */
public class FinddelCommandParser implements Parser<FinddelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FinddelCommand
     * and reutnrs a FinddelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format or is called in main window
     */
    public FinddelCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinddelCommand.MESSAGE_USAGE));
        }

        String[] itemNameKeywords = trimmedArgs.split("\\s+");

        return new FinddelCommand(new ItemNameContainsKeywordPredicate(Arrays.asList(itemNameKeywords)));
    }
}

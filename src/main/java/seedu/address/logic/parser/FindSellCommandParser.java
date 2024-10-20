package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindSellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SellPropertyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindBuyCommand object
 */
public class FindSellCommandParser implements Parser<FindSellCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSellCommand
     * and returns a FindSellCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSellCommand parse(String args) throws ParseException {
        assert args != null;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSellCommand.MESSAGE_USAGE));
        }

        String[] sellPropertyKeywords = trimmedArgs.split("\\s+");

        return new FindSellCommand(new SellPropertyContainsKeywordsPredicate(Arrays.asList(sellPropertyKeywords)));
    }
}

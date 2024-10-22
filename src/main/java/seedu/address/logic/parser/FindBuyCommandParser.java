package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindBuyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BuyPropertyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindBuyCommand object
 */
public class FindBuyCommandParser implements Parser<FindBuyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindBuyCommand
     * and returns a FindBuyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindBuyCommand parse(String args) throws ParseException {
        assert args != null;
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindBuyCommand.MESSAGE_USAGE));
        }

        String[] buyPropertyKeywords = trimmedArgs.split("\\s+");

        return new FindBuyCommand(new BuyPropertyContainsKeywordsPredicate(Arrays.asList(buyPropertyKeywords)));
    }
}

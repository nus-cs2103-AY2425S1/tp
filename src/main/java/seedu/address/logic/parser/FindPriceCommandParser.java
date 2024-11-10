package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PRICE_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindPriceCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.PriceContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPriceCommand object
 */
public class FindPriceCommandParser implements Parser<FindPriceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPriceCommand
     * and returns a FindPriceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPriceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPriceCommand.MESSAGE_USAGE));
        }


        String[] tagKeywords = trimmedArgs.split("\\s+");

        Pattern pattern = Pattern.compile("^\\s*(\\$|\\s*){1,4}$");
        Matcher matcher = pattern.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PRICE_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        return new FindPriceCommand(new PriceContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TAG_PRICE_SEARCH;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns a FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] tagKeywords = trimmedArgs.split("\\s+");
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        Matcher matcher = pattern.matcher(trimmedArgs);

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        if (matcher.find()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TAG_PRICE_SEARCH, FindTagCommand.MESSAGE_USAGE));
        }

        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}

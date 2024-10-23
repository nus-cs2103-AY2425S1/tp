package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.preferredtime.PreferredTime;
import seedu.address.model.preferredtime.PreferredTimeOverlapsRangesPredicate;



/**
 * Parses input arguments and creates a new FindTimeCommand object
 */
public class FindTimeCommandParser implements Parser<FindTimeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindTimeCommand
     * and returns a FindTimeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTimeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTimeCommand.MESSAGE_USAGE));
        }

        String[] timeKeywords = trimmedArgs.split("\\s+");
        List<String> validRanges = Arrays.stream(timeKeywords)
                .filter(PreferredTime::isValidPreferredTime).toList();

        // return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(timeKeywords)));
        return new FindTimeCommand(new PreferredTimeOverlapsRangesPredicate(validRanges));
    }
}

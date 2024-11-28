package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContactContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindTagContactCommand object
 */
public class FindTagContactCommandParser implements Parser<FindTagContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagContactCommand
     * and returns a FindTagContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     *      or if keywords are not all numbers
     */
    public FindTagContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagContactCommand.MESSAGE_USAGE));
        }

        String[] tagContactKeywords = trimmedArgs.split("\\s+");
        return new FindTagContactCommand(new TagContactContainsKeywordPredicate(Arrays.asList(tagContactKeywords)));
    }

}

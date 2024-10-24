package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new {@code FindTagCommand} object.
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns a FindTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        List<String> keywordList = Arrays.asList(trimmedArgs.split("\\s+"));

        return new FindTagCommand(new TagContainsKeywordsPredicate(keywordList));
    }
}


package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.internbuddy.commons.core.LogsCenter;
import seedu.internbuddy.logic.commands.FindCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;
import seedu.internbuddy.model.company.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.log(Level.WARNING, "Empty search query");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

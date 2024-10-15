package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindMedConCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedConContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMedConCommand object.
 */
public class FindMedConCommandParser implements Parser<FindMedConCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindMedConCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the FindMedConCommand
     * and returns a FindMedConCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindMedConCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("No keywords provided for FindMedConCommand");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedConCommand.MESSAGE_USAGE));
        }

        String[] medConKeywords = trimmedArgs.split("\\s+");
        MedConContainsKeywordsPredicate predicate = new MedConContainsKeywordsPredicate(Arrays.asList(medConKeywords));
        logger.info("Successfully parsed the keywords for FindMedConCommand: " + Arrays.toString(medConKeywords));
        return new FindMedConCommand(predicate);
    }
}

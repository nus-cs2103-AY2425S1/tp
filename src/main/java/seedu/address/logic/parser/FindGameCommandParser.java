package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindGameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.GamesContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindGameCommandParser implements Parser<FindGameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindGameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindGameCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGameCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindGameCommand(new GamesContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

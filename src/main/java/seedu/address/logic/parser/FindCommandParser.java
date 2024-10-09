package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // instead of tokenizing with prefix for both name and number
        // split by num/ to allow quick search with name
        String[] searchArgs = trimmedArgs.split(" num/");
        String[] nameKeywords = searchArgs[0].split("\\s+");
        if (searchArgs.length == 2) {
            Phone searchNumber = ParserUtil.parsePhone(searchArgs[1]);
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)), searchNumber);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)), null);
    }

}

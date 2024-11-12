package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");

        try {
            PersonContainsKeywordsPredicate predicate =
                    new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords));
            return new ClearCommand(predicate);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }
}

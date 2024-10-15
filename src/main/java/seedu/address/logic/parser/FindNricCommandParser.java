package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNricCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricContainsKeywordsPredicate;



/**
 * Parses input arguments and creates a new FindNricCommand object
 */
public class FindNricCommandParser implements Parser<FindNricCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindNricCommand
     * and returns a FindNricCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNricCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNricCommand.MESSAGE_USAGE));
        }

        String[] nricKeywords = trimmedArgs.split("\\s+");

        if (Arrays.stream(nricKeywords).anyMatch(nric -> !Nric.isValidNric(nric))) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }

        return new FindNricCommand(new NricContainsKeywordsPredicate(Arrays.asList(nricKeywords)));
    }

}

package seedu.address.logic.parser;

import seedu.address.logic.commands.FindMedConCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedConContainsKeywordsPredicate;

import java.util.Arrays;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindMedConCommand object.
 */
public class FindMedConCommandParser implements Parser<FindMedConCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListMedConCommand
     * and returns a ListMedConCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindMedConCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMedConCommand.MESSAGE_USAGE));
        }

        String[] medConKeywords = trimmedArgs.split("\\s+");
        MedConContainsKeywordsPredicate predicate = new MedConContainsKeywordsPredicate(Arrays.asList(medConKeywords));
        return new FindMedConCommand(predicate);
    }
}

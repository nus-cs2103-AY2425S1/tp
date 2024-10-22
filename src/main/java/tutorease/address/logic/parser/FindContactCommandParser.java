package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import tutorease.address.logic.commands.FindContactCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindContactCommand object
 */
public class FindContactCommandParser implements Parser<FindContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindContactCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindContactCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}

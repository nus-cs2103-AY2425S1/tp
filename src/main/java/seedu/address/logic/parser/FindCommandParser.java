package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        if (args.isEmpty() || !atLeastOnePrefixPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        System.out.println(argMultimap.getAllValues(PREFIX_NAME));
        return new FindCommand(new NameContainsKeywordsPredicate(
                argMultimap.getAllValues(PREFIX_NAME)));
    }

    /**
     * Returns true if at least one of the prefixes contains non-empty
     * {@code Optional} values in the given {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} to
     *     check for the presence of the prefixes.
     * @param prefixes The prefixes to check for presence.
     * @return True if at least one of the prefixes contains non-empty
     */
    private static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap,
            Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix ->
                argumentMultimap.getValue(prefix).isPresent());
    }

}

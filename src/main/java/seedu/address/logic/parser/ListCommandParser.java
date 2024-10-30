package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCompanyCommand;
import seedu.address.logic.commands.ListContactCommand;
import seedu.address.logic.commands.ListJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        switch (trimmedArgs) {
        case ListContactCommand.ENTITY_WORD:
            return new ListContactCommand();
        case ListJobCommand.ENTITY_WORD:
            return new ListJobCommand();
        case ListCompanyCommand.ENTITY_WORD:
            return new ListCompanyCommand();
        case ListAllCommand.ENTITY_WORD:
            return new ListAllCommand();
        default:
            throw new ParseException(ListCommand.MESSAGE_USAGE);
        }
    }

}

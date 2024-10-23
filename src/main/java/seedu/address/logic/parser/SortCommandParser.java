package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FIELD, PREFIX_ORDER);

        if (argMultimap.getValue(PREFIX_FIELD).isEmpty() || argMultimap.getValue(PREFIX_ORDER).isEmpty()) {
            throw new ParseException(SortCommand.MESSAGE_USAGE);
        }

        if (!argMultimap.getValue(PREFIX_FIELD).get().equals("Name")
                && !argMultimap.getValue(PREFIX_FIELD).get().equals("NumProp")) {
            throw new ParseException(SortCommand.MESSAGE_AVAILABLE_FIELDS);
        }

        if (!argMultimap.getValue(PREFIX_ORDER).get().equals("L")
                && !argMultimap.getValue(PREFIX_ORDER).get().equals("H")) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
        }

        return new SortCommand(argMultimap.getValue(PREFIX_FIELD).get(), argMultimap.getValue(PREFIX_ORDER).get());
    }
}

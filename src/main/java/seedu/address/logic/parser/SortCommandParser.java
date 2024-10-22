package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.LIST_MESSAGE_INVALID_COMMAND;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.PersonComparator;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand Object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Checks if the given {@code String} of arguments is empty
     * and executes ListCommand object.
     * @throws ParseException if any user input is detected
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATEOFLASTVISIT);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATEOFLASTVISIT);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return new SortCommand(PersonComparator.NAME,
                    isAscending(argMultimap.getValue(PREFIX_NAME).get().toLowerCase()));
        } else if (argMultimap.getValue(PREFIX_DATEOFLASTVISIT).isPresent()) {
            return new SortCommand(PersonComparator.DATE_OF_LAST_VISIT,
                    isAscending(argMultimap.getValue(PREFIX_NAME).get().toLowerCase()));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private final boolean isAscending(String s) throws ParseException {
        if (s == null || s.isEmpty()) {
            return true;
        }
        if (s.equals("asc") || s.equals("ascending")) {
            return true;
        } else if (s.equals("desc") || s.equals("descending")) {
            return false;
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}

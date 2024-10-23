package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFLASTVISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonComparator;

/**
 * Parses input arguments and creates a new SortCommand Object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Prefix[] INVALID_PREFIXES = {PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_TAG};

    /**
     * Checks if the given {@code String} of arguments is valid
     * and returns an appropriate {@code SortCommand} object.
     * @throws ParseException if any user input is detected
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATEOFLASTVISIT,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_TAG);

        if (argMultimap.anyIsPresent(INVALID_PREFIXES) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATEOFLASTVISIT);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()
                && argMultimap.getValue(PREFIX_DATEOFLASTVISIT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return new SortCommand(PersonComparator.NAME,
                    isAscending(argMultimap.getValue(PREFIX_NAME).get().toLowerCase()));
        } else if (argMultimap.getValue(PREFIX_DATEOFLASTVISIT).isPresent()) {
            return new SortCommand(PersonComparator.DATE_OF_LAST_VISIT,
                    isAscending(argMultimap.getValue(PREFIX_DATEOFLASTVISIT).get().toLowerCase()));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private final boolean isAscending(String s) throws ParseException {
        if (s.isEmpty()) {
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

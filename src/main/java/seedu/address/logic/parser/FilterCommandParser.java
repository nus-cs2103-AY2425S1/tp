package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private static final Prefix ROLE_PREFIX = CliSyntax.PREFIX_ROLE;

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args.trim(), PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS);

        // Check if any valid prefixes are present
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE,
                PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        // Get values, defaulting to empty string if not present
        String name = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse("").trim();
        String phone = argMultimap.getValue(PREFIX_PHONE).orElse("").trim();
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse("").trim();
        String role = argMultimap.getValue(PREFIX_ROLE).orElse("").trim();

        return new FilterCommand(name, role, email, phone, address);
    }

    /**
     * Returns true if at least one of the prefixes contains values in the argument multimap.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

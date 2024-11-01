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

    @Override
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME, PREFIX_ROLE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS
        );

        // Check if any prefix values are empty
        if (areAnyPrefixesEmpty(argMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_NAME).orElse("");
        String role = argMultimap.getValue(PREFIX_ROLE).orElse("");
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse("");
        String phone = argMultimap.getValue(PREFIX_PHONE).orElse("");
        String address = argMultimap.getValue(PREFIX_ADDRESS).orElse("");

        return new FilterCommand(name, role, email, phone, address);
    }

    /**
     * Returns true if at least one of the prefixes contains values in the argument multimap.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if any of the present prefixes has an empty value.
     */
    private static boolean areAnyPrefixesEmpty(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .anyMatch(prefix -> argumentMultimap.getValue(prefix).get().trim().isEmpty());
    }
}

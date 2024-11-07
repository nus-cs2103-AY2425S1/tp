package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_ROLE_CANNOT_BE_EMPTY = "Inputted role to be filtered cannot be blank.";

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
        if (areAnyPrefixesEmpty(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Name name = null;
        Phone phone = null;
        Email email = null;
        Address address = null;
        Optional<Role> role = null;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            if (argMultimap.getValue(PREFIX_ROLE).get().isEmpty()) {
                throw new ParseException(MESSAGE_ROLE_CANNOT_BE_EMPTY);
            }
            role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        }

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

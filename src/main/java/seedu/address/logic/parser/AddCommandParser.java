package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;

/**
 * Parses input arguments and creates a new {@code AddCommand} object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddCommand}
     * and returns an {@code AddCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_ROLE, PREFIX_WEDDING);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE);

        // Parse required fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());

        // Parse optional role
        Optional<Role> role = Optional.empty();
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            try {
                role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
            } catch (ParseException e) {
                throw new ParseException(Role.MESSAGE_CONSTRAINTS);
            }
        }

        // Parse wedding indices
        Set<Index> weddingIndices = ParserUtil.parseWeddingJobs(argMultimap.getAllValues(PREFIX_WEDDING));

        // Create the person with the parsed values
        Person person = new Person(name, phone, email, address, role, null);

        return new AddCommand(person, weddingIndices);
    }

    /**
     * Checks if all specified prefixes are present in the provided {@code ArgumentMultimap}.
     *
     * @param argumentMultimap {@code ArgumentMultimap} containing the prefixes and their values.
     * @param prefixes Array of {@code Prefix} objects to check for presence in {@code argumentMultimap}.
     * @return true if all specified prefixes are present, false otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

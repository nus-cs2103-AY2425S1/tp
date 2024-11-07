package seedu.eventfulnus.logic.parser;

import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.eventfulnus.logic.commands.AddCommand;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.person.Email;
import seedu.eventfulnus.model.person.Name;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.Phone;
import seedu.eventfulnus.model.person.role.Role;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<Role> roleList = ParserUtil.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));

        Person person = new Person(name, phone, email, roleList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

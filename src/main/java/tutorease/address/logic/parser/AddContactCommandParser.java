package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorease.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Set;

import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.person.Address;
import tutorease.address.model.person.Email;
import tutorease.address.model.person.Guardian;
import tutorease.address.model.person.Name;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.Phone;
import tutorease.address.model.person.Role;
import tutorease.address.model.person.Student;
import tutorease.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContactCommand parse(String args) throws ParseException, IllegalArgumentException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_ROLE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_ROLE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person;

        if (role.getRoleString().equals(Role.STUDENT)) {
            person = new Student(name, phone, email, address, role, tagList);
        } else if (role.getRoleString().equals(Role.GUARDIAN)) {
            person = new Guardian(name, phone, email, address, role, tagList);
        } else {
            throw new IllegalArgumentException(Role.MESSAGE_CONSTRAINTS);
        }

        return new AddContactCommand(person);
    }
}

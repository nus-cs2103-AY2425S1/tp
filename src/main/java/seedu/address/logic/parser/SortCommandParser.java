package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;


/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {

        requireNonNull(args);
        String sortBy = args.trim().toLowerCase();

        switch (sortBy) {

        case "name":
            Comparator<Person> comparatorForName = Comparator.comparing(person -> person.getName().fullName);
            return new SortCommand(sortBy, comparatorForName);
        case "role":
            Comparator<Person> comparatorForRole = Comparator.comparing(person -> person.getRole().roleName);
            return new SortCommand(sortBy, comparatorForRole);
        case "phone":
            Comparator<Person> comparatorForPhone = Comparator.comparing(person -> person.getPhone().value);
            return new SortCommand(sortBy, comparatorForPhone);
        case "email":
            Comparator<Person> comparatorForEmail = Comparator.comparing(person -> person.getEmail().value);
            return new SortCommand(sortBy, comparatorForEmail);
        case "address":
            Comparator<Person> comparatorForAddress = Comparator.comparing(person -> person.getAddress().value);
            return new SortCommand(sortBy, comparatorForAddress);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

    }
}

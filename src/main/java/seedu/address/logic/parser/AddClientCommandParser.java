package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.car.Car;
import seedu.address.model.issue.Issue;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new AddClientCommand object
 */
public class AddClientCommandParser implements Parser<AddClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClientCommand
     * and returns an AddClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL, PREFIX_ISSUE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE));
        }

        boolean isCarPresent = false;

        // Check if either all or none of the vehicle prefixes are present
        if (isOnePrefixPresent(argMultimap, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL)
                && !arePrefixesPresent(argMultimap, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddClientCommand.VEHICLE_DETAILS_MISSING));
        } else if (arePrefixesPresent(argMultimap, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL)) {
            isCarPresent = true;
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                                                PREFIX_ADDRESS, PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Person person;

        // If Client has a Car. Issues also can be added to it, if any.
        if (isCarPresent) {
            System.out.println("Car is present" + argMultimap.getValue(PREFIX_VRN).get());
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VRN, PREFIX_VIN, PREFIX_MAKE, PREFIX_MODEL);
            String vrn = argMultimap.getValue(PREFIX_VRN).orElse("");
            String vin = argMultimap.getValue(PREFIX_VIN).orElse("");
            String make = argMultimap.getValue(PREFIX_MAKE).orElse("");
            String model = argMultimap.getValue(PREFIX_MODEL).orElse("");

            // Create Car and Issues.
            Set<Issue> issueList = ParserUtil.parseIssues(argMultimap.getAllValues(PREFIX_ISSUE));
            Car car = ParserUtil.parseCar(vrn, vin, make, model);
            person = new Person(name, phone, email, address, issueList, car);
            return new AddClientCommand(person);
        }

        // If Client does not have a Car, but user tries to add issues to it.
        if (arePrefixesPresent(argMultimap, PREFIX_ISSUE)) {
            throw new ParseException(AddClientCommand.MESSAGE_NO_CAR_TO_ADD_ISSUES);
        }

        // If Client does not have a Car, and user does not try to add issues to it.
        person = new Person(name, phone, email, address, new HashSet<>());
        return new AddClientCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

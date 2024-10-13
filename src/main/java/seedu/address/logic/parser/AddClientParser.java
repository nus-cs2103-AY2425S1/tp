package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddClientProfile;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddClientParser implements Parser<AddClientProfile> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClientProfile parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientProfile.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_PROPERTY);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        //  Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        //  Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        //  Remark remark = new Remark(""); // add command does not allow adding remarks straight away

        // add command does not allow adding properties straight away
        Property property = new Property("");
        //  add command does not allow adding appointment straight away
        Appointment appointment = new Appointment(new Date(""), new From(""), new To(""));

        //  Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Person person = new Person(name, phone, appointment, property);

        return new AddClientProfile(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

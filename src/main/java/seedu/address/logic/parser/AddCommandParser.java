package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_STATUS, PREFIX_TAG, PREFIX_PERSON_ID, PREFIX_DATETIME,
                        PREFIX_APPOINTMENT_TYPE, PREFIX_SICKNESS, PREFIX_MEDICINE);
        String entityType = argMultimap.getEntityType();

        switch (entityType) {
        case PERSON_ENTITY_STRING:
            PersonDescriptor person = parseAddPerson(argMultimap);
            return new AddPersonCommand(person);
        case APPOINTMENT_ENTITY_STRING:
            AppointmentDescriptor appointmentDescriptor = parseAddAppointment(argMultimap);
            int personId = extractPersonId(argMultimap);

            return new AddAppointmentCommand(appointmentDescriptor, personId);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code ArgumentMultimap} and creates a PersonDescriptor object.
     *
     * @param argMultimap Contains the field-value pairs for creating a person, where fields are indicated by prefixes
     *                    (PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_STATUS, PREFIX_EMAIL,
     *                    and optionally PREFIX_TAG)
     * @return A new PersonDescriptor object with the parsed name, phone, email, address, status, and tags
     * @throws ParseException If:
     *         - Any of the required fields (name, address, phone, status, email) is missing, or
     *         - Any required field has duplicate prefixes, or
     *         - Any field value is invalid according to the respective parsing rules in ParserUtil
     */
    public PersonDescriptor parseAddPerson(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(
                argMultimap,
                PREFIX_NAME,
                PREFIX_ADDRESS,
                PREFIX_PHONE,
                PREFIX_STATUS,
                PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_STATUS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        PersonDescriptor person = new PersonDescriptor(name, phone, email, address, status, tagList);
        return person;
    }

    /**
     * Parses the given {@code ArgumentMultimap} and creates an AppointmentDescriptor object.
     *
     * @param argMultimap Contains the field-value pairs for creating an appointment, where fields are
     *                    indicated by prefixes:
     *                    Required: PREFIX_PERSON_ID, PREFIX_DATETIME, PREFIX_APPOINTMENT_TYPE
     *                    Optional: PREFIX_SICKNESS, PREFIX_MEDICINE
     * @return A new AppointmentDescriptor object with the parsed appointment type, datetime, and optional sickness
     *          and medicine details
     * @throws ParseException If:
     *         - Any of the required fields (person ID, datetime, appointment type) is missing, or
     *         - Any field has duplicate prefixes, or
     *         - Any field value is invalid according to the respective parsing rules in ParserUtil
     *                 - Person ID must be a valid integer
     *                 - Datetime must be in the correct format
     *                 - Appointment type must be a valid enum value
     */
    public AppointmentDescriptor parseAddAppointment(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON_ID, PREFIX_DATETIME, PREFIX_APPOINTMENT_TYPE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_ID, PREFIX_DATETIME,
                PREFIX_APPOINTMENT_TYPE, PREFIX_SICKNESS, PREFIX_MEDICINE);
        int personId = ParserUtil.parsePersonId(argMultimap.getValue(PREFIX_PERSON_ID).get());
        LocalDateTime appointmentDateTime = ParserUtil.parseAppointmentDateTime(
                argMultimap.getValue(PREFIX_DATETIME).get());
        AppointmentType appointmentType = ParserUtil.parseAppointmentType(
                argMultimap.getValue(PREFIX_APPOINTMENT_TYPE).get());
        Sickness sickness = argMultimap.getValue(PREFIX_SICKNESS).isPresent()
                ? ParserUtil.parseSickness(argMultimap.getValue(PREFIX_SICKNESS).get())
                : null;
        Medicine medicine = argMultimap.getValue(PREFIX_MEDICINE).isPresent()
                ? ParserUtil.parseMedicine(argMultimap.getValue(PREFIX_MEDICINE).get())
                : null;
        AppointmentDescriptor appointmentDescriptor = new AppointmentDescriptor(
                appointmentType, appointmentDateTime, sickness, medicine);

        return appointmentDescriptor;
    };

    /**
     * Extracts and parses the person ID from the given ArgumentMultimap.
     *
     * @param argMultimap Contains the field-value pairs, must include PREFIX_PERSON_ID
     * @return The parsed person ID as an integer
     * @throws ParseException If:
     *         - The person ID prefix is missing, or
     *         - The person ID value cannot be parsed into a valid integer
     */
    public int extractPersonId(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON_ID)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parsePersonId(argMultimap.getValue(PREFIX_PERSON_ID).get());
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

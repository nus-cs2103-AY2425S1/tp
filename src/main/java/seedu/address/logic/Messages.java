package seedu.address.logic;

import static seedu.address.commons.util.DateUtil.FORMATTER;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX = "The appointment index is invalid";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW = "%1$d appointments listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     *
     * @param duplicatePrefixes the duplicate prefixes
     * @return the error message
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     *
     * @param person the person to be formatted
     * @return formatted description of the person
     */
    public static String formatPerson(Person person) {
        return formatPerson(person.getPersonDescriptor());
    }

    /**
     * Formats the {@code person} for display to the user.
     *
     * @param personDescriptor the person descriptor to be formatted
     * @return formatted description of the person
     */
    public static String formatPerson(PersonDescriptor personDescriptor) {
        return "\n" + "Name: " + personDescriptor.getName() + "\n"
                + "Phone: " + personDescriptor.getPhone() + "\n"
                + "Email: " + personDescriptor.getEmail() + "\n"
                + "Address: " + personDescriptor.getAddress() + "\n"
                + "Tags: " + personDescriptor.getTags().toString() + "\n";
    }

    /**
     * Formats the {@code appointment} for display to the user.
     *
     * @param appointment the appointment to be formatted
     * @return formatted description of the appointment
     */
    public static String formatAppointment(Appointment appointment) {
        return formatAppointment(appointment.getAppointmentDescriptor());
    }

    /**
     * Formats the {@code appointment} for display to the user.
     *
     * @param appointmentDescriptor the appointment descriptor to be formatted
     * @return formatted description of the appointment
     */
    public static String formatAppointment(AppointmentDescriptor appointmentDescriptor) {
        return "\n" + "Appointment Type: "
                + appointmentDescriptor.getAppointmentType() + "\n"
                + "Date and Time: " + appointmentDescriptor.getAppointmentDateTime().format(FORMATTER)
                + "\n" + "Sickness: " + appointmentDescriptor.getSickness() + "\n"
                + "Medicine: " + appointmentDescriptor.getMedicine() + "\n";
    }
}

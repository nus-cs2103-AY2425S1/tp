package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SICKNESS;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.appointment.AppointmentDescriptor;

/**
 * A utility class for Appointment.
 */
public class AppointmentUtil {

    /**
     * Returns an add command string for adding the {@code appointment}.
     */
    public static String getAddPersonCommand(AppointmentDescriptor appointment) {
        return AddCommand.COMMAND_WORD + " " + APPOINTMENT_ENTITY_STRING + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns the part of command string for the given {@code appointment}'s details.
     */
    public static String getAppointmentDetails(AppointmentDescriptor appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_APPOINTMENT_TYPE + appointment.getAppointmentType().value + " ");
        sb.append(PREFIX_DATETIME + formatDateTime(appointment.getAppointmentDateTime()) + " ");
        sb.append(PREFIX_SICKNESS + appointment.getSickness().value + " ");
        sb.append(PREFIX_MEDICINE + appointment.getMedicine().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditAppointmentDescriptor}'s details.
     */
    public static String getEditAppointmentDescriptorDetails(EditAppointmentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getAppointmentType().ifPresent(type -> sb.append(PREFIX_APPOINTMENT_TYPE).append(type).append(" "));
        descriptor.getAppointmentDateTime()
                .ifPresent(datetime -> sb.append(PREFIX_DATETIME).append(formatDateTime(datetime)).append(" "));
        descriptor.getSickness().ifPresent(sickness -> sb.append(PREFIX_SICKNESS).append(sickness.value).append(" "));
        descriptor.getMedicine().ifPresent(medicine -> sb.append(PREFIX_MEDICINE).append(medicine.value).append(" "));
        descriptor.getPersonId().ifPresent(id -> sb.append(PREFIX_PERSON_ID).append(String.valueOf(id)).append(" "));
        return sb.toString();
    }

    /**
     * Returns a formatted local date that is used for edit appointment command input.
     */
    public static String formatDateTime(LocalDateTime appointmentDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return appointmentDateTime.format(formatter);
    }
}

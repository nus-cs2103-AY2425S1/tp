package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.NoteCommand.NoteDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_AGE + person.getAge().value + " ");
        sb.append(PREFIX_SEX + person.getSex().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getAge().ifPresent(age -> sb.append(PREFIX_AGE).append(age.value).append(" "));
        descriptor.getSex().ifPresent(sex -> sb.append(PREFIX_SEX).append(sex.value).append(" "));
        if (descriptor.getAppointments().isPresent()) {
            Set<Appointment> appointments = descriptor.getAppointments().get();
            if (appointments.isEmpty()) {
                sb.append(PREFIX_APPOINTMENT).append(" ");
            } else {
                appointments.forEach(ap -> sb.append(PREFIX_APPOINTMENT).append(ap.toString()).append(" "));
            }
        }
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    public static String getNoteDescriptorDetails(NoteDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        if (descriptor.getAppointments().isPresent()) {
            Set<Appointment> appointments = descriptor.getAppointments().get();
            if (appointments.isEmpty()) {
                sb.append(PREFIX_APPOINTMENT);
            } else {
                appointments.forEach(s -> sb.append(PREFIX_APPOINTMENT)
                        .append(s.appointment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"))).append(" "));
            }
        }

        if (descriptor.getMedications().isPresent()) {
            Set<String> medications = descriptor.getMedications().get();
            if (medications.isEmpty()) {
                sb.append(PREFIX_MEDICATION);
            } else {
                medications.forEach(s -> sb.append(PREFIX_MEDICATION).append(s).append(" "));
            }
        }

        if (descriptor.getRemarks().isPresent()) {
            Set<String> remarks = descriptor.getRemarks().get();
            if (remarks.isEmpty()) {
                sb.append(PREFIX_REMARK);
            } else {
                remarks.forEach(s -> sb.append(PREFIX_REMARK).append(s).append(" "));
            }
        }

        return sb.toString();
    }
}

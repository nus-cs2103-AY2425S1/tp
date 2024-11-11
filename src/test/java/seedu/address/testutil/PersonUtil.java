package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEPERIOD;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Person;

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
        sb.append(PREFIX_NAME).append(person.getName().fullName).append(" ");
        sb.append(PREFIX_NRIC).append(person.getNric().value).append(" ");
        sb.append(PREFIX_DOB).append(person.getDateOfBirth().value).append(" ");
        sb.append(PREFIX_GENDER).append(person.getGender().value).append(" ");
        sb.append(PREFIX_PHONE).append(person.getPhone().value).append(" ");
        sb.append(PREFIX_EMAIL).append(person.getEmail().value).append(" ");
        sb.append(PREFIX_ADDRESS).append(person.getAddress().value).append(" ");
        for (Allergy allergy : person.getAllergies()) {
            sb.append(PREFIX_ALLERGY).append(allergy.allergyName).append(" ");
        }
        for (Appointment s : person.getAppointments()) {
            sb.append(PREFIX_ALLERGY).append(s.getAppointmentName()).append(" ")
              .append(PREFIX_DATE).append(s.getAppointmentDate()).append(" ")
              .append(PREFIX_TIMEPERIOD).append(s.getAppointmentTimePeriod()).append(" ");
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.value).append(" "));
        descriptor.getDateOfBirth().ifPresent(dob -> sb.append(PREFIX_DOB).append(dob.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getAllergies().isPresent()) {
            Set<Allergy> allergies = descriptor.getAllergies().get();
            if (allergies.isEmpty()) {
                sb.append(PREFIX_ALLERGY);
            } else {
                allergies.forEach(s -> sb.append(PREFIX_ALLERGY).append(s.allergyName).append(" "));
            }
        }
        // Appointments not supported in edit command
        return sb.toString();
    }
}

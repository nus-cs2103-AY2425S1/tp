package seedu.address.logic.commands.clientcommands.appointmentcommands;

import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.name.Name;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;

/**
 * Utility class containing helper methods for Appointment-related commands.
 */
public class AppointmentCommandsUtil {

    /**
     * Creates a new Person with the specified appointment.
     * If the role is a BUYER, a Buyer object is created; if the role is a SELLER, a Seller object is created.
     *
     * @param personToEdit the original person to base the new person on
     * @param role the role of the person, either BUYER or SELLER
     * @param appointment the appointment to associate with the new person
     * @return a new Person object (either Buyer or Seller) with the specified appointment
     */
    public static Person createPersonWithAppointment(Person personToEdit, Role role, Appointment appointment) {

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Set<Tag> tags = personToEdit.getTags();

        if (role.equals(Role.BUYER)) {
            return new Buyer(name, phone, email, tags, appointment);
        } else {
            return new Seller(name, phone, email, tags, appointment);
        }
    }
}

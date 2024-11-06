package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;

/**
 * Contains all appointments set in the address book
 * Duplicates are not allowed (by .hasAppointment check)
 */
public class Calendar {
    private final List<Appointment> appointments;

    /**
     * Creates a calendar using the PersonList in {@code addressBook}
     */
    public Calendar(AddressBook addressBook) {
        appointments = addressBook.getPersonList().stream()
                                        .map(Person::getAppointment)
                                        .collect(Collectors.toList());
    }

    /**
     * Adds an appointment from a person to the calendar.
     * The appointment must not exist in the calendar.
     */
    public void addAppointment(Person person) {
        appointments.add(person.getAppointment());
    }

    /**
     * Deletes an appointment of a person in the calendar.
     * The person's appointment must exist in the calendar.
     */
    public void deleteAppointment(Person person) {
        appointments.remove(person.getAppointment());
    }

    /**
     * Replaces the given person {@code target}'s appointment in the list with {@code editedPerson}'s calendar.
     * {@code target}'s appointment must exist in the calendar.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setAppointment(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        appointments.set(appointments.indexOf(target.getAppointment()), editedPerson.getAppointment());
    }

    /**
     * Checks if the {@code Person}'s appointment is in the calendar.
     */
    public boolean hasAppointment(Person person) {
        requireNonNull(person);

        // person has no appointment
        if (person.getAppointment().equals(new Appointment(null))) {
            return false;
        }

        for (Appointment appt : appointments) {
            if (person.getAppointment().isWithinInterval(appt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current {@Code Appointment} can be updated with the provided appointment.
     */
    public boolean isValidAppointmentUpdate(Appointment current, Appointment updated) {
        requireNonNull(current);
        requireNonNull(updated);
        if (current.equals(new Appointment(null))) {
            return false;
        }
        if (updated.equals(new Appointment(null))) {
            return false;
        }
        for (Appointment appt : appointments) {
            if (!current.equals(appt) && updated.isWithinInterval(appt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Replaces content in calendar with {@code addressBook}'s list
     */
    public void setAppointments(ReadOnlyAddressBook addressBook) {
        appointments.removeAll(appointments);
        List<Appointment> newAppointments = addressBook.getPersonList().stream()
                                        .map(Person::getAppointment).collect(Collectors.toList());
        appointments.addAll(newAppointments);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Calendar)) {
            return false;
        }

        Calendar otherCalander = (Calendar) other;
        return appointments.equals(otherCalander.appointments);
    }

    @Override
    public int hashCode() {
        return appointments.hashCode();
    }

    /**
     * Clears all appointments from the calendar.
     */
    public void clearAppointments() {
        appointments.clear();
    }
}

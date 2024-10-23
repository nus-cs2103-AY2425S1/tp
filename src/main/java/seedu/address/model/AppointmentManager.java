package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * A utility class to manage the list of appointments.
 */
public class AppointmentManager {
    private List<Appointment> appointments = new ArrayList<>();
    private final Model model;

    /**
     * Constructor to initialize AppointmentManager.
     *
     * @param model The model object to manage appointments.
     */
    public AppointmentManager(Model model) {
        this.model = model;
        this.update();
    }

    /**
     * Resets the list of appointments to an empty list.
     * This method is used at the start of each test to ensure that the list of appointments is reset to an empty list.
     */
    public void reset() {
        appointments = new ArrayList<>();
    }

    /**
     * Updates the list of appointments.
     * This method is intended to be called when the list of appointments in the model changes.
     */
    public void update() {
        appointments = getSortedAppointments();
        checkForConflicts(appointments);
    }

    private void checkForConflicts(List<Appointment> appointments) {
        appointments.sort(Comparator.comparing(Appointment::getStartTime));

        for (int i = 0; i < appointments.size() - 1; i++) {
            Appointment current = appointments.get(i);
            Appointment next = appointments.get(i + 1);

            // Check if the current appointment ends after the next appointment starts
            if (current.getEndTime().isAfter(next.getStartTime())) {
                throw new IllegalArgumentException("Appointments must not overlap.");
            }
        }
    }

    public List<Appointment> getAppointments() {
        appointments.sort(Comparator.comparing(Appointment::getStartTime));
        return appointments;
    }

    private boolean hasConflict(Appointment newAppointment, List<Appointment> appointments) {
        for (Appointment existingAppointment : appointments) {
            if (newAppointment.getStartTime().isBefore(existingAppointment.getEndTime())
                    && newAppointment.getEndTime().isAfter(existingAppointment.getStartTime())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new appointment to the person's list of appointments if there are no conflicts.
     *
     * @param newAppointment The appointment to be added.
     * @param person The person to whom the appointment is added.
     * @return true if the appointment was successfully added, false if there was a conflict.
     */
    public boolean addAppointment(Appointment newAppointment, Person person) {
        if (hasConflict(newAppointment, appointments)) {
            return false;
        }

        Person updatedPerson = new Person(person);
        updatedPerson.addAppointment(newAppointment);
        person.addAppointment(newAppointment);
        update();

        return true;
    }

    /**
     * Removes an appointment from the person's list of appointments.
     *
     * @param appointment The appointment to be removed.
     * @param person The person from whom the appointment is removed.
     */
    public void removeAppointment(Appointment appointment, Person person) {
        person.removeAppointment(appointment);
        update();
    }

    /**
     * Edits an appointment from the person's list of appointments.
     *
     * @param appointment The appointment to be edited.
     * @param person The person from whom the appointment is edited.
     * @param newAppointment The new appointment to replace the old appointment.
     */
    public void editAppointment(Appointment appointment, Person person, Appointment newAppointment) {
        person.editAppointment(appointment, newAppointment);
        update();
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    private List<Appointment> getSortedAppointments() {
        ObservableList<Person> personList = model.getUnfilteredPersonList();

        List<Appointment> allAppointments = new ArrayList<>();

        for (Person person : personList) {
            allAppointments.addAll(person.getAppointments());
        }

        allAppointments.sort(Comparator.comparing(Appointment::getStartTime));

        return allAppointments;
    }
}

package seedu.address.model;

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
    private static AppointmentManager instance;
    private List<Appointment> appointments = new ArrayList<>();
    private final Model model;

    /**
     * Private constructor to prevent instantiation.
     * This is part of the Singleton pattern.
     */
    private AppointmentManager(Model model) {
        this.model = model;
        this.update();
    }

    /**
     * Returns the single instance of AppointmentManager.
     * <p>
     * This class follows the Singleton pattern as there should only be one instance of the
     * AppointmentManager. If an instance does not exist, one will be created with the provided model.
     * </p>
     * @param model The model object to manage appointments.
     * @return The single instance of AppointmentManager.
     **/
    public static AppointmentManager getInstance(Model model) {
        if (instance == null) {
            instance = new AppointmentManager(model);
        }
        instance.update();
        return instance;
    }

    /**
     * Resets the list of appointments to an empty list.
     * This method is used at the start of each test to ensure that the list of appointments is reset to an empty list.
     */
    public void reset() {
        appointments.clear();
    }

    /**
     * Updates the list of appointments.
     * <p>
     * This method is intended to be called when the list of appointments in the model changes.
     * It will update the list of appointments in the AppointmentManager to reflect the changes.
     * </p>
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
     * Adds a new appointment to the person's list of appointments if there are no conflicts with existing appointments.
     * If there is a conflict, an IllegalArgumentException is thrown.
     *
     * @param newAppointment The appointment to be added.
     * @param person The person to whom the appointment is added.
     */
    public void addAppointment(Appointment newAppointment, Person person) {
        if (hasConflict(newAppointment, appointments)) {
            throw new IllegalArgumentException("Appointment conflicts with an existing one.");
        }

        Person updatedPerson = new Person(person);
        updatedPerson.addAppointment(newAppointment);

        model.setPerson(person, updatedPerson);
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

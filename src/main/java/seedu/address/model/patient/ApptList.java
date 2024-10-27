package seedu.address.model.patient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.ApptSorter;

/**
 * Represents a list of appointments.
 * Guarantees: immutable; is always valid
 * @see Appt
 * @see ApptSorter
 */
public class ApptList {
    private final List<Appt> appts;

    /**
     * Constructs an empty {@code ApptList}.
     */
    public ApptList() {
        this.appts = new ArrayList<>();
    }

    /**
     * Constructs an {@code ApptList} with the given appointments.
     * @param appts
     */
    public ApptList(List<Appt> appts) {
        this.appts = appts;
    }

    /**
     * Adds an appointment to the list.
     * Sorts the list after adding the appointment.
     * @param appt
     */
    public void addAppt(Appt appt) {
        appts.add(appt);
        ApptSorter.sortAppointments(appts);
    }

    /**
     * Deletes an appointment from the list.
     * @param appt
     */
    public void deleteAppt(Appt appt) {
        appts.remove(appt);
    }

    /**
     * Returns an immutable list of appointments.
     */
    public List<Appt> getAppts() {
        return Collections.unmodifiableList(appts);
    }

    /**
     * Returns a string representation of the appointments
     * in the form of a list of strings.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Appt appt : appts) {
            sb.append(appt.toString()).append("\n");
        }
        return sb.toString();
    }

}

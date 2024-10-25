package seedu.address.model;

import java.util.Collections;
import java.util.List;

import seedu.address.model.patient.Appt;

/**
 * Contains utility methods for sorting appointments.
 * Sorts appointments by date and time.
 * @see Appt
 */
public class ApptSorter {
    public static void sortAppointments(List<Appt> appointments) {
        Collections.sort(appointments, Appt.DATETIME_COMPARATOR);
    }
}

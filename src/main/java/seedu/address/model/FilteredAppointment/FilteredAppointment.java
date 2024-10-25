package seedu.address.model.filteredappointment;

import static seedu.address.model.patient.Appt.DATETIME_COMPARATOR;

import java.util.Comparator;

import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Patient;


/**
 * Represents a FilteredAppointment object that has
 */
public class FilteredAppointment {

    public static final Comparator<FilteredAppointment> APPOINTMENT_COMPARATOR = new Comparator<FilteredAppointment>() {
        @Override
        public int compare(FilteredAppointment appt1, FilteredAppointment appt2) {
            if (DATETIME_COMPARATOR.compare(appt1.getAppt(), appt2.getAppt()) == 0) {
                return appt1.getPatient().compareTo(appt2.getPatient());
            }
            return DATETIME_COMPARATOR.compare(appt1.getAppt(), appt2.getAppt());
        }
    };

    private final Appt appt;
    private final Patient patient;

    /**
     * Creates a {@code FilteredAppointment} with the given {@Code Appt} and {@Code Patient}
     * @param appt
     * @param patient
     */
    public FilteredAppointment(Appt appt, Patient patient) {
        this.appt = appt;
        this.patient = patient;
    }

    public Appt getAppt() {
        return appt;
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilteredAppointment)) {
            return false;
        }

        FilteredAppointment otherFilteredAppointment = (FilteredAppointment) other;
        return this.appt.equals(otherFilteredAppointment.getAppt())
                && this.patient.equals(otherFilteredAppointment.getPatient());
    }

    @Override
    public String toString() {
        return appt.toString() + "\n" + patient.getName() + " " + patient.getNric();
    }
}

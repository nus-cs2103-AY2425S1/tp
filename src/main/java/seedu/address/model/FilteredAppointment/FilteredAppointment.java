package seedu.address.model.FilteredAppointment;

import static seedu.address.model.person.Appt.DATETIME_COMPARATOR;

import java.util.Comparator;

import seedu.address.model.person.Appt;
import seedu.address.model.person.Person;




/**
 * Represents a FilteredAppointment object that has
 */
public class FilteredAppointment {

    public static final Comparator<FilteredAppointment> APPOINTMENT_COMPARATOR = new Comparator<FilteredAppointment>() {
        @Override
        public int compare(FilteredAppointment appt1, FilteredAppointment appt2) {
            if (DATETIME_COMPARATOR.compare(appt1.getAppt(), appt2.getAppt()) == 0) {
                return appt1.getPerson().compareTo(appt2.getPerson());
            }
            return DATETIME_COMPARATOR.compare(appt1.getAppt(), appt2.getAppt());
        }
    };

    private final Appt appt;
    private final Person person;

    /**
     * Creates a {@code FilteredAppointment} with the given {@Code Appt} and {@Code Person}
     * @param appt
     * @param person
     */
    public FilteredAppointment(Appt appt, Person person) {
        this.appt = appt;
        this.person = person;
    }

    public Appt getAppt() {
        return appt;
    }

    public Person getPerson() {
        return person;
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
                && this.person.equals(otherFilteredAppointment.getPerson());
    }

    @Override
    public String toString() {
        return appt.toString() + "\n" + person.getName() + " " + person.getNric();
    }
}

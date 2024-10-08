package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.isValidDate;

/**
 * Represents an Appointment in the MediBase.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAppointmentName(String)}; date is valid as
 * declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}; time period is valid as declared in
 * {@link #isValidTimePeriod(String)};
 */
public class Appointment {
    public static final String MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT = "Appointment Date should be in the format"
                                                                            + " of yyyy-MM-dd";
    public static final String MESSAGE_CONSTRAINTS = "Appointments names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String apptName;
    private final String apptDate;
    private final String apptTimePeriod;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param apptName A valid appointment name.
     */
    public Appointment(String apptName, String apptDate, String apptTimePeriod) {
        requireNonNull(apptName);
        checkArgument(isValidAppointmentName(apptName), MESSAGE_CONSTRAINTS);
        this.apptName = apptName;

        requireNonNull(apptDate);
        checkArgument(isValidDate(apptDate), MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        this.apptDate = apptDate;

        requireNonNull(apptTimePeriod);
        checkArgument(isValidDate(apptTimePeriod), MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        this.apptTimePeriod = apptTimePeriod;
    }

    /**
     * Returns true if a given string is a valid appt name.
     */
    public static boolean isValidAppointmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment otherAppointment)) {
            return false;
        }

        return apptName.equals(otherAppointment.apptName)
               && apptDate.equals(otherAppointment.apptDate)
               && apptTimePeriod.equals(otherAppointment.apptTimePeriod);
    }

    @Override
    public int hashCode() {
        return apptName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + apptName + ']';
    }
}

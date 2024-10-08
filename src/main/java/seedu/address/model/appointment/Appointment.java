package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Appointment in the MediBase.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAppointmentName(String)}; date is valid as
 * declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}; time period is valid as declared in
 * {@link #checkIsTimePeriodValid(String)} ;
 */
public class Appointment implements Comparable<Appointment> {

    public static final String MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT = "Appointment date should be in the format"
                                                                            + " of yyyy-MM-dd";
    public static final String MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT = "Appointment time period should be"
                                                                                   + " in 24-Hour Format with start "
                                                                                   + "and end times: HHMM-HHMM";
    public static final String MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_INVALID_ORDER = "Starting time of appointment "
                                                                                    + "time should be before ending "
                                                                                    + "time of appointment";
    public static final String MESSAGE_CONSTRAINTS = "Appointment names should be alphanumeric";
    public static final String NAME_VALIDATION_REGEX = "\\p{Alnum}[\\p{Alnum} ]*";

    public final String appointmentName;
    private final String appointmentDate;
    private final TimePeriod appointmentTimePeriod;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param appointmentName A valid appointment name.
     */
    public Appointment(String appointmentName, String appointmentDate, String appointmentTimePeriod) {
        requireNonNull(appointmentName);
        checkArgument(isValidAppointmentName(appointmentName), MESSAGE_CONSTRAINTS);
        this.appointmentName = appointmentName;

        requireNonNull(appointmentDate);
        checkArgument(isValidDate(appointmentDate), MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        this.appointmentDate = appointmentDate;

        requireNonNull(appointmentTimePeriod);
        checkIsTimePeriodValid(appointmentTimePeriod);
        this.appointmentTimePeriod = new TimePeriod(appointmentTimePeriod);
    }


    /**
     * Checks if the given date and time period String clashes with this appointment.
     *
     * @param date String of the date to check.
     * @param timePeriod String of the time Period to check.
     * @return true if there is a clash.
     */
    public boolean isClashing(String date, String timePeriod) {
        requireNonNull(date);
        requireNonNull(timePeriod);
        checkIsTimePeriodValid(timePeriod);
        return date.equals(appointmentDate) && appointmentTimePeriod.isIntersecting(new TimePeriod(timePeriod));
    }

    /**
     * Returns true if a given string is a valid appointment name.
     */
    public static boolean isValidAppointmentName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     *  checks if a given string is a valid appointment time period.
     */
    public static void checkIsTimePeriodValid(String test) {
        checkArgument(TimePeriod.isValidAppointmentTimePeriodFormat(test),
                      MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT);
        checkArgument(TimePeriod.isValidAppointmentTimePeriodOrder(test),
                      MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_INVALID_ORDER);
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

        return appointmentName.equals(otherAppointment.appointmentName)
               && appointmentDate.equals(otherAppointment.appointmentDate)
               && appointmentTimePeriod.equals(otherAppointment.appointmentTimePeriod);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return appointmentName + '[' + appointmentDate + ' ' + appointmentTimePeriod + ']';
    }

    /**
     * Compares the appointment's <b>starting time</b>.
     *
     * @param other the other appointment to be compared.
     * @return <code>0</code> if they start at the same time, <code><0</code> if <code>other</code> starts later,
     * and <code>>0</code> if <code>other</code> starts earlier.
     */
    @Override
    public int compareTo(Appointment other) {
        return getStartDateTime().compareTo(other.getStartDateTime());
    }

    private LocalDateTime getStartDateTime() {
        return LocalDateTime.of(LocalDate.parse(appointmentDate), appointmentTimePeriod.getStartTime());
    }

    /**
     * Convenience class for ease of checking and comparing.
     */
     class TimePeriod implements Comparable<TimePeriod> {

        static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]-([01]?[0-9]|2[0-3])[0-5][0-9]";

        private String timePeriod;
        private final int startTime;
        private final int endTime;

        public TimePeriod(String timePeriod) {
            assert isValidAppointmentTimePeriodFormat(timePeriod);
            assert isValidAppointmentTimePeriodOrder(timePeriod);

            this.timePeriod = timePeriod;
            startTime = Integer.parseInt(timePeriod.substring(0, 4));
            endTime = Integer.parseInt(timePeriod.substring(5,9));
        }

        /**
         * Checks if another TimePeriods intersects. The start and end times are exclusive.
         *
         * @param o the other TimePeriod to compare.
         * @return true if the TimePeriods intersect, else false.
         */
        public boolean isIntersecting(TimePeriod o) {
            return (o.startTime <= startTime) && (startTime < o.endTime) // starts during other TimePeriod; or
                   || (o.startTime < endTime) && (endTime <= o.endTime); // ends during other TimePeriod.
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(o instanceof TimePeriod otherTimePeriod)) {
                return false;
            }

            // integer equality faster than string equality (2 vs 9 comparisons)
            return otherTimePeriod.startTime == startTime
                   && otherTimePeriod.endTime == endTime;
        }

        @Override
        public int hashCode() {
            return timePeriod.hashCode();
        }

        /**
         * Format state as text for viewing.
         */
        @Override
        public String toString() {
            return timePeriod;
        }

        /**
         * Returns true if a given string is a valid appointment time period format.
         */
        public static boolean isValidAppointmentTimePeriodFormat(String test) {
            return test.matches(TIME_VALIDATION_REGEX);
        }

        /**
         * Returns true if a given string is a valid appointment's starting and ending times are valid.
         */
        public static boolean isValidAppointmentTimePeriodOrder(String test) {
            return Integer.parseInt(test.substring(0, 4)) < Integer.parseInt(test.substring(5,9));
        }

        /**
         * Compares the time period's <b>starting time</b>.
         *
         * @param other the object to be compared.
         * @return <code>0</code> if they start at the same time, <code><0</code> if <code>other</code> starts later,
         * and <code>>0</code> if <code>other</code> starts earlier.
         */
        @Override
        public int compareTo(TimePeriod other) {
            return getStartTime().compareTo(other.getStartTime());
        }

        public LocalTime getStartTime() {
            return LocalTime.parse(timePeriod.substring(0, 4), DateTimeFormatter.ofPattern("HHmm"));
        }
    }
}

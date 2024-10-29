package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.isCorrectDateFormat;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Appointment in the MediBase.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAppointmentName(String)}; date is valid as
 * declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}; time period is valid as declared in
 * {@link #checkIsTimePeriodValid(String)} ;
 */
public class Appointment implements Comparable<Appointment> {

    public static final String MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT = "Appointment date should be in the format"
                                                                            + " of yyyy-MM-dd";
    public static final String MESSAGE_CONSTRAINTS_APPT_DATE_INVALID_DATE_1s = "Appointment date [%s] does not exist";
    public static final String MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT = "Appointment time period should be"
                                                                                   + " in 24-Hour Format with start "
                                                                                   + "and end times: HHMM-HHMM";
    public static final String MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_INVALID_ORDER = "Starting time of appointment "
                                                                                    + "time should be before ending "
                                                                                    + "time of appointment";
    public static final String MESSAGE_CONSTRAINTS = "Appointment names should be alphanumeric";
    public static final String MESSAGE_TOO_LONG_CONSTRAINT = "Appointment names should be less than 30 characters";
    public static final String NAME_VALIDATION_REGEX = "\\p{Alnum}[\\p{Alnum} ]*";
    static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]-([01]?[0-9]|2[0-3])[0-5][0-9]";

    private final String appointmentName;
    private final String appointmentDate;
    private final String appointmentTimePeriod;
    private final LocalTime appointmentStartTime;
    private final LocalTime appointmentEndTime;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param appointmentName A valid appointment name.
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment(String appointmentName, String appointmentDate, String appointmentTimePeriod)
            throws IllegalValueException {
        requireNonNull(appointmentName);
        checkArgument(isValidAppointmentName(appointmentName), MESSAGE_CONSTRAINTS);
        this.appointmentName = appointmentName;

        requireNonNull(appointmentDate);
        checkArgument(isCorrectDateFormat(appointmentDate), MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        checkArgument(isValidDate(appointmentDate), String.format(MESSAGE_CONSTRAINTS_APPT_DATE_INVALID_DATE_1s,
                                                                  appointmentDate));
        this.appointmentDate = appointmentDate;

        requireNonNull(appointmentTimePeriod);
        checkIsTimePeriodValid(appointmentTimePeriod);
        this.appointmentTimePeriod = appointmentTimePeriod;
        this.appointmentStartTime = getTimeFromString(appointmentTimePeriod.substring(0, 4));
        this.appointmentEndTime = getTimeFromString(appointmentTimePeriod.substring(5, 9));

    }

    private LocalTime getTimeFromString(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));
    }


    /**
     * Checks if the given date and time period String clashes with this appointment.
     * <pre>
     *      Clashes:                No clash
     *            s    e               s    e        |
     *  Appt 1:   |----|               |----|        |
     *  Appt 2: |------|            |--|             |
     *         os  oe              os  oe            |
     *                                               |
     *            s    e              s    e         |
     *  Appt 1:   |----|              |----|         |
     *  Appt 2:   |------|                 |--|      |
     *            os     oe                os  oe    |
     *                                               |
     *            s    e                             |
     *  Appt 1:   |----|                             |
     *  Appt 2: |---------|                          |
     *         os        oe                          |
     *                                               |
     *          s         e                          |
     *  Appt 1: |---------|                          |
     *  Appt 2:   |----|                             |
     *           os   oe                             |
     *                                               |
     *          s         e                          |
     *  Appt 1: |---------|                          |
     *  Appt 2: |---------|                          |
     *         os        oe                          |
     * </pre>
     *
     * @param date String of the date to check.
     * @param timePeriod String of the time Period to check.
     * @return true if there is a clash.
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public boolean isClashing(String date, String timePeriod) throws IllegalValueException {
        requireNonNull(date);
        requireNonNull(timePeriod);
        checkIsTimePeriodValid(timePeriod);
        LocalTime startTime = getTimeFromString(timePeriod.substring(0, 4));
        LocalTime endTime = getTimeFromString(timePeriod.substring(5, 9));

        if (!date.equals(appointmentDate)) {
            return false;
        }

        if (timePeriod.equals(appointmentTimePeriod)) {
            return true;
        }

        boolean isApptEndingDuringTimePeriod = appointmentEndTime.isBefore(startTime)
                                               && !(appointmentStartTime.isBefore(endTime));
        boolean isApptStartingDuringTimePeriod = appointmentEndTime.isAfter(startTime)
                                                 && !(appointmentStartTime.isAfter(endTime));

        return isApptEndingDuringTimePeriod || isApptStartingDuringTimePeriod;
    }

    /**
     * Returns true if a given string is a valid appointment name.
     */
    public static boolean isValidAppointmentName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid appointment time period.
     *
     * @throws IllegalValueException if there were any time period constrain violations.
     */
    public static void checkIsTimePeriodValid(String test) throws IllegalValueException {
        checkArgument(isValidAppointmentTimePeriodFormat(test),
                      MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_WRONG_FORMAT);
        checkArgument(isValidAppointmentTimePeriodOrder(test),
                      MESSAGE_CONSTRAINTS_APPT_TIME_PERIOD_INVALID_ORDER);
    }

    private static Boolean isValidAppointmentTimePeriodFormat(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    private static Boolean isValidAppointmentTimePeriodOrder(String test) {
        return Integer.parseInt(test.substring(0, 4)) < Integer.parseInt(test.substring(5, 9));
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
        return appointmentName + " [ " + appointmentDate + " @ " + appointmentTimePeriod + " ]";
    }

    /**
     * Compares the appointment's <b>starting time</b>.
     *
     * @param other the other appointment to be compared.
     * @return <code>0</code> if they start at the same time, <code><0</code> if <code>other</code> starts later,
     *     and <code>>0</code> if <code>other</code> starts earlier.
     */
    @Override
    public int compareTo(Appointment other) {
        return getAppointmentStartDateTime().compareTo(other.getAppointmentStartDateTime());
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTimePeriod() {
        return appointmentTimePeriod;
    }

    public LocalDateTime getAppointmentStartDateTime() {
        return LocalDateTime.of(LocalDate.parse(appointmentDate), appointmentStartTime);
    }
}

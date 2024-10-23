package seedu.address.model.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's scheduled appointment in the address book.
 * Guarantees: immutable; is always valid
 */
public class Schedule {

    private final String dateTime;
    private final String notes;

    private boolean isPaid;

    /**
     * Constructs a {@code Schedule} object with the given date and time.
     *
     * @param dateTime The date and time of the scheduled appointment.
     * @param notes Optional notes regarding the appointment.
     */
    public Schedule(String dateTime, String notes) {
        this.dateTime = dateTime == null ? "" : dateTime;
        this.notes = notes;
        this.isPaid = false;
    }

    /**
     * Returns the date and time associated with the appointment.
     * @return a {@code String} representing the date and time.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Returns the notes associated with the appointment.
     * @return a {@code String} representing the notes.
     */
    public String getNotes() {
        return notes;
    }

    public boolean getPaymentStatus() {
        return isPaid;
    }

    /**
     * Marks the payment as paid.
     */
    public void markPaymentAsPaid() {
        this.isPaid = true;
    }

    /**
     * Marks the payment as unpaid.
     */
    public void markPaymentAsUnpaid() {
        this.isPaid = false;
    }

    /**
     * Returns true if a given string is a valid date time.
     */
    public static void isValidDateTime(String dateTime) throws ParseException {
        try {
            LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
    }

    @Override
    public String toString() {
        return this.dateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && dateTime.equals(((Schedule) other).dateTime)); // state check
    }
    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}

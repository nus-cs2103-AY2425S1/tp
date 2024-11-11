package seedu.address.model.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;

import java.time.LocalDate;
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

    public String getFormattedDateTime() {
        LocalDateTime dateTime = LocalDateTime.parse(getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
        return formattedDate;
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

    public String getIsPaid() {
        return isPaid ? "Paid" : "Unpaid";
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
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDate parsedDate = localDateTime.toLocalDate();
            String[] dateParts = dateTime.split(" ")[0].split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);

            if (parsedDate.getYear() != year || parsedDate.getMonthValue() != month
                    || parsedDate.getDayOfMonth() != day) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        } catch (DateTimeParseException | NumberFormatException e) {
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

package seedu.address.model.contactrecord;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import seedu.address.model.person.CallFrequency;

/**
 * Represents a Person's contacted date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContactRecord(String)}
 */
public class ContactRecord implements Comparable<ContactRecord> {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format of " + DATE_FORMAT + " and should not be in the future.";
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public final LocalDate value;
    private final String notes;

    /**
     * Constructs a {@code ContactRecord}.
     *
     * @param date A valid date and time.
     */
    public ContactRecord(LocalDate date, String notes) {
        requireNonNull(date);
        requireNonNull(notes);
        value = date;
        this.notes = notes;
    }

    /**
     * Returns true if a given string is a valid record with a valid date.
     */
    public static boolean isValidContactRecord(String testDate) {
        try {
            if (!testDate.matches(VALIDATION_REGEX)) {
                return false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate date = LocalDate.parse(testDate, formatter);
            if (date.isAfter(LocalDate.now())) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the current record.
     */
    public static ContactRecord createCurrentRecord(String notes) {
        return new ContactRecord(LocalDate.now(), notes);
    }

    /**
     * Returns a new {@code ContactRecord} that is a specified number of days after the current date.
     * The number of days is determined by the {@code callFrequency}.
     *
     * @param callFrequency The frequency of calls, represented as a {@code CallFrequency} object,
     *                      which determines how many days to add to the current date.
     * @return A new {@code ContactRecord} that is {@code callFrequency} days after the current date.
     */
    public LocalDate add(CallFrequency callFrequency) {
        int daysToAdd = Integer.parseInt(callFrequency.value); // Parse the number of days from callFrequency
        LocalDate nextDate = value.plusDays(daysToAdd);
        return nextDate;
    }

    @Override
    public String toString() {
        return "Date: " + value.toString() + (!Objects.equals(notes, "") ? " Notes: " + notes : "");
    }

    public String getDate() {
        return value.toString();
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactRecord)) {
            return false;
        }

        ContactRecord otherRecord = (ContactRecord) other;
        return value.equals(otherRecord.value) && notes.equals(otherRecord.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, notes);
    }

    @Override
    public int compareTo(ContactRecord other) {
        return value.compareTo(other.value);
    }
}
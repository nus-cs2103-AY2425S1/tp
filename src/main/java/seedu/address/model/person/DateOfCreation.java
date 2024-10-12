package seedu.address.model.person;

import java.time.DateTimeException;
import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents the date of creation for an entity.
 * Encapsulates a {@code LocalDate} to provide additional functionality for managing the creation date.
 */
public class DateOfCreation {

    // Encapsulated date of creation as a LocalDate
    private final LocalDate dateOfCreation;

    /**
     * Constructs a {@code DateOfCreation} with the specified {@code LocalDate}.
     *
     * @param dateOfCreation The date when the entity was created.
     */
    public DateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    /**
     * Constructs a {@code DateOfCreation} with the current date.
     */
    public DateOfCreation() {
        this.dateOfCreation = LocalDate.now(); // Automatically set the creation date to the current date
    }

    /**
     * Returns the stored date of creation.
     *
     * @return A {@code LocalDate} representing the date of creation.
     */
    public LocalDate getDateOfCreation() {
        return this.dateOfCreation;
    }

    /**
     * Checks if the given date is after the date of creation.
     *
     * @param other The date to compare with the creation date.
     * @return True if the given date is after the creation date, otherwise false.
     */
    public boolean isAfter(LocalDate other) {
        return !other.isBefore(this.dateOfCreation);
    }
    /**
     * Creates a {@code DateOfCreation} object from a string representation of a date.
     * The string should follow the format {@code YYYY-MM-DD}.
     *
     * @param date The string representation of the date.
     * @return A {@code DateOfCreation} object with the parsed {@code LocalDate}.
     * @throws IllegalValueException If the string does not conform to a valid date format.
     */
    public static DateOfCreation of(String date) throws IllegalValueException {
        try {
            return new DateOfCreation(LocalDate.parse(date));
        } catch (DateTimeException e) {
            throw new IllegalValueException("Invalid date format!");
        }
    }

    /**
     * Provides a string representation of the date of creation.
     *
     * @return The date of creation formatted as a string.
     */
    @Override
    public String toString() {
        return dateOfCreation.toString();
    }
    @Override
    public boolean equals(Object other) {
        // Check if the other object is the same instance
        if (this == other) {
            return true;
        }

        // Check if the other object is an instance of DateOfCreation
        if (!(other instanceof DateOfCreation)) {
            return false;
        }

        // Compare the dateOfCreation value
        DateOfCreation otherDateOfCreation = (DateOfCreation) other;
        return this.dateOfCreation.equals(otherDateOfCreation.dateOfCreation);
    }

}

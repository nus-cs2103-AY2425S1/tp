package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a reminder in NetBook
 */
public class Reminder {
    public static final String MESSAGE_CONSTRAINTS_DATE = "Date should be in the format DD-MM-YYYY";
    public static final String MESSAGE_CONSTRAINTS_DESCRIPTION = "Description should not be empty";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final LocalDate reminderDate;
    public final String reminderDescription;
    public final Name personToMeet;

    /**
     * Constructs a {@code Reminder}
     *
     * @param date A valid date
     * @param description A valid description
     * @param personToMeet A valid name of the person that the reminder is linked to
     */
    @JsonCreator
    public Reminder(@JsonProperty("date") String date, @JsonProperty("description") String description,
                    @JsonProperty("person") Name personToMeet) {
        requireNonNull(date, description);
        requireNonNull(personToMeet);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS_DATE);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS_DESCRIPTION);
        this.reminderDate = LocalDate.parse(date, formatter);
        this.reminderDescription = description;
        this.personToMeet = personToMeet;
    }

    /**
     * Checks if string is a valid date.
     *
     * @param test String to check.
     * @return Whether date is valid.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if string is a valid description.
     *
     * @param test String to check.
     * @return Whether description is valid.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Getter for reminderDate
     *
     * @return reminderDate of Reminder
     */
    public String getReminderDateAsString() {
        return reminderDate.format(formatter);
    }

    /**
     * Getter for reminderDescription
     *
     * @return reminderDescription of reminder
     */
    public String getReminderDescription() {
        return reminderDescription;
    }

    /**
     * Getter for personToMeet
     *
     * @return personToMeet of Reminder
     */
    public Name getPersonToMeet() {
        return personToMeet;
    }
    @Override
    public String toString() {
        return "Date: " + reminderDate.toString() + "\n" + "Description: " + reminderDescription + "\n" + "Name: " + personToMeet.toString();
    }

    /**
     * Returns true if both reminders have the same date, description, personToMeet.
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getReminderDateAsString().equals(this.getReminderDateAsString())
                && otherReminder.getReminderDescription().equals(this.getReminderDescription())
                && otherReminder.getPersonToMeet().equals(this.getPersonToMeet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return reminderDescription.equals(otherReminder.reminderDescription)
                && reminderDate.equals(otherReminder.reminderDate)
                && personToMeet.equals(otherReminder.personToMeet);
    }

    @Override
    public int hashCode() {
        return reminderDate.hashCode();
    }
}

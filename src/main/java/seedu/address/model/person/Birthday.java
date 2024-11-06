package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_CONSTRAINTS =
            "Birthdays should only contain numbers, and it should be in the format DD MM YYYY. \nElse,"
                    + " ensure that the your day, month or year inputs are valid";
    public final String value;
    public final LocalDate date;

    /**
     * Constructs a {@code birthday}.
     *
     * @param birthday A valid birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_CONSTRAINTS);
        value = birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM uuuu");
        date = LocalDate.parse(birthday, formatter);
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM uuuu");
        LocalDate testDate;

        try {
            LocalDate.parse(test, formatter); // The date is valid
        } catch (DateTimeParseException e) {
            return false; // The date is invalid
        }

        try {
            testDate = LocalDate.of(Integer.parseInt(test.substring(6, 10)), Integer.parseInt(test.substring(3, 5)),
                    Integer.parseInt(test.substring(0, 2))); // The date is valid
        } catch (DateTimeException e) {
            return false; // The date is invalid
        }

        return !testDate.isAfter(LocalDate.now());
    }

    /**
     * Checks if the person's birthday is within 7 days before or after the current date.
     */
    public boolean hasBirthdayWithin7Days() {
        LocalDate today = LocalDate.now();
        LocalDate birthdayThisYear = this.date.withYear(today.getYear());

        long daysToBirthday = ChronoUnit.DAYS.between(today, birthdayThisYear);

        // Adjust if birthday already passed and lookahead is next year's date
        if (daysToBirthday < -7) {
            birthdayThisYear = birthdayThisYear.plusYears(1);
            daysToBirthday = ChronoUnit.DAYS.between(today, birthdayThisYear);
        }

        return Math.abs(daysToBirthday) <= 7;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Birthday)) {
            return false;
        }

        Birthday otherBirthday = (Birthday) other;
        return value.equals(otherBirthday.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

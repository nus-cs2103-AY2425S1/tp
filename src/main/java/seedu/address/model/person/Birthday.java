package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_CONSTRAINTS = "Birthday should be a valid date of the format yyyy-mm-dd and be"
            + " within reasonable limits.";
    public static final String BIRTHDAY_REMINDER_HEADER = "Upcoming birthdays: \n";
    public static final String BIRTHDAY_REMINDER_EMPTY = "No upcoming birthdays.\n";
    public static final String CUSTOM_BIRTHDAY_FORMAT = "'s birthday is on "; //Used for displaying a person's birthday
    public static final Birthday EMPTY_BIRTHDAY = Birthday.of("");
    public static final LocalDate LOWER_BOUND = LocalDate.parse("1908-05-23");
    public final LocalDate value;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_CONSTRAINTS);
        if (birthday.isEmpty()) {
            value = LocalDate.MIN;
        } else {
            value = LocalDate.parse(birthday);
        }
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        if (test.isEmpty()) {
            return true;
        }
        try {
            LocalDate date = LocalDate.parse(test);
            return !date.isBefore(LOWER_BOUND) && !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns a {@code Birthday} object of the passed {@code birthday}, which throws {@link IllegalArgumentException}
     * if the birthday format is invalid.
     * @param birthday of the Person
     */
    public static Birthday of(String birthday) {
        return new Birthday(birthday);
    }

    /**
     * Returns the value stored in the Birthday object as a string.
     */
    public String getValue() {
        return value.toString();
    }

    /**
     * Returns true if the stored date in {@code Birthday} is within a week from today.
     */
    public boolean isBirthdayWithinNextWeek() {
        LocalDate today = LocalDate.now();
        LocalDate dateInOneWeek = today.plusWeeks(1);
        LocalDate upcomingBirthday = getDateOfUpcomingBirthday();
        return upcomingBirthday.isAfter(today.minusDays(1)) && upcomingBirthday.isBefore(dateInOneWeek);
    }

    /**
     * Returns the earliest upcoming day of the birthday.
     */
    public LocalDate getDateOfUpcomingBirthday() {
        LocalDate birthdayThisYear = value.withYear(LocalDate.now().getYear());
        if (birthdayThisYear.isBefore(LocalDate.now())) {
            return birthdayThisYear.plusYears(1);
        } else {
            return birthdayThisYear;
        }
    }

    @Override
    public String toString() {
        return value.equals(LocalDate.MIN) ? "" : value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

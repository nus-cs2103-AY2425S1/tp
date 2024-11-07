package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.exceptions.VolunteerNotAvailableOnAnyDayException;

/**
 * Represents a Event's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 * Throws: DateTimeParseException if an invalid date is provided
 */
public class VolunteerDates {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should follow the format yyyy-mm-dd, and it should not be blank. Multiple dates in a list should"
                    + "separated by a comma (space is optional) E.g 2022-01-15, 2033-02-17";
    private final ArrayList<LocalDate> dates = new ArrayList<>();
    private final StringProperty datesListAsObservableString = new SimpleStringProperty();

    /**
     * Constructs a {@code Date}.
     *
     * @param listOfDates A valid list of dates.
     */
    public VolunteerDates(String listOfDates) throws DateTimeParseException, VolunteerDuplicateDateException {
        String[] dates = listOfDates.trim().split(",");
        this.addStringOfDatesToAvailList(dates);
    }

    public StringProperty getDatesListAsObservableString() {
        return this.datesListAsObservableString;
    }

    /**
     * Adds a given string of dates to its list of dates.
     * @param dates
     * @throws DateTimeParseException
     * @throws VolunteerDuplicateDateException
     */
    public void addStringOfDatesToAvailList(String... dates) throws DateTimeParseException,
            VolunteerDuplicateDateException {

        requireNonNull(dates);
        // Check each date's format and uniqueness
        Set<LocalDate> uniqueDates = new HashSet<>();

        for (String s : dates) {
            requireNonNull(s);
            String d = s.trim(); // Only trim outer spaces
            checkArgument(isValidDate(d), MESSAGE_CONSTRAINTS);
            LocalDate date = LocalDate.parse(d);

            if (!uniqueDates.add(date)) {
                throw new VolunteerDuplicateDateException(d);
            }
        }

        for (LocalDate d: uniqueDates) {
            this.addDateToAvailList(d);
        }

        this.datesListAsObservableString.set(this.toString());
    }

    /**
     * Removes a given string of dates from its list of dates.
     * @param dates
     * @throws DateTimeParseException
     * @throws VolunteerDuplicateDateException
     */

    public void removeStringOfDatesFromAvailList(String... dates) throws DateTimeParseException,
            VolunteerDeleteMissingDateException, VolunteerNotAvailableOnAnyDayException {

        requireNonNull(dates);
        // Check each date's format and uniqueness
        Set<LocalDate> uniqueDates = new HashSet<>();

        for (String s : dates) {
            requireNonNull(s);
            String d = s.trim(); // Only trim outer spaces
            LocalDate date = LocalDate.parse(d);
            if (!hasAvailableDate(date) || !uniqueDates.add(date)) {
                throw new VolunteerDeleteMissingDateException(d);
            }
        }

        if (uniqueDates.size() >= this.dates.size()) {
            throw new VolunteerNotAvailableOnAnyDayException();
        }

        for (LocalDate d: uniqueDates) {
            this.removeDateFromAvailList(d);
        }

        this.datesListAsObservableString.set(this.toString());
    }

    private void addDateToAvailList(LocalDate date) throws VolunteerDuplicateDateException {
        if (this.dates.contains(date)) {
            throw new VolunteerDuplicateDateException(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            this.dates.add(date);
            this.datesListAsObservableString.set(this.toString());
        }
    }

    private void removeDateFromAvailList(LocalDate date) throws VolunteerDeleteMissingDateException {
        if (!this.dates.contains(date)) {
            throw new VolunteerDeleteMissingDateException(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            this.dates.remove(date);
            this.datesListAsObservableString.set(this.toString());
        }
    }


    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid list of dates.
     * @param test
     * @return
     */
    public static boolean isValidListOfDates(String test) {
        requireNonNull(test);
        String[] dates = test.trim().split(",");
        // Check each date's format and uniqueness
        Set<LocalDate> uniqueDates = new HashSet<>();
        for (String s : dates) {
            requireNonNull(s);
            String d = s.trim(); // Only trim outer spaces
            if (!isValidDate(d)) {
                return false;
            }
            LocalDate date = LocalDate.parse(d);
            if (!uniqueDates.add(date)) {
                return false;
            }
        }

        return true;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (LocalDate d : dates) {
            s.append(d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if (dates.indexOf(d) != dates.size() - 1) {
                s.append(", ");
            }
        }
        return s.toString();
    }

    public String toParsableString() {
        return this.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerDates)) {
            return false;
        }

        VolunteerDates otherDate = (VolunteerDates) other;
        return dates.equals(otherDate.dates);
    }

    @Override
    public int hashCode() {
        return dates.hashCode();
    }

    public boolean hasAvailableDate(LocalDate date) {
        return this.dates.contains(date);
    }
}

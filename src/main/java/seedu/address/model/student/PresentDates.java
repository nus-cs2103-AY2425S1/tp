package seedu.address.model.student;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import seedu.address.model.student.exceptions.StudentNotInTutDateException;
import seedu.address.model.tut.TutDate;

/**
 * A class representing a collection of {@link TutDate} objects.
 * Provides methods to add dates, retrieve an unmodifiable list of dates, and to compare
 * {@code PresentDates} objects. Also includes a custom string representation of the dates.
 */
public class PresentDates {

    private final ObservableSet<TutDate> dates;

    /**
     * Constructs a {@code PresentDates} object with an initial set of {@link TutDate} objects.
     *
     * @param dates A {@link Set} of {@link TutDate} objects.
     */
    public PresentDates(Set<TutDate> dates) {
        requireNonNull(dates);
        this.dates = FXCollections.observableSet(dates);
    }

    /**
     * Adds a {@link TutDate} to the collection.
     *
     * @param date The {@link TutDate} to be added.
     */
    public void add(TutDate date) {
        requireNonNull(date);
        dates.add(date);
    }

    /**
     * Returns an unmodifiable observable set of {@link TutDate} objects in the collection.
     *
     * @return An unmodifiable {@link ObservableSet} of {@link TutDate} objects.
     */
    public ObservableSet<TutDate> getList() {
        return FXCollections.unmodifiableObservableSet(dates);
    }

    public void setAttendance(TutDate tutDate) {
        requireNonNull(tutDate);
        dates.add(tutDate);
    }

    public void setAbsent(TutDate tutDate) {
        requireNonNull(tutDate);
        if (!dates.contains(tutDate)) {
            throw new StudentNotInTutDateException();
        }
        dates.remove(tutDate);
    }

    public ObservableSet<TutDate> getDates() {
        return dates;
    }

    /**
     * Returns a string representation of the {@code PresentDates} object.
     * The string is formatted as a list of {@link TutDate} objects enclosed in square brackets.
     *
     * @return A string representation of the {@code PresentDates} object.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[ ");
        for (TutDate date : dates) {
            s.append(date.toString()).append(' ');
        }
        s.append(']');
        return s.toString();
    }

    /**
     * Checks if this {@code PresentDates} object is equal to another object.
     * Two {@code PresentDates} objects are considered equal if their date collections are equal.
     *
     * @param other The object to compare with this {@code PresentDates}.
     * @return {@code true} if both objects have the same dates, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PresentDates otherDates)) {
            return false;
        }
        return dates.equals(otherDates.dates);
    }
}


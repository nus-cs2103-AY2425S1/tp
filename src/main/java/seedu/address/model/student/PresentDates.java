package seedu.address.model.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.tut.TutDate;

/**
 * A class representing a collection of {@link TutDate} objects.
 * Provides methods to add dates, retrieve an unmodifiable list of dates, and to compare
 * {@code PresentDates} objects. Also includes a custom string representation of the dates.
 */
public class PresentDates {

    private final ArrayList<TutDate> dates;

    /**
     * Constructs a {@code PresentDates} object with an initial list of {@link TutDate} objects.
     *
     * @param dates An {@link ArrayList} of {@link TutDate} objects.
     */
    public PresentDates(ArrayList<TutDate> dates) {
        this.dates = dates;
    }

    /**
     * Adds a {@link TutDate} to the collection.
     *
     * @param date The {@link TutDate} to be added.
     */
    public void add(TutDate date) {
        dates.add(date);
    }

    /**
     * Returns an unmodifiable list of {@link TutDate} objects in the collection.
     *
     * @return A {@link List} of {@link TutDate} objects.
     */
    public List<TutDate> getList() {
        return Collections.unmodifiableList(dates);
    }

    /**
     * Returns a string representation of the {@code PresentDates} object.
     * The string is formatted as a list of {@link TutDate} objects enclosed in square brackets.
     *
     * @return A string representation of the {@code PresentDates} object.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (TutDate date : dates) {
            s.append(date.toString());
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
        if (!(other instanceof PresentDates)) {
            return false;
        }
        PresentDates otherDates = (PresentDates) other;
        return dates.equals(otherDates.dates);
    }
}


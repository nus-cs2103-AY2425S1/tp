package seedu.address.model;

import seedu.address.model.person.*;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class SortPreference {
    public static final String MESSAGE_CONSTRAINTS =
            "Sort preferences should be one of the following: high, low, recent, distant, or default";
    private static final String[] VALID_PREFERENCES = {"high", "low", "recent", "distant", "default"};

    public final String value;

    /**
     * Constructs a {@code SortPreference}
     *
     * @param preference A valid sort preference
     */
    public SortPreference(String preference) {
        requireNonNull(preference);
        checkArgument(isValidSortPreference(preference), MESSAGE_CONSTRAINTS);
        value = preference;
    }

    /**
     * Returns true if a given string is a valid sort preference
     */
    public static boolean isValidSortPreference(String test) {
        for (String preference : VALID_PREFERENCES) {
            if (preference.equals(test)) {
                return true;
            }
        }
        return false;
    }

    public Comparator<Person> getComparator() {
        switch(value) {
            case "high":
                return new PriorityHighToLowComparator();
            case "low":
                return new PriorityLowToHighComparator();
            case "distant":
                return new DateDistantToRecentComparator();
            case "recent":
                return new DateRecentToDistantComparator();
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals (Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortPreference)) {
            return false;
        }

        SortPreference otherSortPreference = (SortPreference) other;
        return value.equals(otherSortPreference.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
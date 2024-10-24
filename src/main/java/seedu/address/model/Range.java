package seedu.address.model;

import static java.util.Objects.requireNonNull;

/**
 * A generic class representing a range of values with upper and lower bound.
 * The type of bound is determined by the generic type {@code T}
 *
 * @param <T> the type of the range boundaries
 */
public class Range<T> {
    public final T lowerBound;
    public final T upperBound;

    /**
     * Constructs a new {@code Range} with given lower and upper bounds.
     * @param lowerBound lower bound of the range
     * @param upperBound upper bound of the range
     */
    public Range(T lowerBound, T upperBound) {
        requireNonNull(lowerBound);
        requireNonNull(upperBound);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public String toString() {
        return lowerBound.toString() + " - " + upperBound.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Range)) {
            return false;
        }

        Range otherRange = (Range) other;
        return upperBound.equals(otherRange.upperBound)
                && lowerBound.equals(otherRange.lowerBound);
    }

}

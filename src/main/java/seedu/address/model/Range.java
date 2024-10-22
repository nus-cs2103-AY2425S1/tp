package seedu.address.model;

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
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

}

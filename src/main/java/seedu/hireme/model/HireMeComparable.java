package seedu.hireme.model;

/**
 * Has the ability to compare whether two objects are the same
 */
public interface HireMeComparable<T> {

    /**
     * Returns true if other object is duplicate
     *
     * @param other The other object
     * @return True if the given object is the same as the current one, false otherwise.
     */
    boolean isSame(T other);
}

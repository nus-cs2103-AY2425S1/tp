package seedu.address.logic.parser;

/**
 * A prefix that marks the beginning of an
 * argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;

    /**
     * Constructs a Prefix with the specified prefix string.
     *
     * @param prefix The prefix string associated with this Prefix object.
     */
    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Retrieves the prefix string of this Prefix.
     *
     * @return The prefix string.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns the string representation of this Prefix, which is the prefix string.
     *
     * @return The prefix string.
     */

    @Override
    public String toString() {
        return getPrefix();
    }

    /**
     * Computes the hash code of this Prefix.
     *
     * @return The hash code value for this Prefix, or 0 if the prefix is null.
     */

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    /**
     * Compares this Prefix object to another object for equality.
     *
     * @param other The object to compare against.
     * @return true if the other object is a Prefix with the same prefix string, false otherwise.
     */

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Prefix)) {
            return false;
        }

        Prefix otherPrefix = (Prefix) other;
        return prefix.equals(otherPrefix.prefix);
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    private final String prefix;
    private final String shortPrefix;

    /**
     * Creates a {@code Prefix} object with prefix and shortPrefix specified by the arguments.
     */
    public Prefix(String prefix, String shortPrefix) {
        requireNonNull(prefix, shortPrefix);
        this.prefix = prefix;
        this.shortPrefix = shortPrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getShortPrefix() {
        return shortPrefix;
    }

    @Override
    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

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
        return prefix.equals(otherPrefix.prefix) && shortPrefix.equals(otherPrefix.shortPrefix);
    }
}

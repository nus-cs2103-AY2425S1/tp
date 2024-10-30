package seedu.address.model.util;

/**
 * Represents a pair of objects.
 * @param <A> The type of the first object.
 * @param <B> The type of the second object.
 */
public class Pair<A, B> {
    private final A first;
    private final B second;

    /**
     * Constructs a Pair with the specified values.
     * @param first The first value.
     * @param second The second value.
     */
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first value.
     * @return The first value.
     */
    public A getFirst() {
        return first;
    }

    /**
     * Returns the second value.
     * @return The second value.
     */
    public B getSecond() {
        return second;
    }

    /**
     * Checks if two pairs are equal.
     * @param obj The object to compare with.
     * @return true if the pairs are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return (first == null ? other.first == null : first.equals(other.first))
                    && (second == null ? other.second == null : second.equals(other.second));
        }
        return false;
    }

    /**
     * Computes the hash code of the pair.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        int hashFirst = (first == null) ? 0 : first.hashCode();
        int hashSecond = (second == null) ? 0 : second.hashCode();
        return hashFirst ^ hashSecond;
    }
}

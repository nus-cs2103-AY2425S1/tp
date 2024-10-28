package seedu.address.storage;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class encapsulates a generic Pair structure because JavaFX's Pair class throws an InaccessibleObjectException
 * on GitHub for an unknown reason.
 *
 * @param <K> Type of the key.
 * @param <V> Type of the value.
 */
public class Pair <K, V> implements Serializable {
    private final K key;
    private final V value;

    /**
     * Creates a new Pair instance with the given key and value.
     *
     * @param key The key.
     * @param value The value.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pair<?, ?> otherPair)) {
            return false;
        }

        return key.equals(otherPair.key)
                && value.equals(otherPair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}

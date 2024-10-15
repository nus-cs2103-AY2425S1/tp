package seedu.address.model.person;

import java.util.Objects;

/**
 * A simple class to group two closely related information together.
 *
 * @param <K> Supposed "key".
 * @param <V> Supposed "value".
 */
public class Pair<K, V> {
    private K id;
    private V remarks;

    /**
     * Creates a new Pair instance to group two different elements together.
     * @param id
     * @param remarks
     */
    public Pair(K id, V remarks) {
        this.id = id;
        this.remarks = remarks;
    }

    public K getId() {
        return id;
    }

    public V getRemarks() {
        return remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o; // Typecast to Pair

        // Compare key and value
        return Objects.equals(id, pair.id) && Objects.equals(remarks, pair.remarks);
    }
}

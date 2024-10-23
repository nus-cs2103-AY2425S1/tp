package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Map<?, ?> map) {
        requireNonNull(map);
        map.forEach((key, value) -> {
            requireNonNull(key);
            requireNonNull(value);
        });
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Returns true if the supplied arrays are of the same length.
     */
    public static boolean areOfSameSize(Object[]... arrays) {
        for (Object[] array: arrays) {
            requireNonNull(array);
        }
        if (arrays.length == 0) {
            return true;
        }
        int len = arrays[0].length;
        for (Object[] array: arrays) {
            if (array.length != len) {
                return false;
            }
        }
        return true;
    }
}

package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Returns true if {@code dequeA} has the same items in the same ordering as {@code dequeB}.
     */
    public static boolean isCollectionEqual(Collection<?> dequeA, Collection<?> dequeB) {
        requireAllNonNull(dequeA, dequeB);
        if (dequeA.size() != dequeB.size()) {
            return false;
        }
        if (dequeA.isEmpty()) {
            return true;
        }

        //adapted from https://stackoverflow.com/questions/77306409/java-how-to-tell-if-two-arraydeque-are-equal
        Iterator<?> aIter = dequeA.iterator();
        Iterator<?> bIter = dequeB.iterator();

        while (aIter.hasNext()) {
            if (!Objects.equals(aIter.next(), bIter.next())) {
                return false;
            }
        }
        return true;
    }
}

package seedu.address.model.util;

import java.util.function.Predicate;

/**
 * A utility class to help with building predicates.
 */
public class PredicateBuilder {
    public static <T> Predicate<T> combinePredicates(Predicate<T> primary, Predicate<T> secondary) {
        return secondary == null ? primary : primary.and(secondary);
    }
}

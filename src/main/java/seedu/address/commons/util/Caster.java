package seedu.address.commons.util;

import java.util.function.Function;

/**
 * A class that that helps java to perform (safe) implicit type casting,
 * to mitigate the usage of (possibly unsafe) explicit type casting.
 */
public class Caster<T> implements Function<T, T> {
    /**
     * Casts an object to its supertype implicitly.
     * @param o the object
     * @return the same object
     * @param <T> the supertype to cast to
     */
    public static <T> T cast(T o) {
        return o;
    }

    /**
     * Casts an object to its supertype implicitly.
     * @param t the object
     * @return the same object
     */
    @Override
    public T apply(T t) {
        return t;
    }
}

package seedu.address.commons.util;

import java.util.Objects;

/**
 * Wrapper class around the Objects class for null safe equals comparison.
 */
public class EqualUtil {
    public static boolean nullSafeEquals(Object a, Object b) {
        return Objects.equals(a, b);
    }
}

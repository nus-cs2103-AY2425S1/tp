package seedu.address.commons.util;

import java.util.Objects;

public class EqualUtil {
    public static boolean nullSafeEquals(Object a, Object b) {
        return Objects.equals(a, b);
    }
}

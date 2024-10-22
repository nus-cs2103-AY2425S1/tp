package seedu.address.commons.util;

import java.util.Optional;

public class OptionalUtil {
    /**
     * Returns the value of the {@code Optional} if it is present, otherwise returns {@code other}.
     *
     * @param optional the {@code Optional} to unwrap
     * @return the value of the {@code Optional} if it is present, otherwise {@code other}
     */

    public static String optionalToString(Optional<?> optional) {
        return optional.map(Object::toString).orElse("");
    }
}

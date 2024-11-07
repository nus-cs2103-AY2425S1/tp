package seedu.internbuddy.model.company;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Enum containing valid {@link Status} types.
 */
public enum StatusType {
    INTERESTED, APPLIED, CLOSED;

    private static final HashSet<String> VALUES =
            new HashSet<>(Arrays.stream(StatusType.values()).map(StatusType::name).toList());

    public static boolean hasStatusType(String name) {
        return VALUES.contains(name);
    }
}

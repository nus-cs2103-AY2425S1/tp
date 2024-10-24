package seedu.address.model.person;

import java.util.Comparator;

/**
 * Represents the Attendance Status of a tutorial. Mainly used for GUI generation and testing.
 */
public enum AttendanceStatus {
    PRESENT,
    ABSENT,
    NOT_TAKEN_PLACE;

    public static Comparator<AttendanceStatus> getComparator(boolean isAscendingOrder) {
        return (s1, s2) -> {
            if (s2 == NOT_TAKEN_PLACE) {
                return -1;
            }
            if (s1 == NOT_TAKEN_PLACE) {
                return 1;
            }
            return isAscendingOrder ? s1.compareTo(s2) : s2.compareTo(s1);
        };
    }
}

package seedu.address.model.person.role.committee;

import static java.util.Objects.requireNonNull;

/**
 * Represents the String form of a Position of a CommitteeMember shown to the user.
 */
public class PositionString {
    public static String getPositionString(Position position) {
        requireNonNull(position);
        return switch (position) {
        case PROJECT_DIRECTOR -> "PD";
        case VICE_PROJECT_DIRECTOR -> "VPD";
        case SPORTS_DIRECTOR -> "SD";
        case VICE_SPORTS_DIRECTOR -> "VSD";
        case MEMBER -> "";
        };
    }
}

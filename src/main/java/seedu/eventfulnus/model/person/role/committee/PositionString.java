package seedu.eventfulnus.model.person.role.committee;

import static java.util.Objects.requireNonNull;

/**
 * Represents the String form of a Position of a CommitteeMember shown to the user.
 */
public class PositionString {
    public static String getPositionString(Position position) {
        requireNonNull(position);
        return switch (position) {
        case PROJECT_DIRECTOR -> "Project Director";
        case VICE_PROJECT_DIRECTOR -> "Vice Project Director";
        case SPORTS_DIRECTOR -> "Sports Director";
        case VICE_SPORTS_DIRECTOR -> "Vice Sports Director";
        case MEMBER -> "Member";
        };
    }
}

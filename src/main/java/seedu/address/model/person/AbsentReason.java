package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the reason that the student is absent.
 * The reason should only contain alphanumeric characters and spaces.
 * The reason can be blank to delete attendance
 */
public class AbsentReason {

    public static final String MESSAGE_CONSTRAINTS =
            "Absent reason should only contain alphanumeric characters and spaces, "
                    + "and it can be blank to delete attendance";

    public static final String VALIDATION_REGEX = "[A-Za-z ]*";
    public final String absentReason;

    /**
     * Constructs an {@code AbsentReason}
     *
     * @param absentReason A reason why student is absent.
     */
    public AbsentReason(String absentReason) {
        requireNonNull(absentReason);
        checkArgument(isValidAbsentReason(absentReason), MESSAGE_CONSTRAINTS);
        this.absentReason = absentReason;
    }

    /**
     * Returns true if a given string is a valid reason.
     */
    public static boolean isValidAbsentReason(String reason) {
        return reason != null && (reason.matches(VALIDATION_REGEX) || reason.isEmpty());
    }
    @JsonValue
    @Override
    public String toString() {
        return absentReason;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AbsentReason)) {
            return false;
        }

        AbsentReason otherReason = (AbsentReason) other;
        return absentReason.equals(otherReason.absentReason);
    }

    @Override
    public int hashCode() {
        return absentReason.hashCode();
    }
}

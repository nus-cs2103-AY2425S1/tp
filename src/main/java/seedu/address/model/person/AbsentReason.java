package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the date where the student is absent.
 * The date should only be in the form of DD-MM-YYYY.
 */
public class AbsentReason {

    public static final String MESSAGE_CONSTRAINTS =
            "Absent reason should only contain alphanumeric characters and spaces, "
                    + "and it can be blank to delete attendance";

    public static final String VALIDATION_REGEX = "[A-Za-z ]*";
    public final String value;

    /**
     * Constructs an {@code AbsentDate}
     *
     * @param absentReason A reason why student is absent.
     */
    public AbsentReason(String absentReason) {
        requireNonNull(absentReason);
        checkArgument(isValidAbsentReason(absentReason), MESSAGE_CONSTRAINTS);
        value = absentReason;
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
        return value;
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
        return value.equals(otherReason.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

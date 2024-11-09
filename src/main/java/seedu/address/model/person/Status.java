package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Person's application status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Application status should be one of the following: "
            + "\n1. Applied\n2. Screening\n3. Interview Scheduled\n4. Interviewed\n5. Offer"
            + "\n6. Onboarding\n7. Hired\n8. Rejected";

    public static final List<String> VALID_STATUSES = Arrays.asList(
            "Applied", "Screening", "Interview Scheduled", "Interviewed",
            "Offer", "Onboarding", "Hired", "Rejected"
    );

    public final String value;

    /**
     * Constructs an {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        assert status != null : "Status should not be null";
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = status;
        assert isValidStatus(value) : "Status should match one of the valid options";
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidStatus(String test) {
        return !test.isEmpty() && VALID_STATUSES.contains(test);
    }

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
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

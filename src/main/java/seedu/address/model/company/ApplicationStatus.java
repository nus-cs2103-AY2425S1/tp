package seedu.address.model.company;

import static java.util.Objects.requireNonNull;

/**
 * Represents the application status of a company in the address book.
 * Guarantees: immutable; is valid as declared in
 */
public class ApplicationStatus {

    private static final String DEFAULT_STATUS = "None";

    public final String value;
    /**
     * Constructs an {@code ApplicationStatus}.
     *
     * @param status An application status.
     */
    public ApplicationStatus(String status) {
        requireNonNull(status);
        value = status.isEmpty() ? DEFAULT_STATUS : status;
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
        if (!(other instanceof ApplicationStatus)) {
            return false;
        }

        ApplicationStatus otherStatus = (ApplicationStatus) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

package seedu.address.model.job;

/**
 * Represents a Job's description in the address book.
 */
public class JobDescription {
    public final String value;

    /**
     * Constructs a {@code JobRequirements}.
     *
     * @param description A job requirements.
     */
    public JobDescription(String description) {
        value = description;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobDescription)) {
            return false;
        }

        JobDescription otherDescription = (JobDescription) other;
        return value.equals(otherDescription.value);
    }

    @Override
    public String toString() {
        return value;
    }

}

package seedu.address.model.job;

/**
 * Represents a Job's requirements in the address book.
 */
public class JobRequirements {

    public final String value;

    /**
     * Constructs a {@code JobRequirements}.
     *
     * @param requirements A list of job requirements.
     */
    public JobRequirements(String requirements) {
        value = requirements;
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
        if (!(other instanceof JobRequirements)) {
            return false;
        }

        JobRequirements otherRequirements = (JobRequirements) other;
        return value.equals(otherRequirements.value);
    }

    @Override
    public String toString() {
        return value;
    }

}

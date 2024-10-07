package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's company in the address book.
 */
public class JobCompany {
    public static final String MESSAGE_CONSTRAINTS = "The company provided does not exist";

    // TODO: Change type to Company during integration
    public final String value;

    /**
     * Constructs a {@code JobCompany}.
     *
     * @param companyName An existing company's name.
     */
    public JobCompany(String companyName) {
        requireNonNull(companyName);
        checkArgument(isValidCompany(companyName), MESSAGE_CONSTRAINTS);
        value = companyName;
    }

    /**
     * Returns true if a given company exists.
     */
    public static boolean isValidCompany(String test) {
        // TODO: check if the company exists
        return true;
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
        if (!(other instanceof JobCompany)) {
            return false;
        }

        JobCompany otherCompany = (JobCompany) other;
        return value.equals(otherCompany.value);
    }

    @Override
    public String toString() {
        return value;
    }
}

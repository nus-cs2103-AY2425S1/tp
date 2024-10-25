package seedu.address.model.job;

import seedu.address.model.common.Name;

/**
 * Represents a Job's company in the address book.
 * This extends from the Name class to avoid code duplication.
 */
public class JobCompany extends Name {

    /**
     * Constructs a {@code JobCompany}.
     *
     * @param companyName An existing company's name.
     */
    public JobCompany(String companyName) {
        super(companyName);
    }

    /**
     * Returns true if a given company exists.
     * Not used in constructor but used in ParserUtil to construct a JobCompany.
     */
    public static boolean isValidCompany(String test) {
        return Name.isValidName(test);
    }

    /**
     * Checks if this name is equal to the specified company's name.
     *
     * @param name Name of the company specified.
     * @return true if equal.
     */
    public boolean matchesCompanyName(Name name) {
        return this.fullName.equals(name.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
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
        return fullName.equals(otherCompany.fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }
}

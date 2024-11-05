package seedu.address.model.scheme;

/**
 * Represents a financial scheme in the address book.
 */
public abstract class Scheme {

    /**
     * Returns true if the person is eligible for the scheme.
     */
    public abstract boolean isEligible(double income, int familySize, int incomePerCapita);

    /**
     * Returns the name of the scheme.
     */
    public abstract String getSchemeName();
}

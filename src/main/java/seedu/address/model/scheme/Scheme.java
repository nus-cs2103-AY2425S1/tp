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


    /**
     * Returns the short name of the scheme.
     */
    public abstract String getSchemeNameShort();

    /**
     * Create schemes from storage.
     */
    public static Scheme createScheme(String schemeName) throws IllegalArgumentException {
        switch (schemeName) {
        case MoeFinancialAssistanceScheme.SCHEME_NAME:
            return new MoeFinancialAssistanceScheme();
        case StudentCareFeeAssistanceScheme.SCHEME_NAME:
            return new StudentCareFeeAssistanceScheme();
        default:
            throw new IllegalArgumentException("Scheme not found");
        }
    }

}

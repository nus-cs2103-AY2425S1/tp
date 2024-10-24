package seedu.address.model.scheme;

import static java.util.Objects.requireNonNull;

/**
 * Represents a SCFA scheme in the address book.
 */
public class StudentCareFeeAssistanceScheme extends Scheme {

    public static final String SCHEME_NAME = "Student Care Fee Assistance (SCFA)";
    private static final int INCOME_THRESHOLD = 4500;
    private static final int FAMILY_SIZE_THRESHOLD = 4;
    private static final int INCOME_PER_CAPITA_THRESHOLD = 1125;

    @Override
    public boolean isEligible(double income, int familySize, int incomePerCapita) {
        requireNonNull(income);
        requireNonNull(familySize);
        requireNonNull(incomePerCapita);

        return (income < INCOME_THRESHOLD && familySize <= FAMILY_SIZE_THRESHOLD)
                || (incomePerCapita < INCOME_PER_CAPITA_THRESHOLD && familySize > FAMILY_SIZE_THRESHOLD);
    }

    @Override
    public String getSchemeName() {
        return SCHEME_NAME;
    }

}

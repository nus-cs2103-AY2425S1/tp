package seedu.address.model.scheme;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a MOE FAS scheme in the address book.
 */
public class MoeFinancialAssistanceScheme extends Scheme {
    public static final String SCHEME_NAME = "MOE Financial Assistance Scheme (MOE FAS)";
    public static final int INCOME_THRESHOLD = 3000;
    public static final int FAMILY_SIZE_THRESHOLD = 0;
    public static final int INCOME_PER_CAPITA_THRESHOLD = 750;

    public static final String SCHEME_NAME_SHORT = "MOE FAS";

    @Override
    public boolean isEligible(double income, int familySize, int incomePerCapita) {
        requireAllNonNull(income, familySize, incomePerCapita);
        return (income <= INCOME_THRESHOLD) || (incomePerCapita <= INCOME_PER_CAPITA_THRESHOLD);
    }

    @Override
    public String getSchemeName() {
        return SCHEME_NAME;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        return other instanceof MoeFinancialAssistanceScheme; // instanceof handles nulls
    }

    @Override
    public String getSchemeNameShort() {
        return SCHEME_NAME_SHORT;
    }
}

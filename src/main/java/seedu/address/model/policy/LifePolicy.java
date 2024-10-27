package seedu.address.model.policy;

import java.time.LocalDate;
import java.time.Period;

import seedu.address.model.claim.ClaimList;

/**
 * A Policy of type Life.
 */
public class LifePolicy extends Policy {
    private static final PremiumAmount DEFAULT_PREMIUM_AMOUNT = new PremiumAmount(100.00);
    private static final CoverageAmount DEFAULT_COVERAGE_AMOUNT = new CoverageAmount(10000.00);
    private static final Period DEFAULT_EXPIRY_DATE_PERIOD = Period.ofYears(20);

    /**
     * Constructor for a new LifePolicy initialized with defaults.
     */
    public LifePolicy() {
        super(DEFAULT_PREMIUM_AMOUNT, DEFAULT_COVERAGE_AMOUNT,
                new ExpiryDate(LocalDate.now().plus(DEFAULT_EXPIRY_DATE_PERIOD)),
                new ClaimList());
    }

    /**
     * Constructor for a new LifePolicy with selected fields initialized.
     * Use null for fields to initialize them with the default values.
     *
     * @param premiumAmount the price of the policy, paid per month.
     *                      Use null to initialize this policy with the default premiumAmount.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     *                       Use null to initialize this policy with the default coverageAmount.
     * @param expiryDate the date of Policy's expiry.
     *                   Use null to initialize this policy with the default expiryDate.
     */
    public LifePolicy(PremiumAmount premiumAmount, CoverageAmount coverageAmount, ExpiryDate expiryDate,
                      ClaimList claims) {
        super(
                premiumAmount == null ? DEFAULT_PREMIUM_AMOUNT : premiumAmount,
                coverageAmount == null ? DEFAULT_COVERAGE_AMOUNT : coverageAmount,
                expiryDate == null ? new ExpiryDate(LocalDate.now().plus(DEFAULT_EXPIRY_DATE_PERIOD)) : expiryDate,
                claims);
    }

    @Override
    public PolicyType getType() {
        return PolicyType.LIFE;
    }

    @Override
    public String toString() {
        return String.format("Policy type: %s | ", getType()) + super.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LifePolicy)) {
            return false;
        }

        LifePolicy p = (LifePolicy) other;
        return super.equals(p);
    }
}

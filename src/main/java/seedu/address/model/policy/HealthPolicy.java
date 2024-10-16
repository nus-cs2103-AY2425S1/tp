package seedu.address.model.policy;

import java.time.LocalDate;
import java.time.Period;

import seedu.address.model.person.Person;

/**
 * A Policy of type Health.
 */
public class HealthPolicy extends Policy {
    private static final double DEFAULT_PREMIUM_AMOUNT = 200;
    private static final double DEFAULT_COVERAGE_AMOUNT = 20000;
    private static final Period DEFAULT_EXPIRY_DATE_PERIOD = Period.ofYears(10);

    /**
     * Constructor for a new HealthPolicy initialized with defaults.
     */
    public HealthPolicy() {
        super(DEFAULT_PREMIUM_AMOUNT, DEFAULT_COVERAGE_AMOUNT, LocalDate.now().plus(DEFAULT_EXPIRY_DATE_PERIOD));
    }

    /**
     * Constructor for a new HealthPolicy with all its fields initialized.
     *
     * @param premiumAmount the price of the policy, paid per month.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     * @param expiryDate the date of Policy's expiry.
     * @param insuree the policy insuree.
     * @throws IllegalArgumentException if the premiumAmount or coverageAmount is negative.
     * @throws NullPointerException if the given expiryDate or insuree is null.
     */
    public HealthPolicy(double premiumAmount, double coverageAmount, LocalDate expiryDate, Person insuree) {
        super(premiumAmount, coverageAmount, expiryDate, insuree);
    }

    public HealthPolicy(double premiumAmount, double coverageAmount) {
        super(premiumAmount, coverageAmount);
    }

    @Override
    public PolicyType getType() {
        return PolicyType.HEALTH;
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
        if (!(other instanceof HealthPolicy)) {
            return false;
        }

        HealthPolicy p = (HealthPolicy) other;
        return super.equals(p);
    }
}

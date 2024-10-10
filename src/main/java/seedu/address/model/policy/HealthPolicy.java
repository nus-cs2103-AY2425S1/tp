package seedu.address.model.policy;

/**
 * A Policy of type Health.
 */
public class HealthPolicy extends Policy {
    private static final double DEFAULT_PREMIUM_AMOUNT = 200;
    private static final double DEFAULT_COVERAGE_AMOUNT = 20000;

    /**
     * Constructor for a new HealthPolicy using the default premium amount and coverage amount.
     */
    public HealthPolicy() {
        super(DEFAULT_PREMIUM_AMOUNT, DEFAULT_COVERAGE_AMOUNT);
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

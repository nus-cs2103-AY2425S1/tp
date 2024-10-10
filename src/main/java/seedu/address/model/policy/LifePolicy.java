package seedu.address.model.policy;

/**
 * A Policy of type Life.
 */
public class LifePolicy extends Policy {
    private static final double DEFAULT_PREMIUM_AMOUNT = 100;
    private static final double DEFAULT_COVERAGE_AMOUNT = 10000;

    /**
     * Constructor for a new LifePolicy using the default premium amount and coverage amount.
     */
    public LifePolicy() {
        super(DEFAULT_PREMIUM_AMOUNT, DEFAULT_COVERAGE_AMOUNT);
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

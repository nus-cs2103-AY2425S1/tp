package seedu.address.model.policy;

/**
 * A Policy of type Education.
 */
public class EducationPolicy extends Policy {
    private static final double DEFAULT_PREMIUM_AMOUNT = 50;
    private static final double DEFAULT_COVERAGE_AMOUNT = 5000;

    /**
     * Constructor for a new EducationPolicy using the default premium amount and coverage amount.
     */
    public EducationPolicy() {
        super(DEFAULT_PREMIUM_AMOUNT, DEFAULT_COVERAGE_AMOUNT);
    }

    @Override
    public PolicyType getType() {
        return PolicyType.EDUCATION;
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
        if (!(other instanceof EducationPolicy)) {
            return false;
        }

        EducationPolicy p = (EducationPolicy) other;
        return super.equals(p);
    }
}

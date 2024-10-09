package seedu.address.model.policy;

import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * An abstract class to capture all type of policies.
 */
public abstract class Policy {
    public static final String MESSAGE_CONSTRAINTS = "Policy can only be " + getValidPolicyTypesAsString() + ".";

    private Person insuree;
    private double premiumAmount;
    private double coverageAmount;
    // private LocalDateTime expiryDate;

    /**
     * Constructor for a new Policy without a insuree specified.
     *
     * @param premiumAmount the price of the policy, paid per month.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     * @throws IllegalArgumentException if the premiumAmount or coverageAmount is negative.
     */
    public Policy(double premiumAmount, double coverageAmount) throws IllegalArgumentException {
        if (premiumAmount < 0 || coverageAmount < 0) {
            throw new IllegalArgumentException("Premium amount and coverage amount cannot be negative.");
        }
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
    }

    /**
     * Constructor for a new Policy with the insuree specified.
     *
     * @param premiumAmount the price of the policy, paid per month.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     * @param insuree the policy insuree.
     * @throws IllegalArgumentException if the premiumAmount or coverageAmount is negative.
     * @throws NullPointerException if the given insuree is null.
     */
    public Policy(double premiumAmount, double coverageAmount, Person insuree)
            throws IllegalArgumentException, NullPointerException {
        if (premiumAmount < 0 || coverageAmount < 0) {
            throw new IllegalArgumentException("Premium amount and coverage amount cannot be negative.");
        }
        if (insuree == null) {
            throw new NullPointerException("Policy's insuree cannot be null.");
        }
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.insuree = insuree;
    }

    /**
     * Return the policy's type.
     *
     * @return the policy's type.
     */
    public abstract PolicyType getType();

    /**
     * Return this policy's premium amount.
     *
     * @return this policy's premium amount.
     */
    public double getPremiumAmount() {
        return premiumAmount;
    }

    /**
     * Return this policy's coverage amount.
     *
     * @return this policy's coverage amount.
     */
    public double getCoverageAmount() {
        return coverageAmount;
    }

    /**
     * Return this policy's insuree.
     *
     * @return this policy's insuree.
     */
    public Person getInsuree() {
        return insuree;
    }

    /**
     * Change this policy's premium amount to the specified value, which cannot be negative.
     *
     * @param premiumAmount the new price of the policy, paid per month.
     * @throws IllegalArgumentException if the given premiumAmount is negative.
     */
    public void setPremiumAmount(double premiumAmount) throws IllegalArgumentException {
        if (premiumAmount < 0) {
            throw new IllegalArgumentException("Premium amount cannot be negative.");
        }
        this.premiumAmount = premiumAmount;
    }

    /**
     * Change this policy's coverage amount to the specified value, which cannot be negative.
     *
     * @param coverageAmount the new maximum amount that can be claimed under this policy.
     * @throws IllegalArgumentException if the given coverageAmount is negative.
     */
    public void setCoverageAmount(double coverageAmount) throws IllegalArgumentException {
        if (coverageAmount < 0) {
            throw new IllegalArgumentException("Coverage amount cannot be negative.");
        }
        this.coverageAmount = coverageAmount;
    }

    /**
     * Change this policy's insuree to the specified insuree, which cannot be null.
     *
     * @param insuree the new insuree of this policy.
     * @throws NullPointerException if the given insuree is null.
     */
    public void setInsuree(Person insuree) {
        if (insuree == null) {
            throw new NullPointerException("Policy's insuree cannot be null.");
        }
        this.insuree = insuree;
    }

    @Override
    public String toString() {
        return String.format("Premium amount: $%.2f | Coverage amount: $%.2f",
                premiumAmount, coverageAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Policy)) {
            return false;
        }

        Policy p = (Policy) other;
        boolean sameInsuree = insuree == null ? p.insuree == null : insuree.equals(p.insuree);
        return sameInsuree
                && premiumAmount == p.premiumAmount
                && coverageAmount == p.coverageAmount;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(insuree, premiumAmount, coverageAmount);
    }

    /**
     * Return a nicely formated String displaying the list of valid Policy types.
     *
     * @return a string of valid Policy types.
     */
    private static String getValidPolicyTypesAsString() {
        StringBuilder result = new StringBuilder();
        PolicyType[] validPolicyTypes = PolicyType.getValidPolicyTypes();
        for (int i = 0; i < validPolicyTypes.length - 1; i++) {
            result.append(validPolicyTypes[i] + ", ");
        }
        result.append("or " + validPolicyTypes[validPolicyTypes.length - 1]);
        return result.toString();
    }
}

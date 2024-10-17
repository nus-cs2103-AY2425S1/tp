package seedu.address.model.policy;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.dateformatter.DateFormatter.MM_DD_YYYY_FORMATTER;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * An abstract class to capture all type of policies.
 */
public abstract class Policy {
    public static final String MESSAGE_CONSTRAINTS = "Policy can only be " + getValidPolicyTypesAsString() + ".";

    private double premiumAmount;
    private double coverageAmount;
    private LocalDate expiryDate;

    /**
     * Constructor for a new Policy without an insuree specified.
     *
     * @param premiumAmount the price of the policy, paid per month.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     * @param expiryDate the date of Policy's expiry.
     * @throws IllegalArgumentException if the premiumAmount or coverageAmount is negative.
     * @throws NullPointerException if the expiryDate is null.
     */
    public Policy(double premiumAmount, double coverageAmount, LocalDate expiryDate) {
        requireAllNonNegative(premiumAmount, coverageAmount);
        requireNonNull(expiryDate);

        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.expiryDate = expiryDate;
    }

    /**
     * Throws IllegalArgumentException if amount is less than zero.
     *
     * @param amount to be tested.
     */
    private static void requireNonNegative(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    /**
     * Throws IllegalArgumentException if any amount in amounts is less than zero.
     *
     * @param amounts array to be tested.
     */
    private static void requireAllNonNegative(double... amounts) {
        Arrays.stream(amounts).forEach(Policy::requireNonNegative);
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
     * Return this policy's expiry date.
     *
     * @return this policy's expiry date.
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Change this policy's premium amount to the specified value, which cannot be negative.
     *
     * @param premiumAmount the new price of the policy, paid per month.
     * @throws IllegalArgumentException if the given premiumAmount is negative.
     */
    public void setPremiumAmount(double premiumAmount) throws IllegalArgumentException {
        requireNonNegative(premiumAmount);
        this.premiumAmount = premiumAmount;
    }

    /**
     * Change this policy's coverage amount to the specified value, which cannot be negative.
     *
     * @param coverageAmount the new maximum amount that can be claimed under this policy.
     * @throws IllegalArgumentException if the given coverageAmount is negative.
     */
    public void setCoverageAmount(double coverageAmount) throws IllegalArgumentException {
        requireNonNegative(coverageAmount);
        this.coverageAmount = coverageAmount;
    }

    /**
     * Change this policy's expiry date to the specified expiryDate, which cannot be null.
     *
     * @param expiryDate the new expiry date of this policy.
     * @throws NullPointerException if the given expiryDate is null.
     */
    public void setExpiryDate(LocalDate expiryDate) {
        requireNonNull(expiryDate);
        this.expiryDate = expiryDate;
    }

    /**
     * Return whether this Policy is expired by comparing it with the date today.
     *
     * @return true if this Policy is expired.
     */
    public boolean isExpired() {
        return expiryDate.compareTo(LocalDate.now()) <= 0;
    }

    @Override
    public String toString() {
        return String.format("Premium amount: $%.2f | Coverage amount: $%.2f | Expiry date: %s",
                premiumAmount, coverageAmount, expiryDate.format(MM_DD_YYYY_FORMATTER));
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
        return premiumAmount == p.premiumAmount
                && coverageAmount == p.coverageAmount
                && expiryDate.equals(p.expiryDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(premiumAmount, coverageAmount, expiryDate);
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

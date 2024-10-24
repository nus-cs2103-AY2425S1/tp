package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimSet;


/**
 * An abstract class to capture all type of policies.
 */
public abstract class Policy {
    private PremiumAmount premiumAmount;
    private CoverageAmount coverageAmount;
    private ExpiryDate expiryDate;
    private ClaimSet claimSet;


    /**
     * Constructor for a new Policy without an insuree specified.
     *
     * @param premiumAmount the price of the policy, paid per month.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     * @param expiryDate the date of Policy's expiry.
     * @throws NullPointerException if any of the fields is null.
     */
    public Policy(PremiumAmount premiumAmount, CoverageAmount coverageAmount, ExpiryDate expiryDate) {
        requireAllNonNull(premiumAmount, coverageAmount, expiryDate);
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.expiryDate = expiryDate;
        this.claimSet = new ClaimSet();
    }

    /**
     * Return a suitable Policy based on the PolicyType passed.
     */
    public static Policy makePolicy(PolicyType policyType, PremiumAmount premiumAmount, CoverageAmount coverageAmount,
            ExpiryDate expiryDate) {
        requireNonNull(policyType);
        switch (policyType) {
        case LIFE:
            return new LifePolicy(premiumAmount, coverageAmount, expiryDate);
        case HEALTH:
            return new HealthPolicy(premiumAmount, coverageAmount, expiryDate);
        case EDUCATION:
            return new EducationPolicy(premiumAmount, coverageAmount, expiryDate);
        default:
            throw new RuntimeException("Policy type " + policyType + " is not accounted for.");
        }
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
    public PremiumAmount getPremiumAmount() {
        return premiumAmount;
    }

    /**
     * Return this policy's coverage amount.
     *
     * @return this policy's coverage amount.
     */
    public CoverageAmount getCoverageAmount() {
        return coverageAmount;
    }

    /**
     * Return this policy's expiry date.
     *
     * @return this policy's expiry date.
     */
    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Get the set of claims associated with this policy.
     *
     * @return The {@code ClaimSet} associated with this policy.
     */
    public ClaimSet getClaimSet() {
        return claimSet;
    }

    /**
     * Change this policy's premium amount to the specified {@code premiumAmount}, which cannot be null.
     *
     * @param premiumAmount the new price of the policy, paid per month.
     * @throws NullPointerException if the given {@code premiumAmount} is null.
     */
    public void setPremiumAmount(PremiumAmount premiumAmount) {
        requireNonNull(premiumAmount);
        this.premiumAmount = premiumAmount;
    }

    /**
     * Change this policy's coverage amount to the specified {@code coverageAmount}, which cannot be null.
     *
     * @param coverageAmount the new maximum amount that can be claimed under this policy.
     * @throws NullPointerException if the given {@code coverageAmount} is null.
     */
    public void setCoverageAmount(CoverageAmount coverageAmount) {
        requireNonNull(coverageAmount);
        this.coverageAmount = coverageAmount;
    }

    /**
     * Change this policy's expiry date to the specified {@code expiryDate}, which cannot be null.
     *
     * @param expiryDate the new expiry date of this policy.
     * @throws NullPointerException if the given {@code expiryDate} is null.
     */
    public void setExpiryDate(ExpiryDate expiryDate) {
        requireNonNull(expiryDate);
        this.expiryDate = expiryDate;
    }

    /**
     * Add a claim to this policy's claim set.
     *
     * @param claim The claim to add.
     */
    public void addClaim(Claim claim) {
        claimSet.add(claim);
    }

    /**
     * Remove a claim from this policy's claim set.
     *
     * @param claim The claim to remove.
     */
    public void removeClaim(Claim claim) {
        claimSet.remove(claim);
    }

    @Override
    public String toString() {
        return String.format("Premium amount: $%s | Coverage amount: $%s | Expiry date: %s | Claims: %s",
                premiumAmount, coverageAmount, expiryDate, claimSet);
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
        return premiumAmount.equals(p.premiumAmount)
                && coverageAmount.equals(p.coverageAmount)
                && expiryDate.equals(p.expiryDate)
                && claimSet.equals(p.claimSet);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(premiumAmount, coverageAmount, expiryDate, claimSet);
    }
}

package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.policy.exceptions.DuplicateClaimException;


/**
 * An abstract class to capture all type of policies.
 * Guarantees: immutability
 */
public abstract class Policy {
    private final PremiumAmount premiumAmount;
    private final CoverageAmount coverageAmount;
    private final ExpiryDate expiryDate;
    private final ClaimList claimList;


    /**
     * Constructor for a new Policy.
     *
     * @param premiumAmount the price of the policy, paid per month.
     * @param coverageAmount the maximum amount that can be claimed under this policy.
     * @param expiryDate the date of policy's expiry.
     * @param claims the list of claims filed under this policy.
     * @throws NullPointerException if any of the fields are null.
     */
    public Policy(PremiumAmount premiumAmount, CoverageAmount coverageAmount, ExpiryDate expiryDate,
                  ClaimList claims) {
        requireAllNonNull(premiumAmount, coverageAmount, expiryDate, claims);
        this.premiumAmount = premiumAmount;
        this.coverageAmount = coverageAmount;
        this.expiryDate = expiryDate;
        this.claimList = claims;
    }

    /**
     * Returns a suitable Policy based on the PolicyType passed.
     */
    public static Policy makePolicy(PolicyType policyType, PremiumAmount premiumAmount, CoverageAmount coverageAmount,
                                    ExpiryDate expiryDate, ClaimList claims) {
        requireNonNull(policyType);
        switch (policyType) {
        case LIFE:
            return new LifePolicy(premiumAmount, coverageAmount, expiryDate, claims);
        case HEALTH:
            return new HealthPolicy(premiumAmount, coverageAmount, expiryDate, claims);
        case EDUCATION:
            return new EducationPolicy(premiumAmount, coverageAmount, expiryDate, claims);
        default:
            throw new RuntimeException("Policy type " + policyType + " is not accounted for.");
        }
    }

    /**
     * Returns the policy's type.
     *
     * @return the policy's type.
     */
    public abstract PolicyType getType();

    /**
     * Returns this policy's premium amount.
     *
     * @return this policy's premium amount.
     */
    public PremiumAmount getPremiumAmount() {
        return premiumAmount;
    }

    /**
     * Returns this policy's coverage amount.
     *
     * @return this policy's coverage amount.
     */
    public CoverageAmount getCoverageAmount() {
        return coverageAmount;
    }

    /**
     * Returns this policy's expiry date.
     *
     * @return this policy's expiry date.
     */
    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns this policy's claim list as an unmodifiable list.
     *
     * @return this policy's claim list as an unmodifiable list.
     */
    public List<Claim> getClaimList() {
        return Collections.unmodifiableList(claimList);
    }

    /**
     * Adds a claim to this policy's claim list.
     * Preserves immutability by returning a new Policy, leaving the current one untouched.
     *
     * @param claim the claim to add.
     * @return the new Policy with the claim successfully added.
     * @throws NullPointerException when claim is null.
     * @throws DuplicateClaimException when claim cannot be added because of duplicates.
     */
    public Policy addClaim(Claim claim) {
        requireNonNull(claim);
        ClaimList newClaimList = new ClaimList();
        newClaimList.addAll(getClaimList());
        if (!newClaimList.add(claim)) {
            throw new DuplicateClaimException();
        }
        return makePolicy(getType(), premiumAmount, coverageAmount, expiryDate, newClaimList);
    }

    /**
     * Removes a claim from this policy's claim list at the specified index.
     * Preserves immutability by return a new Policy, leaving the current one untouched.
     *
     * @param index the index of the claim to be removed.
     * @return the new Policy with the claim successfully removed.
     * @throws IndexOutOfBoundsException when the index is out of bounds of the claim list.
     */
    public Policy removeClaim(int index) {
        ClaimList newClaimList = new ClaimList();
        newClaimList.addAll(getClaimList());
        newClaimList.remove(index);
        return makePolicy(getType(), premiumAmount, coverageAmount, expiryDate, newClaimList);
    }

    @Override
    public String toString() {
        StringBuilder claimsString = new StringBuilder();
        if (claimList.isEmpty()) {
            claimsString.append("No claims");
        } else {
            claimsString.append(String.format("Claims:\n%s", claimList));
        }
        return String.format("Premium amount: $%s | Coverage amount: $%s | Expiry date: %s | %s",
                premiumAmount, coverageAmount, expiryDate, claimsString);
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
                && claimList.equals(p.claimList);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(premiumAmount, coverageAmount, expiryDate, claimList);
    }

}

package seedu.address.model.client.insurance;

import java.util.ArrayList;

import seedu.address.model.client.insurance.claim.Claim;

/**
 * The {@code InsurancePlan} abstract class represents a general blueprint for an insurance plan.
 * It defines common behaviors that can be shared across all types of insurance plans.
 * This class is meant to be extended by specific insurance plan implementations.
 */
public abstract class InsurancePlan {

    /**
     * Unique identifier for the insurance plan, to be determined in each class separately.
     */
    protected int insurancePlanId = -1;

    protected ArrayList<Claim> claims;

    InsurancePlan() {
        claims = new ArrayList<Claim>();
    }

    /**
     * Retrieves the insurance plan's unique identifier.
     *
     * @return the insurance plan's ID
     */
    public int getInsurancePlanId() {
        return insurancePlanId;
    }

    /**
     * Removes a claim from the insurance plan's list of claims, if a claim with the same claim id exists.
     *
     * @param claim The claim to be removed.
     */
    public void removeClaim(Claim claim) {
        for (Claim c : claims) {
            if (c.getClaimId().equals(claim.getClaimId())) {
                claims.remove(c);
                break;
            }
        }
    }

    /**
     * Returns a string representation of the insurance plan.
     *
     * @return a string that represents the insurance plan
     */
    @Override
    public String toString() {
        return "Insurance Plan";
    }

    /**
     * Compares this insurance plan to another object for equality.
     * Two insurance plans are considered equal if they are the same object
     * or if they are both instances of {@code InsurancePlan} and share the same insurance plan ID.
     *
     * @param other the object to be compared for equality
     * @return {@code true} if the specified object is equal to this insurance plan, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsurancePlan) // instanceof handles nulls
                && insurancePlanId == ((InsurancePlan) other).getInsurancePlanId(); // check plan id
    }
}

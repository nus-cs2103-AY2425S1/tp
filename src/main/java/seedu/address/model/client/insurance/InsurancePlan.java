package seedu.address.model.client.insurance;

import java.util.ArrayList;

import seedu.address.model.client.exceptions.ClaimException;
import seedu.address.model.client.insurance.claim.Claim;
import seedu.address.model.client.insurance.claim.ClaimComparator;

/**
 * The {@code InsurancePlan} abstract class represents a general blueprint for an insurance plan.
 * It defines common behaviors that can be shared across all types of insurance plans.
 * This class is meant to be extended by specific insurance plan implementations.
 */
public abstract class InsurancePlan {
    public static final String CLAIM_NOT_DETECTED_MESSAGE = "This claim with id: %1$s "
            + "has not been added to this insurance plan of the client: %2$s";

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
     * Retrieves a claim from the list of claims based on the given claim ID.
     *
     * @param claimId The ID of the claim to retrieve.
     * @return The {@code Claim} object that matches the given claim ID.
     * @throws ClaimException if no claim with the given ID is found within the insurance plan.
     */
    public Claim getClaim(String claimId) throws ClaimException {
        for (Claim c : claims) {
            if (c.getClaimId().equals(claimId)) {
                return c;
            }
        }
        throw new ClaimException(CLAIM_NOT_DETECTED_MESSAGE);
    }

    /**
     * Adds a claim to the insurance plan's list of claims.
     *
     * @param claim The claim to be added.
     */
    public void addClaim(Claim claim) {
        claims.add(claim);
        sortClaims();
    }

    /**
     * Removes a claim from the insurance plan's list of claims.
     *
     * @param claim The claim to be removed.
     */
    public void removeClaim(Claim claim) {
        claims.remove(claim);
        sortClaims();
    }

    /**
     * Sorts the claims of this insurance plan only based on claim status then by claim ID.
     */
    public void sortClaims() {
        ClaimComparator claimComparator = new ClaimComparator();
        this.claims.sort(claimComparator);
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
        if (other == this) {
            return true;
        } else if (other instanceof InsurancePlan) {
            InsurancePlan otherInsurancePlan = (InsurancePlan) other;
            if (otherInsurancePlan.insurancePlanId != this.insurancePlanId) {
                return false;
            }
            if (!otherInsurancePlan.claims.equals(this.claims)) {
                return false;
            }
            return true;
        }
        return false;
    }
}

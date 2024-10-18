package seedu.address.model.claim;

import seedu.address.model.policy.PolicyType;

/**
 * Enum representing the possible statuses of a claim.
 */
public enum ClaimStatus {
    PENDING,
    APPROVED,
    REJECTED;

    /**
     * Returns the string representation of the claim status.
     *
     * @return A capitalized string representing the status.
     */
    @Override
    public String toString() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
    public static ClaimStatus fromString(String type) {
        for (ClaimStatus claimStatus : ClaimStatus.values()) {
            if (claimStatus.name().equalsIgnoreCase(type)) {
                return claimStatus;
            }
        }
        throw new IllegalArgumentException("Invalid policy type: " + type);
    }
}

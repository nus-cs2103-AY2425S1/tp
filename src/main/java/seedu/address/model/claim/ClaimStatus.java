package seedu.address.model.claim;

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
        // Capitalize the first letter and make the rest lowercase
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}

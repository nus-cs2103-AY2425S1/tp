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
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
    /**
     * Returns the {@code ClaimStatus} corresponding to the provided string.
     * This method compares the provided string with the names of all enum constants
     * in a case-insensitive manner. If a match is found, the corresponding {@code ClaimStatus}
     * is returned.
     * @param type The string representation of the claim status.
     * @return The {@code ClaimStatus} that matches the provided string.
     * @throws IllegalArgumentException if the provided string does not match any valid {@code ClaimStatus}.
     */
    public static ClaimStatus fromString(String type) {
        for (ClaimStatus claimStatus : ClaimStatus.values()) {
            if (claimStatus.name().equalsIgnoreCase(type)) {
                return claimStatus;
            }
        }
        throw new IllegalArgumentException("Invalid policy type: " + type);
    }
}

package seedu.address.model.claim;

/**
 * Enum representing the possible statuses of a claim.
 */
public enum ClaimStatus {
    PENDING,
    APPROVED,
    REJECTED;
    public static final String MESSAGE_CONSTRAINTS = "Claim status can only be "
            + getValidClaimStatusesAsString() + ".";

    /**
     * Returns true if the provided string matches any valid {@code ClaimStatus}.
     *
     * @param status The string to check.
     * @return True if the string corresponds to a valid claim status, false otherwise.
     */
    public static boolean isValidClaimStatus(String status) {
        if (status == null) {
            return false;
        }
        for (ClaimStatus claimStatus : ClaimStatus.values()) {
            if (claimStatus.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

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
    /**
     * Returns a string listing all valid claim statuses in a readable format,
     * e.g., "Pending, Approved, or Rejected."
     *
     * @return A comma-separated string of valid claim statuses.
     */
    public static String getValidClaimStatusesAsString() {
        StringBuilder result = new StringBuilder();
        ClaimStatus[] statuses = ClaimStatus.values();

        for (int i = 0; i < statuses.length; i++) {
            result.append(statuses[i].toString());
            if (i < statuses.length - 2) {
                result.append(", ");
            } else if (i == statuses.length - 2) {
                result.append(", or ");
            }
        }

        return result.toString();
    }
}

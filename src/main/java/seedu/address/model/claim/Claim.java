package seedu.address.model.claim;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Claim in the system.
 * A claim has a description and status.
 * Guarantees: immutable
 */
public class Claim {
    public static final String CLAIM_STATUS_MESSAGE_CONSTRAINTS = "Claim status can only be "
            + getValidClaimStatusesAsString() + ".";
    /**
     * Error message if the claim description does not meet validation requirements.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "Claims should only contain alphanumeric characters, spaces, and it should not be blank";

    /**
     * The regular expression used to validate a claim description.
     * The claim description must start with an alphanumeric character
     * and can contain spaces or other alphanumeric characters.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String description;
    private final ClaimStatus status;

    /**
     * Constructs a {@code Claim} with the given status and description.
     *
     * @param status The status of the claim.
     * @param description The description of the claim.
     * @throws NullPointerException If either {@code status} or {@code description} is null.
     * @throws IllegalArgumentException If the description does not match the {@code VALIDATION_REGEX}.
     */
    public Claim(ClaimStatus status, String description) {
        requireNonNull(status);
        requireNonNull(description);
        checkArgument(isValidClaim(description), MESSAGE_CONSTRAINTS);
        this.status = status;
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid claim description.
     *
     * @param test The string to test for validity.
     * @return True if the string matches the {@code VALIDATION_REGEX}, false otherwise.
     */
    public static boolean isValidClaim(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the current status of the claim.
     *
     * @return The status of the claim.
     */
    public ClaimStatus getStatus() {
        return this.status;
    }

    /**
     * Returns the current description of the claim.
     *
     * @return The description of the claim.
     */
    public String getClaimDescription() {
        return description;
    }

    /**
     * Returns the string representation of the claim, which is the claim description.
     *
     * @return The claim status and description as a string.
     */
    @Override
    public String toString() {
        return "Status: " + status.toString() + ", Description: " + this.description;
    }

    /**
     * Compares this claim with another object for equality.
     * Returns true if both claims have the same description and status.
     *
     * @param other The object to compare with.
     * @return True if both claims have the same description and status, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Claim)) {
            return false;
        }
        Claim otherClaim = (Claim) other;
        return status.equals(otherClaim.status) && description.equals(otherClaim.description);
    }

    /**
     * Returns the hash code of the claim, based on its description.
     *
     * @return The hash code for the claim description.
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, description);
    }

    private static String getValidClaimStatusesAsString() {
        StringBuilder result = new StringBuilder();
        ClaimStatus[] validClaimStatuses = ClaimStatus.values();
        for (int i = 0; i < validClaimStatuses.length - 1; i++) {
            result.append(validClaimStatuses[i] + ", ");
        }
        result.append("or " + validClaimStatuses[validClaimStatuses.length - 1]);

        return result.toString();
    }
}

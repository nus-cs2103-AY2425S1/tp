package seedu.address.model.client.insurance.claim;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * An object that represents a claim that the client has made under an insurance plan.
 */
public class Claim {
    public static final String INVALID_CLAIM_ID = "Invalid Claim ID, must follow: "
            + "Letter + 4 digits only.";

    public static final String NEGATIVE_CLAIM_AMOUNT = "Claim Amount cannot be negative.";
    public static final String INVALID_CLAIM_AMOUNT = "Claim Amount must be in dollars and cents eg. 151.00";
    public static final String MESSAGE_INVALID_CENTS = "The Claim Amount must contain at least and "
            + "only equal to 2 digits of cents eg. 151.00";
    public static final String MESSAGE_TOO_MANY_DECIMALS = "Too many decimal points in claim amount!";

    private final String claimId;
    private boolean isOpen;
    private final int claimAmount;

    /**
     * Creates a new open Claim based on claim id and amount. This constructor is used when user creates a new claim.
     * The isOpen attribute will take a default value of {@code true} using this constructor.
     *
     * @param claimId     claim id obtained through official channels.
     * @param claimAmount claim amount recorded in cents, can only be a positive integer.
     */
    public Claim(String claimId, int claimAmount) {
        this.claimId = claimId;
        this.isOpen = true;
        this.claimAmount = claimAmount;
    }

    /**
     * Constructs a claim object based on the input parameters. This constructor is used, usually during loading of
     * claims.
     *
     * @param claimId     claim id obtained through official channels.
     * @param isOpen      current status of the claim, only takes a boolean value.
     * @param claimAmount claim amount recorded in cents, can only be a positive integer.
     */
    public Claim(String claimId, boolean isOpen, int claimAmount) {
        this.claimId = claimId;
        this.isOpen = isOpen;
        this.claimAmount = claimAmount;
    }

    /**
     * Checks if the claim id is valid based on preset notations.
     * Regular expression pattern:
     * ^ - start of the string
     * [A-Za-z] - any uppercase or lowercase letter
     * \d{4} - exactly 4 digits
     * $ - end of the string
     * This method was created with the aid of ChatGPT.
     *
     * @param claimId the claimId string to be checked for validity.
     * @throws ParseException if the claimId is invalid.
     */
    public static void checkValidClaimId(String claimId) throws ParseException {
        String pattern = "^[A-Za-z]\\d{4}$";
        if (!claimId.matches(pattern)) {
            throw new ParseException(INVALID_CLAIM_ID);
        }
    }

    /**
     * Checks if the value of the claim amount is non-negative.
     */
    public static void checkValidClaimAmount(int claimAmount) throws ParseException {
        if (claimAmount < 0) {
            throw new ParseException(Claim.NEGATIVE_CLAIM_AMOUNT);
        }
    }

    /**
     * Returns the {@code claimId} of this claim object.
     */
    public String getClaimId() {
        return this.claimId;
    }

    /**
     * Returns the {@code claimStatus} of this claim object.
     */
    public boolean getClaimStatus() {
        return this.isOpen;
    }

    /**
     * Returns the {@code claimAmount} of this claim object.
     */
    public int getClaimAmount() {
        return this.claimAmount;
    }

    /**
     * Marks the claim as "closed" by setting this.isOpen to false.
     */
    public void close() {
        this.isOpen = false;
    }

    /**
     * Compares this claim to another object for equality.
     * Two claims are considered equal if they are the same object
     * or if they are both instances of {@code Claim} and share the same claim ID, claim status and claim amount.
     *
     * @param other the object to be compared for equality
     * @return {@code true} if the specified object is equal to this claim, {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Claim c)) {
            return false;
        }

        return claimId.equals(c.getClaimId())
                && isOpen == c.getClaimStatus()
                && claimAmount == c.getClaimAmount();
    }

    /**
     * Returns a string representation of the claim object.
     *
     * This method overrides the default `toString` method to provide a formatted string
     * that indicates the status of the claim (open or closed), along with the claim ID
     * and the claim amount. The returned string format is as follows:
     * - If the claim is open: "[open] {@code claimId} {@code claimAmount}"
     * - If the claim is closed: "[closed] {@code claimId} {@code claimAmount}"
     *
     * @return A string representation of the claim, including its status, ID, and amount.
     */
    @Override
    public String toString() {
        String status = "open";
        if (!this.isOpen) {
            status = "closed";
        }
        return "[" + status + "] " + this.claimId + " " + Messages.formatClaimAmount(this.claimAmount); }
}

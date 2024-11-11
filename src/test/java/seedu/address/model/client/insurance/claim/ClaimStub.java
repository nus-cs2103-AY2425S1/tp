package seedu.address.model.client.insurance.claim;

/**
 * Stub class for testing purposes only.
 */
public class ClaimStub extends Claim {
    public static final String FAKE_CLAIM_ID = "A1000";
    public static final int FAKE_CLAIM_AMOUNT = 1;

    /**
     * Creates a stub instance with a set claimId and Claim amount of $1.
     */
    public ClaimStub() {
        super(FAKE_CLAIM_ID, FAKE_CLAIM_AMOUNT);
    }

    @Override
    public String getClaimId() {
        return FAKE_CLAIM_ID;
    }

    @Override
    public int getClaimAmount() {
        return FAKE_CLAIM_AMOUNT;
    }
}

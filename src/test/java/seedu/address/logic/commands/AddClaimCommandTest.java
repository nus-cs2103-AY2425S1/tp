package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.policy.PolicyType;

public class AddClaimCommandTest {

    private final Claim validClaim = new Claim(ClaimStatus.PENDING, "Surgery claim");
    private final PolicyType validPolicyType = PolicyType.HEALTH;

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(null, validClaim, validPolicyType));
    }

    @Test
    public void constructor_nullClaim_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(INDEX_FIRST_PERSON, null, validPolicyType));
    }

    @Test
    public void constructor_nullPolicyType_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(INDEX_FIRST_PERSON, validClaim, null));
    }


    @Test
    public void equals() {
        Claim claim1 = new Claim(ClaimStatus.PENDING, "Surgery claim");
        Claim claim2 = new Claim(ClaimStatus.APPROVED, "Dental claim");
        PolicyType healthPolicy = PolicyType.HEALTH;
        PolicyType lifePolicy = PolicyType.LIFE;

        AddClaimCommand addClaimFirstCommand = new AddClaimCommand(INDEX_FIRST_PERSON, claim1, healthPolicy);
        AddClaimCommand addClaimSecondCommand = new AddClaimCommand(INDEX_FIRST_PERSON, claim2, lifePolicy);

        // same object -> returns true
        Assertions.assertEquals(addClaimFirstCommand, addClaimFirstCommand);

        // same values -> returns true
        AddClaimCommand addClaimFirstCommandCopy = new AddClaimCommand(INDEX_FIRST_PERSON, claim1, healthPolicy);
        Assertions.assertEquals(addClaimFirstCommand, addClaimFirstCommandCopy);

        // different types -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(5));

        // null -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(null));

        // different claim -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(addClaimSecondCommand));
    }
}

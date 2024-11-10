package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.Policy;

public class ClaimTest {

    private final ClaimStatus validStatus = ClaimStatus.APPROVED;
    private final String validDescription = "Valid Claim Description";
    private final String invalidDescription = "Invalid$%ClaimDescription";
    private final Claim claim = new Claim(validStatus, validDescription);

    @Test
    public void constructor_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Claim(null, validDescription));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Claim(validStatus, null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Claim(validStatus, invalidDescription));
    }

    @Test
    public void isValidClaim_validDescription_returnsTrue() {
        assertTrue(Claim.isValidClaim(validDescription));
    }

    @Test
    public void isValidClaim_invalidDescription_returnsFalse() {
        assertFalse(Claim.isValidClaim(invalidDescription));
    }

    @Test
    public void equals_sameClaim_returnsTrue() {
        Claim otherClaim = new Claim(validStatus, validDescription);
        assertTrue(claim.equals(otherClaim));
    }

    @Test
    public void equals_differentClaim_returnsFalse() {
        Claim differentClaim = new Claim(ClaimStatus.APPROVED, "Different Description");
        assertFalse(claim.equals(differentClaim));
    }

    @Test
    public void equals_differentInstance_returnsFalse() {
        Policy policy = new LifePolicy();
        assertFalse(claim.equals(policy));
    }
    @Test
    public void equals_sameClaimObj_returnsTrue() {
        assertTrue(claim.equals(claim));
    }

    @Test
    public void hashCode_sameClaim_sameHashCode() {
        Claim otherClaim = new Claim(validStatus, validDescription);
        assertEquals(claim.hashCode(), otherClaim.hashCode());
    }

    @Test
    public void toString_correctFormat_success() {
        String expected = "Status: " + validStatus + ", Description: " + validDescription;
        assertEquals(expected, claim.toString());
    }
}

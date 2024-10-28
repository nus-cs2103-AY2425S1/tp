package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClaimStatusTest {

    @Test
    public void toString_correctFormatting_success() {
        assertEquals("Pending", ClaimStatus.PENDING.toString());
        assertEquals("Approved", ClaimStatus.APPROVED.toString());
        assertEquals("Rejected", ClaimStatus.REJECTED.toString());
    }

    @Test
    public void fromString_validStatusString_success() {
        assertEquals(ClaimStatus.PENDING, ClaimStatus.fromString("PENDING"));
        assertEquals(ClaimStatus.PENDING, ClaimStatus.fromString("pending"));
        assertEquals(ClaimStatus.PENDING, ClaimStatus.fromString("PeNdInG"));

        assertEquals(ClaimStatus.APPROVED, ClaimStatus.fromString("APPROVED"));
        assertEquals(ClaimStatus.APPROVED, ClaimStatus.fromString("approved"));
        assertEquals(ClaimStatus.APPROVED, ClaimStatus.fromString("ApPrOvEd"));

        assertEquals(ClaimStatus.REJECTED, ClaimStatus.fromString("REJECTED"));
        assertEquals(ClaimStatus.REJECTED, ClaimStatus.fromString("rejected"));
        assertEquals(ClaimStatus.REJECTED, ClaimStatus.fromString("ReJeCtEd"));
    }

    @Test
    public void fromString_invalidStatusString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ClaimStatus.fromString("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> ClaimStatus.fromString("approvedd"));
        assertThrows(IllegalArgumentException.class, () -> ClaimStatus.fromString("appro ved"));
    }
    @Test
    void testIsValidClaimStatus_withValidStatus() {
        assertTrue(ClaimStatus.isValidClaimStatus("REJECTED"));
        assertTrue(ClaimStatus.isValidClaimStatus("APPROVED"));
        assertTrue(ClaimStatus.isValidClaimStatus("PENDING"));
    }
    @Test
    void testIsValidClaimStatus_withInvalidStatus() {
        // Test invalid statuses
        assertFalse(ClaimStatus.isValidClaimStatus("INVALID"));
        assertFalse(ClaimStatus.isValidClaimStatus("unknown"));
        assertFalse(ClaimStatus.isValidClaimStatus("123"));
    }
    @Test
    void testIsValidClaimStatus_withNullStatus() {
        // Test null input
        assertFalse(ClaimStatus.isValidClaimStatus(null));
    }
}

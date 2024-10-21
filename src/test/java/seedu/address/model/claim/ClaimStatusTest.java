package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}

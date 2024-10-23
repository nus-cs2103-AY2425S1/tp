package seedu.address.model.client.insurance.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Unit tests for the {@code Claim} class.
 */
public class ClaimTest {

    @Test
    void checkValidClaimId_validClaim_success() {
        try {
            Claim.checkValidClaimId("A1234");
        } catch (ParseException e) {
            fail("Valid claim ID should not throw an exception.");
        }
    }

    @Test
    void checkValidClaimId_invalidClaim_failure() {
        try {
            Claim.checkValidClaimId("12345"); // Invalid because it doesn't start with a letter
            fail("Expected ParseException not thrown.");
        } catch (ParseException e) {
            assertEquals(Claim.INVALID_CLAIM_ID, e.getMessage(),
                    "Exception message should match the invalid claim ID message.");
        }
    }

    @Test
    void checkValidClaimAmount_positiveAmount_success() {
        try {
            Claim.checkValidClaimAmount(1000); // Positive claim amount
        } catch (ParseException e) {
            fail("Valid claim amount should not throw an exception.");
        }
    }

    @Test
    void checkValidClaimAmount_negativeAmount_failure() {
        try {
            Claim.checkValidClaimAmount(-500); // Invalid negative amount
            fail("Expected ParseException not thrown.");
        } catch (ParseException e) {
            assertEquals(Claim.NEGATIVE_CLAIM_AMOUNT, e.getMessage(),
                    "Exception message should match the negative claim amount message.");
        }
    }

    @Test
    void claimConstructor_validInput_success() {
        try {
            Claim claim = new Claim("B1234", 5000);
            assertEquals("B1234", claim.getClaimId(), "Claim ID should be 'B1234'");
            assertEquals(5000, claim.getClaimAmount(), "Claim amount should be 5000 cents");
            assertTrue(claim.getClaimStatus(), "Claim should be open by default");
        } catch (Exception e) {
            fail("Valid input to Claim constructor should not throw an exception.");
        }
    }
}

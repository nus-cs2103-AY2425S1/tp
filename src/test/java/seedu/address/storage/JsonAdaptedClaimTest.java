package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;

import static org.junit.jupiter.api.Assertions.*;

class JsonAdaptedClaimTest {

    private static final String VALID_STATUS = "PENDING";
    private static final String VALID_DESCRIPTION = "Claim for medical expenses";

    private static final String INVALID_STATUS = "INVALID";
    private static final String INVALID_DESCRIPTION = "";

    @Test
    void toModelType_validClaimDetails_returnsClaim() throws Exception {
        JsonAdaptedClaim adaptedClaim = new JsonAdaptedClaim(VALID_STATUS, VALID_DESCRIPTION);
        Claim claim = adaptedClaim.toModelType();

        assertEquals(ClaimStatus.PENDING, claim.getStatus());
        assertEquals(VALID_DESCRIPTION, claim.getClaimDescription());
    }

    @Test
    void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedClaim adaptedClaim = new JsonAdaptedClaim(null, VALID_DESCRIPTION);

        assertThrows(IllegalValueException.class, adaptedClaim::toModelType,
                String.format(JsonAdaptedClaim.MISSING_FIELD_MESSAGE_FORMAT,
                        ClaimStatus.class.getSimpleName()));
    }

    @Test
    void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedClaim adaptedClaim = new JsonAdaptedClaim(INVALID_STATUS, VALID_DESCRIPTION);

        IllegalValueException exception = assertThrows(IllegalValueException.class, adaptedClaim::toModelType);
        assertEquals("Invalid claim status: " + INVALID_STATUS, exception.getMessage());
    }

    @Test
    void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedClaim adaptedClaim = new JsonAdaptedClaim(VALID_STATUS, null);

        assertThrows(IllegalValueException.class, adaptedClaim::toModelType,
                String.format(JsonAdaptedClaim.MISSING_FIELD_MESSAGE_FORMAT, "Description"));
    }

    @Test
    void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedClaim adaptedClaim = new JsonAdaptedClaim(VALID_STATUS, INVALID_DESCRIPTION);

        IllegalValueException exception = assertThrows(IllegalValueException.class, adaptedClaim::toModelType);
        assertEquals(Claim.MESSAGE_CONSTRAINTS, exception.getMessage());
    }
    @Test
    void jsonAdaptedClaim_constructorFromClaimObject_correctFieldValues() {
        Claim claim = new Claim(ClaimStatus.PENDING, VALID_DESCRIPTION);
        JsonAdaptedClaim adaptedClaim = new JsonAdaptedClaim(claim);
        assertEquals(VALID_STATUS, adaptedClaim.getStatus());
        assertEquals(VALID_DESCRIPTION, adaptedClaim.getDescription());
    }
}

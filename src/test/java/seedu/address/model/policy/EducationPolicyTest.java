package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EducationPolicyTest {
    private final EducationPolicy education = new EducationPolicy();
    private final PremiumAmount defaultPremiumAmount = education.getPremiumAmount();
    private final CoverageAmount defaultCoverageAmount = education.getCoverageAmount();
    private final ExpiryDate defaultExpiryDate = education.getExpiryDate();

    @Test
    public void constructor_nullValues_useDefaultValues() {
        // null premiumAmount
        EducationPolicy nullPremiumAmount = new EducationPolicy(null, defaultCoverageAmount,
                defaultExpiryDate, null);
        assertEquals(defaultPremiumAmount, nullPremiumAmount.getPremiumAmount());

        // null coverageAmount
        EducationPolicy nullCoverageAmount = new EducationPolicy(defaultPremiumAmount, null,
                defaultExpiryDate, null);
        assertEquals(defaultCoverageAmount, nullCoverageAmount.getCoverageAmount());

        // null expiryDate
        EducationPolicy nullExpiryDate = new EducationPolicy(defaultPremiumAmount, defaultCoverageAmount, null,
                null);
        assertEquals(defaultExpiryDate, nullExpiryDate.getExpiryDate());
    }

    @Test
    public void getType_returnsCorrectType() {
        assertEquals(PolicyType.EDUCATION, education.getType());
    }

    @Test
    public void toStringMethod() {
        String expectedPrefix = "Policy type: " + education.getType() + " | ";
        assertTrue(education.toString().startsWith(expectedPrefix));
    }

    @Test
    public void equalsMethod() {
        // same object -> returns true
        assertTrue(education.equals(education));

        // null -> returns false
        assertFalse(education.equals(null));

        // same values -> return true
        EducationPolicy policyWithSameValues = new EducationPolicy();
        assertTrue(education.equals(policyWithSameValues));

        // different type -> returns false
        Policy life = new LifePolicy();
        life.setPremiumAmount(education.getPremiumAmount());
        life.setCoverageAmount(education.getCoverageAmount());
        assertFalse(education.equals(life));
    }
}

package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EducationPolicyTest {
    private final EducationPolicy education = new EducationPolicy();
    private final double defaultPremiumAmount = education.getPremiumAmount();
    private final double defaultCoverageAmount = education.getCoverageAmount();
    private final LocalDate defaultExpiryDate = education.getExpiryDate();

    @Test
    public void constructor_negativeAmounts_useDefaultValues() {
        // negative premiumAmount
        EducationPolicy negativePremiumAmount = new EducationPolicy(-1, defaultCoverageAmount, defaultExpiryDate);
        assertEquals(defaultPremiumAmount, negativePremiumAmount.getPremiumAmount());

        // negative coverageAmount
        EducationPolicy negativeCoverageAmount = new EducationPolicy(defaultPremiumAmount, -1, defaultExpiryDate);
        assertEquals(defaultCoverageAmount, negativeCoverageAmount.getCoverageAmount());
    }

    @Test
    public void constructor_nullExpiryDate_useDefaultValue() {
        EducationPolicy nullExpiryDate = new EducationPolicy(defaultPremiumAmount, defaultCoverageAmount, null);
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

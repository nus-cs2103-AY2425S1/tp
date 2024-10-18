package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class LifePolicyTest {
    private final LifePolicy life = new LifePolicy();
    private final double defaultPremiumAmount = life.getPremiumAmount();
    private final double defaultCoverageAmount = life.getCoverageAmount();
    private final LocalDate defaultExpiryDate = life.getExpiryDate();

    @Test
    public void constructor_negativeAmounts_useDefaultValues() {
        // negative premiumAmount
        LifePolicy negativePremiumAmount = new LifePolicy(-1, defaultCoverageAmount, defaultExpiryDate);
        assertEquals(defaultPremiumAmount, negativePremiumAmount.getPremiumAmount());

        // negative coverageAmount
        LifePolicy negativeCoverageAmount = new LifePolicy(defaultPremiumAmount, -1, defaultExpiryDate);
        assertEquals(defaultCoverageAmount, negativeCoverageAmount.getCoverageAmount());
    }

    @Test
    public void constructor_nullExpiryDate_useDefaultValue() {
        LifePolicy nullExpiryDate = new LifePolicy(defaultPremiumAmount, defaultCoverageAmount, null);
        assertEquals(defaultExpiryDate, nullExpiryDate.getExpiryDate());
    }

    @Test
    public void getType_returnsCorrectType() {
        assertEquals(PolicyType.LIFE, life.getType());
    }

    @Test
    public void toStringMethod() {
        String expectedPrefix = "Policy type: " + life.getType() + " | ";
        assertTrue(life.toString().startsWith(expectedPrefix));
    }

    @Test
    public void equalsMethod() {
        // same object -> returns true
        assertTrue(life.equals(life));

        // null -> returns false
        assertFalse(life.equals(null));

        // same values -> return true
        LifePolicy policyWithSameValues = new LifePolicy();
        assertTrue(life.equals(policyWithSameValues));

        // different type -> returns false
        Policy health = new HealthPolicy();
        health.setPremiumAmount(life.getPremiumAmount());
        health.setCoverageAmount(health.getCoverageAmount());
        assertFalse(life.equals(health));
    }
}

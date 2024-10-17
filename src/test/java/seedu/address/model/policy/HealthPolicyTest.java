package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class HealthPolicyTest {
    private final HealthPolicy health = new HealthPolicy();
    private final double defaultPremiumAmount = health.getPremiumAmount();
    private final double defaultCoverageAmount = health.getCoverageAmount();
    private final LocalDate defaultExpiryDate = health.getExpiryDate();

    @Test
    public void constructor_negativeAmounts_useDefaultValues() {
        // negative premiumAmount
        HealthPolicy negativePremiumAmount = new HealthPolicy(-1, defaultCoverageAmount, defaultExpiryDate);
        assertEquals(defaultPremiumAmount, negativePremiumAmount.getPremiumAmount());

        // negative coverageAmount
        HealthPolicy negativeCoverageAmount = new HealthPolicy(defaultPremiumAmount, -1, defaultExpiryDate);
        assertEquals(defaultCoverageAmount, negativeCoverageAmount.getCoverageAmount());
    }

    @Test
    public void constructor_nullExpiryDate_useDefaultValue() {
        HealthPolicy nullExpiryDate = new HealthPolicy(defaultPremiumAmount, defaultCoverageAmount, null);
        assertEquals(defaultExpiryDate, nullExpiryDate.getExpiryDate());
    }

    @Test
    public void getType_returnsCorrectType() {
        assertEquals(PolicyType.HEALTH, health.getType());
    }

    @Test
    public void toStringMethod() {
        String expectedPrefix = "Policy type: " + health.getType() + " | ";
        assertTrue(health.toString().startsWith(expectedPrefix));
    }

    @Test
    public void equalsMethod() {
        // same object -> returns true
        assertTrue(health.equals(health));

        // null -> returns false
        assertFalse(health.equals(null));

        // same values -> return true
        HealthPolicy policyWithSameValues = new HealthPolicy();
        assertTrue(health.equals(policyWithSameValues));

        // different type -> returns false
        Policy life = new LifePolicy();
        life.setPremiumAmount(health.getPremiumAmount());
        life.setCoverageAmount(health.getCoverageAmount());
        assertFalse(health.equals(life));
    }
}

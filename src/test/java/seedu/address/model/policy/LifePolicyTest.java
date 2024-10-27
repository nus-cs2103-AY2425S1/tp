package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.ClaimList;

public class LifePolicyTest {
    private final LifePolicy life = new LifePolicy();
    private final PremiumAmount defaultPremiumAmount = life.getPremiumAmount();
    private final CoverageAmount defaultCoverageAmount = life.getCoverageAmount();
    private final ExpiryDate defaultExpiryDate = life.getExpiryDate();

    @Test
    public void constructor_negativeAmounts_useDefaultValues() {
        // null premiumAmount
        LifePolicy nullPremiumAmount = new LifePolicy(null, defaultCoverageAmount,
                defaultExpiryDate, new ClaimList());
        assertEquals(defaultPremiumAmount, nullPremiumAmount.getPremiumAmount());

        // null coverageAmount
        LifePolicy nullCoverageAmount = new LifePolicy(defaultPremiumAmount, null,
                defaultExpiryDate, new ClaimList());
        assertEquals(defaultCoverageAmount, nullCoverageAmount.getCoverageAmount());

        // null expiryDate
        LifePolicy nullExpiryDate = new LifePolicy(defaultPremiumAmount, defaultCoverageAmount,
                null, new ClaimList());
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

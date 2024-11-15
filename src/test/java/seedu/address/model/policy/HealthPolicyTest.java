package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.claim.ClaimList;

public class HealthPolicyTest {
    private final HealthPolicy health = new HealthPolicy();
    private final PremiumAmount defaultPremiumAmount = health.getPremiumAmount();
    private final CoverageAmount defaultCoverageAmount = health.getCoverageAmount();
    private final ExpiryDate defaultExpiryDate = health.getExpiryDate();

    @Test
    public void constructor_nullValues_useDefaultValues() {
        // null premiumAmount
        HealthPolicy nullPremiumAmount = new HealthPolicy(null, defaultCoverageAmount,
                defaultExpiryDate, null);
        assertEquals(defaultPremiumAmount, nullPremiumAmount.getPremiumAmount());

        // null coverageAmount
        HealthPolicy nullCoverageAmount = new HealthPolicy(defaultPremiumAmount, null,
                defaultExpiryDate, null);
        assertEquals(defaultCoverageAmount, nullCoverageAmount.getCoverageAmount());

        // null expiryDate
        HealthPolicy nullExpiryDate = new HealthPolicy(defaultPremiumAmount, defaultCoverageAmount,
                null, null);
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
        ClaimList sameClaimList = new ClaimList(health.getClaimList());
        Policy life = new LifePolicy(health.getPremiumAmount(), health.getCoverageAmount(),
                health.getExpiryDate(), sameClaimList);
        assertFalse(health.equals(life));
    }
}

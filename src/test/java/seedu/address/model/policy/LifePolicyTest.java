package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LifePolicyTest {
    private final LifePolicy life = new LifePolicy();

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

package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyMapTest {
    final LifePolicy life = new LifePolicy();
    final HealthPolicy health = new HealthPolicy();
    final EducationPolicy education = new EducationPolicy();

    @Test
    public void addMethod() {
        final PolicyMap policies = new PolicyMap();

        assertTrue(policies.add(life));
        assertTrue(policies.add(health));
        assertTrue(policies.add(education));

        assertFalse(policies.add(life));
        assertFalse(policies.add(health));
        assertFalse(policies.add(education));

        assertThrows(NullPointerException.class, () -> policies.add(null));
    }

    @Test
    public void deleteMethod() {
        final PolicyMap policies = new PolicyMap();
        policies.add(life);
        policies.add(health);
        policies.add(education);

        assertTrue(policies.delete(life.getType()));
        assertTrue(policies.delete(health.getType()));
        assertTrue(policies.delete(education.getType()));

        assertFalse(policies.delete(life.getType()));
        assertFalse(policies.delete(health.getType()));
        assertFalse(policies.delete(education.getType()));

        assertFalse(policies.delete(null));
    }

    @Test
    public void equalsMethod() {
        final PolicyMap policies = new PolicyMap();
        policies.add(life);
        policies.add(health);
        policies.add(education);

        // same object -> returns true
        assertTrue(policies.equals(policies));

        // null -> returns false
        assertFalse(policies.equals(null));

        // same values -> returns true
        final PolicyMap samePolicies = new PolicyMap();
        samePolicies.add(education);
        samePolicies.add(health);
        samePolicies.add(life);
        assertTrue(policies.equals(samePolicies));

        // different values -> returns false
        final PolicyMap differentPolicies = new PolicyMap();
        differentPolicies.add(life);
        differentPolicies.add(education);
        assertFalse(policies.equals(differentPolicies));
    }
}

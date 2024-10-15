package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicySetTest {
    final LifePolicy life = new LifePolicy();
    final HealthPolicy health = new HealthPolicy();
    final EducationPolicy education = new EducationPolicy();

    @Test
    public void addMethod() {
        final PolicySet policies = new PolicySet();

        assertTrue(policies.add(life));
        assertTrue(policies.add(health));
        assertTrue(policies.add(education));

        assertFalse(policies.add(life));
        assertFalse(policies.add(health));
        assertFalse(policies.add(education));

        assertThrows(NullPointerException.class, () -> policies.add(null));
    }

    @Test
    public void removeMethod() {
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(health);
        policies.add(education);

        assertTrue(policies.remove(life.getType()));
        assertTrue(policies.remove(health.getType()));
        assertTrue(policies.remove(education.getType()));

        assertFalse(policies.remove(life.getType()));
        assertFalse(policies.remove(health.getType()));
        assertFalse(policies.remove(education.getType()));

        assertThrows(NullPointerException.class, () -> policies.remove(null));
    }

    @Test
    public void equalsMethod() {
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(health);
        policies.add(education);

        // same object -> returns true
        assertTrue(policies.equals(policies));

        // null -> returns false
        assertFalse(policies.equals(null));

        // same values -> returns true
        final PolicySet samePolicies = new PolicySet();
        samePolicies.add(education);
        samePolicies.add(health);
        samePolicies.add(life);
        assertTrue(policies.equals(samePolicies));

        // different values -> returns false
        final PolicySet differentPolicies = new PolicySet();
        differentPolicies.add(life);
        differentPolicies.add(education);
        assertFalse(policies.equals(differentPolicies));
    }
}

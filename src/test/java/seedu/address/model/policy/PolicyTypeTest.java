package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PolicyTypeTest {
    @Test
    public void getValidPolicyTypesMethod() {
        List<PolicyType> policies = Arrays.asList(PolicyType.getValidPolicyTypes());
        assertTrue(policies.contains(PolicyType.LIFE));
        assertTrue(policies.contains(PolicyType.HEALTH));
        assertTrue(policies.contains(PolicyType.EDUCATION));
    }

    @Test
    public void toStringMethod() {
        assertEquals("Life", PolicyType.LIFE.toString());
        assertEquals("Health", PolicyType.HEALTH.toString());
        assertEquals("Education", PolicyType.EDUCATION.toString());
        assertNotEquals("life", PolicyType.LIFE.toString());
    }

    @Test
    public void isValidPolicyType() {
        assertTrue(PolicyType.isValidPolicyType("Life"));
        assertTrue(PolicyType.isValidPolicyType("Health"));
        assertTrue(PolicyType.isValidPolicyType("Education"));
        assertFalse(PolicyType.isValidPolicyType("live"));
    }
}

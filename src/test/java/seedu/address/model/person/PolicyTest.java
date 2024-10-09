package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PolicyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> new Policy(null, "2023-01-01", "2024-01-01"));
        assertThrows(NullPointerException.class,
                () -> new Policy("Policy Name", null, "2024-01-01"));
        assertThrows(NullPointerException.class,
                () -> new Policy("Policy Name", "2023-01-01", null));
    }

    @Test
    public void constructor_invalidPolicy_throwsIllegalArgumentException() {
        String invalidPolicyName = "";
        assertThrows(IllegalArgumentException.class,
                () -> new Policy(invalidPolicyName, "2023-01-01", "2024-01-01"));
        assertThrows(IllegalArgumentException.class,
                () -> new Policy("Policy Name", "invalid-date", "2024-01-01"));
        assertThrows(IllegalArgumentException.class,
                () -> new Policy("Policy Name", "2023-01-01", "invalid-date"));
        assertThrows(IllegalArgumentException.class,
                () -> new Policy("Policy Name", "2024-01-01", "2023-01-01"));
    }

    @Test
    public void isValidPolicy() {
        // null policy details
        assertThrows(NullPointerException.class,
                () -> Policy.isValidPolicy(null, "2023-01-01", "2024-01-01"));
        assertThrows(NullPointerException.class,
                () -> Policy.isValidPolicy("Policy Name", null, "2024-01-01"));
        assertThrows(NullPointerException.class,
                () -> Policy.isValidPolicy("Policy Name", "2023-01-01", null));

        // invalid policies
        assertFalse(Policy.isValidPolicy("",
                "2023-01-01", "2024-01-01")); // empty policy name
        assertFalse(Policy.isValidPolicy("Policy Name",
                "invalid-date", "2024-01-01")); // invalid start date
        assertFalse(Policy.isValidPolicy("Policy Name",
                "2023-01-01", "invalid-date")); // invalid end date
        assertFalse(Policy.isValidPolicy("Policy Name",
                "2024-01-01", "2023-01-01")); // endDate before startDate

        // valid policies
        assertTrue(Policy.isValidPolicy("Health Insurance",
                "2023-01-01", "2024-01-01"));
    }

    @Test
    public void equals() {
        Policy policy = new Policy("Health Insurance",
                "2023-01-01", "2024-01-01");

        // same values -> returns true
        assertTrue(policy.equals(new Policy("Health Insurance",
                "2023-01-01", "2024-01-01")));

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different types -> returns false
        assertFalse(policy.equals(5));

        // different values -> returns false
        assertFalse(policy.equals(new Policy("Life Insurance",
                "2023-01-01", "2024-01-01"))); // different policy name
        assertFalse(policy.equals(new Policy("Health Insurance",
                "2023-01-02", "2024-01-01"))); // different start date
        assertFalse(policy.equals(new Policy("Health Insurance",
                "2023-01-01", "2024-01-02"))); // different end date
    }
}

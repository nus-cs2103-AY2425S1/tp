package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_NAME_LIFE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class PolicyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Policy(null, "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT));
        assertThrows(NullPointerException.class, () ->
                new Policy("Policy Name", null, "2024-01-01", VALID_INSURANCE_PAYMENT));
        assertThrows(NullPointerException.class, () ->
                new Policy("Policy Name", "2023-01-01", null, VALID_INSURANCE_PAYMENT));
    }

    @Test
    public void constructor_invalidPolicy_throwsIllegalArgumentException() {
        String invalidPolicyName = "";
        assertThrows(IllegalArgumentException.class, () ->
                new Policy(invalidPolicyName, "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT));
        assertThrows(IllegalArgumentException.class, () ->
                new Policy(VALID_POLICY_NAME_LIFE, "invalid-date", "2024-01-01", VALID_INSURANCE_PAYMENT));
        assertThrows(IllegalArgumentException.class, () ->
                new Policy(VALID_POLICY_NAME_LIFE, "2023-01-01", "invalid-date", VALID_INSURANCE_PAYMENT));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_POLICY_NAME_LIFE,
                "2023-12-12", "2024-12-12", INVALID_INSURANCE_PAYMENT));

    }

    @Test
    public void isValidPolicy() {
        // null policy details
        assertThrows(NullPointerException.class, () ->
                Policy.isValidPolicy(null, "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT));
        assertThrows(NullPointerException.class, () ->
                Policy.isValidPolicy(VALID_POLICY_NAME_LIFE, null, "2024-01-01", VALID_INSURANCE_PAYMENT));
        assertThrows(NullPointerException.class, () ->
                Policy.isValidPolicy(VALID_POLICY_NAME_LIFE, "2023-01-01", null, VALID_INSURANCE_PAYMENT));

        // invalid policies
        assertFalse(Policy.isValidPolicy("",
                "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT)); // empty policy name
        assertFalse(Policy.isValidPolicy("Policy Name",
                "invalid-date", "2024-01-01", VALID_INSURANCE_PAYMENT)); // invalid start date
        assertFalse(Policy.isValidPolicy(VALID_POLICY_NAME_LIFE,
                "2023-01-01", "invalid-date", VALID_INSURANCE_PAYMENT)); // invalid end date
        // valid policies
        assertTrue(Policy.isValidPolicy(VALID_POLICY_NAME_LIFE,
                "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT));
    }

    @Test
    public void validatePolicyDetails() {
        //policy name cannot contain special characters and numbers
        assertThrows(CommandException.class, () -> new Policy("Policy!123",
                "2024-12-12", "2025-01-01", VALID_INSURANCE_PAYMENT));

        //end date cannot be before start date
        assertThrows(CommandException.class, () -> new Policy(VALID_POLICY_NAME_LIFE,
                "2024-12-12", "2023-12-12", VALID_INSURANCE_PAYMENT));

        //start date cannot be equal to end date
        assertThrows(CommandException.class, () -> new Policy(VALID_POLICY_NAME_LIFE,
                "2025-12-12", "2025-12-12", VALID_INSURANCE_PAYMENT));

        //start date cannot be before today's date (assuming today's date is 2024-10-10)
        assertThrows(CommandException.class, ()-> new Policy(VALID_POLICY_NAME_LIFE,
                "2023-12-12", "2024-12-12", VALID_INSURANCE_PAYMENT));

        //start date can only be 1900s and onward
        assertThrows(CommandException.class, ()-> new Policy(VALID_POLICY_NAME_LIFE,
                "1899-12-31", "2025-12-12", VALID_INSURANCE_PAYMENT));
    }

    @Test
    public void equals() throws CommandException {
        Policy policy = new Policy(VALID_POLICY_NAME_LIFE,
                "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT);

        // same values -> returns true
        assertTrue(policy.equals(new Policy(VALID_POLICY_NAME_LIFE,
                "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT)));

        // same object -> returns true
        assertTrue(policy.equals(policy));
        // null -> returns false
        assertFalse(policy.equals(null));

        // different types -> returns false
        assertFalse(policy.equals(5));

        // different values -> returns false
        assertFalse(policy.equals(new Policy("health insurance",
                "2023-01-01", "2024-01-01", VALID_INSURANCE_PAYMENT))); // different policy name
        assertFalse(policy.equals(new Policy(VALID_POLICY_NAME_LIFE,
                "2023-01-02", "2024-01-01", VALID_INSURANCE_PAYMENT))); // different start date
        assertFalse(policy.equals(new Policy(VALID_POLICY_NAME_LIFE,
                "2023-01-01", "2024-01-02", VALID_INSURANCE_PAYMENT))); // different end date
    }
}

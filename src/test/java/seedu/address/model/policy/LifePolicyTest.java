package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class LifePolicyTest {
    private final LifePolicy life = new LifePolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        LocalDate expiryDate = LocalDate.now();
        Person insuree = new PersonBuilder().build();
        assertThrows(NullPointerException.class, () -> new LifePolicy(0, 0, null, insuree));
        assertThrows(NullPointerException.class, () -> new LifePolicy(0, 0, expiryDate, null));
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

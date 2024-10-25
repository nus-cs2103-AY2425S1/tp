package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HealthRiskTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HealthRisk(null));
    }

    @Test
    public void constructor_invalidHealthRisk_throwsIllegalArgumentException() {
        String invalidHealthRisk = "";
        assertThrows(IllegalArgumentException.class, () -> new HealthRisk(invalidHealthRisk));
    }

    @Test
    public void isValidHealthRisk() {
        // null health risk
        assertThrows(NullPointerException.class, () -> HealthRisk.isValidHealthRisk(null));

        // invalid health risk
        assertFalse(HealthRisk.isValidHealthRisk("")); // empty string
        assertFalse(HealthRisk.isValidHealthRisk(" ")); // spaces only
        assertFalse(HealthRisk.isValidHealthRisk("bad")); // spaces only

        // valid health risk
        assertTrue(HealthRisk.isValidHealthRisk("HIGH"));
        assertTrue(HealthRisk.isValidHealthRisk("MEDIUM"));
        assertTrue(HealthRisk.isValidHealthRisk("LOW"));
    }

    @Test
    public void equals() {
        HealthRisk healthRisk = new HealthRisk("HIGH");

        // same values -> returns true
        assertTrue(healthRisk.equals(new HealthRisk("HIGH")));

        // same object -> returns true
        assertTrue(healthRisk.equals(healthRisk));

        // null -> returns false
        assertFalse(healthRisk.equals(null));

        // different types -> returns false
        assertFalse(healthRisk.equals(5.0f));

        // different values -> returns false
        assertFalse(healthRisk.equals(new HealthRisk("LOW")));
    }
}

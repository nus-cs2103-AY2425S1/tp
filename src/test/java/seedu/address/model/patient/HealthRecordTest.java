package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HealthRecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HealthRecord(null));
    }

    @Test
    public void constructor_invalidHealthRecord_throwsIllegalArgumentException() {
        String invalidHealthRecord = "";
        assertThrows(IllegalArgumentException.class, () -> new HealthRecord(invalidHealthRecord));
    }

    @Test
    public void isValidHealthRecord() {
        // null health record
        assertThrows(NullPointerException.class, () -> HealthRecord.isValidHealthRecord(null));

        // invalid health record
        assertFalse(HealthRecord.isValidHealthRecord("")); // empty string
        assertFalse(HealthRecord.isValidHealthRecord(" ")); // spaces only

        // valid health record
        assertTrue(HealthRecord.isValidHealthRecord("Undergoing treatment for cancer"));
        assertTrue(HealthRecord.isValidHealthRecord("Diabetes"));
    }

    @Test
    public void equals() {
        HealthRecord healthRecord = new HealthRecord("Diabetes");

        // same values -> returns true
        assertTrue(healthRecord.equals(new HealthRecord("Diabetes")));

        // same object -> returns true
        assertTrue(healthRecord.equals(healthRecord));

        // null -> returns false
        assertFalse(healthRecord.equals(null));

        // different types -> returns false
        assertFalse(healthRecord.equals(5.0f));

        // different values -> returns false
        assertFalse(healthRecord.equals(new HealthRecord("High cholesterol")));
    }
}

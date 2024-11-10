package seedu.address.model.healthservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HealthServiceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HealthService(null));
    }

    @Test
    public void constructor_invalidHealthServiceName_throwsIllegalArgumentException() {
        String invalidHealthServiceName = "Surgery";
        assertThrows(IllegalArgumentException.class, () -> new HealthService(invalidHealthServiceName));
    }

    @Test
    public void constructor_validHealthServiceName_success() {
        String validHealthServiceName = "BLOOD TEST";
        HealthService healthService = new HealthService(validHealthServiceName);
        assertEquals(validHealthServiceName, healthService.healthServiceName);
    }

    @Test
    public void isValidHealthService_validHealthServiceNames_success() {
        assertTrue(HealthService.isValidHealthServiceName("blood test"));
        assertTrue(HealthService.isValidHealthServiceName("Cancer SCREENING"));
        assertTrue(HealthService.isValidHealthServiceName("Vaccination"));
        assertTrue(HealthService.isValidHealthServiceName("consult"));
    }

    @Test
    public void isValidHealthService_invalidHealthServiceNames_failure() {
        assertFalse(HealthService.isValidHealthServiceName("Surgery"));
        assertFalse(HealthService.isValidHealthServiceName("Dentist"));
        assertFalse(HealthService.isValidHealthServiceName("VACCINATIONN"));
        assertFalse(HealthService.isValidHealthServiceName(" "));
    }

    @Test
    public void equals() {
        HealthService bloodTest = new HealthService("BLOOD TEST");
        HealthService cancerScreening = new HealthService("CANCER SCREENING");

        // same values -> returns true
        assertTrue(bloodTest.equals(new HealthService("BLOOD TEST")));

        // same object -> returns true
        assertTrue(bloodTest.equals(bloodTest));

        // null -> returns false
        assertFalse(bloodTest.equals(null));

        // different types -> returns false
        assertFalse(bloodTest.equals(1));

        // different values -> returns false
        assertFalse(bloodTest.equals(cancerScreening));
    }
}

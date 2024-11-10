package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.healthservice.HealthService;

public class ApptTest {

    @Test
    public void constructor_validArgs_success() {
        LocalDateTime dateTime = LocalDateTime.parse("2024-12-12 13:00", Appt.STRICT_FORMATTER);
        HealthService healthService = new HealthService("CONSULT");
        Appt appt = new Appt(dateTime, healthService);
        assertTrue(appt.getDateTime() != null);
        assertTrue(appt.getHealthService() != null);
        assertEquals(dateTime, appt.getDateTime());
        assertEquals(healthService, appt.getHealthService());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appt(null, null));
    }

    @Test
    public void constructor_nullDateTime_throwsNullPointerException() {
        HealthService healthService = new HealthService("CONSULT");
        assertThrows(NullPointerException.class, () -> new Appt(null, healthService));
    }

    @Test
    public void constructor_nullHealthService_throwsNullPointerException() {
        LocalDateTime dateTime = LocalDateTime.parse("2024-12-12 13:00", Appt.STRICT_FORMATTER);
        assertThrows(NullPointerException.class, () -> new Appt(dateTime, null));
    }

    @Test
    public void isValidDateTime() {
        // null date and time
        assertThrows(NullPointerException.class, () -> Appt.isValidDateTime(null));

        // invalid date and time
        assertFalse(Appt.isValidDateTime("")); // empty string
        assertFalse(Appt.isValidDateTime(" ")); // spaces only
        assertFalse(Appt.isValidDateTime("2024-12-32 13:00")); // invalid date
        assertFalse(Appt.isValidDateTime("2024-12-31 24:00")); // invalid time
        assertFalse(Appt.isValidDateTime("2025-02-29 00:00")); // invalid date

        // valid date and time
        assertTrue(Appt.isValidDateTime("2024-12-31 13:00"));
        assertTrue(Appt.isValidDateTime("2024-01-01 23:59"));
        assertTrue(Appt.isValidDateTime("2024-02-28 00:00"));
    }

    @Test
    public void equals() {
        LocalDateTime dateTime = LocalDateTime.parse("2024-12-12 13:00", Appt.STRICT_FORMATTER);
        HealthService healthService = new HealthService("CONSULT");
        Appt appt = new Appt(dateTime, healthService);

        // same values -> returns true
        assertTrue(appt.equals(new Appt(dateTime, healthService)));

        // same object -> returns true
        assertTrue(appt.equals(appt));

        // null -> returns false
        assertFalse(appt.equals(null));

        // different types -> returns false
        assertFalse(appt.equals(1));

        // different values -> returns false
        assertFalse(appt.equals(new Appt(LocalDateTime.parse("2024-12-12 14:00", Appt.STRICT_FORMATTER),
            healthService)));
    }
}

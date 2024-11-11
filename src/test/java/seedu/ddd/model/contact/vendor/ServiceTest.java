package seedu.ddd.model.contact.vendor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ServiceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Service(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Service(invalidPhone));
    }

    @Test
    public void isValidService() {
        // null service
        assertThrows(NullPointerException.class, () -> Service.isValidService(null));

        // invalid services
        assertFalse(Service.isValidService("")); // empty string
        assertFalse(Service.isValidService("!@#$%^&*()")); // special characters

        // valid services
        assertFalse(Service.isValidService(" "));
        assertTrue(Service.isValidService("abc"));
        assertTrue(Service.isValidService("abc123"));
        assertTrue(Service.isValidService("abc 123"));
    }

    @Test
    public void equals() {
        Service service = new Service("catering");

        // same values -> returns true
        assertTrue(service.equals(new Service("catering")));

        // same object -> returns true
        assertTrue(service.equals(service));

        // null -> returns false
        assertFalse(service.equals(null));

        // different types -> returns false
        assertFalse(service.equals(5.0f));

        // different values -> returns false
        assertFalse(service.equals(new Service("not catering")));
    }
}

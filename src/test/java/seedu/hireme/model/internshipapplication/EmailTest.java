package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_emptyEmail_throwsIllegalArgumentException() {
        String emptyEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(emptyEmail));
    }

    @Test
    public void constructor_invalidEmailFormat_throwsIllegalArgumentException() {
        // Missing '@' and domain
        assertThrows(IllegalArgumentException.class, () -> new Email("missingatsign.com"));

        // Multiple '@' symbols
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid@@example.com"));

        // Starts with special character
        assertThrows(IllegalArgumentException.class, () -> new Email(".invalid@domain.com"));

        // Ends with special character
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid@domain.com-"));

        // Invalid characters in domain
        assertThrows(IllegalArgumentException.class, () -> new Email("user@domain!com"));

        // Domain too short
        assertThrows(IllegalArgumentException.class, () -> new Email("user@d.c"));

        // Leading/trailing spaces
        assertThrows(IllegalArgumentException.class, () -> new Email(" user@example.com"));
        assertThrows(IllegalArgumentException.class, () -> new Email("user@example.com "));
    }

    @Test
    public void constructor_validEmail_createsEmailSuccessfully() {
        // Simple valid email
        Email email1 = new Email("user@example.com");
        assertEquals("user@example.com", email1.toString());

        // Email with subdomains
        Email email2 = new Email("user@mail.example.co.uk");
        assertEquals("user@mail.example.co.uk", email2.toString());

        // Email with special characters in local-part
        Email email3 = new Email("user.name+tag@domain.com");
        assertEquals("user.name+tag@domain.com", email3.toString());

        // Valid alphanumeric-only email
        Email email4 = new Email("username123@domain.com");
        assertEquals("username123@domain.com", email4.toString());
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email.com");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email.com")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email.com")));
    }

    @Test
    public void toStringMethod() {
        Email email = new Email("valid@email.com");
        assertEquals("valid@email.com", email.toString());
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Email email1 = new Email("test@domain.com");
        Email email2 = new Email("test@domain.com");
        Email email3 = new Email("different@domain.com");

        // Consistent hash codes for equal emails
        assertEquals(email1.hashCode(), email2.hashCode());
        assertNotEquals(email1.hashCode(), email3.hashCode());
    }

}

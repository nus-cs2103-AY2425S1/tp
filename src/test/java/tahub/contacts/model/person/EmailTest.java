package tahub.contacts.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void testValidEmailAbcD() {
        assertTrue(Email.isValidEmail("abc-d@mail.com"));
    }

    @Test
    public void testValidEmailAbcDef() {
        assertTrue(Email.isValidEmail("abc.def@mail.com"));
    }

    @Test
    public void testValidEmailAbc() {
        assertTrue(Email.isValidEmail("abc@mail.com"));
    }

    @Test
    public void testValidEmailAbcUnderscoreDef() {
        assertTrue(Email.isValidEmail("abc_def@mail.com"));
    }

    @Test
    public void testInvalidEmailAbcDashAtMailCom() {
        assertFalse(Email.isValidEmail("abc-@mail.com"));
    }

    @Test
    public void testInvalidEmailAbcDotDotDef() {
        assertFalse(Email.isValidEmail("abc..def@mail.com"));
    }

    @Test
    public void testInvalidEmailDotAbc() {
        assertFalse(Email.isValidEmail(".abc@mail.com"));
    }

    @Test
    public void testInvalidEmailAbcHashDef() {
        assertFalse(Email.isValidEmail("abc#def@mail.com"));
    }

    @Test
    public void testValidEmailMailCc() {
        assertTrue(Email.isValidEmail("abc.def@mail.cc"));
    }

    @Test
    public void testValidEmailMailArchive() {
        assertTrue(Email.isValidEmail("abc.def@mail-archive.com"));
    }

    @Test
    public void testValidEmailMailOrg() {
        assertTrue(Email.isValidEmail("abc.def@mail.org"));
    }

    @Test
    public void testValidEmailMailCom() {
        assertTrue(Email.isValidEmail("abc.def@mail.com"));
    }

    @Test
    public void testInvalidEmailMailC() {
        assertFalse(Email.isValidEmail("abc.def@mail.c"));
    }

    @Test
    public void testInvalidEmailMailHashArchive() {
        assertFalse(Email.isValidEmail("abc.def@mail#archive.com"));
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
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void containsSpecialChar() {
        //no special char
        Id id1 = new Id("P12345");
        assertFalse(id1.containsSpecialChar());

        Id id2 = new Id("Patient1235");
        assertFalse(id2.containsSpecialChar());

        //special char
        Id id3 = new Id("P12356/");
        assertTrue(id3.containsSpecialChar());

        Id id4 = new Id("#P12345()");
        assertTrue(id4.containsSpecialChar());

        Id id5 = new Id("C135()()-");
        assertTrue(id5.containsSpecialChar());
    }

    @Test
    public void isValidId() {
        // null id
        assertThrows(NullPointerException.class, ()-> Id.isValidId(null));

        // invalid id
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId("  ")); // spaces only
        assertFalse(Id.isValidId("^@$&*")); // only invalid special characters
        assertFalse(Id.isValidId("45IN*352")); // contains invalid special characters
        assertFalse(Id.isValidId("550e8400-e29b-41d4-a716-4466554400001")); // 37 characters

        // valid id
        assertTrue(Id.isValidId("ANGDSAV")); // alphabets only
        assertTrue(Id.isValidId("1351823")); // numbers only
        assertTrue(Id.isValidId("550e8400e29b41d4")); // alphanumeric
        assertTrue(Id.isValidId("2/AS-F#N(A)S")); // with valid special characters
        assertTrue(Id.isValidId("550e8400-e29b-41d4-a716-446655440000")); // 36 characters

    }
}

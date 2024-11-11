package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WardTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidWard = "";
        assertThrows(IllegalArgumentException.class, () -> new Ward(invalidWard));
    }

    @Test
    public void containsSpecialChar() {
        //no special char
        Ward w1 = new Ward("A1");
        assertFalse(w1.containsSpecialChar());

        Ward w2 = new Ward("B1235");
        assertFalse(w2.containsSpecialChar());

        //special char
        Ward w3 = new Ward("B1235!");
        assertTrue(w3.containsSpecialChar());

        Ward w4 = new Ward("B7gas!@#");
        assertTrue(w4.containsSpecialChar());

        Ward w5 = new Ward("C135()()!");
        assertTrue(w5.containsSpecialChar());
    }

    @Test
    public void isValidWard() {
        // null Ward
        assertThrows(NullPointerException.class, ()-> Ward.isValidWard(null));

        // invalid Ward
        assertFalse(Ward.isValidWard("")); // empty string
        assertFalse(Ward.isValidWard("  ")); // spaces only
        assertFalse(Ward.isValidWard("^@$&*")); // only special characters
        // more than 50 characters
        assertFalse(Ward.isValidWard("550e8400-e29b-41d4-a716-4466554400001asdasdfasdfasdfasdfasdfasdfasdf"));

        // valid Ward
        assertTrue(Ward.isValidWard("ANGDSAV")); // alphabets only
        assertTrue(Ward.isValidWard("1351823")); // numbers only
        assertTrue(Ward.isValidWard("550e8400e29b41d4")); // alphanumeric
        assertTrue(Ward.isValidWard("2/AS-F#N(A)S")); // with valid special characters
        assertTrue(Ward.isValidWard("550e8400-e29b-41d4-a716-44665544000011111111111111")); // 50 characters

    }
}

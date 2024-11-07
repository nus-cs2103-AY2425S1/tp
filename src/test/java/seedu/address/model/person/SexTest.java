package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SexTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sex(null));
    }

    @Test
    public void constructor_invalidSex_throwsIllegalArgumentException() {
        String invalidSex = "";
        assertThrows(IllegalArgumentException.class, () -> new Sex(invalidSex));
    }

    @Test
    public void isValidSex() {
        // null sex
        assertThrows(NullPointerException.class, () -> Sex.isValidSex(null));

        // invalid sex
        assertFalse(Sex.isValidSex("km")); // not one of "f" or "m"
        assertFalse(Sex.isValidSex(" ")); // spaces only
        assertFalse(Sex.isValidSex("12345")); // numbers


        // valid sex
        assertTrue(Sex.isValidSex("f"));
        assertTrue(Sex.isValidSex("m"));
        assertTrue(Sex.isValidSex("M"));
    }

    @Test
    public void equals() {
        Sex sex = new Sex("M");

        // same values -> returns true
        assertTrue(sex.equals(new Sex("m")));
        assertTrue(sex.equals(new Sex("M")));

        // same object -> returns true
        assertTrue(sex.equals(sex));

        // null -> returns false
        assertFalse(sex.equals(null));

        // different types -> returns false
        assertFalse(sex.equals(5.0f));

        // different values -> returns false
        assertFalse(sex.equals(new Sex("f")));
    }
}

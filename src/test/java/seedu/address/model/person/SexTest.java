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
        assertFalse(Sex.isValidSex("")); // empty string
        assertFalse(Sex.isValidSex(" ")); // spaces only

        // valid sexes
        assertTrue(Sex.isValidSex("Female"));
        assertTrue(Sex.isValidSex("Male"));
        assertTrue(Sex.isValidSex("Unknown gender"));
    }

    @Test
    public void equals() {
        Sex sex = new Sex("Valid Sex");

        // same values -> returns true
        assertTrue(sex.equals(new Sex("Valid Sex")));

        // same object -> returns true
        assertTrue(sex.equals(sex));

        // null -> returns false
        assertFalse(sex.equals(null));

        // different types -> returns false
        assertFalse(sex.equals(5.0f));

        // different values -> returns false
        assertFalse(sex.equals(new Sex("Other Valid Sex")));
    }
}


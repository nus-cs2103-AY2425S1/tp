package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age((String) null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null age
        assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // blank age
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only

        // invalid age
        assertFalse(Age.isValidAge(-1));
        assertFalse(Age.isValidAge(151));
        assertFalse(Age.isValidAge("-0.001"));
        assertFalse(Age.isValidAge("-1.1"));
        assertFalse(Age.isValidAge("150.00001"));
        assertFalse(Age.isValidAge("1.0"));

        // valid age
        assertTrue(Age.isValidAge(0));
        assertTrue(Age.isValidAge(150));
        assertTrue(Age.isValidAge(75));
    }

    @Test
    public void emptyAge() {
        Age age = Age.createEmpty();

        assertTrue(age.isEmpty());

        assertEquals(age.toString(), "<REPRESENTATION FOR EMPTY AGE>");

        assertEquals(age.getValueForUi(), "-");
    }

    @Test
    public void equals() {
        Age age = new Age(21);

        // same values -> returns true
        assertTrue(age.equals(new Age("21")));

        // same object -> returns true
        assertTrue(age.equals(age));

        // null -> returns false
        assertFalse(age.equals(null));

        // different types -> returns false
        assertFalse(age.equals("abc"));

        // different values -> returns false
        assertFalse(age.equals(new Age(22)));
    }

    @Test
    public void stringForUi() {
        assertEquals(new Age(21).getValueForUi(), "21");
    }

    @Test
    public void hashcode() {
        assertEquals(new Age(25).hashCode(), new Age(25).value.hashCode());
    }
}

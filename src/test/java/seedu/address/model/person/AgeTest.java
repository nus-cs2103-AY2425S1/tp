package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void equals() {
        Age age = new Age(new Birthday("13 05 2002"));

        // same values -> returns true
        assertTrue(age.equals(new Age(new Birthday("13 05 2002"))));

        // same object -> returns true
        assertTrue(age.equals(age));

        // null -> returns false
        assertFalse(age.equals(null));

        // different types -> returns false
        assertFalse(age.equals(5.0f));

        // different years of at least one year apart -> returns false
        assertFalse(age.equals(new Age(new Birthday("13 05 1999"))));
    }
}


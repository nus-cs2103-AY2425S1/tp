package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void isValidId() {
        // null name
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid name
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId(" ")); // spaces only
        assertFalse(Id.isValidId("^")); // only non-alphanumeric characters
        assertFalse(Id.isValidId("5252*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Id.isValidId("abac gjku")); // alphabets only
        assertTrue(Id.isValidId("12345")); // numbers only
        assertTrue(Id.isValidId("kk12658j")); // alphanumeric characters
        assertTrue(Id.isValidId("A0276123J20")); // with capital letters
        assertTrue(Id.isValidId("A0276123J20 A2552 6456 R20")); // long names
    }

    @Test
    public void equals() {
        Id id = new Id("Valid Id");

        // same values -> returns true
        assertTrue(id.equals(new Id("Valid Id")));

        // same object -> returns true
        assertTrue(id.equals(id));

        // null -> returns false
        assertFalse(id.equals(null));

        // different types -> returns false
        assertFalse(id.equals(5.0f));

        // different values -> returns false
        assertFalse(id.equals(new Id("Other Valid Name")));
    }
}

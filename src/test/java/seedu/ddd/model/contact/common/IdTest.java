package seedu.ddd.model.contact.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    public void isValidId() {
        // null ID
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid IDs
        assertFalse(Id.isValidId(-1));
        assertFalse(Id.isValidId(-10));
        assertFalse(Id.isValidId(""));
        assertFalse(Id.isValidId(" "));

        // valid IDs
        assertTrue(Id.isValidId(0));
        assertTrue(Id.isValidId(1));
        assertTrue(Id.isValidId(10));
        assertTrue(Id.isValidId(100));
        assertTrue(Id.isValidId("0"));
        assertTrue(Id.isValidId("1"));
        assertTrue(Id.isValidId("10"));
        assertTrue(Id.isValidId("100"));
    }

    @Test
    public void equals() {
        Id id = new Id(0);

        // same values -> returns true
        assertTrue(id.equals(new Id(0)));

        // same object -> returns true
        assertTrue(id.equals(id));

        // null -> returns false
        assertFalse(id.equals(null));

        // different types -> returns false
        assertFalse(id.equals("Other type"));

        // different values -> returns false
        assertFalse(id.equals(new Id(1)));
    }
}

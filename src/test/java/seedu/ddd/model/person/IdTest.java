package seedu.ddd.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.common.Id;

public class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid ids
        assertFalse(Id.isValidId(-1));
        assertFalse(Id.isValidId(-10));
        assertFalse(Id.isValidId(""));
        assertFalse(Id.isValidId(" "));

        // valid ids
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

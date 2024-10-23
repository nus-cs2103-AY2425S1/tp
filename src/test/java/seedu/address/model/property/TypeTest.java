package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_HDB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_LANDED;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Type(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class, () -> new Type(invalidCode));
    }

    @Test
    public void isValidType() {
        // null name
        assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid name
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("^")); // only non-alphanumeric characters
        assertFalse(Type.isValidType("C-ONDO")); // contains non-alphanumeric characters
        assertFalse(Type.isValidType("HDB ")); // contains SPACE
        assertFalse(Type.isValidType("la.nded")); // contains non-alphanumeric characters
        assertFalse(Type.isValidType("PUBLIC")); // Invalid type
        assertFalse(Type.isValidType("HDBB")); // Invalid type

        // valid name
        assertTrue(Type.isValidType("CONDO")); // alphabets only
        assertTrue(Type.isValidType("HDB"));
        assertTrue(Type.isValidType("LANDED"));
        assertTrue(Type.isValidType("ConDo"));
        assertTrue(Type.isValidType("landed"));
        assertTrue(Type.isValidType("hDb"));
    }

    @Test
    public void isLandedType() {
        // invalid type
        assertFalse(new Type("condo").isLandedType()); // not landed type
        assertFalse(new Type("hDB").isLandedType()); // not landed type

        // valid type
        assertTrue(new Type("landed").isLandedType()); // landed type
    }

    @Test
    public void equals() {
        Type type = new Type(VALID_TYPE_LANDED);

        // same values -> returns true
        assertTrue(type.equals(new Type(VALID_TYPE_LANDED)));

        // same object -> returns true
        assertTrue(type.equals(type));

        // null -> returns false
        assertFalse(type.equals(null));

        // different types -> returns false
        assertFalse(type.equals(5.0f));

        // different values -> returns false
        assertFalse(type.equals(new Type(VALID_TYPE_HDB)));
    }
}

package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> GroupName.isValidName(null));

        // invalid name
        assertFalse(GroupName.isValidName("")); // empty string
        assertFalse(GroupName.isValidName(" ")); // spaces only
        assertFalse(GroupName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(GroupName.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(GroupName.isValidName("peter jack")); // alphabets only
        assertFalse(GroupName.isValidName("12345")); // numbers only
        assertFalse(GroupName.isValidName("peter the 2nd")); // alphanumeric characters
        assertFalse(GroupName.isValidName("Capital Tan")); // with capital letters
        assertFalse(GroupName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names

        // valid name
        // all uppercase
        assertTrue(GroupName.isValidName("CS2103-F12-4"));
        // all lowercase
        assertTrue(GroupName.isValidName("cs2103-a12-4"));
        // mixed case
        assertTrue(GroupName.isValidName("cs2103-B12-4"));
    }

    @Test
    public void equals() {
        GroupName groupName = new GroupName("CS2103-F12-4");

        // same values -> returns true
        assertTrue(groupName.equals(new GroupName("CS2103-F12-4")));

        // same object -> returns true
        assertTrue(groupName.equals(groupName));

        // null -> returns false
        assertFalse(groupName.equals(null));

        // different types -> returns false
        assertFalse(groupName.equals(5.0f));

        // different values -> returns false
        assertFalse(groupName.equals(new GroupName("CS2103-F12-2")));
    }
}

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

        // valid name
        assertTrue(GroupName.isValidName("peter jack")); // alphabets only
        assertTrue(GroupName.isValidName("12345")); // numbers only
        assertTrue(GroupName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(GroupName.isValidName("Capital Tan")); // with capital letters
        assertTrue(GroupName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        GroupName groupName = new GroupName("Valid Name");

        // same values -> returns true
        assertTrue(groupName.equals(new GroupName("Valid Name")));

        // same object -> returns true
        assertTrue(groupName.equals(groupName));

        // null -> returns false
        assertFalse(groupName.equals(null));

        // different types -> returns false
        assertFalse(groupName.equals(5.0f));

        // different values -> returns false
        assertFalse(groupName.equals(new GroupName("Other Valid Name")));
    }
}

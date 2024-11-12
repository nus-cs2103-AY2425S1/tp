package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupName(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidGroupName));
    }

    @Test
    public void isValidName() {
        // null group name
        assertThrows(NullPointerException.class, () -> GroupName.isValidName(null));

        // invalid group name
        assertFalse(GroupName.isValidName("")); // empty string
        assertFalse(GroupName.isValidName(" ")); // spaces only
        assertFalse(GroupName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(GroupName.isValidName("group@123")); // contains non-alphanumeric characters

        // valid group name
        assertTrue(GroupName.isValidName("Study Group")); // alphabets and space
        assertTrue(GroupName.isValidName("Group123")); // alphanumeric characters
        assertTrue(GroupName.isValidName("123456")); // numbers only
        assertTrue(GroupName.isValidName("Group One 1st Edition")); // mixed alphanumeric with spaces
    }

    @Test
    public void equals() {
        GroupName groupName = new GroupName("Valid Group");

        // same values -> returns true
        assertEquals(groupName, new GroupName("Valid Group"));

        // same object -> returns true
        assertEquals(groupName, groupName);

        // null -> returns false
        assertNotEquals(null, groupName);

        // different types -> returns false
        assertFalse(groupName.equals(5.0f));

        // different values -> returns false
        assertNotEquals(groupName, new GroupName("Other Group"));
    }

    @Test
    public void toStringMethod() {
        GroupName groupName = new GroupName("Study Group");
        assertEquals("Study Group", groupName.toString());
    }

    @Test
    public void hashCodeTest() {
        GroupName groupName = new GroupName("Study Group");
        assertEquals(groupName.hashCode(), new GroupName("Study Group").hashCode());
    }
}

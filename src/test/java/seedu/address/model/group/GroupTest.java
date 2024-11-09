package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroupName));
    }

    @Test
    public void testIsValidGroupName_null_throwsNullPointerException() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Group.isValidGroupName(null));
    }

    @Test
    public void testIsValidGroupName_longString_returnsFalse() {
        // invalid name as the name of the group name is too long
        String invalidName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                + "Phasellus finibus libero ipsum, eu semper ante consectetur ut. Maecenas "
                + "varius libero vitae sapien vulputate porta.";
        assertFalse(Group.isValidGroupName(invalidName));
    }

    @Test
    public void testIsValidGroupName_validString_returnsTrue() {
        String validName = "Team awesome Trio 33";
        assertTrue(Group.isValidGroupName(validName));
    }

    @Test
    public void testGetGroupName_returnsGroupName() {
        String validName = "Team awesome Trio 33";
        Group group = new Group(validName);
        assertEquals("Team awesome Trio 33", group.getGroupName());
    }

    @Test
    public void equals() {
        Group firstGroup = new Group("group 1");
        Group secondGroup = new Group("group 1");
        Group thirdGroup = new Group("group 2");
        Group firstGroupUpperCase = new Group("GROUP 1");

        // same object -> returns true
        assertTrue(firstGroup.equals(firstGroup));

        // same values -> returns true
        assertTrue(firstGroup.equals(secondGroup));

        // different types -> returns false
        assertFalse(firstGroup.equals("group 1"));

        // same values but different cases -> returns true
        assertTrue(firstGroup.equals(firstGroupUpperCase));

        // null -> returns false
        assertFalse(firstGroup.equals(null));

        // different values -> returns false
        assertFalse(firstGroup.equals(thirdGroup));
    }

    @Test
    public void testHashCode_sameGroupName_shouldHaveSameHashCode() {
        // Two Group objects with the same groupName
        Group firstGroup = new Group("group 1");
        Group secondGroup = new Group("group 1");

        // Assert that the hash codes are the same
        assertEquals(firstGroup.hashCode(), secondGroup.hashCode());
    }

    @Test
    public void testHashCode_differentGroupName_shouldHaveDifferentHashCode() {
        // Two Group objects with the different groupName
        Group firstGroup = new Group("group 1");
        Group secondGroup = new Group("group 2");

        // Assert that the hash codes are not the same
        assertNotEquals(firstGroup.hashCode(), secondGroup.hashCode());
    }

}

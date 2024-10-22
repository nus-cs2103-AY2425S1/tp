package keycontacts.model.student;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GroupTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroup_throwsIllegalArgumentException() {
        String invalidGroup = " ";
        assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroup));
    }

    @Test
    public void isValidGroupName() {
        // null group name
        assertThrows(NullPointerException.class, () -> Group.isValidGroupName(null));

        // invalid groups
        assertFalse(Group.isValidGroupName(" ")); // spaces only
        assertFalse(Group.isValidGroupName(" group")); // begins with a whitespace

        // valid groups
        assertTrue(Group.isValidGroupName("Group 1"));
        assertTrue(Group.isValidGroupName("")); // empty string
    }

    @Test
    public void isSameGroup() {
        Group group = new Group("Group");
        Group noGroup = new Group("");

        // same object -> returns true
        assertTrue(group.isSameGroup(group));
        assertTrue(noGroup.isSameGroup(noGroup));

        // same values -> returns true
        assertTrue(group.isSameGroup(new Group("Group")));

        // comparing two NoGroups -> return false
        assertFalse(noGroup.isSameGroup(new Group("")));

        // different values -> returns false
        assertFalse(group.isSameGroup(noGroup));
        assertFalse(noGroup.isSameGroup(group));
        assertFalse(group.isSameGroup(new Group("Other Group")));
    }

    @Test
    public void equals() {
        Group group = new Group("Valid Group");

        // same values -> returns true
        assertTrue(group.equals(new Group("Valid Group")));

        // same object -> returns true
        assertTrue(group.equals(group));

        // null -> returns false
        assertFalse(group.equals(null));

        // different types -> returns false
        assertFalse(group.equals(5.0f));

        // different values -> returns false
        assertFalse(group.equals(new Group("Other Valid Group")));
    }

    @Test
    public void hashCodeMethod() {
        String testGroup = "Test group";
        Group group = new Group(testGroup);

        assertEquals(testGroup.hashCode(), group.hashCode());
    }
}

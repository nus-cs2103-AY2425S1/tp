package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_ONE;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_ONE;

import org.junit.jupiter.api.Test;

public class GroupTest {
    private static final Group FIRST_GROUP = new Group(new GroupName(GROUP_ONE));
    private static final Group SECOND_GROUP = new Group(new GroupName(TEAM_ONE));

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(FIRST_GROUP.isSameGroup(FIRST_GROUP));
        assertTrue(SECOND_GROUP.isSameGroup(SECOND_GROUP));

        // null -> returns false
        assertFalse(FIRST_GROUP.isSameGroup(null));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Group firstGroupCopy = new Group(FIRST_GROUP.getGroupName());
        assertTrue(FIRST_GROUP.equals(firstGroupCopy));

        // same object -> returns true
        assertTrue(FIRST_GROUP.equals(FIRST_GROUP));

        // null -> returns false
        assertFalse(FIRST_GROUP.equals(null));

        // different type -> returns false
        assertFalse(FIRST_GROUP.equals(5));

        // different student -> returns false
        assertFalse(FIRST_GROUP.equals(SECOND_GROUP));
    }

    @Test
    public void toStringMethod() {
        String expected =
                Group.class.getCanonicalName() + "{groupname=" + FIRST_GROUP.getGroupName()
                        + ", students=" + FIRST_GROUP.getStudents() + "}";
        assertEquals(expected, FIRST_GROUP.toString());
    }
}

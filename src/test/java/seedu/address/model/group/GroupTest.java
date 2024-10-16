package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class GroupTest {

    private final List<Person> members = Arrays.asList(ALICE, BOB);
    private final GroupName groupName = new GroupName("Group A");
    private final Group group = new Group(groupName.toString(), members);

    @Test
    public void constructor_nullGroupName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group(null, members));
    }

    @Test
    public void constructor_nullMembers_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> new Group(groupName.toString(), null));
    }

    @Test
    public void getGroupName() {
        assertEquals(groupName, group.getGroupName());
    }

    @Test
    public void getMembers() {
        assertEquals(members, group.getMembers());
    }

    @Test
    public void toStringMethod() {
        String expected = "[Group: Group A, Members: Alice Pauline, Bob Choo]";
        assertEquals(expected, group.toString());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Group groupCopy = new Group(groupName.toString(), members);
        assertTrue(group.equals(groupCopy));

        // same object -> returns true
        assertTrue(group.equals(group));

        // null -> returns false
        assertThrows(NullPointerException.class, () -> group.equals(null));

        // different type -> returns false
        assertThrows(ClassCastException.class, () -> group.equals(5));

        // different group name -> returns false
        Group differentGroup = new Group("Group B", members);
        assertFalse(() -> group.equals(differentGroup));

        // different members -> returns false
        List<Person> differentMembers = Arrays.asList(ALICE);
        Group groupWithDifferentMembers = new Group(groupName.toString(), differentMembers);
        assertFalse(() -> group.equals(groupWithDifferentMembers));
    }
}

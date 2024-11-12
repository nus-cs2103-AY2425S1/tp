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

import seedu.address.logic.commands.GroupCommand;
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
        List<String> studentNames = Arrays.asList("Alice", "Bob");
        GroupCommand groupCommand1 = new GroupCommand("Group1", studentNames);
        GroupCommand groupCommand2 = new GroupCommand("Group1", studentNames);

        // same object -> returns true
        assertTrue(groupCommand1.equals(groupCommand1));

        // same values -> returns true
        assertTrue(groupCommand1.equals(groupCommand2));

        // different types -> returns false
        assertFalse(groupCommand1.equals(1));

        // null -> returns false
        assertFalse(groupCommand1.equals(null));

        // different group name -> returns false
        GroupCommand groupCommandDifferentName = new GroupCommand("Group2", studentNames);
        assertFalse(groupCommand1.equals(groupCommandDifferentName));

        // different student list -> returns false
        List<String> differentStudentNames = Arrays.asList("Alice");
        GroupCommand groupCommandDifferentStudents = new GroupCommand("Group1", differentStudentNames);
        assertFalse(groupCommand1.equals(groupCommandDifferentStudents));
    }
}

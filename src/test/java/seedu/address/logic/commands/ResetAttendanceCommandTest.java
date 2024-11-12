package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ResetAttendanceCommand}.
 */
public class ResetAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_resetAttendance_success() {
        List<Person> studentsInList = model.getFilteredPersonList().stream()
                .filter(person -> person instanceof Student)
                .collect(Collectors.toList());

        if (!studentsInList.isEmpty()) {
            ResetAttendanceCommand resetAttendanceCommand = new ResetAttendanceCommand();
            String expectedMessage = ResetAttendanceCommand.MESSAGE_SUCCESS;

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.resetAttendance();

            assertCommandSuccess(resetAttendanceCommand, model, expectedMessage, expectedModel);
        } else {
            assertTrue(model.getFilteredPersonList().isEmpty(),
                    "Expected non-empty list of students for this test.");
        }
    }

    @Test
    public void execute_noStudentsInList_throwsCommandException() {
        model.updateFilteredPersonList(person -> false);

        ResetAttendanceCommand resetAttendanceCommand = new ResetAttendanceCommand();

        assertCommandFailure(resetAttendanceCommand, model, Messages.MESSAGE_NO_STUDENTS);
    }

    @Test
    public void equals() {
        ResetAttendanceCommand resetAttendanceCommand1 = new ResetAttendanceCommand();
        ResetAttendanceCommand resetAttendanceCommand2 = new ResetAttendanceCommand();

        // same object -> returns true
        assertTrue(resetAttendanceCommand1.equals(resetAttendanceCommand1));

        // same values -> returns true
        assertTrue(resetAttendanceCommand1.equals(resetAttendanceCommand2));

        // different types -> returns false
        assertFalse(resetAttendanceCommand1.equals(1));

        // null -> returns false
        assertFalse(resetAttendanceCommand1.equals(null));
    }

    @Test
    public void toStringMethod() {
        ResetAttendanceCommand resetAttendanceCommand = new ResetAttendanceCommand();
        String expected = ResetAttendanceCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, resetAttendanceCommand.toString());
    }
}

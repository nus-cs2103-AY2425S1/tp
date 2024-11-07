package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
 * Contains integration tests (interaction with the Model) for
 * {@code MarkAttendanceCommand}.
 */
public class MarkAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markAttendanceNonEmptyList_success() {
        List<Person> studentsInList = model.getFilteredPersonList().stream()
                .filter(person -> person instanceof Student)
                .collect(Collectors.toList());

        if (!studentsInList.isEmpty()) {
            MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand();

            String expectedMessage = MarkAttendanceCommand.MESSAGE_SUCCESS;

            Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.markAttendance();

            assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
        } else {
            assertTrue(model.getFilteredPersonList().isEmpty(),
                    "Expected non-empty list of students for this test");
        }
    }

    @Test
    public void execute_noStudentsInList_throwsCommandException() {
        model.updateFilteredPersonList(person -> false);

        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand();

        assertCommandFailure(markAttendanceCommand, model, Messages.MESSAGE_NO_STUDENTS);
    }

    @Test
    public void equals() {
        MarkAttendanceCommand markAttendanceCommand1 = new MarkAttendanceCommand();
        MarkAttendanceCommand markAttendanceCommand2 = new MarkAttendanceCommand();

        // same object -> returns true
        assertTrue(markAttendanceCommand1.equals(markAttendanceCommand1));

        // same values -> returns true
        assertTrue(markAttendanceCommand1.equals(markAttendanceCommand2));

        // different types -> returns false
        assertTrue(!markAttendanceCommand1.equals(INDEX_FIRST_PERSON));

        // null -> returns false
        assertTrue(!markAttendanceCommand1.equals(null));
    }
}

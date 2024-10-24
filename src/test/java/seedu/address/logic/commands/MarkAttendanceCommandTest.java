package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkAttendanceCommand}.
 */
public class MarkAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markAttendance_success() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand();

        String expectedMessage = MarkAttendanceCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.markAttendance();

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
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
        assertFalse(markAttendanceCommand1.equals(1));

        // null -> returns false
        assertFalse(markAttendanceCommand1.equals(null));
    }

    @Test
    public void toStringMethod() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand();
        String expected = MarkAttendanceCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, markAttendanceCommand.toString());
    }
}

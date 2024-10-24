package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkAttendanceByTutorialCommand}.
 */
public class MarkAttendanceByTutorialCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        MarkAttendanceByTutorialCommand markAttendanceFirstCommand = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"), "12/12/2024");
        MarkAttendanceByTutorialCommand markAttendanceSecondCommand = new MarkAttendanceByTutorialCommand(
                new Tutorial("Chemistry"), "13/12/2024");

        // same object -> returns true
        assertTrue(markAttendanceFirstCommand.equals(markAttendanceFirstCommand));

        // same values -> returns true
        MarkAttendanceByTutorialCommand markAttendanceFirstCommandCopy = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"), "12/12/2024");
        assertTrue(markAttendanceFirstCommand.equals(markAttendanceFirstCommandCopy));

        // different types -> returns false
        assertFalse(markAttendanceFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markAttendanceFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(markAttendanceFirstCommand.equals(markAttendanceSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkAttendanceByTutorialCommand markAttendanceCommand = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"), "12/12/2024");
        String expected = MarkAttendanceByTutorialCommand.class.getCanonicalName() + "{targetIndex="
                + targetIndex + "}";
        assertEquals(expected, markAttendanceCommand.toString());
    }
}


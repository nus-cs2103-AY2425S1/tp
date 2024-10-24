package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attendance;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkAttendanceByStudentCommand}.
 */
public class MarkAttendanceByStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceByStudentCommand markAttendanceCommand = new MarkAttendanceByStudentCommand(
                outOfBoundIndex, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                "Math");

        assertCommandFailure(markAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkAttendanceByStudentCommand markAttendanceCommand = new MarkAttendanceByStudentCommand(
                outOfBoundIndex, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                "Math");

        assertCommandFailure(markAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkAttendanceByStudentCommand markAttendanceFirstCommand = new MarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                "Math");
        MarkAttendanceByStudentCommand markAttendanceSecondCommand = new MarkAttendanceByStudentCommand(
                INDEX_SECOND_PERSON, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                "Chemistry");

        // same object -> returns true
        assertTrue(markAttendanceFirstCommand.equals(markAttendanceFirstCommand));

        // same values -> returns true
        MarkAttendanceByStudentCommand markAttendanceFirstCommandCopy = new MarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                "Math");
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
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        MarkAttendanceByStudentCommand markAttendanceCommand = new MarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, attendance, "Math");
        String expected = MarkAttendanceByStudentCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", "
                + "attendance=" + attendance + ", "
                + "tutorial=Math}";
        assertEquals(expected, markAttendanceCommand.toString());
    }
}


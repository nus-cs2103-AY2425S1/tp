package seedu.address.logic.commands;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains tests for {@code UnmarkAttendanceByStudentCommand}.
 */
public class UnmarkAttendanceByStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_validInputs_createsCommand() {
        UnmarkAttendanceByStudentCommand unmarkAttendanceByStudentCommand = new UnmarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON,
                new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                new Tutorial("Mathematics"));
        assertNotNull(unmarkAttendanceByStudentCommand);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkAttendanceByStudentCommand unmarkAttendanceCommand = new UnmarkAttendanceByStudentCommand(
                outOfBoundsIndex,
                new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                new Tutorial("Mathematics"));

        assertCommandFailure(unmarkAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnmarkAttendanceByStudentCommand unmarkAttendanceCommand = new UnmarkAttendanceByStudentCommand(
                outOfBoundsIndex, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                new Tutorial("Mathematics"));
        assertCommandFailure(unmarkAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkAttendanceByStudentCommand unmarkAttendanceFirstCommand = new UnmarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                new Tutorial("Mathematics"));

        UnmarkAttendanceByStudentCommand unmarkAttendanceSecondCommand = new UnmarkAttendanceByStudentCommand(
                INDEX_SECOND_PERSON, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                new Tutorial("English"));

        // same object -> returns true
        assertTrue(unmarkAttendanceFirstCommand.equals(unmarkAttendanceFirstCommand));

        // same values -> returns true
        UnmarkAttendanceByStudentCommand unmarkAttendanceFirstCommandCopy = new UnmarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)),
                new Tutorial("Mathematics"));
        assertTrue(unmarkAttendanceFirstCommand.equals(unmarkAttendanceFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkAttendanceFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkAttendanceFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unmarkAttendanceFirstCommand.equals(unmarkAttendanceSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));
        Tutorial tutorial = new Tutorial("Mathematics");

        UnmarkAttendanceByStudentCommand unmarkAttendanceByStudentCommand = new UnmarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, attendance, tutorial);


        String expected = UnmarkAttendanceByStudentCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", "
                + "attendance=" + attendance + ", "
                + "tutorial=" + tutorial + "}";

        assertEquals(expected, unmarkAttendanceByStudentCommand.toString());
    }
}


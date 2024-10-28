package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attendance;
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
                new Tutorial("Math"),
                new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)));
        MarkAttendanceByTutorialCommand markAttendanceSecondCommand = new MarkAttendanceByTutorialCommand(
                new Tutorial("Chemistry"),
                new Attendance(LocalDate.parse("13/12/2024", Attendance.VALID_DATE_FORMAT)));

        // same object -> returns true
        assertTrue(markAttendanceFirstCommand.equals(markAttendanceFirstCommand));

        // same values -> returns true
        MarkAttendanceByTutorialCommand markAttendanceFirstCommandCopy = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"),
                new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)));
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
        Tutorial tutorial = new Tutorial("Math");
        Attendance attendance = new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT));

        MarkAttendanceByTutorialCommand markAttendanceCommand = new MarkAttendanceByTutorialCommand(
                tutorial, attendance);

        String expected = MarkAttendanceByTutorialCommand.class.getCanonicalName()
                + "{tutorial=" + tutorial + ", "
                + "attendance=" + attendance + "}";

        assertEquals(expected, markAttendanceCommand.toString());
    }
}


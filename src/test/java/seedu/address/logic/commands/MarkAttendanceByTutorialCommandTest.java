package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkAttendanceByTutorialCommand}.
 */
public class MarkAttendanceByTutorialCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTutorial_success() {
        Tutorial tutorial = new Tutorial("Math");
        Attendance attendance = new Attendance(LocalDate.parse("10/10/2024", Attendance.VALID_DATE_FORMAT));

        List<Participation> participationList = model.getParticipationList()
                .stream()
                .filter(participation -> participation.getTutorial().equals(tutorial))
                .toList();

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        for (Participation currentParticipation : participationList) {
            List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
            LocalDate attendanceDate = LocalDate.parse(attendance.toString(), Attendance.VALID_DATE_FORMAT);
            updatedAttendance.add(new Attendance(attendanceDate));

            Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                    currentParticipation.getTutorial(), updatedAttendance);

            expectedModel.setParticipation(currentParticipation, updatedParticipation);
        }

        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorial, attendance);

        String expectedMessage = String.format(MarkAttendanceByTutorialCommand.MESSAGE_MARK_ATTENDANCE_TUTORIAL_SUCCESS,
                        tutorial.getSubject(), attendance);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_NoSuchTutorial_throwsCommandException() {
        Tutorial tutorialToMark = new Tutorial("CS2103T");
        Attendance attendance = new Attendance(LocalDate.parse("10/10/2024", Attendance.VALID_DATE_FORMAT));

        assertFalse(model.getTutorialList().stream().anyMatch(tutorial -> tutorial.equals(tutorialToMark)));

        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorialToMark, attendance);

        assertCommandFailure(markAttendanceCommand, model,
                String.format(Messages.MESSAGE_TUTORIAL_NOT_FOUND, tutorialToMark.getSubject()));

    }

    @Test
    public void execute_NoStudentsTakingTutorial_throwsCommandException() {
        Tutorial tutorialToMark = new Tutorial("Science");
        Attendance attendance = new Attendance(LocalDate.parse("10/10/2024", Attendance.VALID_DATE_FORMAT));

        assertTrue(model.getTutorialList().stream().anyMatch(tutorial -> tutorial.equals(tutorialToMark)));

        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorialToMark, attendance);

        assertCommandFailure(markAttendanceCommand, model,
                String.format(MarkAttendanceByTutorialCommand.MESSAGE_EMPTY_TUTORIAL, tutorialToMark.getSubject()));

    }

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


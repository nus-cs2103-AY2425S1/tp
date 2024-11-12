package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_MATH;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BENSON_MATH;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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

    @BeforeEach
    public void setUp() {
        model.addParticipation(BENSON_MATH);
    }

    @Test
    public void execute_noStudentsWithDuplicateWeeklyAttendance_success() {
        Tutorial tutorial = new Tutorial("Math");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 17));

        List<Participation> participationList = model.getParticipationList()
                .filtered(participation -> participation.getTutorial().equals(tutorial));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        for (Participation currentParticipation : participationList) {
            List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
            updatedAttendance.add(new Attendance(LocalDate.of(2024, 10, 17)));

            Participation updatedParticipation = new Participation(currentParticipation.getStudent(),
                    currentParticipation.getTutorial(), updatedAttendance);

            expectedModel.setParticipation(currentParticipation, updatedParticipation);
        }

        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorial, attendance);

        String expectedMessage = String.format(MarkAttendanceByTutorialCommand.MESSAGE_MARK_TUTORIAL_ATTENDANCE_SUCCESS,
                        tutorial.getSubject(), attendance);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someStudentsWithDuplicateWeeklyAttendance_success() {
        // add new attendance for Benson on 17/10/2024
        Attendance attendanceBenson = new Attendance(LocalDate.of(2024, 10, 17));

        List<Attendance> updatedAttendanceBenson = new ArrayList<>(BENSON_MATH.getAttendanceList());
        updatedAttendanceBenson.add(attendanceBenson);

        Participation updatedParticipationBenson = new Participation(BENSON_MATH.getStudent(),
                BENSON_MATH.getTutorial(), updatedAttendanceBenson);
        model.setParticipation(BENSON_MATH, updatedParticipationBenson);

        Tutorial tutorial = new Tutorial("Math");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 17));

        // add updated participation for Alice
        List<Attendance> updatedAttendanceAlice = new ArrayList<>(ALICE_MATH.getAttendanceList());
        updatedAttendanceAlice.add(new Attendance(LocalDate.of(2024, 10, 17)));

        Participation updatedParticipationAlice = new Participation(ALICE_MATH.getStudent(),
                ALICE_MATH.getTutorial(), updatedAttendanceAlice);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setParticipation(ALICE_MATH, updatedParticipationAlice);

        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorial, attendance);

        String expectedMessage = String.format(
                MarkAttendanceByTutorialCommand.MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE_SOME_STUDENTS,
                tutorial.getSubject(), attendance, ALICE.getFullName() + " ", BENSON.getFullName() + " ");

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allStudentsWithDuplicateWeeklyAttendance_throwsCommandException() {
        Tutorial tutorial = new Tutorial("Math");
        // new attendance with date in same week and year with as current attendance with date 10/10/2024
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 11));
        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorial, attendance);

        String expectedMessage = String.format(
                MarkAttendanceByTutorialCommand.MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE_ALL_STUDENTS,
                tutorial.getSubject(), attendance);

        assertCommandFailure(markAttendanceCommand, model, expectedMessage);
    }

    @Test
    public void execute_noSuchTutorial_throwsCommandException() {
        Tutorial tutorialToMark = new Tutorial("CS2103T");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));

        assertFalse(model.getTutorialList().stream().anyMatch(tutorial -> tutorial.equals(tutorialToMark)));

        MarkAttendanceByTutorialCommand markAttendanceCommand =
                new MarkAttendanceByTutorialCommand(tutorialToMark, attendance);

        assertCommandFailure(markAttendanceCommand, model,
                String.format(Messages.MESSAGE_TUTORIAL_NOT_FOUND, tutorialToMark.getSubject()));
    }

    @Test
    public void execute_noStudentsTakingTutorial_throwsCommandException() {
        Tutorial tutorialToMark = new Tutorial("Science");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));

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
                new Attendance(LocalDate.of(2024, 10, 10)));
        MarkAttendanceByTutorialCommand markAttendanceSecondCommand = new MarkAttendanceByTutorialCommand(
                new Tutorial("Chemistry"),
                new Attendance(LocalDate.of(2024, 10, 10)));

        // same object -> returns true
        assertTrue(markAttendanceFirstCommand.equals(markAttendanceFirstCommand));

        // same values -> returns true
        MarkAttendanceByTutorialCommand markAttendanceFirstCommandCopy = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"),
                new Attendance(LocalDate.of(2024, 10, 10)));
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
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));

        MarkAttendanceByTutorialCommand markAttendanceCommand = new MarkAttendanceByTutorialCommand(
                tutorial, attendance);

        String expected = MarkAttendanceByTutorialCommand.class.getCanonicalName()
                + "{tutorial=" + tutorial + ", "
                + "attendance=" + attendance + "}";

        assertEquals(expected, markAttendanceCommand.toString());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        MarkAttendanceByTutorialCommand command = new MarkAttendanceByTutorialCommand(
                new Tutorial("Math"),
                new Attendance(LocalDate.of(2024, 10, 10)));

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void constructor_nullArgs_throwsNullPointerException() {
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));
        Tutorial tutorial = new Tutorial("Math");

        // null tutorial
        assertThrows(NullPointerException.class, () -> new MarkAttendanceByTutorialCommand(
                null, attendance));

        // null attendance
        assertThrows(NullPointerException.class, () -> new MarkAttendanceByTutorialCommand(
                tutorial, null));
    }
}


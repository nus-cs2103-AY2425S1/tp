package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.MarkAttendanceByStudentCommand.MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE;
import static seedu.address.logic.commands.MarkAttendanceByStudentCommand.MESSAGE_INVALID_TUTORIAL_FOR_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON_MATH;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.ParticipationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkAttendanceByStudentCommand}.
 */
public class MarkAttendanceByStudentCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model.addParticipation(BENSON_MATH);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Tutorial tutorial = new Tutorial("Math");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 17));

        // current participation of second person
        Participation currentParticipation = BENSON_MATH;

        Participation updatedParticipation = createUpdatedParticipation(currentParticipation, attendance);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setParticipation(currentParticipation, updatedParticipation);

        MarkAttendanceByStudentCommand markAttendanceCommand =
                new MarkAttendanceByStudentCommand(INDEX_SECOND_PERSON, attendance, tutorial);

        String expectedMessage = String.format(MarkAttendanceByStudentCommand.MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToMarkAttendance.getName(), tutorial.getSubject(), attendance);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markPreviousWeekUnfilteredList_success() {
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Tutorial tutorial = new Tutorial("Math");

        // current participation of second person with attendance for 12/12/2024
        Participation currentParticipation = BENSON_MATH;

        // create participation to mark attendance for sun of previous week of 10/10/2024
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 6));

        Participation updatedParticipation = createUpdatedParticipation(currentParticipation, attendance);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setParticipation(currentParticipation, updatedParticipation);

        MarkAttendanceByStudentCommand markAttendanceCommand1 =
                new MarkAttendanceByStudentCommand(INDEX_SECOND_PERSON, attendance, tutorial);

        String expectedMessage = String.format(MarkAttendanceByStudentCommand.MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToMarkAttendance.getName(), tutorial.getSubject(), attendance);

        assertCommandSuccess(markAttendanceCommand1, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markNextWeekUnfilteredList_success() {
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Tutorial tutorial = new Tutorial("Math");

        // current participation of second person with attendance for 10/10/2024
        Participation currentParticipation = BENSON_MATH;

        // create participation to mark attendance for mon of next week of 10/10/2024
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 14));

        Participation updatedParticipation = createUpdatedParticipation(currentParticipation, attendance);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setParticipation(currentParticipation, updatedParticipation);

        MarkAttendanceByStudentCommand markAttendanceCommand =
                new MarkAttendanceByStudentCommand(INDEX_SECOND_PERSON, attendance, tutorial);

        String expectedMessage = String.format(MarkAttendanceByStudentCommand.MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToMarkAttendance.getName(), tutorial.getSubject(), attendance);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceByStudentCommand markAttendanceCommand = new MarkAttendanceByStudentCommand(
                outOfBoundIndex,
                new Attendance(LocalDate.of(2024, 10, 14)),
                new Tutorial("Math"));

        assertCommandFailure(markAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notEnrolledInTutorialUnfilteredList_throwsCommandException() {
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        Tutorial tutorial = new Tutorial("Science");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 14));

        MarkAttendanceByStudentCommand markAttendanceCommand =
                new MarkAttendanceByStudentCommand(INDEX_SECOND_PERSON, attendance, tutorial);

        String expectedMessage = String.format(MESSAGE_INVALID_TUTORIAL_FOR_STUDENT,
                studentToMarkAttendance.getFullName(), tutorial.getSubject());

        assertCommandFailure(markAttendanceCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateWeeklyAttendanceUnfilteredList_throwsCommandException() {
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        Tutorial tutorial = new Tutorial("Math");

        // mon of week for 10/10/2024
        Attendance attendance1 = new Attendance(LocalDate.of(2024, 10, 7));
        // sun of week for 10/10/2024
        Attendance attendance2 = new Attendance(LocalDate.of(2024, 10, 13));

        MarkAttendanceByStudentCommand markAttendanceCommand1 =
                new MarkAttendanceByStudentCommand(INDEX_SECOND_PERSON, attendance1, tutorial);
        MarkAttendanceByStudentCommand markAttendanceCommand2 =
                new MarkAttendanceByStudentCommand(INDEX_SECOND_PERSON, attendance2, tutorial);

        String expectedMessage1 = String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE,
                studentToMarkAttendance.getName(), attendance1, tutorial.getSubject());
        String expectedMessage2 = String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE,
                studentToMarkAttendance.getName(), attendance2, tutorial.getSubject());

        assertCommandFailure(markAttendanceCommand1, model, expectedMessage1);
        assertCommandFailure(markAttendanceCommand2, model, expectedMessage2);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tutorial tutorial = new Tutorial("Math");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 17));

        Participation currentParticipation = BENSON_MATH;
        Participation updatedParticipation = createUpdatedParticipation(currentParticipation, attendance);

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_SECOND_PERSON);
        expectedModel.setParticipation(currentParticipation, updatedParticipation);

        MarkAttendanceByStudentCommand markAttendanceCommand =
                new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance, tutorial);

        String expectedMessage = String.format(MarkAttendanceByStudentCommand.MESSAGE_MARK_ATTENDANCE_STUDENT_SUCCESS,
                studentToMarkAttendance.getName(), tutorial.getSubject(), attendance);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkAttendanceByStudentCommand markAttendanceCommand = new MarkAttendanceByStudentCommand(
                outOfBoundIndex, new Attendance(LocalDate.of(2024, 10, 10)),
                new Tutorial("Math"));

        assertCommandFailure(markAttendanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notEnrolledInTutorialFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Tutorial tutorial = new Tutorial("Science");
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));

        MarkAttendanceByStudentCommand markAttendanceCommand =
                new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance, tutorial);

        String expectedMessage = String.format(MESSAGE_INVALID_TUTORIAL_FOR_STUDENT,
                studentToMarkAttendance.getFullName(), tutorial.getSubject());

        assertCommandFailure(markAttendanceCommand, model, expectedMessage);
    }

    @Test
    public void execute_duplicateWeeklyAttendanceFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person studentToMarkAttendance = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Tutorial tutorial = new Tutorial("Math");

        // mon of week for 10/10/2024
        Attendance attendance1 = new Attendance(LocalDate.of(2024, 10, 7));
        // sun of week for 10/10/2024
        Attendance attendance2 = new Attendance(LocalDate.of(2024, 10, 13));

        MarkAttendanceByStudentCommand markAttendanceCommand1 =
                new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance1, tutorial);
        MarkAttendanceByStudentCommand markAttendanceCommand2 =
                new MarkAttendanceByStudentCommand(INDEX_FIRST_PERSON, attendance2, tutorial);

        String expectedMessage1 = String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE,
                studentToMarkAttendance.getName(), attendance1, tutorial.getSubject());
        String expectedMessage2 = String.format(MESSAGE_DUPLICATE_WEEKLY_ATTENDANCE,
                studentToMarkAttendance.getName(), attendance2, tutorial.getSubject());

        assertCommandFailure(markAttendanceCommand1, model, expectedMessage1);
        assertCommandFailure(markAttendanceCommand2, model, expectedMessage2);
    }

    @Test
    public void equals() {
        MarkAttendanceByStudentCommand markAttendanceFirstCommand = new MarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, new Attendance(LocalDate.of(2024, 10, 10)),
                new Tutorial("Math"));
        MarkAttendanceByStudentCommand markAttendanceSecondCommand = new MarkAttendanceByStudentCommand(
                INDEX_SECOND_PERSON, new Attendance(LocalDate.of(2024, 10, 10)),
                new Tutorial("Chemistry"));

        // same object -> returns true
        assertTrue(markAttendanceFirstCommand.equals(markAttendanceFirstCommand));

        // same values -> returns true
        MarkAttendanceByStudentCommand markAttendanceFirstCommandCopy = new MarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, new Attendance(LocalDate.of(2024, 10, 10)),
                new Tutorial("Math"));
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
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));
        Tutorial tutorial = new Tutorial("Math");

        MarkAttendanceByStudentCommand markAttendanceCommand = new MarkAttendanceByStudentCommand(
                INDEX_FIRST_PERSON, attendance, tutorial);

        String expected = MarkAttendanceByStudentCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + ", "
                + "attendance=" + attendance + ", "
                + "tutorial=" + tutorial + "}";

        assertEquals(expected, markAttendanceCommand.toString());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        MarkAttendanceByStudentCommand command = new MarkAttendanceByStudentCommand(
                Index.fromZeroBased(0),
                new Attendance(LocalDate.of(2024, 10, 10)),
                new Tutorial("Math"));

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void constructor_nullArgs_throwsNullPointerException() {
        Attendance attendance = new Attendance(LocalDate.of(2024, 10, 10));
        Tutorial tutorial = new Tutorial("Math");

        // null index
        assertThrows(NullPointerException.class, () -> new MarkAttendanceByStudentCommand(
                null, attendance, tutorial));

        // null attendance
        assertThrows(NullPointerException.class, () -> new MarkAttendanceByStudentCommand(
                Index.fromZeroBased(0), null, tutorial));

        // null tutorial
        assertThrows(NullPointerException.class, () -> new MarkAttendanceByStudentCommand(
                Index.fromZeroBased(0), attendance, null));
    }

    private Participation createUpdatedParticipation(Participation currentParticipation, Attendance attendance) {

        List<Attendance> updatedAttendance = new ArrayList<>(currentParticipation.getAttendanceList());
        updatedAttendance.add(new Attendance(attendance.attendanceDate));

        return new ParticipationBuilder(currentParticipation).withAttendanceList(updatedAttendance).build();
    }
}


package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code MarkPresentAllCommand}.
 */
public class MarkPresentAllCommandTest {

    private final TutorialGroup validTutorialGroup = new TutorialGroup("A01");
    private final TutorialGroup emptyTutorialGroup = new TutorialGroup("B01");

    private final LocalDate validDate = LocalDate.of(2024, 10, 23);

    private final Student student1 = new StudentBuilder().withName("John Doe")
            .withStudentNumber("A1234567M")
            .withTutorialGroup("A01").build();
    private final Student student2 = new StudentBuilder().withName("Jane Doe")
            .withStudentNumber("A7654321M")
            .withTutorialGroup("A01").build();

    @Test
    public void equals() {
        TutorialGroup tutorialGroup1 = new TutorialGroup("A01");
        TutorialGroup tutorialGroup2 = new TutorialGroup("A02");

        LocalDate date1 = LocalDate.of(2024, 10, 23);
        LocalDate date2 = LocalDate.of(2024, 10, 24);

        MarkPresentAllCommand standardMarkPresentAllCommand = new MarkPresentAllCommand(tutorialGroup1, date1);

        // same object -> returns true
        assertTrue(standardMarkPresentAllCommand.equals(standardMarkPresentAllCommand));

        // same values -> returns true
        MarkPresentAllCommand standardMarkPresentAllCommandCopy = new MarkPresentAllCommand(tutorialGroup1, date1);
        assertTrue(standardMarkPresentAllCommand.equals(standardMarkPresentAllCommandCopy));

        // different types -> returns false
        assertFalse(standardMarkPresentAllCommand.equals(1));

        // null -> returns false
        assertFalse(standardMarkPresentAllCommand.equals(null));

        // different tutorial group -> returns false
        assertFalse(standardMarkPresentAllCommand.equals(new MarkPresentAllCommand(tutorialGroup2, date1)));

        // different date -> returns false
        assertFalse(standardMarkPresentAllCommand.equals(new MarkPresentAllCommand(tutorialGroup1, date2)));
    }

    @Test
    void execute_validTutorialGroup_success() {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);
        MarkPresentAllCommand command = new MarkPresentAllCommand(validTutorialGroup, validDate);

        String expectedMessage = String.format(MarkPresentAllCommand.MESSAGE_SUCCESS, validTutorialGroup,
                DateTimeFormatter.ofPattern("MMM d yyyy").format(validDate));
        Student markedStudent1 = new StudentBuilder().withName("John Doe")
                .withStudentNumber("A1234567M")
                .withTutorialGroup("A01").build();
        markedStudent1.markAttendance(validDate, "p");
        Student markedStudent2 = new StudentBuilder().withName("Jane Doe")
                .withStudentNumber("A7654321M")
                .withTutorialGroup("A01").build();
        markedStudent2.markAttendance(validDate, "p");
        Model expectedModel = new ModelManager();
        expectedModel.addStudent(markedStudent1);
        expectedModel.addStudent(markedStudent2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_emptyTutorialGroup_throwsCommandException() {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);
        MarkPresentAllCommand command = new MarkPresentAllCommand(emptyTutorialGroup, validDate);

        String expectedMessage = MarkPresentAllCommand.MESSAGE_EMPTY_TG;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        TutorialGroup tutorialGroup = new TutorialGroup("A01");
        LocalDate date = LocalDate.of(2024, 10, 23);
        MarkPresentAllCommand markPresentAllCommand = new MarkPresentAllCommand(tutorialGroup, date);
        String expectedString = MarkPresentAllCommand.class.getCanonicalName() + "{tutorialGroup=" + tutorialGroup
                + ", date=" + date + "}";
        assertEquals(expectedString, markPresentAllCommand.toString());
    }

    @Test
    public void execute_undoMarkPresentAllCommand_success() throws Exception {
        Model model = new ModelManager();
        student1.undoAttendance(LocalDate.of(2020, 1, 1));
        student2.undoAttendance(LocalDate.of(2020, 1, 1));
        model.addStudent(student1);
        model.addStudent(student2);
        MarkPresentAllCommand markCommand = new MarkPresentAllCommand(validTutorialGroup, validDate);
        markCommand.execute(model);

        CommandStack commandStack = CommandStack.getInstance();
        commandStack.push(markCommand);

        UndoCommand undoCommand = new UndoCommand();
        CommandResult result = undoCommand.execute(model);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(model.getStudentByName(student1.getName()).getAttendanceRecord().isEmpty());
        assertTrue(model.getStudentByName(student2.getName()).getAttendanceRecord().isEmpty());
    }

    @Test
    public void execute_undoMarkPresentAllCommandTwoAttendance_success() throws Exception {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);
        MarkPresentAllCommand markCommand = new MarkPresentAllCommand(validTutorialGroup, validDate);
        UnmarkPresentAllCommand unmarkCommand = new UnmarkPresentAllCommand(validTutorialGroup, validDate);
        Attendance attendance = new Attendance("a");
        unmarkCommand.execute(model);
        markCommand.execute(model);
        markCommand.undo(model);

        AttendanceRecord ar = student1.getAttendanceRecord().get(1);
        assertTrue(ar.getAttendance().equals(attendance));
        AttendanceRecord ar2 = student2.getAttendanceRecord().get(1);
        assertTrue(ar2.getAttendance().equals(attendance));
    }


    @Test
    public void execute_noCommandToUndo_throwsCommandException() {
        Model model = new ModelManager();
        UndoCommand undoCommand = new UndoCommand();
        CommandResult result = undoCommand.execute(model);

        assertEquals("There are no commands to undo", result.getFeedbackToUser());
    }
}

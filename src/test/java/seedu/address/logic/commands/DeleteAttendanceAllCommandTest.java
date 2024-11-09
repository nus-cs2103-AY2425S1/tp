package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;
public class DeleteAttendanceAllCommandTest {
    private final TutorialGroup validTutorialGroup = new TutorialGroup("G04");
    private final TutorialGroup emptyTutorialGroup = new TutorialGroup("G05");
    private final LocalDate validDate = LocalDate.of(2023, 10, 9);

    private final Student student1 = new StudentBuilder().withName("John Doe")
            .withStudentNumber("A0191222D")
            .withTutorialGroup("G04")
            .withAttendanceRecord(validDate, "p")
            .build();

    private final Student student2 = new StudentBuilder().withName("Jane Doe")
            .withStudentNumber("A0191222E")
            .withTutorialGroup("G04")
            .withAttendanceRecord(validDate, "p")
            .build();



    @Test
    void execute_validTutorialGroup_success() throws CommandException {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);

        DeleteAttendanceAllCommand command = new DeleteAttendanceAllCommand(validTutorialGroup, validDate);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(DeleteAttendanceAllCommand.MESSAGE_SUCCESS, validTutorialGroup, validDate);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(student1.getAttendanceRecord().isEmpty());
        assertTrue(student2.getAttendanceRecord().isEmpty());
    }

    @Test
    void execute_emptyTutorialGroup_throwsCommandException() {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);

        DeleteAttendanceAllCommand command = new DeleteAttendanceAllCommand(emptyTutorialGroup, validDate);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void undo_validTutorialGroupAndDate_success() throws Exception {
        Model model = new ModelManager();
        model.addStudent(student1);
        model.addStudent(student2);

        DeleteAttendanceAllCommand command = new DeleteAttendanceAllCommand(validTutorialGroup, validDate);
        command.execute(model);

        CommandStack commandStack = CommandStack.getInstance();
        commandStack.push(command);

        UndoCommand undoCommand = new UndoCommand();
        CommandResult result = undoCommand.execute(model);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(student1.getAttendanceRecord().stream()
                .anyMatch(record -> record.getDate().equals(validDate) && record.getAttendance().value.equals("p")));
        assertTrue(student2.getAttendanceRecord().stream()
                .anyMatch(record -> record.getDate().equals(validDate) && record.getAttendance().value.equals("p")));

    }

    @Test
    void equalsMethod() {
        TutorialGroup tg1 = new TutorialGroup("G04");
        TutorialGroup tg2 = new TutorialGroup("G05");
        LocalDate date1 = LocalDate.of(2023, 10, 9);
        LocalDate date2 = LocalDate.of(2023, 10, 10);

        DeleteAttendanceAllCommand command1 = new DeleteAttendanceAllCommand(tg1, date1);
        DeleteAttendanceAllCommand command2 = new DeleteAttendanceAllCommand(tg1, date1);
        DeleteAttendanceAllCommand command3 = new DeleteAttendanceAllCommand(tg2, date1);
        DeleteAttendanceAllCommand command4 = new DeleteAttendanceAllCommand(tg1, date2);

        assertEquals(command1, command1);
        assertEquals(command1, command2);
        assertNotEquals(command1, command3);
        assertNotEquals(command1, command4);
        assertNotEquals(command1, new Object());
        assertNotEquals(command1, null);
    }

    @Test
    void toStringMethod() {
        TutorialGroup tg = new TutorialGroup("G04");
        LocalDate date = LocalDate.of(2023, 10, 9);
        DeleteAttendanceAllCommand command = new DeleteAttendanceAllCommand(tg, date);
        String expectedString = DeleteAttendanceAllCommand.class.getCanonicalName()
                + "{tutorialGroup=" + tg + ", date=" + date + "}";
        assertEquals(expectedString, command.toString());
    }
}

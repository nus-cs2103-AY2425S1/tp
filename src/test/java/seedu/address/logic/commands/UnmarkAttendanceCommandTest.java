package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TeacherBuilder;

public class UnmarkAttendanceCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_unmarkAttendance_success() throws CommandException {
        int initialDays = 5;
        Student testStudent = new StudentBuilder().withName("Test Student")
                .withDaysAttended(initialDays).build();
        model.addPerson(testStudent);

        Student updatedStudent = (Student) testStudent.withIncrementedAttendance();
        model.setPerson(testStudent, updatedStudent);

        assertEquals(initialDays + 1, updatedStudent.getDaysAttended().getValue(),
                "Attendance increment failed");

        Index studentIndex = Index.fromZeroBased(model.getFilteredPersonList().indexOf(updatedStudent));
        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(
                new Index[]{studentIndex});

        unmarkAttendanceCommand.executeCommand(model);
        Student unmarkedStudent = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());
        assertEquals(initialDays, unmarkedStudent.getDaysAttended().getValue(), "Attendance unmarking failed");
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(new Index[]{outOfBoundsIndex});

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + ": " + outOfBoundsIndex.getOneBased());
    }

    @Test
    public void execute_nonStudentPerson_throwsCommandException() {
        Teacher nonStudentPerson = new TeacherBuilder().withName(VALID_NAME_MICHAEL).build();
        model.addPerson(nonStudentPerson);
        Index indexNonStudent = Index.fromZeroBased(model.getFilteredPersonList().size() - 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(new Index[]{indexNonStudent});

        assertThrows(CommandException.class, () -> command.executeCommand(model),
                Messages.MESSAGE_INVALID_STUDENT_INDEX);
    }

    @Test
    public void execute_studentWithZeroDaysAttendance_throwsCommandException() {
        Student studentWithZeroDays = new StudentBuilder().withName("Student Zero Days").withDaysAttended(0).build();
        model.addPerson(studentWithZeroDays);
        Index indexZeroDays = Index.fromZeroBased(model.getFilteredPersonList().indexOf(studentWithZeroDays));
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(new Index[]{indexZeroDays});

        assertThrows(CommandException.class, () -> command.executeCommand(model),
                Messages.MESSAGE_INVALID_ATTENDANCE);
    }

    @Test
    public void equals() {
        UnmarkAttendanceCommand unmarkFirstCommand = new UnmarkAttendanceCommand(new Index[]{INDEX_FIRST_PERSON});
        UnmarkAttendanceCommand unmarkSecondCommand = new UnmarkAttendanceCommand(new Index[]{INDEX_SECOND_PERSON});
        UnmarkAttendanceCommand unmarkMultipleCommand = new UnmarkAttendanceCommand(
                new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON});

        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));
        assertTrue(unmarkFirstCommand.equals(
                new UnmarkAttendanceCommand(new Index[]{INDEX_FIRST_PERSON})));
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
        assertFalse(unmarkFirstCommand.equals(unmarkMultipleCommand));
        assertFalse(unmarkFirstCommand.equals(null));
        assertFalse(unmarkFirstCommand.equals("string"));
    }

    @Test
    public void toString_validIndices_correctStringRepresentation() {
        Index[] indices = {Index.fromOneBased(1), Index.fromOneBased(2)};
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(indices);
        String expectedString = UnmarkAttendanceCommand.class.getCanonicalName() + "{targetIndexArray="
                + Arrays.toString(indices) + "}";

        assertEquals(expectedString, command.toString(), "String representation mismatch");
    }
}

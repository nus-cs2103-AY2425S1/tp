package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICHAEL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

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

    private Model model = new ModelManager();

    @Test
    public void execute_validIndex_success() throws Exception {
        int initialDays = 5;
        Student testStudent = new StudentBuilder().withName("Test Student").withDaysAttended(initialDays).build();
        model.addPerson(testStudent);

        Index studentIndex = Index.fromZeroBased(model.getFilteredPersonList().indexOf(testStudent));
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(studentIndex);

        // Attendance should be 6 now
        model.markAttendance();

        String expectedMessage = UnmarkAttendanceCommand.MESSAGE_SUCCESS;
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(testStudent);

        // Attendance should return to 5 after unmark
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(initialDays, testStudent.getDaysAttended().getValue());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Arrange
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(outOfBoundsIndex);

        // Act & Assert
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonStudentPerson_throwsCommandException() {
        // Arrange: Create a non-student person and add to the model
        Teacher nonStudentPerson = new TeacherBuilder().withName(VALID_NAME_MICHAEL).build();
        model.addPerson(nonStudentPerson);
        Index indexNonStudent = Index.fromZeroBased(
                model.getFilteredPersonList().size() - 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(indexNonStudent);

        // Act & Assert
        assertThrows(CommandException.class, () -> command.executeCommand(model),
                Messages.MESSAGE_INVALID_STUDENT_INDEX);
    }

    @Test
    public void equals() {
        // Arrange
        UnmarkAttendanceCommand unmarkFirstCommand = new UnmarkAttendanceCommand(INDEX_FIRST_PERSON);
        UnmarkAttendanceCommand unmarkSecondCommand = new UnmarkAttendanceCommand(INDEX_SECOND_PERSON);

        // Act & Assert
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand)); // same object
        assertTrue(unmarkFirstCommand.equals(new UnmarkAttendanceCommand(INDEX_FIRST_PERSON))); // same values
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand)); // different index
        assertFalse(unmarkFirstCommand.equals(null)); // null comparison
        assertFalse(unmarkFirstCommand.equals("string")); // different types
    }

    @Test
    public void toString_validIndex_correctStringRepresentation() {
        Index index = Index.fromOneBased(1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(index);
        String expectedString = UnmarkAttendanceCommand.class.getCanonicalName() + "{targetIndex="
            + Index.class.getCanonicalName() + "{zeroBasedIndex=" + index.getZeroBased() + "}}";

        assertEquals(expectedString, command.toString());
    }
}

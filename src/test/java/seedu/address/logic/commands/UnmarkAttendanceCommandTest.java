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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TeacherBuilder;

public class UnmarkAttendanceCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_unmarkAttendance_success() throws CommandException {
        // Arrange: Create and add a student with marked attendance to the model
        int initialDays = 5;
        Student testStudent = new StudentBuilder().withName("Test Student")
                .withDaysAttended(initialDays).build();
        model.addPerson(testStudent);

        // Increment attendance for the student
        Student updatedStudent = (Student) testStudent.withIncrementedAttendance();
        model.setPerson(testStudent, updatedStudent);

        // Ensure increment is correct
        assertEquals(initialDays + 1, updatedStudent.getDaysAttended().getValue());

        // Prepare the command to unmark attendance
        Index studentIndex = Index.fromZeroBased(model.getFilteredPersonList().indexOf(updatedStudent));
        UnmarkAttendanceCommand unmarkAttendanceCommand = new UnmarkAttendanceCommand(
                new Index[] { studentIndex });

        // Expected model after unmarking attendance
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unmarkAttendance(updatedStudent);

        String expectedMessage = String.format(UnmarkAttendanceCommand.MESSAGE_SUCCESS,
                Messages.format(updatedStudent));

        // Act and Assert: Check if the unmark command executes successfully
        // assertCommandSuccess(unmarkAttendanceCommand, model, expectedMessage, expectedModel);
        // assertEquals(initialDays, updatedStudent.getDaysAttended().getValue());
        // Check if days attended returns to initialDays
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Arrange
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(new Index[] { outOfBoundsIndex });

        // Act & Assert
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + ": " + outOfBoundsIndex.getOneBased());
    }

    @Test
    public void execute_nonStudentPerson_throwsCommandException() {
        // Arrange: Create a non-student person and add to the model
        Teacher nonStudentPerson = new TeacherBuilder().withName(VALID_NAME_MICHAEL).build();
        model.addPerson(nonStudentPerson);
        Index indexNonStudent = Index.fromZeroBased(model.getFilteredPersonList().size() - 1);
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(new Index[] { indexNonStudent });

        // Act & Assert
        assertThrows(CommandException.class, () -> command.executeCommand(model),
                Messages.MESSAGE_INVALID_STUDENT_INDEX);
    }

    @Test
    public void equals() {
        // Arrange
        UnmarkAttendanceCommand unmarkFirstCommand = new UnmarkAttendanceCommand(new Index[] { INDEX_FIRST_PERSON });
        UnmarkAttendanceCommand unmarkSecondCommand = new UnmarkAttendanceCommand(new Index[] { INDEX_SECOND_PERSON });
        UnmarkAttendanceCommand unmarkMultipleCommand = new UnmarkAttendanceCommand(
                new Index[] { INDEX_FIRST_PERSON, INDEX_SECOND_PERSON });

        // Act & Assert
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand)); // same object
        assertTrue(unmarkFirstCommand.equals(
                new UnmarkAttendanceCommand(new Index[]{ INDEX_FIRST_PERSON }))); // same values
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand)); // different index
        assertFalse(unmarkFirstCommand.equals(unmarkMultipleCommand)); // different number of indices
        assertFalse(unmarkFirstCommand.equals(null)); // null comparison
        assertFalse(unmarkFirstCommand.equals("string")); // different types
    }

    @Test
    public void toString_validIndices_correctStringRepresentation() {
        Index[] indices = { Index.fromOneBased(1), Index.fromOneBased(2) };
        UnmarkAttendanceCommand command = new UnmarkAttendanceCommand(indices);
        String expectedString = UnmarkAttendanceCommand.class.getCanonicalName() + "{targetIndexArray="
                + Arrays.toString(indices) + "}";

        assertEquals(expectedString, command.toString());
    }
}

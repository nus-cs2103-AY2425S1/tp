package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.deletecommands.DeleteStudentCommand;
import seedu.address.logic.parser.deletecommands.DeleteStudentCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentNumber_success() {
        Student studentToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentNumber studentNumber = studentToDelete.getStudentNumber();
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(studentNumber);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS,
            Messages.format(studentToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(studentToDelete);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentNumber_throwsCommandException() {
        assertParseFailure(new DeleteStudentCommandParser(),
            "B1234567", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
    }


    @Test
    public void equals() {
        StudentNumber sno1 = new StudentNumber("A0123456B");
        StudentNumber sno2 = new StudentNumber("A0123456C");
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(sno1);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(sno2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(sno1);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentNumber targetNumber = new StudentNumber("A0123456B");
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(targetNumber);
        String expected = DeleteStudentCommand.class.getCanonicalName() + "{targetStudentNumber=" + targetNumber + "}";
        assertEquals(expected, deleteStudentCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}

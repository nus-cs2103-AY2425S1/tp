package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.academyassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_FIRST_PERSON;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    @Test
    public void execute_validStudentIdUnfilteredList_success() {
        Person personToDelete = model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(STUDENT_ID_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getStudentId());

        ModelManager expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistenceStudentIdUnfilteredList_throwsCommandException() {
        StudentId nonExistenceStudentId = new StudentId("S99999");
        DeleteCommand deleteCommand = new DeleteCommand(nonExistenceStudentId);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NO_STUDENT_FOUND);
    }

    @Test
    public void execute_validStudentIdFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getPersonWithStudentId(STUDENT_ID_FIRST_PERSON);

        DeleteCommand deleteCommand = new DeleteCommand(STUDENT_ID_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getStudentId());

        Model expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIdFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        StudentId nonExistenceStudentId = new StudentId("S99999");

        DeleteCommand deleteCommand = new DeleteCommand(nonExistenceStudentId);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NO_STUDENT_FOUND);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(STUDENT_ID_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(STUDENT_ID_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(STUDENT_ID_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId targetStudentId = new StudentId("S99999");;
        DeleteCommand deleteCommand = new DeleteCommand(targetStudentId);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetStudentId=" + targetStudentId + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}

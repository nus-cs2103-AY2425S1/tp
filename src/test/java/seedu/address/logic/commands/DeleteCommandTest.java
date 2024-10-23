package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentIdUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId studentIdToDelete = personToDelete.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(studentIdToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        System.out.println(deleteCommand + "\n" + expectedMessage + "\n");
        System.out.println(model + "\n" + expectedModel + "\n");

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIdUnfilteredList_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("12345679");
        DeleteCommand deleteCommand = new DeleteCommand(invalidStudentId);
        assertCommandFailure(
                deleteCommand, model, String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, invalidStudentId));
    }

    @Test
    public void execute_validStudentIdFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId studentIdToDelete = personToDelete.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(studentIdToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personDisplayedDeleted_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.setPersonToDisplay(personToDelete);
        StudentId studentIdToDelete = personToDelete.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(studentIdToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personDisplayedNotDeleted_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDisplay = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        model.setPersonToDisplay(personToDisplay);
        StudentId studentIdToDelete = personToDelete.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(studentIdToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), personToDisplay);
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        StudentId firstStudentId = firstPerson.getStudentId();
        StudentId secondStudentId = secondPerson.getStudentId();

        DeleteCommand deleteFirstCommand = new DeleteCommand(firstStudentId);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondStudentId);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstStudentId);
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
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId targetStudentId = firstPerson.getStudentId();

        DeleteCommand deleteCommand = new DeleteCommand(targetStudentId);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetStudentId=" + targetStudentId
                + ", module=" + null + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_invalidModuleToDelete_throwsCommandException() {
        Person personWithNoModule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId studentIdToDelete = personWithNoModule.getStudentId();
        Module nonExistentModule = new Module("CS9999");

        DeleteCommand deleteCommand = new DeleteCommand(studentIdToDelete, nonExistentModule);
        assertCommandFailure(
                deleteCommand, model, String.format(DeleteCommand.MESSAGE_MODULE_NOT_FOUND, studentIdToDelete));
    }

    @Test
    public void execute_deleteModule_whenNoModules() {
        Person personWithoutModules = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId studentIdToDelete = personWithoutModules.getStudentId();
        Module moduleToDelete = new Module("CS2103T");

        DeleteCommand deleteCommand = new DeleteCommand(studentIdToDelete, moduleToDelete);
        assertCommandFailure(
                deleteCommand, model, String.format(DeleteCommand.MESSAGE_MODULE_NOT_FOUND, studentIdToDelete));
    }

    @Test
    public void execute_invalidStudentIdWithModule_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("12345679");
        Module moduleToDelete = new Module("CS2103T");
        DeleteCommand deleteCommand = new DeleteCommand(invalidStudentId, moduleToDelete);

        assertCommandFailure(
                deleteCommand, model, String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, invalidStudentId));
    }

}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.task.Task;
import seedu.address.model.person.task.TaskDeadline;
import seedu.address.model.person.task.TaskDescription;
import seedu.address.model.person.task.TaskList;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.Ui.UiState;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {
    private static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);

    private Model model;
    private Task testTask = new Task(new TaskDescription("First Assignment"), new TaskDeadline("2024-10-16"));
    private Person targetPerson;
    @BeforeEach
    public void setUp() {
        // ensure the model has task to delete
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(model.getPersonByName(targetPerson.getName()))
                .build();
        TaskList updatedTaskList = updatedPerson.getTaskList();
        updatedTaskList.add(testTask);
        model.setPerson(targetPerson, updatedPerson);

        // reinitialise target person after updating the model
        targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    }
    @Test
    public void execute_validArgument_success() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetPerson.getName(), INDEX_FIRST_TASK);

        Task targetTask = targetPerson.getTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS,
                targetTask.getTaskDescription(), targetPerson.getName(), targetTask.getTaskDeadline());

        // Ensure initial and final state of the model is actually different
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Person updatedPerson = new PersonBuilder(expectedModel.getPersonByName(targetPerson.getName()))
                .build();
        TaskList updatedTaskList = updatedPerson.getTaskList();
        updatedTaskList.remove(targetTask);
        expectedModel.setPerson(targetPerson, updatedPerson);
        assertEquals(expectedModel, new ModelManager(getTypicalAddressBook(), new UserPrefs()));
        assertNotEquals(model, expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, UiState.DETAILS, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Person targetPerson = model.getFilteredPersonList().get(0);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetPerson.getName(), outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidName_throwsCommandException() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(new Name("UNKNOWN NAME"), INDEX_FIRST_TASK);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        Name person1 = new Name("Ada");
        Name person2 = new Name("Bob");
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        DeleteTaskCommand deleteFirstPersonFirstIndexCommand = new DeleteTaskCommand(person1, index1);
        DeleteTaskCommand deleteFirstPersonSecondIndexCommand = new DeleteTaskCommand(person1, index2);
        DeleteTaskCommand deleteSecondPersonFirstIndexCommand = new DeleteTaskCommand(person2, index1);

        //same object -> returns true
        assertTrue(deleteFirstPersonFirstIndexCommand.equals(deleteFirstPersonFirstIndexCommand));

        //same values -> returns true
        DeleteTaskCommand deleteFirstPersonFirstIndexCommandCopy = new DeleteTaskCommand(person1, index1);
        assertTrue(deleteFirstPersonFirstIndexCommand.equals(deleteFirstPersonFirstIndexCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstPersonFirstIndexCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstPersonFirstIndexCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstPersonFirstIndexCommand.equals(deleteSecondPersonFirstIndexCommand));

        // different index -> returns false
        assertFalse(deleteFirstPersonFirstIndexCommand.equals(deleteFirstPersonSecondIndexCommand));
    }

    @Test
    public void toStringMethod() {
        Name targetName = new Name("Bob");
        Index targetIndex = Index.fromOneBased(1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetName, targetIndex);
        String expected = DeleteTaskCommand.class.getCanonicalName()
                + "{targetName=" + targetName + ", "
                + "targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteTaskCommand.toString());
    }
}

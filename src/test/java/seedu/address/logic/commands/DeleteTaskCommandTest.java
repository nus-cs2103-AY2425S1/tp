package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

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
import seedu.address.testutil.PersonBuilder;

public class DeleteTaskCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private PersonBuilder testPersonBuilder = new PersonBuilder();
    private Task testFirstTask = new Task(new TaskDescription("First Assignment"), new TaskDeadline("2024-10-16"));
    private Task testSecondTask = new Task(new TaskDescription("Second Assignment"), new TaskDeadline("2024-10-19"));
    private Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    private Index INDEX_SECOND_TASK = Index.fromOneBased(2);

    @Test
    public void execute_validArgument_success() {
        //Have a test person with a task
        Person testPerson = testPersonBuilder.build();
        model.addPerson(testPerson);
        testPerson.getTaskList().add(testFirstTask);

        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(testPerson.getName(),INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS,
                testFirstTask.toString());

        // Test Person without the task for expected model
        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel.addPerson(testPersonBuilder.build());
        // Check that initially model is different
        assertNotEquals(model, expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {

    }


    @Test
    public void execute_invalidName_throwsCommandException() {

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
                + "{targetName=" + targetName + "}"
                + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteTaskCommand.toString());
    }
}

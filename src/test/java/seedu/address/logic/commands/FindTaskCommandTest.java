package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBookWithTasks;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FindTaskCommand}.
 */
public class FindTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithTasks(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookWithTasks(), new UserPrefs());

    @Test
    public void equals() {
        FindTaskCommand findFirstCommand = new FindTaskCommand(INDEX_FIRST_PERSON);
        FindTaskCommand findSecondCommand = new FindTaskCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTaskCommand findFirstCommandCopy = new FindTaskCommand(INDEX_FIRST_PERSON);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FindTaskCommand findTaskCommand = new FindTaskCommand(outOfBoundsIndex);

        assertCommandFailure(findTaskCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPersonIndex_noTasksFound() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        FindTaskCommand command = new FindTaskCommand(targetIndex);
        expectedModel.updateFilteredTaskList(task -> task.getPatient().equals(BOB));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_validPersonIndex_tasksFound() {
        // Assume tasks are related to ALICE
        Index targetIndex = INDEX_FIRST_PERSON;
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        FindTaskCommand command = new FindTaskCommand(targetIndex);
        expectedModel.updateFilteredTaskList(task -> task.getPatient().equals(ALICE));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredTaskList(), expectedModel.getFilteredTaskList());
    }

    @Test
    public void toStringMethod() {
        FindTaskCommand findTaskCommand = new FindTaskCommand(INDEX_FIRST_PERSON);
        String expected = "FindTaskCommand{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, findTaskCommand.toString());
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.findcommands.FindTaskCommand.NO_TASKS_FOUND;
import static seedu.address.testutil.TypicalTasks.TASK_COMPLETED;
import static seedu.address.testutil.TypicalTasks.TASK_PENDING;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindTaskCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

public class FindTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("iP");
        List<String> secondPredicateKeywordList = Arrays.asList("iP", "tP");

        TaskNameContainsKeywordsPredicate firstPredicate =
            new TaskNameContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskNameContainsKeywordsPredicate secondPredicate =
            new TaskNameContainsKeywordsPredicate(secondPredicateKeywordList);

        FindTaskCommand findFirstCommand = new FindTaskCommand(firstPredicate);
        FindTaskCommand findSecondCommand = new FindTaskCommand(secondPredicate);

        // same object --> true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values --> true
        FindTaskCommand findFirstCommandCopy = new FindTaskCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different type --> false
        assertFalse(findFirstCommand.equals(1));

        // null --> false
        assertFalse(findFirstCommand.equals(null));

        // different commands
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() {
        String expectedMessage = NO_TASKS_FOUND;
        TaskNameContainsKeywordsPredicate predicate = new TaskNameContainsKeywordsPredicate(Collections.emptyList());
        FindTaskCommand command = new FindTaskCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        TaskNameContainsKeywordsPredicate predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("iP", "tP"));
        FindTaskCommand command = new FindTaskCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TASK_COMPLETED, TASK_PENDING), model.getFilteredTaskList());
    }
}

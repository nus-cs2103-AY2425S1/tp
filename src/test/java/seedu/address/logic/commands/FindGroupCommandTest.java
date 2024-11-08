package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.findcommands.FindGroupCommand.NO_GROUPS_FOUND;
import static seedu.address.testutil.TypicalGroups.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindGroupCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.GroupNameContainsKeywordsPredicate;

//@@author gho7sie

/**
 * Contains integration tests (interaction with the Model) for {@code FindGroupCommand}.
 */
public class FindGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        GroupNameContainsKeywordsPredicate firstPredicate =
            new GroupNameContainsKeywordsPredicate(Collections.singletonList("first"));
        GroupNameContainsKeywordsPredicate secondPredicate =
            new GroupNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindGroupCommand findFirstCommand = new FindGroupCommand(firstPredicate);
        FindGroupCommand findSecondCommand = new FindGroupCommand(secondPredicate);

        // same object --> true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values --> true
        FindGroupCommand findFirstCommandCopy = new FindGroupCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different type --> false
        assertFalse(findFirstCommand.equals(3));

        // null --> false
        assertFalse(findFirstCommand.equals(null));

        // different predicate
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noGroupFound() {
        GroupNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, NO_GROUPS_FOUND, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGroupList());
    }


    /**
     * Parses {@code userInput} into a {@code GroupNameContainsKeywordsPredicate}.
     */
    private GroupNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new GroupNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

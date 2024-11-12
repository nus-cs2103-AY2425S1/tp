package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.GOONERS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.GroupContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        GroupContainsKeywordsPredicate firstGroupPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("firstGroup"));
        GroupContainsKeywordsPredicate secondGroupPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("secondGroup"));

        FindGroupCommand findFirstGroupCommand = new FindGroupCommand(firstGroupPredicate);
        FindGroupCommand findSecondGroupCommand = new FindGroupCommand(secondGroupPredicate);
        FindGroupCommand findFirstGroupCommandCopy = new FindGroupCommand(firstGroupPredicate);

        assertTrue(findFirstGroupCommand.equals(findFirstGroupCommand));

        assertTrue(findFirstGroupCommand.equals(findFirstGroupCommandCopy));

        assertFalse(findFirstGroupCommand.equals(findSecondGroupCommand));

        assertFalse(findFirstGroupCommand.equals("notACommand"));

        assertFalse(findFirstGroupCommand.equals(null));
    }

    @Test
    public void execute_zeroKeywords_noGroupFound() {
        String expectedMessage = String.format(Messages.getMessageGroupsListedOverview(0));
        GroupContainsKeywordsPredicate predicate = prepareGroupPredicate(" ");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGroupList());
    }
    @Test
    public void execute_findGroup() {
        model.addGroup(GOONERS);
        expectedModel.addGroup(GOONERS);

        GroupContainsKeywordsPredicate groupPredicate = prepareGroupPredicate("gooners");

        FindGroupCommand command = new FindGroupCommand(groupPredicate);

        model.updateFilteredGroupList(groupPredicate);

        expectedModel.updateFilteredGroupList(groupPredicate);

        int numOfGroup = expectedModel.getFilteredGroupList().size();
        // Calculate the expected message based on the number of persons in the filtered list
        String expectedMessage = String.format(
                Messages.getMessageGroupsListedOverview(numOfGroup));

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);

    }
    @Test
    public void equals_nonNullGroupPredicate_returnsCorrectComparison() {
        GroupContainsKeywordsPredicate firstGroupPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("keyword"));
        GroupContainsKeywordsPredicate secondGroupPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("differentKeyword"));

        FindGroupCommand commandWithFirstPredicate = new FindGroupCommand(firstGroupPredicate);
        FindGroupCommand commandWithSecondPredicate = new FindGroupCommand(secondGroupPredicate);

        assertTrue(commandWithFirstPredicate.equals(new FindGroupCommand(firstGroupPredicate)));

        assertFalse(commandWithFirstPredicate.equals(commandWithSecondPredicate));
    }

    @Test
    public void execute_nonNullGroupPredicate_updatesFilteredGroupList() {
        GroupContainsKeywordsPredicate predicate = prepareGroupPredicate("gooners");
        FindGroupCommand command = new FindGroupCommand(predicate);

        model.addGroup(GOONERS);
        expectedModel.addGroup(GOONERS);

        int numOfGroup = expectedModel.getFilteredGroupList().size();
        String expectedMessage = String.format(
                Messages.getMessageGroupsListedOverview(numOfGroup));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);

        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(expectedModel.getFilteredGroupList(), model.getFilteredGroupList());
    }

    @Test
    public void toStringMethod() {
        GroupContainsKeywordsPredicate predicate = new GroupContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindGroupCommand findGroupCommand = new FindGroupCommand(predicate);
        String expected = FindGroupCommand.class.getCanonicalName() + "{groupPredicate=" + predicate + "}";
        assertEquals(expected, findGroupCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code GroupContainsKeywordsPredicate}.
     */
    private GroupContainsKeywordsPredicate prepareGroupPredicate(String userInput) {
        return new GroupContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    @Test
    public void execute_emptyPredicate_noGroupFound() {
        GroupContainsKeywordsPredicate predicate = prepareGroupPredicate("");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        String expectedMessage = String.format(Messages.getMessageGroupsListedOverview(0));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGroupList());
    }

    @Test
    public void execute_validPredicate_success() {
        model.addGroup(GOONERS);
        expectedModel.addGroup(GOONERS);
        GroupContainsKeywordsPredicate predicate = prepareGroupPredicate("gooners");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        int numOfGroup = expectedModel.getFilteredGroupList().size();
        String expectedMessage = String.format(
                Messages.getMessageGroupsListedOverview(numOfGroup));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(expectedModel.getFilteredGroupList(), model.getFilteredGroupList());
    }
}

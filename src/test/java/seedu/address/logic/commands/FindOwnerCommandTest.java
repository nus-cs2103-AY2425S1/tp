package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_OWNERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalOwners;

/**
 * Contains integration tests (interaction with the Model) for {@code FindOwnerCommand}.
 */
public class FindOwnerCommandTest {
    private Model ownerModel = new ModelManager(TypicalOwners.getTypicalPawPatrol(), new UserPrefs());
    private Model expectedOwnerModel = new ModelManager(TypicalOwners.getTypicalPawPatrol(), new UserPrefs());

    @Test
    public void equals() {
        // Test for owners
        OwnerNameContainsKeywordsPredicate firstOwnerPredicate =
                new OwnerNameContainsKeywordsPredicate(Collections.singletonList("first"));
        OwnerNameContainsKeywordsPredicate secondOwnerPredicate =
                new OwnerNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindOwnerCommand findOwnerFirstCommand = new FindOwnerCommand(firstOwnerPredicate);
        FindOwnerCommand findOwnerSecondCommand = new FindOwnerCommand(secondOwnerPredicate);

        // same object -> returns true
        assertTrue(findOwnerFirstCommand.equals(findOwnerFirstCommand));

        // same values -> returns true
        FindOwnerCommand findOwnerFirstCommandCopy = new FindOwnerCommand(firstOwnerPredicate);
        assertTrue(findOwnerFirstCommand.equals(findOwnerFirstCommandCopy));

        // different types -> returns false
        assertFalse(findOwnerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findOwnerFirstCommand.equals(null));

        // different owners -> returns false
        assertFalse(findOwnerFirstCommand.equals(findOwnerSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noOwnerFound() {
        String expectedMessage = String.format(MESSAGE_OWNERS_LISTED_OVERVIEW, 0);
        OwnerNameContainsKeywordsPredicate predicate = prepareOwnerPredicate(" ");
        FindOwnerCommand command = new FindOwnerCommand(predicate);
        expectedOwnerModel.updateFilteredOwnerList(predicate);
        assertCommandSuccess(command, ownerModel, expectedMessage, expectedOwnerModel);
        assertEquals(Collections.emptyList(), ownerModel.getFilteredOwnerList());
    }

    @Test
    public void execute_multipleKeywords_multipleOwnersFound() {
        String expectedOwnerMessage = String.format(MESSAGE_OWNERS_LISTED_OVERVIEW, 3);
        OwnerNameContainsKeywordsPredicate ownerPredicate = prepareOwnerPredicate("Kurz Elle Kunz");
        FindOwnerCommand commandOwner = new FindOwnerCommand(ownerPredicate);
        expectedOwnerModel.updateFilteredOwnerList(ownerPredicate);
        assertCommandSuccess(commandOwner, ownerModel, expectedOwnerMessage, expectedOwnerModel);
        assertEquals(Arrays.asList(seedu.address.testutil.TypicalOwners.CARL,
                        seedu.address.testutil.TypicalOwners.ELLE,
                        seedu.address.testutil.TypicalOwners.FIONA),
                ownerModel.getFilteredOwnerList());
    }

    /**
     * Parses {@code userInput} into a {@code OwnerNameContainsKeywordsPredicate}.
     */
    private OwnerNameContainsKeywordsPredicate prepareOwnerPredicate(String userInput) {
        return new OwnerNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

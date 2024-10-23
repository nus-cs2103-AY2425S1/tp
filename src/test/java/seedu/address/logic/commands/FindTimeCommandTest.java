package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.preferredtime.PreferredTimeOverlapsRangesPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code FindTimeCommand}.
 */
public class FindTimeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PreferredTimeOverlapsRangesPredicate firstPredicate =
                new PreferredTimeOverlapsRangesPredicate(Collections.singletonList("1200-1400"));
        PreferredTimeOverlapsRangesPredicate secondPredicate =
                new PreferredTimeOverlapsRangesPredicate(Collections.singletonList("1800-2100"));

        FindTimeCommand findFirstCommand = new FindTimeCommand(firstPredicate);
        FindTimeCommand findSecondCommand = new FindTimeCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTimeCommand findFirstCommandCopy = new FindTimeCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PreferredTimeOverlapsRangesPredicate predicate = preparePredicate(" ");
        FindTimeCommand command = new FindTimeCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    // TODO: add execute_singleKeyword_multiplePersonsFound(or no need?)

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PreferredTimeOverlapsRangesPredicate predicate = preparePredicate("1200-1300 1900-2000");
        FindTimeCommand command = new FindTimeCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PreferredTimeOverlapsRangesPredicate predicate =
                new PreferredTimeOverlapsRangesPredicate(Arrays.asList("keyword"));
        FindTimeCommand findTimeCommand = new FindTimeCommand(predicate);
        String expected = FindTimeCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findTimeCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PreferredTimeOverlapsRangesPredicate}.
     */
    private PreferredTimeOverlapsRangesPredicate preparePredicate(String userInput) {
        return new PreferredTimeOverlapsRangesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

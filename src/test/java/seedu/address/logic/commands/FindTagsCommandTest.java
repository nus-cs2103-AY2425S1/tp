package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindTagsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    //Suppress for future milestone
    //    @Test
    //    public void execute_zeroKeywords_noPersonFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
    //        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredPersonList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    //    }
    //
    //    @Test
    //    public void execute_multipleKeywords_multiplePersonsFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
    //        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredPersonList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    //    }

    @Test
    public void toStringMethod() {
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindTagCommand findTagCommand = new FindTagCommand(predicate);
        String expected = FindTagCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findTagCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

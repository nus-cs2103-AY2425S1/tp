package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTelegramHandleCommand}.
 */
public class FindTelegramHandleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TelegramHandleContainsKeywordsPredicate firstPredicate =
            new TelegramHandleContainsKeywordsPredicate(Collections.singletonList("alice"));
        TelegramHandleContainsKeywordsPredicate secondPredicate =
            new TelegramHandleContainsKeywordsPredicate(Collections.singletonList("bob"));

        FindTelegramHandleCommand findFirstCommand = new FindTelegramHandleCommand(firstPredicate);
        FindTelegramHandleCommand findSecondCommand = new FindTelegramHandleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTelegramHandleCommand findFirstCommandCopy = new FindTelegramHandleCommand(firstPredicate);
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
        TelegramHandleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTelegramHandleCommand command = new FindTelegramHandleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code TelegramHandleContainsKeywordsPredicate}.
     */
    private TelegramHandleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TelegramHandleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

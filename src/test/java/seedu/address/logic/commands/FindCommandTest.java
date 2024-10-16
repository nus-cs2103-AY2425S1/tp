package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCompanies.APPLE;
import static seedu.address.testutil.TypicalCompanies.BYTEDANCE;
import static seedu.address.testutil.TypicalCompanies.GRAB;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCompanyFound() {
        // Modify the expected message to match the output of FindCommand
        String expectedMessage = "There is no company that suits your keyword!";

        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCompanyList(predicate);

        // Assert that the command executes successfully and returns the correct result
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that the filtered company list is empty as expected
        assertEquals(Collections.emptyList(), model.getFilteredCompanyList());
    }

    @Test
    public void execute_multipleKeywords_multipleCompaniesFound() {
        String expectedMessage = String.format("Found %d companies!", 3)
                + "\n" + Arrays.asList(GRAB, APPLE, BYTEDANCE);
        NameContainsKeywordsPredicate predicate = preparePredicate("Grab Apple Bytedance");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCompanyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GRAB, APPLE, BYTEDANCE), model.getFilteredCompanyList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);

        // Update this to reflect the correct toString format
        String expected = "FindCommand[predicate=" + predicate + "]";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        if (userInput.trim().isEmpty()) {
            return new NameContainsKeywordsPredicate(Collections.emptyList()); // Handle empty input
        }
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_VENDORS_FOUND;
import static seedu.address.logic.Messages.MESSAGE_VENDORS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalVendors.CARL;
import static seedu.address.testutil.TypicalVendors.ELLE;
import static seedu.address.testutil.TypicalVendors.FIONA;
import static seedu.address.testutil.TypicalVendors.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vendor.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindVendorCommand}.
 */
public class FindVendorCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindVendorCommand findFirstCommand = new FindVendorCommand(firstPredicate);
        FindVendorCommand findSecondCommand = new FindVendorCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindVendorCommand findFirstCommandCopy = new FindVendorCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different vendor -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noVendorFoundError() {
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindVendorCommand command = new FindVendorCommand(predicate);
        expectedModel.updateFilteredVendorList(predicate);
        assertCommandFailure(command, model, MESSAGE_NO_VENDORS_FOUND);
    }

    @Test
    public void execute_multipleKeywords_multipleVendorsFound() {
        String expectedMessage = String.format(MESSAGE_VENDORS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindVendorCommand command = new FindVendorCommand(predicate);
        expectedModel.updateFilteredVendorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredVendorList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindVendorCommand findCommand = new FindVendorCommand(predicate);
        String expected = FindVendorCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.Messages.MESSAGE_NO_VENDORS_FOUND;
import static seedu.eventtory.logic.Messages.MESSAGE_VENDORS_LISTED_OVERVIEW;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.testutil.TypicalVendors.CARL;
import static seedu.eventtory.testutil.TypicalVendors.ELLE;
import static seedu.eventtory.testutil.TypicalVendors.FIONA;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.vendor.VendorContainsKeywordsPredicate;
import seedu.eventtory.ui.UiState;

/**
 * Contains integration tests (interaction with the Model) for {@code FindVendorCommand}.
 */
public class FindVendorCommandTest {
    private Model model = new ModelManager(getTypicalEventTory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventTory(), new UserPrefs());

    @Test
    public void equals() {
        VendorContainsKeywordsPredicate firstPredicate =
                new VendorContainsKeywordsPredicate(Collections.singletonList("first"));
        VendorContainsKeywordsPredicate secondPredicate =
                new VendorContainsKeywordsPredicate(Collections.singletonList("second"));

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
        VendorContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindVendorCommand command = new FindVendorCommand(predicate);
        expectedModel.updateFilteredVendorList(predicate);
        assertCommandFailure(command, model, MESSAGE_NO_VENDORS_FOUND);
    }

    @Test
    public void execute_multipleKeywords_multipleVendorsFound() {
        String expectedMessage = String.format(MESSAGE_VENDORS_LISTED_OVERVIEW, 3);
        VendorContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindVendorCommand command = new FindVendorCommand(predicate);
        expectedModel.updateFilteredVendorList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredVendorList());
    }

    @Test
    public void toStringMethod() {
        VendorContainsKeywordsPredicate predicate = new VendorContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindVendorCommand findCommand = new FindVendorCommand(predicate);
        String expected = FindVendorCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void execute_wrongView_invalidViewError() {
        model.setUiState(UiState.EVENT_LIST);
        VendorContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindVendorCommand command = new FindVendorCommand(predicate);
        assertCommandFailure(command, model, FindVendorCommand.MESSAGE_FIND_VENDOR_FAILURE_INVALID_VIEW);
    }

    /**
     * Parses {@code userInput} into a {@code VendorContainsKeywordsPredicate}.
     */
    private VendorContainsKeywordsPredicate preparePredicate(String userInput) {
        return new VendorContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

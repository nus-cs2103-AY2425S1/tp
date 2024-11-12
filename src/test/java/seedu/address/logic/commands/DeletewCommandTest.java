package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWeddingAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WEDDING;
import static seedu.address.testutil.TypicalPersons.ALICE_WEDDING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookFilterWithWeddings;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.WeddingBuilder;

public class DeletewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookFilterWithWeddings(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Wedding weddingToDelete = model.getFilteredWeddingList().get(INDEX_FIRST_WEDDING.getZeroBased());

        DeletewCommand deletewCommand = new DeletewCommand(INDEX_FIRST_WEDDING, null);

        String expectedMessage = String.format(DeletewCommand.MESSAGE_DELETE_WEDDING_SUCCESS,
                Messages.format(weddingToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteWedding(weddingToDelete);

        assertCommandSuccess(deletewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletewCommand deletewCommand = new DeletewCommand(outOfBoundIndex, null);

        assertCommandFailure(deletewCommand, model, String.format(
                Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX, outOfBoundIndex.getOneBased(),
                model.getFilteredWeddingList().size()));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showWeddingAtIndex(model, INDEX_FIRST_WEDDING);

        Wedding weddingToDelete = model.getFilteredWeddingList().get(INDEX_FIRST_WEDDING.getZeroBased());

        DeletewCommand deletewCommand = new DeletewCommand(INDEX_FIRST_WEDDING, null);

        String expectedMessage = String.format(DeletewCommand.MESSAGE_DELETE_WEDDING_SUCCESS,
                Messages.format(weddingToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteWedding(weddingToDelete);
        // Make sure expectedModel has same filter state
        expectedModel.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);

        assertCommandSuccess(deletewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showWeddingAtIndex(model, INDEX_FIRST_WEDDING);

        Index outOfBoundIndex = INDEX_SECOND_WEDDING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletewCommand deletewCommand = new DeletewCommand(outOfBoundIndex, null);

        assertCommandFailure(deletewCommand, model, String.format(
                Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX, outOfBoundIndex.getOneBased(),
                model.getFilteredWeddingList().size()));
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        model.updateFilteredWeddingList(p -> false);

        DeletewCommand deletewCommand = new DeletewCommand(INDEX_FIRST_WEDDING, null);

        assertCommandFailure(deletewCommand, model, String.format(DeletewCommand.MESSAGE_DELETE_EMPTY_LIST_ERROR));
    }

    @Test
    public void execute_validKeyword_success() {
        // unique name
        NameMatchesWeddingPredicate predicate = preparePredicate("Alice");
        Wedding weddingToDelete = ALICE_WEDDING;
        DeletewCommand deletewCommand = new DeletewCommand(null, predicate);

        String expectedMessage = String.format(DeletewCommand.MESSAGE_DELETE_WEDDING_SUCCESS,
                Messages.format(weddingToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteWedding(weddingToDelete);
        // Make sure expectedModel has the same filter state
        expectedModel.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);

        assertCommandSuccess(deletewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validKeywordMultipleMatches_success() {
        // keyword matches with multiple weddings
        Wedding toAdd = new WeddingBuilder().build();
        model.addWedding(toAdd);
        NameMatchesWeddingPredicate predicate = preparePredicate("Alice");
        DeletewCommand deletewCommand = new DeletewCommand(null, predicate);

        String expectedMessage = String.format(DeletewCommand.MESSAGE_DUPLICATE_HANDLING);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredWeddingList(predicate);

        assertCommandSuccess(deletewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeyword_throwsCommandException() {
        // No wedding corresponding to the keyword
        NameMatchesWeddingPredicate predicate = preparePredicate("Alex");
        DeletewCommand deletewCommand = new DeletewCommand(null, predicate);

        Model actualModel = model;
        showNoWedding(actualModel);

        assertCommandFailure(deletewCommand, actualModel,
                String.format(DeletewCommand.MESSAGE_DELETE_EMPTY_LIST_ERROR));
    }

    @Test
    public void equals() {
        DeletewCommand deletewFirstCommand = new DeletewCommand(null, null);
        DeletewCommand deletewSecondCommand = new DeletewCommand(null, null);

        // same object -> returns true
        assertTrue(deletewFirstCommand.equals(deletewSecondCommand));

        deletewFirstCommand = new DeletewCommand(INDEX_FIRST_WEDDING, null);
        deletewSecondCommand = new DeletewCommand(INDEX_SECOND_WEDDING, null);

        // same object -> returns true
        assertTrue(deletewFirstCommand.equals(deletewFirstCommand));

        // same values -> returns true
        DeletewCommand deletewFirstCommandCopy = new DeletewCommand(INDEX_FIRST_WEDDING, null);
        assertTrue(deletewFirstCommand.equals(deletewFirstCommandCopy));

        // different types -> returns false
        assertFalse(deletewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deletewFirstCommand.equals(deletewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeletewCommand deletewCommand = new DeletewCommand(targetIndex, null);
        String expected = DeletewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deletewCommand.toString());

        NameMatchesWeddingPredicate predicate = preparePredicate(VALID_NAME_ALICEWEDDING);
        deletewCommand = new DeletewCommand(null, predicate);
        expected = DeletewCommand.class.getCanonicalName() + "{targetKeywords=" + predicate.toString() + "}";
        assertEquals(expected, deletewCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no weddings.
     */
    private void showNoWedding(Model model) {
        model.updateFilteredWeddingList(p -> false);

        assertTrue(model.getFilteredWeddingList().isEmpty());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameMatchesWeddingPredicate preparePredicate(String userInput) {
        return new NameMatchesWeddingPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

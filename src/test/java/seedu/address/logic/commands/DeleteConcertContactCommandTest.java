package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalConcertContacts.ALICE_COACHELLA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERTCONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONCERTCONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteConcertContactCommand}.
 */
public class DeleteConcertContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validParams_success() {
        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(
                INDEX_FIRST_CONCERTCONTACT);
        String expectedMessage = String.format(
                DeleteConcertContactCommand.MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS, Messages.format(
                        ALICE_COACHELLA));
        CommandResult expecteCommandResult = new CommandResult(expectedMessage, false, false, true);
        assertCommandSuccess(deleteConcertContactCommand, model, expecteCommandResult, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(
                outOfBoundIndex);

        assertCommandFailure(deleteConcertContactCommand, model,
                Messages.MESSAGE_INVALID_CONCERT_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteConcertContactCommand deleteConcertContactFirstCommand =
                new DeleteConcertContactCommand(INDEX_FIRST_CONCERTCONTACT);
        DeleteConcertContactCommand deleteConcertContactFirstSecondCommand =
                new DeleteConcertContactCommand(INDEX_SECOND_CONCERTCONTACT);

        // same object -> returns true
        assertTrue(deleteConcertContactFirstCommand.equals(deleteConcertContactFirstCommand));

        // same values -> returns true
        DeleteConcertContactCommand deleteConcertContactFirstCommandCopy =
                new DeleteConcertContactCommand(INDEX_FIRST_CONCERTCONTACT);
        assertTrue(deleteConcertContactFirstCommand.equals(deleteConcertContactFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteConcertContactFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteConcertContactFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteConcertContactFirstCommand.equals(
                deleteConcertContactFirstSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(
                index);
        String expected = DeleteConcertContactCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, deleteConcertContactCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered list to show no concerts.
     */
    private void showNoConcert(Model model) {
        model.updateFilteredConcertList(c -> false);

        assertTrue(model.getFilteredConcertList().isEmpty());
    }
}

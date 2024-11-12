package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showConcertContactAtIndex;
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
import seedu.address.model.concert.ConcertContact;

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
        CommandResult expecteCommandResult = new CommandResult(expectedMessage,
                false, false, true);
        assertCommandSuccess(deleteConcertContactCommand, model, expecteCommandResult, model);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ConcertContact concertContactToDelete = model.getFilteredConcertContactList().get(INDEX_FIRST_CONCERTCONTACT
                .getZeroBased());
        DeleteConcertContactCommand deleteCommand = new DeleteConcertContactCommand(INDEX_FIRST_CONCERTCONTACT);

        String expectedMessage = String.format(DeleteConcertContactCommand.MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS,
                Messages.format(concertContactToDelete));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                false, false, true);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteConcertContact(concertContactToDelete);

        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
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
    public void execute_validIndexFilteredList_success() {
        showConcertContactAtIndex(model, INDEX_FIRST_CONCERTCONTACT);

        ConcertContact concertContactToDelete = model.getFilteredConcertContactList().get(INDEX_FIRST_CONCERTCONTACT
                .getZeroBased());
        DeleteConcertContactCommand deleteCommand = new DeleteConcertContactCommand(INDEX_FIRST_CONCERTCONTACT);

        String expectedMessage = String.format(DeleteConcertContactCommand.MESSAGE_DELETE_CONCERT_CONTACT_SUCCESS,
                Messages.format(concertContactToDelete));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                false, false, true);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteConcertContact(concertContactToDelete);
        showNoConcertContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_throwsCommandException() {
        showConcertContactAtIndex(model, INDEX_FIRST_CONCERTCONTACT);

        Index outOfBoundIndex = INDEX_SECOND_CONCERTCONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getConcertContactList().size());

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
     * Updates {@code model}'s filtered list to show no concert contacts.
     */
    private void showNoConcertContact(Model model) {
        model.updateFilteredConcertContactList(c -> false);

        assertTrue(model.getFilteredConcertContactList().isEmpty());
    }
}

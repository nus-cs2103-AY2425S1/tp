package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showConcertAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

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
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(outOfBoundIndex,
                INDEX_FIRST_CONCERT);

        assertCommandFailure(deleteConcertContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidConcertIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConcertList().size() + 1);
        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(INDEX_FIRST_PERSON,
                outOfBoundIndex);

        assertCommandFailure(deleteConcertContactCommand, model, Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(outOfBoundIndex,
                INDEX_FIRST_CONCERT);

        assertCommandFailure(deleteConcertContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidConcertIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);

        Index outOfBoundIndex = INDEX_SECOND_CONCERT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getConcertList().size());

        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(INDEX_FIRST_PERSON,
                outOfBoundIndex);

        assertCommandFailure(deleteConcertContactCommand, model, Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteConcertContactCommand deleteConcertContactFirstCommand = new DeleteConcertContactCommand(
                INDEX_FIRST_PERSON, INDEX_FIRST_CONCERT);
        DeleteConcertContactCommand deleteConcertContactFirstSecondCommand = new DeleteConcertContactCommand(
                INDEX_SECOND_PERSON, INDEX_SECOND_CONCERT);

        // same object -> returns true
        assertTrue(deleteConcertContactFirstCommand.equals(deleteConcertContactFirstCommand));

        // same values -> returns true
        DeleteConcertContactCommand deleteConcertContactFirstCommandCopy = new DeleteConcertContactCommand(
                INDEX_FIRST_PERSON, INDEX_FIRST_CONCERT);
        assertTrue(deleteConcertContactFirstCommand.equals(deleteConcertContactFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteConcertContactFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteConcertContactFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteConcertContactFirstCommand.equals(deleteConcertContactFirstSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index indexP = Index.fromOneBased(1);
        Index indexC = Index.fromOneBased(2);
        DeleteConcertContactCommand deleteConcertContactCommand = new DeleteConcertContactCommand(indexP,
                indexC);
        String expected = DeleteConcertContactCommand.class.getCanonicalName() + "{indexP=" + indexP + ", indexC="
                + indexC + "}";
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

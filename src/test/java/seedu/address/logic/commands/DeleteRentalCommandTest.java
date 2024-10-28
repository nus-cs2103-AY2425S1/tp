package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandPromptsSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RENTAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RENTAL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteRentalCommand}.
 */
public class DeleteRentalCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client secondClient = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        RentalInformation rentalToDelete = secondClient.getRentalInformation().get(INDEX_FIRST_RENTAL.getZeroBased());
        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(INDEX_SECOND_PERSON, INDEX_FIRST_RENTAL);

        String expectedPrompt = String.format(DeleteRentalCommand.MESSAGE_PROMPT,
                Messages.formatRentalInformation(rentalToDelete));
        String expectedMessage = String.format(DeleteRentalCommand.MESSAGE_DELETE_RENTAL_SUCCESS,
                Messages.formatRentalInformation(rentalToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Client editedClient = new PersonBuilder(secondClient)
                .withRentalInformation(secondClient.getRentalInformation().get(INDEX_SECOND_RENTAL.getZeroBased()))
                .build();
        expectedModel.setPerson(secondClient, editedClient);

        assertCommandPromptsSuccess(deleteRentalCommand, model, expectedPrompt, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(outOfBoundsIndex, INDEX_FIRST_RENTAL);

        assertCommandFailure(deleteRentalCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRentalIndexUnfilteredList_throwsCommandException() {
        Client secondClient = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Index outOfBoundsIndex = Index.fromOneBased(secondClient.getRentalInformation().size() + 1);
        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(INDEX_SECOND_PERSON, outOfBoundsIndex);

        assertCommandFailure(deleteRentalCommand, model, Messages.MESSAGE_INVALID_RENTAL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Client firstClient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RentalInformation rentalToDelete = firstClient.getRentalInformation().get(INDEX_FIRST_RENTAL.getZeroBased());
        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL);

        String expectedPrompt = String.format(DeleteRentalCommand.MESSAGE_PROMPT,
                Messages.formatRentalInformation(rentalToDelete));
        String expectedMessage = String.format(DeleteRentalCommand.MESSAGE_DELETE_RENTAL_SUCCESS,
                Messages.formatRentalInformation(rentalToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Client editedClient = new PersonBuilder(firstClient).withRentalInformation().build();
        expectedModel.setPerson(firstClient, editedClient);

        assertCommandPromptsSuccess(deleteRentalCommand, model, expectedPrompt, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        // ensures that the first client has 1 rental information
        Client firstClient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(INDEX_FIRST_RENTAL.getOneBased() <= firstClient.getRentalInformation().size());

        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(outOfBoundIndex, INDEX_FIRST_RENTAL);

        assertCommandFailure(deleteRentalCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRentalIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Client firstClient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(firstClient.getRentalInformation().size() + 1);

        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(deleteRentalCommand, model, Messages.MESSAGE_INVALID_RENTAL_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRentalCommand deleteFromFirstClientCommand =
                new DeleteRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL);
        DeleteRentalCommand deleteFromSecondClientCommand =
                new DeleteRentalCommand(INDEX_SECOND_PERSON, INDEX_FIRST_RENTAL);
        DeleteRentalCommand deleteFirstCommand = new DeleteRentalCommand(INDEX_SECOND_PERSON, INDEX_FIRST_RENTAL);
        DeleteRentalCommand deleteSecondCommand = new DeleteRentalCommand(INDEX_SECOND_PERSON, INDEX_SECOND_RENTAL);

        // same object -> returns true
        assertTrue(deleteFromFirstClientCommand.equals(deleteFromFirstClientCommand));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRentalCommand deleteFromFirstClientCommandCopy =
                new DeleteRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL);
        DeleteRentalCommand deleteFirstCommandCopy =
                new DeleteRentalCommand(INDEX_SECOND_PERSON, INDEX_FIRST_RENTAL);
        assertTrue(deleteFromFirstClientCommand.equals(deleteFromFirstClientCommandCopy));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(deleteFromFirstClientCommand.equals(deleteFromSecondClientCommand));

        // different rental -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index clientIndex = Index.fromOneBased(1);
        Index rentalIndex = Index.fromOneBased(2);
        DeleteRentalCommand deleteRentalCommand = new DeleteRentalCommand(clientIndex, rentalIndex);
        String expected = DeleteRentalCommand.class.getCanonicalName()
                + "{clientIndex=" + clientIndex + ", "
                + "rentalIndex=" + rentalIndex + "}";
        assertEquals(expected, deleteRentalCommand.toString());
    }
}

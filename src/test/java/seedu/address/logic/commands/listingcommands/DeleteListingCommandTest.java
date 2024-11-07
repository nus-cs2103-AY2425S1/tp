package seedu.address.logic.commands.listingcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showListingAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.*;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.testutil.TypicalListings;

public class DeleteListingCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), TypicalListings.getTypicalListings());

    @Test
    public void execute_validListingUnfilteredList_success() {
        Listing listingToDelete = model.getFilteredListingList().get(INDEX_FIRST_LISTING.getZeroBased());
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(INDEX_FIRST_LISTING);

        String expectedMessage = String.format(DeleteListingCommand.MESSAGE_DELETE_LISTING_SUCCESS,
                Messages.format(listingToDelete));

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), model.getListings());
        expectedModel.deleteListing(listingToDelete);

        assertCommandSuccess(deleteListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listingIndexOutOfBoundsUnfilteredListingList_throwsCommandException() {
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(LISTING_INDEX_OUT_OF_BOUNDS);

        assertCommandFailure(deleteListingCommand, model, Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listingIndexOutOfBoundsFilteredListingList_throwsCommandException() {
        showListingAtIndex(model, INDEX_FIRST_LISTING);
        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX, () ->
                new DeleteListingCommand(INDEX_SECOND_LISTING).execute(model));
    }

    @Test
    public void execute_validListingFilteredList_success() {
        Listing listingToDelete = model.getFilteredListingList().get(INDEX_SECOND_LISTING.getZeroBased());
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(INDEX_SECOND_LISTING);

        String expectedMessage = String.format(DeleteListingCommand.MESSAGE_DELETE_LISTING_SUCCESS,
                Messages.format(listingToDelete));

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), model.getListings());
        expectedModel.deleteListing(listingToDelete);

        assertCommandSuccess(deleteListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteListingCommand deleteFirstCommand = new DeleteListingCommand(INDEX_FIRST_LISTING);
        DeleteListingCommand deleteSecondCommand = new DeleteListingCommand(INDEX_SECOND_LISTING);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteListingCommand deleteFirstCommandCopy = new DeleteListingCommand(INDEX_FIRST_LISTING);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

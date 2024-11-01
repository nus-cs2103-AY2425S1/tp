package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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

    private static final Name DO_NOT_EXIST_LISTING_NAME = new Name("DO NOT EXIST LISTING NAME");
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), TypicalListings.getTypicalListings());

    @Test
    public void execute_validListingUnfilteredList_success() {
        Listing listingToDelete = TypicalListings.PASIR_RIS;
        DeleteListingCommand deleteListingCommand =
                new DeleteListingCommand(TypicalListings.PASIR_RIS.getName());
        String expectedMessage = String.format(DeleteListingCommand.MESSAGE_DELETE_LISTING_SUCCESS,
                listingToDelete.getName());

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), model.getListings());
        expectedModel.deleteListing(listingToDelete);

        assertCommandSuccess(deleteListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidListingUnfilteredList_throwsCommandException() {
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(DO_NOT_EXIST_LISTING_NAME);

        assertCommandFailure(deleteListingCommand, model, DeleteListingCommand.MESSAGE_LISTING_NOT_FOUND);
    }

    @Test
    public void execute_validListingFilteredList_success() {
        Listing listingToDelete = TypicalListings.KENT_RIDGE;
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(listingToDelete.getName());

        String expectedMessage = String.format(DeleteListingCommand.MESSAGE_DELETE_LISTING_SUCCESS,
                listingToDelete.getName());

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), model.getListings());
        expectedModel.deleteListing(listingToDelete);

        assertCommandSuccess(deleteListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidListingFilteredList_throwsCommandException() {
        Listing listingToDelete = TypicalListings.BUONA_VISTA;
        Listings typicalListings = TypicalListings.getTypicalListings();
        typicalListings.removeListing(listingToDelete);
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), typicalListings);

        assertThrows(CommandException.class, DeleteListingCommand.MESSAGE_LISTING_NOT_FOUND, () ->
                new DeleteListingCommand(TypicalListings.BUONA_VISTA.getName()).execute(model));
    }

    @Test
    public void equals() {
        DeleteListingCommand deleteListingFirstCommand =
                new DeleteListingCommand(TypicalListings.HOUGANG.getName());
        DeleteListingCommand deleteListingSecondCommand =
                new DeleteListingCommand(TypicalListings.SENTOSA.getName());

        // same object -> returns true
        assertTrue(deleteListingFirstCommand.equals(deleteListingFirstCommand));

        // same values -> returns true
        DeleteListingCommand deleteListingFirstCommandCopy =
                new DeleteListingCommand(TypicalListings.HOUGANG.getName());
        assertTrue(deleteListingFirstCommand.equals(deleteListingFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteListingFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteListingFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteListingFirstCommand.equals(deleteListingSecondCommand));
    }
}

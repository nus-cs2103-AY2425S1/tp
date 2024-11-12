package seedu.address.logic.commands.clientcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteClientProfileCommand}.
 */
public class DeleteClientProfileCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void execute_validBuyerUnfilteredList_success() {

        Person personToDelete = DANIEL;
        DeleteClientProfileCommand deleteCommand =
                new DeleteClientProfileCommand(INDEX_FOURTH_PERSON, true);

        String expectedMessage = String.format(DeleteClientProfileCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalListings());
        expectedModel.getListings().getListingList().forEach(listing -> listing.removeBuyer(personToDelete));
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validSellerUnfilteredList_success() {

        Person personToDelete = ALICE;
        DeleteClientProfileCommand deleteCommand =
                new DeleteClientProfileCommand(INDEX_FIRST_PERSON, true);

        String expectedMessage = String.format(DeleteClientProfileCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalListings());
        List<Listing> listingsToDelete = expectedModel.getListings().getListingList().stream()
                .filter(listing -> listing.getSeller().equals(personToDelete))
                .toList();
        for (Listing listing : listingsToDelete) {
            expectedModel.deleteListing(listing);
        }
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonUnfilteredList_throwsCommandException() {
        DeleteClientProfileCommand deleteCommand =
                new DeleteClientProfileCommand(Index.fromZeroBased(model.getFilteredPersonList().size()));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // model without any listings
        Model modelWithoutListings = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
        showPersonAtIndex(modelWithoutListings, INDEX_FIRST_PERSON);

        Person personToDelete = modelWithoutListings.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index indexOfPersonToDelete = INDEX_FIRST_PERSON;

        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(indexOfPersonToDelete);

        String expectedMessage = String.format(DeleteClientProfileCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, modelWithoutListings, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteClientProfileCommand deleteFirstCommand = new DeleteClientProfileCommand(INDEX_FIRST_PERSON);
        DeleteClientProfileCommand deleteSecondCommand = new DeleteClientProfileCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientProfileCommand deleteFirstCommandCopy = new DeleteClientProfileCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteClientProfileCommand deleteCommand = new DeleteClientProfileCommand(INDEX_FIRST_PERSON);
        String expected =
                DeleteClientProfileCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}

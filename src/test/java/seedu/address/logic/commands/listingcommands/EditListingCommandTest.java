package seedu.address.logic.commands.listingcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showListingAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showListingWithName;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LISTING;
import static seedu.address.testutil.TypicalIndexes.LISTING_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.SENGKANG;
import static seedu.address.testutil.TypicalListings.SIMEI;
import static seedu.address.testutil.TypicalListings.TAMPINES;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.listingcommands.EditListingCommand.EditListingDescriptor;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditListingDescriptorBuilder;
import seedu.address.testutil.ListingBuilder;

public class EditListingCommandTest {
    private static final Listing FIRST_LISTING = PASIR_RIS;
    private static final Listing FIFTH_LISTING = SENGKANG;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Listing editedListing = new ListingBuilder(SIMEI).withBuyers(SENGKANG.getBuyers()
                .toArray(new Person[0])).build();

        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIFTH_LISTING, descriptor);

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));

        expectedModel.setListing(model.getListingByName(SENGKANG.getName()), editedListing);

        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Listing editedListing = new ListingBuilder(SENGKANG).withArea("50").withSeller(ALICE).build();
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIFTH_LISTING, descriptor);

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));
        expectedModel.setListing(model.getListingByName(SENGKANG.getName()), editedListing);
        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIFTH_LISTING,
                new EditListingDescriptor());
        Listing editedListing = SENGKANG;

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));

        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showListingWithName(model, PASIR_RIS.getName());
        Listing editedListing = new ListingBuilder(SIMEI).withBuyers(PASIR_RIS.getBuyers()
                .toArray(new Person[0])).build();
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIRST_LISTING, descriptor);

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));
        showListingWithName(expectedModel, PASIR_RIS.getName());

        expectedModel.setListing(PASIR_RIS, editedListing);
        expectedModel.updateFilteredListingList(listings -> true);
        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateListingName_failure() {
        Listing editedListing = PASIR_RIS;
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing)
                .withAddress(SENGKANG.getAddress()).build();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIFTH_LISTING, descriptor);

        assertCommandFailure(editListingCommand, model, EditListingCommand.MESSAGE_DUPLICATE_LISTING);
    }

    @Test
    public void execute_duplicateListingAddress_failure() {
        Listing editedListing = PASIR_RIS;
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing)
                .withName(SENGKANG.getName()).build();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIFTH_LISTING, descriptor);

        assertCommandFailure(editListingCommand, model, EditListingCommand.MESSAGE_DUPLICATE_LISTING);
    }

    @Test
    public void execute_listingIndexOutOfBoundsUnfilteredList_failure() {
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().build();

        EditListingCommand editListingCommand = new EditListingCommand(LISTING_INDEX_OUT_OF_BOUNDS, descriptor);
        assertCommandFailure(editListingCommand, model, Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listingIndexOutOfBoundsFilteredListingList_failure() {
        showListingAtIndex(model, INDEX_FIRST_LISTING);

        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().build();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_SECOND_LISTING, descriptor);

        assertCommandFailure(editListingCommand, model, Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditListingDescriptor editListingDescriptor = new EditListingDescriptorBuilder(PASIR_RIS)
                .withName(SIMEI.getName()).build();
        final EditListingCommand standardCommand = new EditListingCommand(INDEX_FIRST_LISTING, editListingDescriptor);

        // same values -> returns true
        EditListingDescriptor copyDescriptor = new EditListingDescriptorBuilder(PASIR_RIS)
                .withName(SIMEI.getName()).build();
        EditListingCommand commandWithSameValues = new EditListingCommand(INDEX_FIRST_LISTING, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        EditListingCommand differentName = new EditListingCommand(INDEX_SECOND_LISTING, editListingDescriptor);
        assertFalse(standardCommand.equals(differentName));

        // different descriptor -> returns false
        EditListingDescriptor otherDescriptor = new EditListingDescriptorBuilder(TAMPINES)
                .withName(SIMEI.getName()).build();
        EditListingCommand differentDescriptor = new EditListingCommand(INDEX_FIRST_LISTING, otherDescriptor);
        assertFalse(standardCommand.equals(differentDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditListingDescriptor editListingDescriptor = new EditListingDescriptor();
        EditListingCommand editListingCommand = new EditListingCommand(INDEX_FIRST_LISTING, editListingDescriptor);
        String expected = EditListingCommand.class.getCanonicalName() + "{listingIndex=" + INDEX_FIRST_LISTING
                + ", editListingDescriptor=" + editListingDescriptor + "}";
        assertEquals(expected, editListingCommand.toString());
    }
}

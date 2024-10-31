package seedu.address.logic.commands;

import net.bytebuddy.matcher.StringMatcher;
import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.logic.commands.EditListingCommand.EditListingDescriptor;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditListingDescriptorBuilder;
import seedu.address.testutil.ListingBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showListingWithName;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.SENGKANG;
import static seedu.address.testutil.TypicalListings.SIMEI;
import static seedu.address.testutil.TypicalListings.TAMPINES;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class EditListingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Listing editedListing = new ListingBuilder(SIMEI).withBuyers(SENGKANG.getBuyers()
                .toArray(new Person[0])).build();
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editListingCommand = new EditListingCommand(SENGKANG.getName(), descriptor);

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));
        expectedModel.setListing(model.getListingByName(SENGKANG.getName()), editedListing);

        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Listing editedListing = new ListingBuilder(SENGKANG).withArea(50).withSeller(ALICE).build();
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editListingCommand = new EditListingCommand(SENGKANG.getName(), descriptor);

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));
        expectedModel.setListing(model.getListingByName(SENGKANG.getName()), editedListing);

        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditListingCommand editListingCommand = new EditListingCommand(SENGKANG.getName(), new EditListingDescriptor());
        Listing editedListing = SENGKANG;

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS, Messages.format(editedListing));

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
        EditListingCommand editListingCommand = new EditListingCommand(PASIR_RIS.getName(), descriptor);

        String expectedMessage = String.format(EditListingCommand.MESSAGE_EDIT_LISTING_SUCCESS,
                Messages.format(editedListing));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Listings(model.getListings()));
        showListingWithName(expectedModel, PASIR_RIS.getName());
        expectedModel.setListing(PASIR_RIS, editedListing);

        assertCommandSuccess(editListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateListingFilteredList_failure() {
        Listing editedListing = PASIR_RIS;
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder(editedListing).build();
        EditListingCommand editListingCommand = new EditListingCommand(SENGKANG.getName(), descriptor);

        assertCommandFailure(editListingCommand, model, EditListingCommand.MESSAGE_DUPLICATE_LISTING);
    }

    @Test
    public void execute_invalidListingUnfilteredList_failure() {
        Listing notInListings = SIMEI;
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().build();

        EditListingCommand editListingCommand = new EditListingCommand(notInListings.getName(), descriptor);
        assertCommandFailure(editListingCommand, model, EditListingCommand.MESSAGE_INVALID_LISTING_NAME);
    }

    @Test
    public void execute_invalidListingFilteredList_failure() {
        showListingWithName(model, PASIR_RIS.getName());
        Listing notInList = TAMPINES;
        EditListingDescriptor descriptor = new EditListingDescriptorBuilder().build();
        EditListingCommand editListingCommand = new EditListingCommand(notInList.getName(), descriptor);

        assertCommandFailure(editListingCommand, model, EditListingCommand.MESSAGE_INVALID_LISTING_NAME);
    }

    @Test
    public void equals() {
        EditListingDescriptor editListingDescriptor = new EditListingDescriptorBuilder(PASIR_RIS).withName(SIMEI.getName()).build();
        final EditListingCommand standardCommand = new EditListingCommand(PASIR_RIS.getName(), editListingDescriptor);

        // same values -> returns true
        EditListingDescriptor copyDescriptor = new EditListingDescriptorBuilder(PASIR_RIS).withName(SIMEI.getName()).build();
        EditListingCommand commandWithSameValues = new EditListingCommand(PASIR_RIS.getName(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        EditListingCommand differentName = new EditListingCommand(TAMPINES.getName(), editListingDescriptor);
        assertFalse(standardCommand.equals(differentName));

        // different descriptor -> returns false
        EditListingDescriptor otherDescriptor = new EditListingDescriptorBuilder(TAMPINES).withName(SIMEI.getName()).build();
        EditListingCommand differentDescriptor = new EditListingCommand(PASIR_RIS.getName(), otherDescriptor);
        assertFalse(standardCommand.equals(differentDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditListingDescriptor editListingDescriptor = new EditListingDescriptor();
        EditListingCommand editListingCommand = new EditListingCommand(PASIR_RIS.getName(), editListingDescriptor);
        String expected = EditListingCommand.class.getCanonicalName() + "{listingName=" + PASIR_RIS.getName()
                + ", editListingDescriptor=" + editListingDescriptor + "}";
        assertEquals(expected, editListingCommand.toString());
    }
}

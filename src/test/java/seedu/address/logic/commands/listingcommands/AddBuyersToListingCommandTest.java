package seedu.address.logic.commands.listingcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showListingAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalIndexes.LISTING_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.PERSON_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.TAMPINES;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.ListingBuilder;

public class AddBuyersToListingCommandTest {
    private static final Listing VALID_LISTING = PASIR_RIS;
    private static final Name VALID_LISTING_NAME = PASIR_RIS.getName();
    private static final Index OTHER_LISTING_INDEX = INDEX_SECOND_LISTING;
    private static final Name OTHER_LISTING_NAME = TAMPINES.getName();
    private static final Name SELLER_NAME = ALICE.getName();
    private static final Set<Index> VALID_BUYER_INDEXES = Set.of(INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON);
    private static final Set<Index> OTHER_BUYER_INDEXES = Set.of(INDEX_SIXTH_PERSON, INDEX_SEVENTH_PERSON);
    private static final Person FOURTH_BUYER = DANIEL;
    private static final Person FIFTH_BUYER = ELLE;
    private static final Set<Index> SELLER_INDEX = Set.of(INDEX_FIRST_PERSON);
    private static final Set<Index> BUYER_INDEXES_OUT_OF_BOUNDS = Set.of(PERSON_INDEX_OUT_OF_BOUNDS);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBuyersToListingCommand(INDEX_FIRST_LISTING,
                null));
    }

    @Test
    public void constructor_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBuyersToListingCommand(null,
                VALID_BUYER_INDEXES));
    }

    @Test
    public void execute_buyerAddedToListingUnfilteredListingList_success() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);

        String expectedMessage = String.format(AddBuyersToListingCommand.MESSAGE_ADD_BUYERS_SUCCESS,
                Messages.format(VALID_LISTING));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());
        Listing editedListing = new ListingBuilder(VALID_LISTING).withBuyers(DANIEL, ELLE).build();

        expectedModel.setListing(VALID_LISTING, editedListing);

        assertCommandSuccess(addBuyersToListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_buyerAddedToListingFilteredListingList_success() {
        showListingAtIndex(model, INDEX_FIRST_LISTING);
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);

        String expectedMessage = String.format(AddBuyersToListingCommand.MESSAGE_ADD_BUYERS_SUCCESS,
                Messages.format(VALID_LISTING));

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), getTypicalListings());
        showListingAtIndex(expectedModel, INDEX_FIRST_LISTING);

        Listing editedListing = new ListingBuilder(VALID_LISTING).withBuyers(DANIEL, ELLE).build();

        expectedModel.setListing(VALID_LISTING, editedListing);
        assertCommandSuccess(addBuyersToListingCommand, model, expectedMessage,expectedModel);
    }

    @Test
    public void execute_buyerIndexOutOfBoundsUnfilteredPersonList_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, BUYER_INDEXES_OUT_OF_BOUNDS);
        assertCommandFailure(addBuyersToListingCommand, model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_buyerIndexOutOfBoundsFilteredPersonList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);
        assertCommandFailure(addBuyersToListingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listingIndexOutOfBoundsUnfilteredListingList_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(LISTING_INDEX_OUT_OF_BOUNDS, VALID_BUYER_INDEXES);
        assertCommandFailure(addBuyersToListingCommand, model, Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listingIndexOutOfBoundsFilteredListingList_throwsCommandException() {
        showListingAtIndex(model, INDEX_SECOND_LISTING);
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_THIRD_LISTING, VALID_BUYER_INDEXES);
        assertCommandFailure(addBuyersToListingCommand, model, Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_inputSeller_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, SELLER_INDEX);
        assertCommandFailure(addBuyersToListingCommand, model,
                String.format(AddBuyersToListingCommand.MESSAGE_PERSON_NOT_BUYER,
                        INDEX_FIRST_PERSON.getOneBased(), SELLER_NAME));
    }

    @Test
    public void equals() {

        AddBuyersToListingCommand validAddBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);

        AddBuyersToListingCommand otherAddBuyersToListingCommand =
                new AddBuyersToListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);

        // same object -> returns true
        assertTrue(validAddBuyersToListingCommand.equals(validAddBuyersToListingCommand));

        // same values -> returns true
        assertTrue(validAddBuyersToListingCommand.equals(otherAddBuyersToListingCommand));

        // different types -> returns false
        assertFalse(validAddBuyersToListingCommand.equals(1));

        // null -> returns false
        assertFalse(validAddBuyersToListingCommand.equals(null));

        // different person -> returns false
        otherAddBuyersToListingCommand = new AddBuyersToListingCommand(INDEX_FIRST_LISTING, OTHER_BUYER_INDEXES);
        assertFalse(validAddBuyersToListingCommand.equals(otherAddBuyersToListingCommand));

        // different listingIndex -> returns false
        otherAddBuyersToListingCommand = new AddBuyersToListingCommand(OTHER_LISTING_INDEX, VALID_BUYER_INDEXES);
        assertFalse(validAddBuyersToListingCommand.equals(otherAddBuyersToListingCommand));
    }
}

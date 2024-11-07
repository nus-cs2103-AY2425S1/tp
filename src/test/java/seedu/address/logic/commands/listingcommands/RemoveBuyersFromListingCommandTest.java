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
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalIndexes.LISTING_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalIndexes.PERSON_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.testutil.ListingBuilder;

public class RemoveBuyersFromListingCommandTest {
    private static final Set<Index> VALID_BUYER_INDEXES = Set.of(INDEX_FOURTH_PERSON);
    private static final Set<Index> OTHER_BUYER_INDEXES = Set.of(INDEX_SIXTH_LISTING);
    private static final Name FOURTH_BUYER_NAME = DANIEL.getName();
    private static final Set<Index> BUYER_INDEXES_OUT_OF_BOUNDS = Set.of(PERSON_INDEX_OUT_OF_BOUNDS);
    private static final Set<Index> SELLER_INDEX = Set.of(INDEX_FIRST_LISTING);
    private static final Name SELLER_NAME = ALICE.getName();
    private static final Set<Index> EMPTY_SET = new HashSet<>();
    private static final Set<Index> NOT_BUYER_OF_LISTING = Set.of(INDEX_FIFTH_PERSON);
    private static final Listing VALID_LISTING = PASIR_RIS;
    private static final Name VALID_LISTING_NAME = PASIR_RIS.getName();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void constructor_nullListingName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveBuyersFromListingCommand(null,
                VALID_BUYER_INDEXES));
    }

    @Test
    public void constructor_nullBuyersToRemove_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING,
                null));
    }

    @Test
    public void execute_validValues_success() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);
        String expectedMessage = String.format(RemoveBuyersFromListingCommand.MESSAGE_REMOVE_BUYERS_SUCCESS,
                VALID_LISTING_NAME, FOURTH_BUYER_NAME);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());
        Listing editedListing = new ListingBuilder(VALID_LISTING).withBuyers(GEORGE).build();
        expectedModel.setListing(VALID_LISTING, editedListing);
        assertCommandSuccess(removeBuyersFromListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_buyerIndexOutOfBoundsUnfilteredPersonList_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, BUYER_INDEXES_OUT_OF_BOUNDS);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_buyerIndexOutOfBoundsFilteredPersonList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);
        assertCommandFailure(removeBuyersFromListingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listingIndexOutOfBoundsUnfilteredListingList_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(LISTING_INDEX_OUT_OF_BOUNDS, VALID_BUYER_INDEXES);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listingIndexOutOfBoundsFilteredListingList_throwsCommandException() {
        showListingAtIndex(model, INDEX_SECOND_LISTING);
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(LISTING_INDEX_OUT_OF_BOUNDS, VALID_BUYER_INDEXES);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_inputSeller_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, SELLER_INDEX);
        System.out.println(INDEX_FIRST_LISTING.getOneBased());
        System.out.println(SELLER_NAME);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                String.format(RemoveBuyersFromListingCommand.MESSAGE_PERSON_NOT_BUYER, INDEX_FIRST_LISTING.getOneBased(), SELLER_NAME));
    }

    @Test
    public void execute_notBuyerOfListing_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, NOT_BUYER_OF_LISTING);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                String.format(RemoveBuyersFromListingCommand.MESSAGE_NOT_BUYER_FOR_LISTING,
                        ELLE.getName(), Messages.format(VALID_LISTING)));
    }

    @Test
    public void execute_emptySet_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, EMPTY_SET);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                RemoveBuyersFromListingCommand.MESSAGE_EMPTY_SET);
    }

    @Test
    public void equals() {
        RemoveBuyersFromListingCommand validRemoveBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);

        RemoveBuyersFromListingCommand otherRemoveBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING, VALID_BUYER_INDEXES);

        // same object -> returns true
        assertTrue(validRemoveBuyersFromListingCommand.equals(validRemoveBuyersFromListingCommand));

        // same values -> returns true
        assertTrue(validRemoveBuyersFromListingCommand.equals(otherRemoveBuyersFromListingCommand));

        // null -> returns false
        assertFalse(validRemoveBuyersFromListingCommand.equals(null));

        // different type -> returns false
        assertFalse(validRemoveBuyersFromListingCommand.equals(4));

        // different Listing -> returns false
        otherRemoveBuyersFromListingCommand = new RemoveBuyersFromListingCommand(INDEX_SECOND_LISTING,
                VALID_BUYER_INDEXES);
        assertFalse(validRemoveBuyersFromListingCommand.equals(otherRemoveBuyersFromListingCommand));

        // different Buyer -> returns false
        otherRemoveBuyersFromListingCommand = new RemoveBuyersFromListingCommand(INDEX_FIRST_LISTING,
                OTHER_BUYER_INDEXES);
        assertFalse(validRemoveBuyersFromListingCommand.equals(otherRemoveBuyersFromListingCommand));
    }
}

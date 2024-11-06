package seedu.address.logic.commands.listingcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.SIMEI;
import static seedu.address.testutil.TypicalListings.TAMPINES;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.testutil.ListingBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddBuyersToListingCommandTest {

    private static final Listing VALID_LISTING = PASIR_RIS;
    private static final Name VALID_LISTING_NAME = VALID_LISTING.getName();
    private static final Name OTHER_LISTING_NAME = TAMPINES.getName();
    private static final Name INVALID_LISTING_NAME = SIMEI.getName();
    private static final Name SELLER_NAME = ALICE.getName();
    private static final Set<Name> SELLER = Set.of(SELLER_NAME);
    private static final Name VALID_BUYER_NAME = ELLE.getName();
    private static final Set<Name> EXISTING_BUYERS = Set.of(GEORGE.getName());
    private static final Set<Name> VALID_BUYER_NAMES = Set.of(VALID_BUYER_NAME);
    private static final Name INVALID_BUYER_NAME = HOON.getName();
    private static final Set<Name> INVALID_BUYERS = Set.of(INVALID_BUYER_NAME);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBuyersToListingCommand(VALID_LISTING_NAME,
                null));
    }

    @Test
    public void constructor_nullListing_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBuyersToListingCommand(null,
                VALID_BUYER_NAMES));
    }

    @Test
    public void execute_buyerAddedToListing_success() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, VALID_BUYER_NAMES);
        String expectedMessage = String.format(AddBuyersToListingCommand.MESSAGE_ADD_BUYERS_SUCCESS,
                VALID_LISTING_NAME);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());
        Listing editedListing = new ListingBuilder(VALID_LISTING).withBuyers(ELLE, GEORGE, DANIEL).build();
        expectedModel.setListing(VALID_LISTING, editedListing);
        assertCommandSuccess(addBuyersToListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_buyerNotInClientList_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, INVALID_BUYERS);
        assertCommandFailure(addBuyersToListingCommand, model,
                String.format(AddBuyersToListingCommand.MESSAGE_BUYER_NOT_FOUND, INVALID_BUYER_NAME));
    }

    @Test
    public void execute_listingNotInListingList_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INVALID_LISTING_NAME, VALID_BUYER_NAMES);
        assertCommandFailure(addBuyersToListingCommand, model, AddBuyersToListingCommand.MESSAGE_LISTING_NOT_FOUND);
    }

    @Test
    public void execute_existingBuyerOfListing_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, EXISTING_BUYERS);
        assertCommandFailure(addBuyersToListingCommand, model, AddBuyersToListingCommand.MESSAGE_DUPLICATE_BUYERS);
    }

    @Test
    public void execute_inputSeller_throwsCommandException() {
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, SELLER);
        assertCommandFailure(addBuyersToListingCommand, model,
                String.format(AddBuyersToListingCommand.MESSAGE_PERSON_NOT_BUYER, SELLER_NAME));
    }

    @Test
    public void equals() {
        Buyer alice = new PersonBuilder().withName("Alice").buildBuyer();
        Buyer bob = new PersonBuilder().withName("Bob").buildBuyer();
        Set<Name> setBuyer1 = Set.of(alice.getName());
        Set<Name> setBuyer2 = Set.of(bob.getName());
        AddBuyersToListingCommand addAliceToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, setBuyer1);
        AddBuyersToListingCommand addBobToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, setBuyer2);

        // same object -> returns true
        assertTrue(addAliceToListingCommand.equals(addAliceToListingCommand));

        // same values -> returns true
        AddBuyersToListingCommand addAliceToListingCommandCopy =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, setBuyer1);
        assertTrue(addAliceToListingCommand.equals(addAliceToListingCommandCopy));

        // different types -> returns false
        assertFalse(addAliceToListingCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceToListingCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceToListingCommand.equals(addBobToListingCommand));

        // different listingName -> returns false
        AddBuyersToListingCommand addToDifferentListing =
                new AddBuyersToListingCommand(OTHER_LISTING_NAME, setBuyer1);
        assertFalse(addAliceToListingCommand.equals(addToDifferentListing));
    }
}

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
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.testutil.ListingBuilder;

public class RemoveBuyersFromListingCommandTest {

    private static final Set<Name> VALID_BUYERS = Set.of(DANIEL.getName());
    private static final Set<Name> INVALID_BUYERS = Set.of(AMY.getName());
    private static final Name SELLER_NAME = ALICE.getName();
    private static final Set<Name> SELLER = Set.of(SELLER_NAME);
    private static final Set<Name> EMPTY_SET = new HashSet<>();
    private static final Set<Name> NOT_BUYER_OF_LISTING = Set.of(ELLE.getName());
    private static final Listing VALID_LISTING = PASIR_RIS;
    private static final Name VALID_LISTING_NAME = PASIR_RIS.getName();
    private static final Name INVALID_LISTING_NAME = SIMEI.getName();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());

    @Test
    public void constructor_nullListingName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveBuyersFromListingCommand(null,
                VALID_BUYERS));
    }

    @Test
    public void constructor_nullBuyersToRemove_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveBuyersFromListingCommand(VALID_LISTING_NAME,
                null));
    }

    @Test
    public void execute_validValues_success() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(VALID_LISTING_NAME, VALID_BUYERS);
        String expectedMessage = String.format(RemoveBuyersFromListingCommand.MESSAGE_REMOVE_BUYERS_SUCCESS,
                VALID_LISTING_NAME);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());
        Listing editedListing = new ListingBuilder(VALID_LISTING).withBuyers(GEORGE).build();
        expectedModel.setListing(VALID_LISTING, editedListing);
        assertCommandSuccess(removeBuyersFromListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_buyerNotInClientList_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(VALID_LISTING_NAME, INVALID_BUYERS);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                String.format(RemoveBuyersFromListingCommand.MESSAGE_BUYER_NOT_FOUND, AMY.getName()));
    }

    @Test
    public void execute_listingNotInListingList_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(INVALID_LISTING_NAME, VALID_BUYERS);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                RemoveBuyersFromListingCommand.MESSAGE_LISTING_NOT_FOUND);
    }

    @Test
    public void execute_inputSeller_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(VALID_LISTING_NAME, SELLER);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                String.format(RemoveBuyersFromListingCommand.MESSAGE_PERSON_NOT_BUYER, SELLER_NAME));
    }

    @Test
    public void execute_notBuyerOfListing_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(VALID_LISTING_NAME, NOT_BUYER_OF_LISTING);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                String.format(RemoveBuyersFromListingCommand.MESSAGE_NOT_BUYER_FOR_LISTING,
                        ELLE.getName(), VALID_LISTING_NAME));
    }

    @Test
    public void execute_emptySet_throwsCommandException() {
        RemoveBuyersFromListingCommand removeBuyersFromListingCommand =
                new RemoveBuyersFromListingCommand(VALID_LISTING_NAME, EMPTY_SET);
        assertCommandFailure(removeBuyersFromListingCommand, model,
                RemoveBuyersFromListingCommand.MESSAGE_EMPTY_SET);
    }

    @Test
    public void equals() {
        RemoveBuyersFromListingCommand removeBuyersFromPasirRis =
                new RemoveBuyersFromListingCommand(PASIR_RIS.getName(), Set.of(DANIEL.getName()));

        RemoveBuyersFromListingCommand removeBuyersFromTampines =
                new RemoveBuyersFromListingCommand(TAMPINES.getName(), Set.of(DANIEL.getName()));

        // same object -> returns true
        assertTrue(removeBuyersFromPasirRis.equals(removeBuyersFromPasirRis));

        // same values -> returns true
        RemoveBuyersFromListingCommand copy = new RemoveBuyersFromListingCommand(PASIR_RIS.getName(),
                Set.of(DANIEL.getName()));

        assertTrue(removeBuyersFromPasirRis.equals(copy));

        // null -> returns false
        assertFalse(removeBuyersFromPasirRis.equals(null));

        // different type -> returns false
        assertFalse(removeBuyersFromPasirRis.equals(4));

        // different Listing -> returns false
        assertFalse(removeBuyersFromPasirRis.equals(removeBuyersFromTampines));

        // different Buyer -> returns false
        RemoveBuyersFromListingCommand differentBuyer = new RemoveBuyersFromListingCommand(PASIR_RIS.getName(),
                Set.of(GEORGE.getName()));
        assertFalse(removeBuyersFromPasirRis.equals(differentBuyer));
    }
}

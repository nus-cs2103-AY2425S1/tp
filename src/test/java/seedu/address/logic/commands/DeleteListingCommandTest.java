package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.testutil.PersonBuilder;

public class DeleteListingCommandTest {

    // use TypicalListings
    private static final Listing WOODLANDS_LISTING = new Listing(
            new Name("Woodlands"),
            new Address("Woodlands"),
            new Price("600k", new BigDecimal(600000)),
            new Area(100),
            Region.NORTH,
            new PersonBuilder().withName("Jack").buildSeller(),
            Set.of(new PersonBuilder().withName("Jimmy").buildBuyer())
    );
    private static final Listing SENGKANG_LISTING = new Listing(
            new Name("Sengkang"),
            new Address("Sengkang"),
            new Price("300", new BigDecimal(300000)),
            new Area(99),
            Region.EAST,
            new PersonBuilder().withName("Tom").buildSeller(),
            Set.of(new PersonBuilder().withName("Alex").buildBuyer())
    );
    private static final Listing CLEMENTI_LISTING = new Listing(
            new Name("Clementi"),
            new Address("Clementi"),
            new Price("500k", new BigDecimal(500000)),
            new Area(98),
            Region.SOUTHWEST,
            new PersonBuilder().withName("Alice").buildSeller(),
            Set.of(new PersonBuilder().withName("Veronica").buildBuyer())
    );
    private static final Listings typicalListings;
    static {
        typicalListings = new Listings();
        typicalListings.addListing(WOODLANDS_LISTING);
        typicalListings.addListing(SENGKANG_LISTING);
        typicalListings.addListing(CLEMENTI_LISTING);
    }

    private static final Name DO_NOT_EXIST_LISTING_NAME = new Name("DO NOT EXIST LISTING NAME");
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), typicalListings);

    @Test
    public void execute_validListingUnfilteredList_success() {
        Listing listingToDelete = WOODLANDS_LISTING;
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(WOODLANDS_LISTING.getName());

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
        Listing listingToDelete = SENGKANG_LISTING;
        DeleteListingCommand deleteListingCommand = new DeleteListingCommand(listingToDelete.getName());

        String expectedMessage = String.format(DeleteListingCommand.MESSAGE_DELETE_LISTING_SUCCESS,
                listingToDelete.getName());

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), model.getListings());
        expectedModel.deleteListing(listingToDelete);

        assertCommandSuccess(deleteListingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidListingFilteredList_throwsCommandException() {
        Listing listingToDelete = CLEMENTI_LISTING;
        Listings typicalListings = DeleteListingCommandTest.typicalListings;
        typicalListings.removeListing(listingToDelete);
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), typicalListings);

        assertThrows(CommandException.class, DeleteListingCommand.MESSAGE_LISTING_NOT_FOUND, () ->
                new DeleteListingCommand(CLEMENTI_LISTING.getName()).execute(model));
    }

    @Test
    public void equals() {
        DeleteListingCommand deleteListingFirstCommand = new DeleteListingCommand(WOODLANDS_LISTING.getName());
        DeleteListingCommand deleteListingSecondCommand = new DeleteListingCommand(SENGKANG_LISTING.getName());

        // same object -> returns true
        assertTrue(deleteListingFirstCommand.equals(deleteListingFirstCommand));

        // same values -> returns true
        DeleteListingCommand deleteListingFirstCommandCopy = new DeleteListingCommand(WOODLANDS_LISTING.getName());
        assertTrue(deleteListingFirstCommand.equals(deleteListingFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteListingFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteListingFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteListingFirstCommand.equals(deleteListingSecondCommand));
    }
}

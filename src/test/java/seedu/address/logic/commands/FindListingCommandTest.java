package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_LISTINGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.ListingContainsKeywordsPredicate;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.testutil.PersonBuilder;

public class FindListingCommandTest {

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

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), typicalListings);
    private Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), typicalListings);

    @Test
    public void equals() {
        ListingContainsKeywordsPredicate firstPredicate =
                new ListingContainsKeywordsPredicate(Collections.singletonList("Woodlands"));
        ListingContainsKeywordsPredicate secondPredicate =
                new ListingContainsKeywordsPredicate(Collections.singletonList("Sengkang"));

        FindListingCommand findListingFirstCommand = new FindListingCommand(firstPredicate);
        FindListingCommand findListingSecondCommand = new FindListingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findListingFirstCommand.equals(findListingFirstCommand));

        // same values -> returns true
        FindListingCommand findListingFirstCommandCopy = new FindListingCommand(firstPredicate);
        assertTrue(findListingFirstCommand.equals(findListingFirstCommandCopy));

        // different types -> returns false
        assertFalse(findListingFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findListingFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findListingFirstCommand.equals(findListingSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noListingFound() {
        String expectedMessage = String.format(MESSAGE_LISTINGS_LISTED_OVERVIEW, 0);
        ListingContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindListingCommand command = new FindListingCommand(predicate);
        expectedModel.updateFilteredListingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredListingList());
    }

    @Test
    public void execute_multipleKeywords_multipleListingsFound() {
        String expectedMessage = String.format(MESSAGE_LISTINGS_LISTED_OVERVIEW, 3);
        ListingContainsKeywordsPredicate predicate =
                preparePredicate("Woodlands Sengkang Clementi");
        FindListingCommand command = new FindListingCommand(predicate);
        expectedModel.updateFilteredListingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(WOODLANDS_LISTING, SENGKANG_LISTING, CLEMENTI_LISTING),
                model.getFilteredListingList());
    }

    @Test
    public void toStringMethod() {
        ListingContainsKeywordsPredicate predicate = new ListingContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindListingCommand findListingCommand = new FindListingCommand(predicate);
        String expected = FindListingCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findListingCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ListingContainsKeywordsPredicate}.
     */
    private ListingContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ListingContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
